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
 * This class implements the about-us REST API call.  All CLS REST APIs
 * contain common elements as described in the {@link Base} class, and this
 * class implements the API specific data and the methods needed to construct it.
 * @author ken
 */
public class AboutUs extends Base<AboutUs> implements Serializable{
    /**
     * This object wraps the API-specific data for the about-us API.
     */
    public ApiObjAboutUs apiObj;
    /**
     * This constructor allows you to override the base URL when constructing an
     * object. By default, it's http://api.cloudylogic.com/, but can be overridden
     * as needed. Most common override: http://localhost:port, for use when you are 
     * running the REST API locally.
     * @param wsUrl the base URL to use for the API call
     */
    public AboutUs(String wsUrl){
        super(wsUrl,Constants.API_ABOUT_US);
    }
    /**
     * This constructor is used to create an instance of the about-us API, using
     * the publicly available API for CLS REST (hosted on api.cloudylogic.com).
     */
    public AboutUs(){
        super(Constants.WSURL,Constants.API_ABOUT_US);
    }
    /**
     * This method loads the JSON from the web service, using the current values
     * for the BASE URL and the API name.
     * @return {@link AboutUs} object
     */
    @Override
    public AboutUs load(){
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
     * @param o this object must be of type {@link AboutUs}
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
        if (!(o instanceof AboutUs)){
            return false;
        }
        
        AboutUs au = (AboutUs)o;
        
        return dbgObj.equals(au.dbgObj) && apiVer.equals(au.apiVer) && apiObj.equals(au.apiObj) ;
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