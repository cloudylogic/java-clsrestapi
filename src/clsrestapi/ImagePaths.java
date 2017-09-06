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

import com.google.gson.Gson;
import java.io.Serializable;

/**
 * This class implements the image-paths REST API call.  All CLS REST APIs
 * contain common elements as described in the {@link Base} class, and this
 * class implements the API specific data and the methods needed to construct it.
 * <br><br>
 * The default behavior for this API is return all image paths, however, you
 * can request a specific image path by passing the client identifier in the path.
 * See the constructor docs below for details.
 * @author ken
 */
public class ImagePaths extends Base<ImagePaths> implements Serializable{
    /**
     * This object wraps the API-specific data for the ImagePaths API.
     */
    public ApiObjImagePaths apiObj;
    
    /**
     * Create an instance of the ImagePaths object, but override both the base URL and the apiName. 
     * This allows you to specify a specific ImagePath object to fetch in the call. The API name must
     * be one of a predefined set, e.g. "JavaDesktop", "iOS", "JavaAndroid", etc. e.g., if you pass 
     * "image-paths/JavaDesktop/", then the ImagePath object for the JavaDesktop client is returned.
     * If you do not specify a specific API in the path, then the image paths for all clients are returned. 
     * This is the default behavior for the other two constructors.
     * @param wsUrl the base URL to use for constructing this object.
     * @param apiName the fully qualified client ID object to return. e.g. "/image-paths/JavaDesktop/"
     * returns the ImagePath API object for the "JavaDesktop" client.
     */
    public ImagePaths(String wsUrl, String apiName){
        super(wsUrl, apiName);
    }

    /**
     * Review the method docs for {@link AboutUs#AboutUs(java.lang.String wsUrl)} for the details.
     * @param wsUrl the base URL to use for constructing this object
     */
    public ImagePaths(String wsUrl){
        super(wsUrl, Constants.API_IMAGE_PATHS);
    }
    /**
     * Create an instance of the ImagePaths object using the public REST server at api.cloudylogic.com.
     * Review the method docs for {@link AboutUs#AboutUs()} for additional details.
     */
    public ImagePaths(){
        super(Constants.WSURL,Constants.API_IMAGE_PATHS);
    }

    /**
     * Overrides the equals() method and compares the passed object's instance
     * data against the instance data of the current object. If it is identical,
     * returns true.
     * @param o this object must be of type {@link ImagePaths}
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
        if (!(o instanceof ImagePaths)){
            return false;
        }
        
        ImagePaths ip = (ImagePaths)o;
        
        return dbgObj.equals(ip.dbgObj) && apiVer.equals(ip.apiVer) && apiObj.equals(ip.apiObj) ;
    }
    
    /**
     * This method loads the JSON from the web service, using the current values
     * for the BASE URL and the API name.
     * @return {@link ImagePaths} object
     */
    @Override
    public ImagePaths load(){
        try{
            String json = loadJSONfromWebService();

            Gson gson = new Gson();

            return gson.fromJson(json, this.getClass());
        } catch (CRAException e) {
            //e.printStackTrace();
        }
        // TODO: What other exceptions should I catch here?
        return null;        
    }
    
    /**
     * This method prints a nicely formatted string that shows the current object's
     * instance data.
     * @return a formatted String that is suitable for printing on the console or in a log.
     */
    @Override
    public String toString(){
        String s;
        
        s = getHeader() + super.toString() + apiObj.toString();
        
        return s;
    }
    
}
