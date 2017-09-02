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
 * This class implements the API specific data of reels. The common data is
 * described in the {@link Base} class, and it consists of an {@link ApiVer} object
 * and a {@link DbgObj} object.
 * @author ken
 */
public class ApiObjReels implements Serializable{
    /**
     * the number of reels that are contained in the {@link ApiObjReels#reelList} array.
     */
    public int  numReels;
    /**
     * a list of demo reels represented as {@link Video} objects.
     */
    public List<Video> reelList;
    
    /**
     * Get the demo reel at the index specified by 'reel'. TODO: Should this be a clone???
     * @param reel The demo reel position in the list.
     * @return Specified demo reel or null if 'reel' is out of range.
     */
    public Video getReel(int reel){
        if (reel < this.numReels ){
            return reelList.get(reel);
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
        if (!(o instanceof ApiObjReels)){
            return false;
        }
        
        ApiObjReels ri = (ApiObjReels)o;
        
        return  numReels == ri.numReels &&
                reelList.equals(ri.reelList);
    }
    
    /**
     * Converts the current object instance data to a formatted string.
     * @return the current objects' instance data
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("apiObj:" + Constants.NL);

        sb.append("\tnumReels: ").append(numReels).append(Constants.NL);
        sb.append("\treelList: ").append(Constants.NL);

        int counter = 0;
        for(ListIterator<Video> iter = reelList.listIterator(); iter.hasNext();){
            Video video = iter.next();
            
            sb.append("\t\treelList[").append(counter).append("].title: ").append(video.title).append(Constants.NL);
            sb.append("\t\treelList[").append(counter).append("].url: ").append(video.url).append(Constants.NL);
            sb.append("\t\treelList[").append(counter).append("].sUrl: ").append(video.sUrl).append(Constants.NL);
            sb.append("\t\treelList[").append(counter).append("].thumb: ").append(video.thumb).append(Constants.NL);
            sb.append("\t\treelList[").append(counter).append("].frame: ").append(video.frame).append(Constants.NL);
            sb.append(Constants.NL);
            counter++;
        }
        
        return sb.toString();
    }
    
}
