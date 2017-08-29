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
import java.util.List;
import java.util.ListIterator;

/**
 * This class implements the API specific data of contact-info. The common data is
 * described in the {@link Base} class, and it consists of an {@link ApiVer} object
 * and a {@link DbgObj} object.
 * @author ken
 */
public class ApiObjContactInfo implements Serializable {
    /**
     * a short description of where the business is located.
     */
    public String location;
    /**
     * US mailing address.
     */
    public Address address;
    /**
     * email address for the business.
     */
    public String email;
    /**
     * phone number for the business.
     */
    public String phone;
    /**
     * a list of the {@link SocialNetwork}'s that the business maintains an identity on.
     */
    public List<SocialNetwork> socialNetworks;
    
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
        if (!(o instanceof ApiObjContactInfo)){
            return false;
        }
        
        ApiObjContactInfo ci = (ApiObjContactInfo)o;
        
        return  location.equals(ci.location) &&
                address.equals(ci.address) &&
                email.equals(ci.email) &&
                phone.equals(ci.phone) &&
                socialNetworks.equals(ci.socialNetworks);
    }

    /**
     * Converts the current object instance data to a formatted string.
     * @return the current objects' instance data
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("apiObj:").append(Constants.NL);
        
        sb.append("\tLocation: ").append(location).append(Constants.NL);
        sb.append(address);
        sb.append("\tEmail: ").append(email).append(Constants.NL);
        sb.append("\tPhone: ").append(phone).append(Constants.NL);

        if( socialNetworks.size() > 0 ){
            sb.append("\tSocialNetworks = [").append(Constants.NL);
            int counter = 0;
            for(ListIterator<SocialNetwork> iter = socialNetworks.listIterator(); iter.hasNext();){
                SocialNetwork sn = iter.next();

                sb.append("\t\tsocialNetworks[").append(counter).append("].network: ").append(sn.network).append(Constants.NL);
                sb.append("\t\tsocialNetworks[").append(counter).append("].id: ").append(sn.id).append(Constants.NL);
                sb.append("\t\tsocialNetworks[").append(counter).append("].url: ").append(sn.url).append(Constants.NL);
                sb.append(Constants.NL);
                counter++;
            }
            sb.append("\t]").append(Constants.NL);
        }
        
        return sb.toString();
    }
}
