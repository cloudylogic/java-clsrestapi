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
 * This class implements the contact-info REST API call.  All CLS REST APIs
 * contain common elements as described in the {@link Base} class, and this
 * class implements the API specific data and the methods needed to construct it.
 * @author ken
 */
public class ContactInfo extends Base<ContactInfo>{
    /**
     * This object wraps the API-specific data for the contact-info API.
     */
    public ApiObjContactInfo apiObj;
    
    /**
     * Review the method docs for {@link AboutUs#AboutUs(java.lang.String wsUrl)} for the details.
     * @param wsUrl the base URL to use for constructing this object
     */
    public ContactInfo(String wsUrl){
        super(wsUrl, Constants.API_CONTACT_INFO);
    }
    /**
     * Create an instance of the contact-info object using the public REST server at api.cloudylogic.com.
     * Review the method docs for {@link AboutUs#AboutUs()} for additional details.
     */
    public ContactInfo(){
        super(Constants.WSURL,Constants.API_CONTACT_INFO);
    }

    /**
     * Overrides the equals() method and compares the passed object's instance
     * data against the instance data of the current object. If it is identical,
     * returns true.
     * @param o this object must be of type {@link ContactInfo}
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
        if (!(o instanceof ContactInfo)){
            return false;
        }
        
        ContactInfo ci = (ContactInfo)o;
        
        return dbgObj.equals(ci.dbgObj) && apiVer.equals(ci.apiVer) && apiObj.equals(ci.apiObj) ;
    }
    
    /**
     * This method loads the JSON from the web service, using the current values
     * for the BASE URL and the API name.
     * @return {@link ContactInfo} object
     */
    @Override
    public ContactInfo load(){
        try {
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