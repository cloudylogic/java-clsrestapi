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

import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Ken Lowrie
 */
public class ContactInfoTest {
    
    static String className;
    
    public ContactInfoTest() {
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
     * Test of equals method, of class ContactInfo.
     */
    @Test
    public void testEquals() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        
        TestHelpers.logMsg(methodName, "Running ...");
        ContactInfo instance1 = new ContactInfo().load();
        ContactInfo instance2 = new ContactInfo(Constants.WSURL).load();
        assert(instance1 != null);
        assert(instance2 != null);
        boolean expResult = true;
        boolean result = instance1.equals(instance2);
        assertEquals(expResult, result);
        assert(instance1 != instance2);
    }

    /**
     * Test of load method, of class ContactInfo.
     */
    @Test
    public void testLoad() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        
        TestHelpers.logMsg(methodName, "Running ...");
        ContactInfo instance = new ContactInfo();
        ContactInfo result = instance.load();
        assert(instance != null);
        assert(result instanceof ContactInfo);
    }

    /**
     * Test of toString method, of class ContactInfo.
     */
    @Test
    public void testToString() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        
        TestHelpers.logMsg(methodName, "Running ...");
        ContactInfo instance = new ContactInfo().load();
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
        ContactInfo instance = new ContactInfo().load();
        assert(instance != null);
        
        TestHelpers.logMsg(methodName, "checkDbgObjInstanceData");
        TestHelpers.checkDbgObjInstanceData(instance.dbgObj,Constants.API_CONTACT_INFO);

        TestHelpers.logMsg(methodName, "checkApiVerInstanceData");
        TestHelpers.checkApiVerInstanceData(instance.apiVer,Constants.API_CONTACT_INFO);

        TestHelpers.logMsg(methodName, "checkApiObjInstanceData");
        assert(instance.apiObj.location.startsWith("Cloudy Logic Studios")); 
        assert(instance.apiObj.address instanceof Address);
        assert(instance.apiObj.email.equals("info@cloudylogic.com"));
        assert(instance.apiObj.phone.equals("512.710.7257"));
        assert(instance.apiObj.socialNetworks.size() == 3);
        assert(instance.apiObj.socialNetworks.get(0) instanceof SocialNetwork);
        
    }
    
    @Test
    public void testSerialization() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        
        TestHelpers.logMsg(methodName, "Running ...");
        ContactInfo instance = new ContactInfo().load();
        assert(instance != null);
        
        String filename = TestHelpers.tempFile(Constants.API_CONTACT_INFO);
        
        assert(filename != null);
        
        TestHelpers.logMsg(methodName, "serializing class type " + instance.getClass());
        try {
            instance.serialize(filename);
        } catch (CRAException e){
            fail("Exception while serializing object: " + e.getMessage());
        }
        
        TestHelpers.logMsg(methodName, "deSerializing class type " + instance.getClass());
        
        try {
            ContactInfo obj2 = instance.deSerialize(filename);
            assert(obj2 != null);
            assert(instance.equals(obj2));
        } catch (CRAException e){
            fail("Exception while deSerializing object: " + e.getMessage());
        }   
    }
}
