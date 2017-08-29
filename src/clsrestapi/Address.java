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
 * This class implements a US format mailing address. The {@link ContactInfo} class
 * contains one of these objects.
 * @author ken
 */
public class Address implements Serializable{
    /**
     * The name of the entity.
     */
    public String name;
    /**
     * The street address.
     */
    public String street;
    /**
     * The city.
     */
    public String city;
    /**
     * The state.
     */
    public String state;
    /**
     * The zipcode.
     */
    public String zipcode;
    
    /**
     * Get the mailing address for the company.
     * @return String in USPS format.
     */
    public String getMailingAddress(){
        StringBuilder sb = new StringBuilder(name).append(Constants.NL);
        
        sb.append(street).append(Constants.NL);
        sb.append(city).append(", ");
        sb.append(state).append(' ');
        sb.append(zipcode).append(Constants.NL);
        
        return sb.toString();        
    }
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
        if (!(o instanceof Address)){
            return false;
        }
        
        Address addr = (Address)o;
        
        return  name.equals(addr.name) &&
                street.equals(addr.street) &&
                city.equals(addr.city) &&
                state.equals(addr.state) &&
                zipcode.equals(addr.zipcode);
    }
    /**
     * Converts the current object instance data to a formatted string.
     * @return the current objects' instance data
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("\tAddress:").append(Constants.NL);
        
        sb.append("\t\tName: ").append(name).append(Constants.NL);
        sb.append("\t\tStreet: ").append(street).append(Constants.NL);
        sb.append("\t\tCity: ").append(city).append(Constants.NL);
        sb.append("\t\tState: ").append(state).append(Constants.NL);
        sb.append("\t\tZip: ").append(zipcode).append(Constants.NL);
        
        return sb.toString();
    }
}

