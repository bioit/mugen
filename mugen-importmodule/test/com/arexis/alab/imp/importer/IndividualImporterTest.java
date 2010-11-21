/*
 * IndividualImporterTest.java
 * JUnit based test
 *
 * Created on December 4, 2006, 8:45 AM
 */

package com.arexis.alab.imp.importer;

import junit.framework.*;
import com.arexis.agdbfiles.DataFileObject;
import com.arexis.agdbfiles.Report;
import com.arexis.mugen.MugenCaller;
import javax.naming.directory.BasicAttributes;

/**
 *
 * @author se22519
 */
public class IndividualImporterTest extends TestCase
{
    
    public IndividualImporterTest(String testName)
    {
        super(testName);
    }

    protected void setUp() throws Exception
    {
    }

    protected void tearDown() throws Exception
    {
    }

    /**
     * Test of init method, of class com.arexis.alab.imp.importer.IndividualImporter.
     */
    public void testInit()
    {
        System.out.println("init");
        
        IndividualImporter instance = new IndividualImporter();
        
        instance.init();
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of imp method, of class com.arexis.alab.imp.importer.IndividualImporter.
     */
    public void testImp() throws Exception
    {
        System.out.println("imp");
        
        DataFileObject dfo = null;
        IndividualImporter instance = new IndividualImporter();
        
        instance.imp(dfo);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of getReport method, of class com.arexis.alab.imp.importer.DataFileImporter.
     */
    public void testGetReport()
    {
        System.out.println("getReport");
        
        IndividualImporter instance = new IndividualImporter();
        
        Report expResult = null;
        Report result = instance.getReport();
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of setCaller method, of class com.arexis.alab.imp.importer.DataFileImporter.
     */
    public void testSetCaller()
    {
        System.out.println("setCaller");
        
        MugenCaller caller = null;
        IndividualImporter instance = new IndividualImporter();
        
        instance.setCaller(caller);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of setAttributes method, of class com.arexis.alab.imp.importer.DataFileImporter.
     */
    public void testSetAttributes()
    {
        System.out.println("setAttributes");
        
        BasicAttributes attrs = null;
        IndividualImporter instance = new IndividualImporter();
        
        instance.setAttributes(attrs);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    
    
   
    
    
}
