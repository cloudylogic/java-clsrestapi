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
 * This class implements the API specific data of versions. The common data is
 * described in the {@link Base} class, and it consists of an {@link ApiVer} object
 * and a {@link DbgObj} object.
 * @author ken
 */
public class ApiObjVersions implements Serializable{
    /**
     * the number of API objects that are in the {@link ApiObjVersions#apiList} list.
     */
    public int  numApis;
    /**
     * the list of API versions represented as {@link ApiVer} objects.
     */
    public List<ApiVer> apiList;
    
    /**
     * Return the ApiVer object of the specified API. This is a cloned version of
     * the object that's stored in the object's apiList vector.
     * @param apiName The name of the API whose version object you want.
     * @return ApiVer for specified API or null if not found/present.
     */
    public ApiVer getApiVersion(String apiName){
        int counter = 0;
        for(ListIterator<ApiVer> iter = apiList.listIterator(); iter.hasNext();){
            ApiVer version = iter.next();
            
            if (apiName.equals(version.apiName)){
                try {
                    return (ApiVer) version.clone();
                } catch (CloneNotSupportedException ex) {

                }
            }
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
        if (!(o instanceof ApiObjVersions)){
            return false;
        }
        
        ApiObjVersions vi = (ApiObjVersions)o;
        
        return  numApis == vi.numApis &&
                apiList.equals(vi.apiList);
    }

    /**
     * Converts the current object instance data to a formatted string.
     * @return the current objects' instance data
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("apiObj:").append(Constants.NL);
        
        sb.append("\tnumApis: ").append(numApis).append(Constants.NL);
        sb.append("\tapiList: ").append(Constants.NL);
        
        int counter = 0;
        for(ListIterator<ApiVer> iter = apiList.listIterator(); iter.hasNext();){
            ApiVer version = iter.next();
            
            sb.append("\t\tapiList[").append(counter).append("].apiName: ").append(version.apiName).append(Constants.NL);
            sb.append("\t\tapiList[").append(counter).append("].apiVersion: ").append(version.apiVersion).append(Constants.NL);
            sb.append("\t\tapiList[").append(counter).append("].apiDataVersion: ").append(version.apiDataVersion).append(Constants.NL);
            sb.append(Constants.NL);
            counter++;
        }
        return sb.toString();
    }
    
}
