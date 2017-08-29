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
 * This class implements a showcase video object; basically everything needed to represent a
 * streaming video that is used to showcase a business or entity.
 * @author Ken Lowrie
 */
public class ShowCaseVideo extends Video implements Serializable{
    /**
     * the type of video this is. e.g. business profile, music video, etc.
     */
    public String type;
    /**
     * the roles of the various crewmembers on the video.
     */
    public Roles roles;
    /**
     * the long description of the video.
     */
    public String description;
    
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
        if (!(o instanceof ShowCaseVideo)){
            return false;
        }
        
        ShowCaseVideo si = (ShowCaseVideo)o;
        
        return  type.equals(si.type) &&
                description.equals(si.description) &&
                roles.equals(si.roles);
    }

    /**
     * Converts the current object instance data to a formatted string.
     * @return the current objects' instance data
     */
    @Override
        public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("Type: ").append(type).append(Constants.NL);
        sb.append("Roles: ").append(roles).append(Constants.NL);
        sb.append("Description: ").append(description).append(Constants.NL);
        
        return sb.toString();
    }    
    
}
