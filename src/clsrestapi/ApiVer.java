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

import java.io.Serializable;

/**
 * This class implements the ApiVer object.  All CLS REST APIs
 * contain two common objects, an ApiVer (this class) and a {@link DbgObj}.
 * @author ken
 */
public class ApiVer implements Serializable, Cloneable{
    /**
     * the API name
     */
    public String apiName;
    /**
     * the version of the server side code for this API
     */
    public String apiVersion;
    /**
     * the version of the data returned by the server side. this provides a convenient way 
     * for the client to determine whether or not the server API needs to be invoked, or if
     * the currently cached data is still valid.
     */
    public String apiDataVersion;
    
    /**
     * Construct an ApiVer object
     * @param apiName The name of the API
     * @param apiVersion The version of the API
     * @param apiDataVersion The version of the data returned by the API
     */
    public ApiVer (String apiName, String apiVersion, String apiDataVersion){
        this.apiName = apiName;
        this.apiVersion = apiVersion;
        this.apiDataVersion = apiDataVersion;
    }
    
    /**
     * Overrides the equals() method and compares the passed object's instance
     * data against the instance data of the current object. If it is identical,
     * returns true.
     * @param o this object must be of type {@link ApiVer}
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
        if (!(o instanceof ApiVer)){
            return false;
        }
        
        ApiVer ai = (ApiVer)o;
        
        return  apiName.equals(ai.apiName) && 
                apiVersion.equals(ai.apiVersion) && 
                apiDataVersion.equals(ai.apiDataVersion);
    }

    /**
     * Implement support for cloning of the ApiVer object.
     * @return Object that is a duplicate of the current instance
     * @throws CloneNotSupportedException 
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    /**
     * Generates a printable version of the ApiVer object.
     * 
     * @return String - This is a formatted version of the object data.
     */
    @Override
    public String toString(){
        String s;
        
        s = "\tapiName: " + apiName + Constants.NL +
            "\tapiVersion: " + apiVersion + Constants.NL +
            "\tapiDataVersion: " + apiDataVersion + Constants.NL;
        
        return s;
    }
    
}
