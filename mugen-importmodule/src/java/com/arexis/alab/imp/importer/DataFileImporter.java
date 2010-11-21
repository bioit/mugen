/*
 * DataFileImporter.java
 *
 * Created on October 24, 2006, 9:04 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.arexis.alab.imp.importer;

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
public abstract class DataFileImporter
{
    private static Logger logger = Logger.getLogger(DataFileImporter.class);
    
    /**
     * All attributes available for the user then importing file.
     */
    protected BasicAttributes attrs;
    
    /**
     * The report of the check or import. Warnings and errors are shown.
     */
    protected Report report;
    
    /**
     * The caller object
     */
    protected MugenCaller caller;
    
    protected static HashMap importers;
    
    public static DataFileImporter getImporter(com.arexis.agdbfiles.Format format)
    {
        if (importers == null)
            initImporters();
        
        DataFileImporter imp = (DataFileImporter)importers.get(format);
        return imp;
    }
    
    public static DataFileImporter getImporter(File file)
    {
        com.arexis.agdbfiles.FormatHelper fh = new com.arexis.agdbfiles.FormatHelper();
        com.arexis.agdbfiles.Format format = fh.getFormat(file);
        return getImporter(format);
    }
    
    public static void initImporters()
    {
        importers = new HashMap();
        importers.put(new com.arexis.agdbfiles.Format("INDIVIDUAL","LIST","1"), new IndividualImporter());
        importers.put(new com.arexis.agdbfiles.Format("GROUPING","LIST","1"), new GroupingImporter());
        importers.put(new com.arexis.agdbfiles.Format("MARKER","LIST","1"), new MarkerImporter());
    }
    
    
    /** Creates a new instance of DataFileImporter */
    public DataFileImporter()
    {
        
    }
    
    public void init()
    {
        report = new Report();
    }
    
    public abstract void imp(DataFileObject obj) throws ApplicationException;
    
    
    //public abstract void imp(File file);
    public void imp(File file, ImportJobRemote job) throws ApplicationException
    {
        try 
        {
            FormatHelper fh = new FormatHelper();
            
            DataFileReader reader = fh.getReader(file);
            
            logger.debug("Number of objects = "+reader.getNumberOfItems());
            
            DataFileObject obj = reader.next();
            int i=0;
            while (obj!=null)
            {    
                imp(obj);
                obj = reader.next();
                i++;
                try
                {
                    //logger.debug("before pct: "+(i%100));
                    
                    if ((i%100)==0)
                    {
                        Double pct = new Double((i/(1.0*reader.getNumberOfItems()))*100);
                        logger.debug("after, pct="+pct.intValue());
                        job.setProgress(pct.intValue());
                    }
                }
                catch (RemoteException ex)
                {
                    logger.error("error", ex);
                    //ex.printStackTrace();
                }
            }
            
            String test = "";
            if (attrs==null)
                throw new Exception("Attributes is null");
            if (attrs.get("test")==null)
                test = "";
            else
                test = (String)attrs.get("test").get();
            
            if (test.equalsIgnoreCase("true"))
            {
                report.error("Test mode only, rollback all changes!");
            }
        }
        catch (NamingException e)
        {
            logger.error("Failed to get test parameter", e);
            report.error("Error in importer. Failed to import.");
        }
        catch (FileFormatException fe)
        {
            logger.error("Import aborted in DataFileImporter due to a file format error", fe);
            report.error("Error in file format. Failed to import.");
        }
        catch (ApplicationException e)
        {
            logger.error("Import aborted in DataFileImporter due to an application exception", e);
            report.error("Failed to import due to a program error.");
        }
        catch (Exception e)
        {
            logger.error("Import aborted in DataFileImporter due to an unknown exception", e);
            report.error("Failed to import due to a program error.");
            //report.add("xyz", Report.ERROR);
        }
        logger.debug("imp done.");
    }
    
    /**
     * Set the attributes to this object. All importers hace the attrs object 
     * available.
     * @param attrs Attributes objects with parameter data from user input.
     */
    public void setAttributes(BasicAttributes attrs)
    {
        this.attrs = attrs;
    }
    
    public void setCaller(MugenCaller caller)
    {
        this.caller = caller;
    }
    
    /**
     * Get the report from the Importer. This is used by the import system to get 
     * feedback to the user.
     * @return A report of warnings and errors.
     */
    public Report getReport()
    {
        return report;
    }
}
