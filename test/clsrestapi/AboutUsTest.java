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

import static clsrestapi.TestHelpers.getHost;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ken Lowrie
 */
public class AboutUsTest {
    
    public AboutUsTest() {
    }
    
    static String className;
    
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
    
    @Before
    public void setUp() {
        //System.out.println("setUp");
    }
    
    @After
    public void tearDown() {
        //System.out.println("tearDown");
    }

    /**
     * Test of load method, of class AboutUs.
     */
    @Test
    public void testLoad() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        
        TestHelpers.logMsg(methodName, "Running ...");
        AboutUs instance = new AboutUs(getHost());
        AboutUs result = instance.load();
        assert(result instanceof AboutUs);
    }

    /**
     * Test of equals method, of class AboutUs.
     */
    @Test
    public void testEquals() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        
        TestHelpers.logMsg(methodName, "Running ...");
        Object o = null;
        AboutUs instance1 = new AboutUs(getHost()).load();
        AboutUs instance2 = new AboutUs(getHost()).load();
        assert(instance1 != null);
        assert(instance2 != null);
        boolean expResult = true;
        boolean result = instance1.equals(instance2);
        assertEquals(expResult, result);
        assert(instance1 != instance2);
    }

    /**
     * Test of toString method, of class AboutUs.
     */
    @Test
    public void testToString() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        
        TestHelpers.logMsg(methodName, "Running ...");
        AboutUs instance = new AboutUs(getHost()).load();
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
        AboutUs instance = new AboutUs(getHost()).load();
        assert(instance != null);
        
        TestHelpers.logMsg(methodName, "checkDbgObjInstanceData");
        TestHelpers.checkDbgObjInstanceData(instance.dbgObj,Constants.API_ABOUT_US);

        TestHelpers.logMsg(methodName, "checkApiVerInstanceData");
        TestHelpers.checkApiVerInstanceData(instance.apiVer,Constants.API_ABOUT_US);

        TestHelpers.logMsg(methodName, "checkApiObjInstanceData");
        assert(instance.apiObj.aboutus.startsWith("Although Cloudy Logic")); 
        
    }
    
    @Test
    public void testSerialization() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        
        TestHelpers.logMsg(methodName, "Running ...");
        AboutUs instance = new AboutUs(getHost()).load();
        assert(instance != null);
        
        String filename = TestHelpers.tempFile(Constants.API_ABOUT_US);
        
        assert(filename != null);
        
        TestHelpers.logMsg(methodName, "serializing class type " + instance.getClass());
        try {
            instance.serialize(filename);
        } catch (CRAException e){
            fail("Exception while serializing object: " + e.getMessage());
        }
        
        TestHelpers.logMsg(methodName, "deSerializing class type " + instance.getClass());
        
        try {
            AboutUs obj2 = instance.deSerialize(filename);
            assert(obj2 != null);
            assert(instance.equals(obj2));
        } catch (CRAException e){
            fail("Exception while deSerializing object: " + e.getMessage());
        }   
    }
    
}
