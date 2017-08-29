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

/**
 * This class implements the our-work REST API call.  All CLS REST APIs
 * contain common elements as described in the {@link Base} class, and this
 * class implements the API specific data and the methods needed to construct it.
 * <br><br>
 * The default behavior for this API is return all showcase videos, however, you
 * can request a specific video by passing the fully qualified showcase video number.
 * See the docs on {@link OurWork#OurWork(java.lang.String, java.lang.String)} for details.
 * @author ken
 */
public class OurWork extends Base<OurWork>{
    /**
     * This object wraps the API-specific data for the our-work API.
     */
    public ApiObjOurWork apiObj;
    
    /**
     * Create an instance of the our-work object, but override both the base URL and the apiName. 
     * This allows you to specify a specific showcase video
     * to fetch. Be sure that the showcase video number is within the range <tt>0 &lt; showcasevideo &lt; numVideos</tt>.
     * e.g. if you pass "/our-work/2/", then showcase video #3 is returned (0 specifies first video, 1 specifies 2nd video, ...)
     * If you do not specify a video number in the path, then all showcase videos are returned. This is the default
     * behavior for the other two constructors.
     * @param wsUrl the base URL to use for constructing this object.
     * @param apiName the fully qualified showcase video number to return. e.g. "/our-work/2/"
     * returns the 3rd showcase video.
     */
    public OurWork(String wsUrl, String apiName){
        super(wsUrl, apiName);
    }

    /**
     * Review the method docs for {@link AboutUs#AboutUs(java.lang.String wsUrl)} for the details.
     * @param wsUrl the base URL to use for constructing this object
     */
    public OurWork(String wsUrl){
        super(wsUrl, Constants.API_OUR_WORK);
    }
    /**
     * Create an instance of the our-work object using the public REST server at api.cloudylogic.com.
     * Review the method docs for {@link AboutUs#AboutUs()} for additional details.
     */
    public OurWork(){
        super(Constants.WSURL,Constants.API_OUR_WORK);
    }

    /**
     * This method loads the JSON from the web service, using the current values
     * for the BASE URL and the API name.
     * @return {@link OurWork} object
     */
    @Override
    public OurWork load(){
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
     * Overrides the equals() method and compares the passed object's instance
     * data against the instance data of the current object. If it is identical,
     * returns true.
     * @param o this object must be of type {@link OurWork}
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
        if (!(o instanceof OurWork)){
            return false;
        }
        
        OurWork ow = (OurWork)o;
        
        return dbgObj.equals(ow.dbgObj) && apiVer.equals(ow.apiVer) && apiObj.equals(ow.apiObj) ;
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