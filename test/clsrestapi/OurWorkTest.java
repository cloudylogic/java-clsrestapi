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
public class OurWorkTest {

    static String className;
    
    public OurWorkTest() {
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
     * Test of load method, of class OurWork.
     */
    @Test
    public void testLoad() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        
        TestHelpers.logMsg(methodName, "Running ...");
        OurWork instance = new OurWork(getHost());
        OurWork result = instance.load();
        assert(instance != null);
        assert(result instanceof OurWork);
    }

    /**
     * Test of equals method, of class OurWork.
     */
    @Test
    public void testEquals() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        
        TestHelpers.logMsg(methodName, "Running ...");
        OurWork instance1 = new OurWork(getHost()).load();
        OurWork instance2 = new OurWork(getHost()).load();
        assert(instance1 != null);
        assert(instance2 != null);
        boolean expResult = true;
        boolean result = instance1.equals(instance2);
        assertEquals(expResult, result);
        assert(instance1 != instance2);
    }

    /**
     * Test of toString method, of class OurWork.
     */
    @Test
    public void testToString() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        
        TestHelpers.logMsg(methodName, "Running ...");
        OurWork instance = new OurWork(getHost()).load();
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
        
        OurWork instance = new OurWork(getHost()).load();
        assert(instance != null);
        
        TestHelpers.logMsg(methodName, "checkDbgObjInstanceData");
        TestHelpers.checkDbgObjInstanceData(instance.dbgObj,Constants.API_OUR_WORK);

        TestHelpers.logMsg(methodName, "checkApiVerInstanceData");
        TestHelpers.checkApiVerInstanceData(instance.apiVer,Constants.API_OUR_WORK, "1.0", "1.1");

        TestHelpers.logMsg(methodName, "checkApiObjInstanceData");
        assert(instance.apiObj.numVideos == 8);
        assert(instance.apiObj.videoList.get(0) instanceof ShowCaseVideo);
        ShowCaseVideo scvideo = instance.apiObj.videoList.get(0);
        
        assert(!scvideo.description.isEmpty());
        assert(!scvideo.frame.isEmpty());
        assert(!scvideo.url.isEmpty());
        assert(!scvideo.sUrl.isEmpty());
        assert(!scvideo.thumb.isEmpty());
        assert(!scvideo.title.isEmpty());
        assert(!scvideo.type.isEmpty());
        assert(scvideo.roles instanceof Roles);
        TestHelpers.logMsg(methodName, "video[0].title = " + scvideo.title);
        TestHelpers.logMsg(methodName, "video[0].type = " + scvideo.type);
    }    
    
    @Test
    public void testSerialization() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        
        TestHelpers.logMsg(methodName, "Running ...");
        OurWork instance = new OurWork(getHost()).load();
        assert(instance != null);
        
        String filename = TestHelpers.tempFile(Constants.API_OUR_WORK);
        
        assert(filename != null);
        
        TestHelpers.logMsg(methodName, "serializing class type " + instance.getClass());
        try {
            instance.serialize(filename);
        } catch (CRAException e){
            fail("Exception while serializing object: " + e.getMessage());
        }
        
        TestHelpers.logMsg(methodName, "deSerializing class type " + instance.getClass());
        
        try {
            OurWork obj2 = instance.deSerialize(filename);
            assert(obj2 != null);
            assert(instance.equals(obj2));
        } catch (CRAException e){
            fail("Exception while deSerializing object: " + e.getMessage());
        }   
    }
}
