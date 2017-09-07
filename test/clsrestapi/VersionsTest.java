/*
 * Copyright 2017 Ken Lowrie <ken@klowrie.net>.
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

import static clsrestapi.TestHelpers.getHost;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Ken Lowrie <ken@klowrie.net>
 */
public class VersionsTest {
    
    static String className;
    
    public VersionsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        className = new Object(){}.getClass().getEnclosingClass().getName();
        
        if (className.endsWith("Test")) className = className.substring(0, className.length() - 4);
        
        TestHelpers.logStart(className);
    }
    
    @AfterClass
    public static void tearDownClass() {
        TestHelpers.logEnd(className);
    }
    
    /**
     * Test of equals method, of class Versions.
     */
    @Test
    public void testEquals() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        
        TestHelpers.logMsg(methodName, "Running ...");
        Versions instance1 = new Versions(getHost()).load();
        Versions instance2 = new Versions(getHost()).load();
        assert(instance1 != null);
        assert(instance2 != null);
        boolean expResult = true;
        boolean result = instance1.equals(instance2);
        assertEquals(expResult, result);
        assert(instance1 != instance2);
    }

    /**
     * Test of load method, of class Versions.
     */
    @Test
    public void testLoad() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        
        TestHelpers.logMsg(methodName, "Running ...");
        Versions instance = new Versions(getHost());
        Versions result = instance.load();
        assert(instance != null);
        assert(result instanceof Versions);
    }

    /**
     * Test of toString method, of class Versions.
     */
    @Test
    public void testToString() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        
        TestHelpers.logMsg(methodName, "Running ...");
        Versions instance = new Versions(getHost()).load();
        assert(instance != null);
        String expResult = "";
        String result = instance.toString();
        assertNotEquals(expResult, result);
        assert(result.startsWith("---"));
    }
    
    @Test
    public void checkInstanceData() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        
        TestHelpers.logMsg(methodName, "Running ...");
        Versions instance = new Versions(getHost()).load();
        assert(instance != null);
        
        TestHelpers.logMsg(methodName, "checkDbgObjInstanceData");
        TestHelpers.checkDbgObjInstanceData(instance.dbgObj,Constants.API_VERSIONS);

        TestHelpers.logMsg(methodName, "checkApiVerInstanceData");
        TestHelpers.checkApiVerInstanceData(instance.apiVer,Constants.API_VERSIONS);

        TestHelpers.logMsg(methodName, "checkApiObjInstanceData");
        assert(instance.apiObj.numApis == 6);
        assert(instance.apiObj.apiList.get(0) instanceof ApiVer);
    }    
    
    @Test
    public void testSerialization() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        
        TestHelpers.logMsg(methodName, "Running ...");
        Versions instance = new Versions(getHost()).load();
        assert(instance != null);
        
        String filename = TestHelpers.tempFile(Constants.API_VERSIONS);
        
        assert(filename != null);
        
        TestHelpers.logMsg(methodName, "serializing class type " + instance.getClass());
        try {
            instance.serialize(filename);
        } catch (CRAException e){
            fail("Exception while serializing object: " + e.getMessage());
        }
        
        TestHelpers.logMsg(methodName, "deSerializing class type " + instance.getClass());
        
        try {
            Versions obj2 = instance.deSerialize(filename);
            assert(obj2 != null);
            assert(instance.equals(obj2));
        } catch (CRAException e){
            fail("Exception while deSerializing object: " + e.getMessage());
        }   
    }
}
