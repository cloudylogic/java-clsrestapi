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
 * This class implements the API specific data of about-us. The common data is
 * described in the {@link Base} class, and it consists of an {@link ApiVer} object
 * and a {@link DbgObj} object.
 * @author ken
 */
public class ApiObjAboutUs implements Serializable{
    /**
     * Holds the text returned by the about-us API.
     */
    public String  aboutus;
    
    /**
     * This method tests to see if the passed object is an instance of
     * this class, and if it is, the object instance data is compared to
     * the current instance's data to see if they are identical.
     * @param o object whose instance data should be compared to the current instance.
     * @return boolean indicating whether or not the passed instance data is
     * a match to the current instance data.
     */
    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if (!(o instanceof ApiObjAboutUs)){
            return false;
        }
        
        ApiObjAboutUs auo = (ApiObjAboutUs)o;
        
        return  aboutus.equals(auo.aboutus);
    }

    /**
     * Converts the current object instance data to a formatted string.
     * @return the current objects' instance data
     */
    @Override
    public String toString(){
        String s;
        
        s = "apiObj:" + Constants.NL +
            "\taboutUs: " + aboutus + Constants.NL;
        
        return s;
    }
}
