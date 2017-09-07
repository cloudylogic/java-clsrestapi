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
 * This class implements a Social Network object; basically everything needed to represent a
 * Social Network identity for a business.
 * @author Ken Lowrie
 */
public class SocialNetwork implements Serializable {
    /**
     * the name of the network, e.g. Facebook.
     */
    public String network;
    /**
     * the identity of the business on the network.
     */
    public String id;
    /**
     * the URL for the network.
     */
    public String url;
    /**
     * the image name for the network.
     */
    public String image;
    
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
        if (!(o instanceof SocialNetwork)){
            return false;
        }
        
        SocialNetwork sn = (SocialNetwork)o;
        
        return  network.equals(sn.network) &&
                id.equals(sn.id) &&
                url.equals(sn.url) &&
                image.equals(sn.image);
    }

    /**
     * Converts the current object instance data to a formatted string.
     * @return the current objects' instance data
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("\tAddress:").append(Constants.NL);
        
        sb.append("\tNetwork: ").append(network).append(Constants.NL);
        sb.append("\tID: ").append(id).append(Constants.NL);
        sb.append("\tUrl: ").append(url).append(Constants.NL);
        sb.append("\tImage: ").append(image).append(Constants.NL);
        
        return sb.toString();
    }   
}
