/*
 * Copyright 2017 Ken Lowrie.
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
package clsrestapi;

import com.google.gson.Gson;
import java.io.Serializable;

/**
 * This class implements the our-work REST API call.  All CLS REST APIs
 * contain common elements as described in the {@link Base} class, and this
 * class implements the API specific data and the methods needed to construct it.
 * <br><br>
 * The default behavior for this API is return all API version numbers, however, you
 * can request a specific API version by passing the fully qualified API name in the path.
 * See the docs on {@link Versions#Versions(java.lang.String, java.lang.String)} for details.
 * @author ken
 */
public class Versions extends Base<Versions> implements Serializable{
    /**
     * This object wraps the API-specific data for the versions API.
     */
    public ApiObjVersions apiObj;
    
    /**
     * Create an instance of the versions object, but override both the base URL and the apiName. 
     * This allows you to specify a specific API version object to fetch in the call. The API name must
     * be one of "about-us", "contact-info", "reels", "our-work", "versions". e.g., if you pass "versions/reels/", then
     * then version object for the reels API is returned.
     * If you do not specify a specific API in the path, then the version numbers for all APIs are returned. This is the default
     * behavior for the other two constructors.
     * @param wsUrl the base URL to use for constructing this object.
     * @param apiName the fully qualified API version object to return. e.g. "/versions/our-work/"
     * returns the version API object for the "our-work" API.
     */
    public Versions(String wsUrl, String apiName){
        super(wsUrl, apiName);
    }

    /**
     * Review the method docs for {@link AboutUs#AboutUs(java.lang.String wsUrl)} for the details.
     * @param wsUrl the base URL to use for constructing this object
     */
    public Versions(String wsUrl){
        super(wsUrl, Constants.API_VERSIONS);
    }
    /**
     * Create an instance of the versions object using the public REST server at api.cloudylogic.com.
     * Review the method docs for {@link AboutUs#AboutUs()} for additional details.
     */
    public Versions(){
        super(Constants.WSURL,Constants.API_VERSIONS);
    }

    /**
     * Overrides the equals() method and compares the passed object's instance
     * data against the instance data of the current object. If it is identical,
     * returns true.
     * @param o this object must be of type {@link Versions}
     * @return boolean indicating whether the object passed is identical to the
     * current instance. Note that if you've created a copy of the current object
     * through assignment, this will return true (as expected), otherwise it
     * manually compares the instance data of each object to verify that they
     * are the same.
     */
    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if (!(o instanceof Versions)){
            return false;
        }
        
        Versions vi = (Versions)o;
        
        return dbgObj.equals(vi.dbgObj) && apiVer.equals(vi.apiVer) && apiObj.equals(vi.apiObj) ;
    }
    
    /**
     * This method loads the JSON from the web service, using the current values
     * for the BASE URL and the API name.
     * @return {@link Versions} object
     */
    @Override
    public Versions load(){
        try{
            String json = loadJSONfromWebService();

            Gson gson = new Gson();

            return gson.fromJson(json, this.getClass());
        } catch (CRAException e) {
            //e.printStackTrace();
        }
        // TODO: What other exceptions should I catch here?
        return null;        
    }
    
    /**
     * This method prints a nicely formatted string that shows the current object's
     * instance data.
     * @return a formatted String that is suitable for printing on the console or in a log.
     */
    @Override
    public String toString(){
        String s;
        
        s = getHeader() + super.toString() + apiObj.toString();
        
        return s;
    }
    
}
