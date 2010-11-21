/*
 * DataFileImporterTest.java
 * JUnit based test
 *
 * Created on December 4, 2006, 8:45 AM
 */

package com.arexis.alab.imp.importer;

import junit.framework.*;
import com.arexis.agdbfiles.DataFileObject;
import com.arexis.agdbfiles.DataFileReader;
import com.arexis.agdbfiles.FileFormatException;
import com.arexis.agdbfiles.FormatHelper;
import com.arexis.agdbfiles.Report;
import com.arexis.alab.imp.importjob.ImportJobRemote;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import java.io.File;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttributes;
import org.apache.log4j.Logger;

/**
 *
 * @author se22519
 */
public class DataFileImporterTest extends TestCase
{
    
    public DataFileImporterTest(String testName)
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
     * Test of getImporter method, of class com.arexis.alab.imp.importer.DataFileImporter.
     */
    public void testGetImporter()
    {
        System.out.println("getImporter");
        
        com.arexis.agdbfiles.Format format = null;
        
        DataFileImporter expResult = null;
        DataFileImporter result = DataFileImporter.getImporter(format);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initImporters method, of class com.arexis.alab.imp.importer.DataFileImporter.
     */
    public void testInitImporters()
    {
        System.out.println("initImporters");
        
        DataFileImporter.initImporters();
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    

    

    

    

    
    
}
