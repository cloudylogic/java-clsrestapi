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
 * This class implements the ImagePath object.  This object stores
 * a client identifier and the corresponding path on the server where
 * images for that client are stored.
 * @author ken
 */
public class ImagePath implements Serializable, Cloneable{
    /**
     * the client ID
     */
    public String clientID;
    /**
     * the path for images on the server side code for this client
     */
    public String imagePath;
    
    /**
     * Construct an ImagePath object
     * @param clientID The name of the Client
     * @param imagePath The image path on the server for this client
     */
    public ImagePath (String clientID, String imagePath){
        this.clientID = clientID;
        this.imagePath = imagePath;
    }
    
    /**
     * Overrides the equals() method and compares the passed object's instance
     * data against the instance data of the current object. If it is identical,
     * returns true.
     * @param o this object must be of type {@link ImagePath}
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
        if (!(o instanceof ImagePath)){
            return false;
        }
        
        ImagePath ip = (ImagePath)o;
        
        return  clientID.equals(ip.clientID) && 
                imagePath.equals(ip.imagePath);
    }

    /**
     * Implement support for cloning of the ImagePath object.
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
        
        s = "\tclientID: " + clientID + Constants.NL +
            "\timagePath: " + imagePath + Constants.NL;
        
        return s;
    }
    
}
