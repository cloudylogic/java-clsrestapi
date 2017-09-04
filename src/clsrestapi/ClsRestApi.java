package clsrestapi;

/*
 * Copyright 2017 Ken Lowrie <ken@klowrie.net>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;

class ApiWrapper<T extends Base> {
    protected boolean loaded;
    protected boolean fromCache;
    protected boolean fromRemote;
    protected String apiName;
    protected String cacheName;
    private final Cache cache;
    private final Versions currentVersions;
    public T api;
    
    public ApiWrapper(T api, Cache cache, String apiName, Versions currentVersions){
        this.loaded = false;
        this.fromCache = false;
        this.fromRemote = false;
        this.api = api;
        this.cache = cache;
        this.apiName = apiName;
        this.cacheName = cache.absolutePath(apiName);
        this.currentVersions = currentVersions;
        try {
            T craObj = init();
            
            //TODO: I don't think this check is needed, since I already
            //verified it in the init() API. Should I remove this one?
            api = craObj.getClass().isInstance(api) ? craObj : null;
        } catch(CRAException E){
            api = null;
        }
        loaded = api != null;
    }
    
    public void logMsg(String msg){
        System.out.println(msg);
    }
    
    private void setFromCache(){
        fromCache = true;
        fromRemote = false;
    }
    
    private void setFromRemote(){
        fromCache = false;
        fromRemote = true;
    }
    
    public boolean loadedFromCache(){
        return fromCache;
    }
    
    public boolean loadedFromNetwork(){
        return fromRemote;
    }

    /**
     * Load the specified CLS Rest Object from the server, and then Serialize it
     * to the cache for the next time around.
     * @return Returns the loaded object, regardless of whether serialization worked.
     * Returns null if the load() method failed...
     * @throws CRAException 
     */
    private T loadAndSerialize() throws CRAException {
        T tempObject;
        
        try {
            /*
            Make the network call to reload this API object from the server.
            */
            Object tmpObj = api.load();
            /*
            Okay, T refers to a class that is derived from Base, so if we try to
            cast tmpObj to T, we'll get a warning that it's unsafe. So what we need
            to do here is make sure that tmpObj is actually an instance of api, which
            is of type T, and if so, then we'll suppress the warning and do the cast.
            */
            if( tmpObj.getClass().isInstance(api)){
                @SuppressWarnings("unchecked")
                T tmp1 = (T) tmpObj;
                tempObject = tmp1;
            } else {
                tempObject = null;
            }
            /*
            it'll be null if an error occurred when we tried to make the server call.
            */
            if(tempObject != null){
                /*
                Set the fromCache and fromRemote flags to indicate where this object came from
                */
                setFromRemote();
                try{
                    /*
                    Serialize the newly updated object to the cache. Right now, I'm not really
                    caring if this fails, but I would like to address that at some point. TODO:
                    */
                    tempObject.serialize(cacheName);
                } catch (IllegalArgumentException | SecurityException E){
                    // TODO do we need some way to detect this failure...
                    logMsg("API [" + apiName + "] failed serialization: " + E.getMessage());
                }
            }
        } catch (IllegalArgumentException | SecurityException E) {
            logMsg("Exception: " + E.toString() + " msg: " + E.getMessage());
            tempObject = null;  // Make sure that we signal the load failure to the caller.
        }
        return tempObject;
    }

    
    /**
     * Construct a CLS REST API object from cache, if available, otherwise by making
     * the network API call. If it's created again, then cache it, for next time. Before
     * just taking the cached version, check to see if the latest version reported by
     * the server is newer or has newer data, and if so, invalidate the cache, and reload it.
     * @return A loaded CLS REST API of specified class 'craClass'.
     * @throws CRAException 
     */
    private T init() throws CRAException {
        T tempObject;
        
        if( cache.itemExists(apiName)){
            /*
            The API object is currently in cache, go load it from there.
            */
            logMsg("API [" + apiName + "] is in the cache: " + cacheName);
            
            try{
                Object tmpObj = api.deSerialize(cacheName);
                /*
                Okay, T refers to a class that is derived from Base, so if we try to
                cast tmpObj to T, we'll get a warning that it's unsafe. So what we need
                to do here is make sure that tmpObj is actually an instance of api, which
                is of type T, and if so, then we'll suppress the warning and do the cast.
                */
                if( tmpObj.getClass().isInstance(api)){
                    @SuppressWarnings("unchecked")
                    T tmp1 = (T) tmpObj;
                    tempObject = tmp1;
                    /*
                    Set the fromCache and fromRemote flags to indicate where this object came from
                    */
                    setFromCache();
                } else {
                    tempObject = null;
                }
            } catch(IllegalArgumentException | SecurityException E){
                logMsg("Exception: " + E.toString() + " msg: " + E.getMessage());
                tempObject = null; // make sure the object is null...
            }
            
            if( tempObject == null){
                /*
                If we can't load from cache, then load it from the server. This is most often
                caused by changes in the underlying classes (methods and/or instance data changing),
                so that deSerialize is unable to load a saved object from disk. In that case, we
                just want to make a fresh new object and then update the cache for next time.
                */
                logMsg("API [" + apiName + "] failed to load from cache. Loading from remote.");
                tempObject = loadAndSerialize();
            } else if ( currentVersions.apiObj != null){
                /*
                The previous check (currentVersions.apiObj) will be null if the server was down when
                the standalone Versions() class was instantiated at the start. This isn't really
                fatal, expecially if all the API objects are currently cached, but we don't want
                to try and access the apiObj if there's nothing there!
                */
                logMsg("Check if current version of [" + apiName + "] is out of date and reload...");
                /*
                Okay, let's grab the current ApiVer from the object that was loaded from cache, and
                see if it's the same as the "latest" version that the server has.
                */
                
                try {
                    /*
                    Make sure that cachedApiVer is a proper ApiVer object. If it isn't for some
                    strange reason, no big deal, we just can't determine if a reload is required.
                    However, if this does happen every time, something is wrong...
                    */
                    ApiVer latestApiVer = currentVersions.apiObj.getApiVersion(apiName);
                    /*
                    It shouldn't be possible for latestApiVer to be null, but check anyway!
                    */
                    if (latestApiVer != null && latestApiVer.equals(tempObject.apiVer)){
                        logMsg("Cached version and latest version are the same");
                    } else {
                        /*
                        The ApiVer objects differ, so let's reload this API from the server and update the cache
                        */
                        logMsg("Cached version and latest version are different. Reloading from server...");
                        tempObject = loadAndSerialize();
                    }
                } catch (SecurityException | IllegalArgumentException E) {
                    logMsg("Exception: " + E.toString() + " msg: " + E.getMessage());
                }
            } else {
                logMsg("currentVersions not available, cannot check if refresh needed for API [" + apiName + "]");
            }

        } else {                
            /*
            This is the path when the object isn't currently cached...
            */
            logMsg("API [" + apiName + "] is NOT in the cache");
            tempObject = loadAndSerialize();
        }
        return tempObject;  
    }
}

/**
 *
 * @author Ken Lowrie <ken@klowrie.net>
 */
public class ClsRestApi {
    private final Cache objCache;
    private final Versions currentVersions;
    
    private ApiWrapper<AboutUs> craAboutUs = null;
    private ApiWrapper<Versions> craVersions = null;
    private ApiWrapper<ContactInfo> craContactInfo = null;
    private ApiWrapper<Reels> craReels = null;
    private ApiWrapper<OurWork> craOurWork = null;
    
    
    public ClsRestApi(String localCache) throws IOException, CRAException {
        objCache = new Cache(localCache);
        /*
        currentVersions holds the results of instantiating a new Versions() object
        from the api server. This one call is necessary so that we are able to determine
        whether any of the additional API objects that are in the cache have gone stale.
        */
        this.currentVersions = new Versions().load();
    }
    
    public AboutUs getAboutUs(){
        if (craAboutUs == null) craAboutUs = new ApiWrapper<>(new AboutUs(), objCache, Constants.API_ABOUT_US, this.currentVersions);
        
        return craAboutUs.loaded ? craAboutUs.api : null;
    }
    
    public Versions getVersions(){
        if (craVersions == null) craVersions = new ApiWrapper<>(new Versions(), objCache, Constants.API_VERSIONS, this.currentVersions);
        
        return craVersions.loaded ? craVersions.api : null;
    }
    
    public ContactInfo getContactInfo(){
        if (craContactInfo == null) craContactInfo = new ApiWrapper<>(new ContactInfo(), objCache, Constants.API_CONTACT_INFO, this.currentVersions);
        
        return craContactInfo.loaded ? craContactInfo.api : null;
    }
    
    public Reels getReels(){
        if (craReels == null) craReels = new ApiWrapper<>(new Reels(), objCache, Constants.API_REELS, this.currentVersions);
        
        return craReels.loaded ? craReels.api : null;
    }
    
    public OurWork getOurWork(){
        if (craOurWork == null) craOurWork = new ApiWrapper<>(new OurWork(), objCache, Constants.API_OUR_WORK, this.currentVersions);
        
        return craOurWork.loaded ? craOurWork.api : null;
    }
    
}
