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
 * This class implements the API specific data of our-work. The common data is
 * described in the {@link Base} class, and it consists of an {@link ApiVer} object
 * and a {@link DbgObj} object.
 * @author ken
 */
public class ApiObjOurWork implements Serializable{
    /**
     * the number of showcase videos stored in the {@link ApiObjOurWork#videoList} list.
     */
    public int  numVideos;
    /**
     * The list of showcase videos represented as {@link ShowCaseVideo} objects.
     */
    public List<ShowCaseVideo> videoList;
    
     /**
     * Get the showcase video at the index specified by 'showCaseVideo'. TODO: Should this be a clone???
     * @param showCaseVideo The showcase video position in the list.
     * @return Specified showcase video or null if 'reel' is out of range.
     */
    public ShowCaseVideo getShowCaseVideo(int showCaseVideo){
        if (showCaseVideo < this.numVideos ){
            return videoList.get(showCaseVideo);
        }
        return null;
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
        if (!(o instanceof ApiObjOurWork)){
            return false;
        }
        
        ApiObjOurWork wi = (ApiObjOurWork)o;
        
        return  numVideos == wi.numVideos &&
                videoList.equals(wi.videoList);
    }
    
    /**
     * Converts the current object instance data to a formatted string.
     * @return the current objects' instance data
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("apiObj:").append(Constants.NL);
        
        sb.append("\tnumVideos: ").append(numVideos).append(Constants.NL);
        sb.append("\tvideoList: ").append(Constants.NL);
        
        int counter = 0;
        for(ListIterator<ShowCaseVideo> iter = videoList.listIterator(); iter.hasNext();){
            ShowCaseVideo video = iter.next();
            
            sb.append("\t\tvideoList[").append(counter).append("].title: ").append(video.title).append(Constants.NL);
            sb.append("\t\tvideoList[").append(counter).append("].url: ").append(video.url).append(Constants.NL);
            sb.append("\t\tvideoList[").append(counter).append("].sUrl: ").append(video.sUrl).append(Constants.NL);
            sb.append("\t\tvideoList[").append(counter).append("].hdUrl: ").append(video.hdUrl).append(Constants.NL);
            sb.append("\t\tvideoList[").append(counter).append("].sdUrl: ").append(video.sdUrl).append(Constants.NL);
            sb.append("\t\tvideoList[").append(counter).append("].mobileUrl: ").append(video.mobileUrl).append(Constants.NL);
            sb.append("\t\tvideoList[").append(counter).append("].thumb: ").append(video.thumb).append(Constants.NL);
            sb.append("\t\tvideoList[").append(counter).append("].frame: ").append(video.frame).append(Constants.NL);
            sb.append("\t\tvideoList[").append(counter).append("].type: ").append(video.type).append(Constants.NL);
            sb.append("\t\tvideoList[").append(counter).append("].role: ").append(video.roles).append(Constants.NL);
            sb.append("\t\tvideoList[").append(counter).append("].description: ").append(video.description).append(Constants.NL);
            sb.append(Constants.NL);
            counter++;
        }
        return sb.toString();
    }
}
