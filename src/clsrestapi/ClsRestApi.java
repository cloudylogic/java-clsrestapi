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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ApiWrapper {
    protected boolean loaded;
    protected boolean fromCache;
    protected boolean fromRemote;
    protected String apiName;
    protected String cacheName;
    private final Cache cache;
    private final Versions currentVersions;
    
    public ApiWrapper(Cache cache, String apiName, Versions currentVersions){
        this.loaded = false;
        this.fromCache = false;
        this.fromRemote = false;
        this.cache = cache;
        this.apiName = apiName;
        this.cacheName = cache.absolutePath(apiName);
        this.currentVersions = currentVersions;
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
     * @param craClass
     * @param craObj
     * @return Returns the loaded object, regardless of whether serialization worked.
     * Returns null if the load() method failed...
     * @throws CRAException 
     */
    private Object loadAndSerialize(Class<?> craClass, Object craObj) throws CRAException {
        Object tempObj;
        Method load;
        
        try {
            /*
            Make the network call to reload this API object from the server.
            */
            load = craClass.getMethod("load");
            tempObj = load.invoke(craObj);
            /*
            it'll be null if an error occurred when we tried to make the server call.
            */
            if(tempObj != null){
                /*
                Set the fromCache and fromRemote flags to indicate where this object came from
                */
                setFromRemote();
                try{
                    /*
                    Serialize the newly updated object to the cache. Right now, I'm not really
                    caring if this fails, but I would like to address that at some point. TODO:
                    */
                    Method serialize = craClass.getMethod("serialize", new Class[] {String.class});
                    serialize.invoke(tempObj,cacheName);
                } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException E){
                    // TODO do we need some way to detect this failure...
                    logMsg("API [" + apiName + "] failed serialization: " + E.getMessage());
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException E) {
            logMsg("Exception: " + E.toString() + " msg: " + E.getMessage());
            tempObj = null;  // Make sure that we signal the load failure to the caller.
        }
        return tempObj;
    }

    
    /**
     * Construct a CLS REST API object from cache, if available, otherwise by making
     * the network API call. If it's created again, then cache it, for next time. Before
     * just taking the cached version, check to see if the latest version reported by
     * the server is newer or has newer data, and if so, invalidate the cache, and reload it.
     * @param craClass
     * @param craObj
     * @return A loaded CLS REST API of specified class 'craClass'.
     * @throws CRAException 
     */
    protected final Object init(Class<?> craClass, Object craObj) throws CRAException {
        Object tempObject;
        
        if( cache.itemExists(apiName)){
            /*
            The API object is currently in cache, go load it from there.
            */
            logMsg("API [" + apiName + "] is in the cache: " + cacheName);
            
            try{
                Method deSerialize = craClass.getMethod("deSerialize", new Class[] {String.class});
                
                tempObject = deSerialize.invoke(craObj, cacheName);
                /*
                Set the fromCache and fromRemote flags to indicate where this object came from
                */
                setFromCache();
            } catch(IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException E){
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
                tempObject = loadAndSerialize(craClass, craObj);
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
                    Field apiVerField = craClass.getField("apiVer");
                    
                    Object cachedApiVerObject = apiVerField.get(tempObject);
                    ApiVer cachedApiVer = cachedApiVerObject instanceof ApiVer ? (ApiVer)cachedApiVerObject : null;
                    
                    /*
                    Make sure that cachedApiVer is a proper ApiVer object. If it isn't for some
                    strange reason, no big deal, we just can't determine if a reload is required.
                    However, if this does happen every time, something is wrong...
                    */
                    if (cachedApiVer != null) {
                        ApiVer latestApiVer = currentVersions.apiObj.getApiVersion(apiName);
                        /*
                        It shouldn't be possible for latestApiVer to be null, but check anyway!
                        */
                        if (latestApiVer != null && latestApiVer.equals(cachedApiVer)){
                            logMsg("Cached version and latest version are the same");
                        } else {
                            /*
                            The ApiVer objects differ, so let's reload this API from the server and update the cache
                            */
                            logMsg("Cached version and latest version are different. Reloading from server...");
                            tempObject = loadAndSerialize(craClass, craObj);
                        }
                    } else {
                        logMsg("cachedApiVer is null. This should not happen often, if at all.");
                    }
                } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException E) {
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
            tempObject = loadAndSerialize(craClass, craObj);
        }
        return tempObject;  
    }
}

class CraAboutUs extends ApiWrapper {
    public AboutUs aboutUs;
    
    public CraAboutUs(Cache cache, Versions currentVersions){
        
        super(cache, Constants.API_ABOUT_US, currentVersions);
        
        try {
            Object craObj = init(AboutUs.class, new AboutUs());
            
            aboutUs = craObj instanceof AboutUs ? (AboutUs)craObj : null;
        } catch(CRAException E){
            aboutUs = null;
        }
        loaded = aboutUs != null;
    }
}

class CraVersions extends ApiWrapper {
    public Versions versions;
    
    public CraVersions(Cache cache, Versions currentVersions){
        
        super(cache, Constants.API_VERSIONS, currentVersions);
        
        try {
            Object craObj = init(Versions.class, new Versions());
            
            versions = craObj instanceof Versions ? (Versions)craObj : null;
        } catch(CRAException E){
            versions = null;
        }
        loaded = versions != null;
    }
}

class CraContactInfo extends ApiWrapper {
    public ContactInfo contactInfo;
    
    public CraContactInfo(Cache cache, Versions currentVersions){
        
        super(cache, Constants.API_CONTACT_INFO, currentVersions);
        
        try {
            Object craObj = init(ContactInfo.class, new ContactInfo());
            
            contactInfo = craObj instanceof ContactInfo ? (ContactInfo)craObj : null;
        } catch(CRAException E){
            contactInfo = null;
        }
        loaded = contactInfo != null;
    }
}

class CraReels extends ApiWrapper {
    public Reels reels;
    
    public CraReels(Cache cache, Versions currentVersions){
        
        super(cache, Constants.API_REELS, currentVersions);
        
        try {
            Object craObj = init(Reels.class, new Reels());
            
            reels = craObj instanceof Reels ? (Reels)craObj : null;
        } catch(CRAException E){
            reels = null;
        }
        loaded = reels != null;
    }
}

class CraOurWork extends ApiWrapper {
    public OurWork ourWork;
    
    public CraOurWork(Cache cache, Versions currentVersions){
        
        super(cache, Constants.API_OUR_WORK, currentVersions);
        
        try {
            Object craObj = init(OurWork.class, new OurWork());
            
            ourWork = craObj instanceof OurWork ? (OurWork)craObj : null;
        } catch(CRAException E){
            ourWork = null;
        }
        loaded = ourWork != null;
    }
}

/**
 *
 * @author Ken Lowrie <ken@klowrie.net>
 */
public class ClsRestApi {
    private final Cache objCache;
    private final Versions currentVersions;
    
    private CraAboutUs craAboutUs = null;
    private CraVersions craVersions = null;
    private CraContactInfo craContactInfo = null;
    private CraReels craReels = null;
    private CraOurWork craOurWork = null;
    
    
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
        if (craAboutUs == null) craAboutUs = new CraAboutUs(objCache, this.currentVersions);
        
        return craAboutUs.loaded ? craAboutUs.aboutUs : null;
    }
    
    public Versions getVersions(){
        if (craVersions == null) craVersions = new CraVersions(objCache, this.currentVersions);
        
        return craVersions.loaded ? craVersions.versions : null;
    }
    
    public ContactInfo getContactInfo(){
        if (craContactInfo == null) craContactInfo = new CraContactInfo(objCache, this.currentVersions);
        
        return craContactInfo.loaded ? craContactInfo.contactInfo : null;
    }
    
    public Reels getReels(){
        if (craReels == null) craReels = new CraReels(objCache, this.currentVersions);
        
        return craReels.loaded ? craReels.reels : null;
    }
    
    public OurWork getOurWork(){
        if (craOurWork == null) craOurWork = new CraOurWork(objCache, this.currentVersions);
        
        return craOurWork.loaded ? craOurWork.ourWork : null;
    }
    
}
