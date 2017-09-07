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

/**
 * This class implements the DbgObj object.  All CLS REST APIs contain two
 * common objects, a DbgObj (this class) and a {@link ApiVer}.
 * @author ken
 */
public class DbgObj implements Serializable{
    /**
     * indicates whether the server was able to parse the API name and parameters.
     */
    public boolean parseOK;
    /**
     * a list of log messages generated by the server when debugging the CLS REST API.
     */
    public List<String> traceMsgQ;
    /**
     * a list of the API keys as parsed by the server
     */
    public List<String> restAPIkeys;
    /**
     * the request_uri as seen by the server
     */
    public String request_uri;
    /**
     * the query_string as parsed by the server
     */
    public String query_string;
    
    /**
     * Overrides the equals() method and compares the passed object's instance
     * data against the instance data of the current object. If it is identical,
     * returns true.
     * @param o this object must be of type {@link DbgObj}
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
        if (!(o instanceof DbgObj)){
            return false;
        }
        
        DbgObj dbgo = (DbgObj)o;
        
        return parseOK == dbgo.parseOK && 
                request_uri.equals(dbgo.request_uri) && 
                traceMsgQ.equals(dbgo.traceMsgQ) && 
                restAPIkeys.equals(dbgo.restAPIkeys) && 
                query_string == null ||
                query_string.equals(dbgo.query_string);
    }
    /**
     * Generates a printable version of the DbgObj object.
     * 
     * @return String - This is a formatted version of the object data.
     */
    @Override
    public String toString(){
        String s;
        
        s = "\tparseOK: " + parseOK + Constants.NL +
            "\trequest_uri: " + request_uri + Constants.NL +
            "\tquery_string: " + query_string + Constants.NL +
            "\trestAPIkeys: " + restAPIkeys + Constants.NL +
            "\ttraceMsgQ: " + traceMsgQ + Constants.NL;
        
        return s;
    }
}

