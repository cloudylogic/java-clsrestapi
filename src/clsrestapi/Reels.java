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
 * This class implements the reels REST API call.  All CLS REST APIs
 * contain common elements as described in the {@link Base} class, and this
 * class implements the API specific data and the methods needed to construct it.
 * <br><br>
 * The default behavior for this API is return all demo reels, however, you
 * can request a specific reel by passing the fully qualified demo reel number.
 * See the docs on {@link Reels#Reels(java.lang.String, java.lang.String)} for details.
 * @author ken
 */
public class Reels extends Base<Reels> implements Serializable{
    /**
     * This object wraps the API-specific data for the reels API.
     */
    public ApiObjReels apiObj;
    
    /**
     * Create an instance of the reels object, but override both the base URL and the apiName. 
     * This allows you to specify a specific a specific demo reel
     * to fetch. Be sure that the demo reel number is within the range <tt>0 &lt; demoReelNum &lt; numReels</tt>.
     * e.g. if you pass "/reels/2/", then demo reel #3 is returned (0 specifies first reel, 1 specifies 2nd reel, ...)
     * If you do not specify a reel number in the path, then all demo reels are returned. This is the default
     * behavior for the other two constructors. <b>NOTE</b>: Demo reel zero (0) is always the <em>latest</em> demo reel.
     * @param wsUrl the base URL to use for constructing this object.
     * @param apiName the fully qualified demo reel to return. e.g. "/demo/2/"
     * returns the 3rd demo reel.
     */
    public Reels(String wsUrl, String apiName){
        super(wsUrl, apiName);
    }

    /**
     * Review the method docs for {@link AboutUs#AboutUs(java.lang.String wsUrl)} for the details.
     * @param wsUrl the base URL to use for constructing this object
     */
    public Reels(String wsUrl){
        super(wsUrl, Constants.API_REELS);
    }
    /**
     * Create an instance of the reels object using the public REST server at api.cloudylogic.com.
     * Review the method docs for {@link AboutUs#AboutUs()} for additional details.
     */
    public Reels(){
        super(Constants.WSURL,Constants.API_REELS);
    }

    /**
     * Overrides the equals() method and compares the passed object's instance
     * data against the instance data of the current object. If it is identical,
     * returns true.
     * @param o this object must be of type {@link Reels}
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
        if (!(o instanceof Reels)){
            return false;
        }
        
        Reels ri = (Reels)o;
        
        return dbgObj.equals(ri.dbgObj) && apiVer.equals(ri.apiVer) && apiObj.equals(ri.apiObj) ;
    }
    
    /**
     * This method loads the JSON from the web service, using the current values
     * for the BASE URL and the API name.
     * @return {@link Reels} object
     */
    @Override
    public Reels load(){
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