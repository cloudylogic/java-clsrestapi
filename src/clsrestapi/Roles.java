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
 * This class implements the roles object, which allows additional properties to be
 * attached to a video.
 * @author Ken Lowrie
 */
public class Roles implements Serializable{
    /**
     * the director of the video.
     */
    public String director;
    /**
     * the dp or cinematographer of the video.
     */
    public String dp;
    /**
     * the camera operator for the video.
     */
    public String camera;
    /**
     * the editor for the video.
     */
    public String editor;
    
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
        if (!(o instanceof Roles)){
            return false;
        }
        
        Roles ri = (Roles)o;
        
        return  director.equals(ri.director) &&
                dp.equals(ri.dp) &&
                camera.equals(ri.camera) &&
                editor.equals(ri.editor);
    }

    /**
     * Converts the current object instance data to a formatted string.
     * @return the current objects' instance data
     */
    @Override
        public String toString(){
        StringBuilder sb = new StringBuilder();
        
        if( !director.isEmpty() ) sb.append("Director: ").append(director).append(", ");
        if( !dp.isEmpty() ) sb.append("DP: ").append(dp).append(", ");
        if( !camera.isEmpty() ) sb.append("Camera: ").append(camera).append(", ");
        if( !editor.isEmpty() ) sb.append("Editor: ").append(editor).append(", ");

        /*
         * Remove the trailing ", " if it's there...
         */
        if( sb.toString().endsWith(", ")) sb.setLength(sb.length()-2);
        
        return sb.toString();
    }

}

