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

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.io.File;

/**
 * This class implements a few helper methods for testing the common API object data.
 * @author Ken Lowrie
 */
public class TestHelpers {
    public static String getHost(){
        boolean debug = true;
        if (debug){
            return "http://localhost:8000";
        }
        return Constants.WSURL;
    }
    /**
     * Validate the contents of a dbgObj from an API return call.
     * @param dbgObj Instance of {@link DbgObj} to validate
     * @param apiName The API name being checked
     */
    public static void checkDbgObjInstanceData(DbgObj dbgObj, String apiName){
        assert(dbgObj.parseOK);
        assert(dbgObj.query_string == null ? true : dbgObj.query_string.isEmpty());
        assert(dbgObj.request_uri.equals("/" + apiName + "/"));
        assert(dbgObj.restAPIkeys.get(0).equals(apiName));
        assert(dbgObj.traceMsgQ.isEmpty());
    }
    
    public static void checkApiVerInstanceData(ApiVer apiVer, String ... apiInfo){
        
        String apiName = apiInfo.length > 0 ? apiInfo[0] : "Unknown";
        String api_ver = apiInfo.length > 1 ? apiInfo[1] : "1.0";
        String api_data_ver = apiInfo.length > 2 ? apiInfo[2] : "1.0";
//        System.out.println("num args is: " + apiInfo.length);
        
        ApiVer apiVer2 = new ApiVer(apiName, api_ver, api_data_ver);
//        System.out.println("name: "+apiVer.apiName+" ver: "+apiVer.apiVersion+ " data_ver: "+apiVer.apiDataVersion);
//        System.out.println("name: "+apiName+" ver: "+api_ver+ " data_ver: "+api_data_ver);
        
        assert(apiVer.equals(apiVer2));
    }
    
    public static void logStart(String className){
        String dashes = String.join("", Collections.nCopies(60, "-"));

        StringBuilder s = new StringBuilder(dashes);
        
        s.append(Constants.NL);
        s.append("Started Testing Class: ");
        s.append(className);
        s.append(Constants.NL);
        s.append(dashes);
        s.append(Constants.NL);

        System.out.println(s.toString());
    }
    public static void logEnd(String className){
        String dashes = String.join("", Collections.nCopies(60, "-"));

        StringBuilder s = new StringBuilder(Constants.NL);
        
        s.append(dashes);
        s.append(Constants.NL);
        s.append("Finished Testing Class: ");
        s.append(className);
        s.append(Constants.NL);
        s.append(dashes);
        s.append(Constants.NL);

        System.out.println(s.toString());
    }

    public static void logMsg(String method, String msg){
        System.out.println(method + ": " + msg);        
    }

    public static String tempFile(String prefix, String suffix, boolean deleteOnExit){
        File tempFile;
        
        try{
            tempFile = File.createTempFile(prefix, suffix);
            if (deleteOnExit) tempFile.deleteOnExit();
        } catch (IOException e){
            tempFile = null;
        }
        
        return tempFile != null ? tempFile.toString() : null;
    }

    public static String tempFile(String prefix, boolean deleteOnExit){
        return tempFile(prefix, ".ser", deleteOnExit);
    }
    
    public static String tempFile(String prefix, String suffix){
        return tempFile(prefix, suffix, true);
    }

    public static String tempFile(String prefix){
        return tempFile(prefix, ".ser", true);
    }

    public static String tempFile(){
        return tempFile("serial", ".ser", true);
    }

    private TestHelpers(){
        
    }
}

