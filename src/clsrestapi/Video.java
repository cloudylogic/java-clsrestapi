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
 * This class implements a video object; basically everything needed to represent a
 * streaming video.
 * @author Ken Lowrie
 */
public class Video implements Serializable{
    /**
     * the title of the video.
     */
    public String title;
    /**
     * the URL for the video.
     */
    public String url;
    /**
     * the streaming URL for the video.
     */
    public String sUrl;
    /**
     * the thumbnail for the video.
     */
    public String thumb;
    /**
     * the image frame for the video; essentially a larger thumbnail that will be show as a still frame in the player.
     */
    public String frame;
    
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
        if (!(o instanceof Video)){
            return false;
        }
        
        Video vi = (Video)o;
        
        return  title.equals(vi.title) &&
                url.equals(vi.url) &&
                sUrl.equals(vi.sUrl) &&
                thumb.equals(vi.thumb) &&
                frame.equals(vi.frame);
    }

    /**
     * Converts the current object instance data to a formatted string.
     * @return the current objects' instance data
     */
    @Override
    public String toString(){
        String s;
        
        s = "\ttitle: " + title + Constants.NL +
            "\turl: " + url + Constants.NL +
            "\tsUrl: " + sUrl + Constants.NL +
            "\tthumb: " + thumb + Constants.NL +
            "\tframe: " + frame + Constants.NL;
        
        return s;
    }
    
}
