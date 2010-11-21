package com.arexis.alab.importerbean;

import com.arexis.agdbfiles.DataFileReader;
import com.arexis.agdbfiles.FormatHelper;
import com.arexis.agdbfiles.ReadableReport;
import com.arexis.agdbfiles.Report;
import com.arexis.alab.imp.ImportMessage;
import com.arexis.alab.imp.ServiceLocator;
import com.arexis.alab.imp.importer.DataFileImporter;
import com.arexis.alab.imp.importjob.ImportAttributes;
import com.arexis.alab.imp.importjob.ImportJobRemote;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.exceptions.ExceptionLogUtil;
import com.arexis.mugen.exceptions.LoggableEJBException;
import com.arexis.mugen.resource.file.FileRemote;
import java.io.File;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.*;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttributes;
import javax.transaction.UserTransaction;
import org.apache.log4j.Logger;

/**
 * This is the bean class for the ImportProcessorBean enterprise bean.
 * Created Mar 7, 2006 8:43:27 AM
 * @author heto
 */
public class ImporterBean implements SessionBean, ImporterRemoteBusiness
{
    private SessionContext context;
    
    private ServiceLocator locator;
    
    //private Parser[] parsers;
    private static Logger logger = Logger.getLogger(ImporterBean.class);
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise bean, Web services)
    // TODO Add business methods or web service operations
    /**
     * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
     */
    public void setSessionContext(SessionContext aContext)
    {
        context = aContext;
    }
    
    /**
     * @see javax.ejb.SessionBean#ejbActivate()
     */
    public void ejbActivate()
    {
        
    }
    
    /**
     * @see javax.ejb.SessionBean#ejbPassivate()
     */
    public void ejbPassivate()
    {
        
    }
    
    /**
     * @see javax.ejb.SessionBean#ejbRemove()
     */
    public void ejbRemove()
    {
        
    }
    // </editor-fold>
    
    /**
     * See section 7.10.3 of the EJB 2.0 specification
     * See section 7.11.3 of the EJB 2.1 specification
     */
    public void ejbCreate()
    {
        // TODO implement ejbCreate if necessary, acquire resources
        // This method has access to the JNDI context so resource aquisition
        // spanning all methods can be performed here such as home interfaces
        // and data sources.
        locator = ServiceLocator.getInstance();
    }
    
    
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
    
    /**
     * Use this method to start a import in background.
     * This calls the message bean.
     */
    public void startImport(int jobid, MugenCaller caller) throws ApplicationException
    {
        try
        {
            logger.debug("StartImport");
            ImportJobRemote job = lookupImportJobBean().findByPrimaryKey(new Integer(jobid));
            
            if (!job.getStatus().equals("CHK"))
                throw new ApplicationException("Check has failed on this job. Please correct the error and check again");
            
            // Store the db-file 
            File file = lookupResourceManagerBean().getDiskFile(job.getFile().getFileId(), caller);
            DataFileImporter importer = DataFileImporter.getImporter(file);
            file.delete();
            
            if (importer==null)
                throw new ApplicationException("Cannot import this file. Could not find an importer for this file");
            
            ImportAttributes attrs = job.getAttributes();
            
            ImportMessage msg = new ImportMessage();
            msg.caller = (MugenCaller)caller;
            //msg.fileid = job.getFile().getFileId();
            msg.jobid = jobid;
            msg.attrs = attrs;
            msg.mode = ImportMessage.IMPORT;
            
            sendJMSMessageToImpDestination(msg);
            
            logger.debug("StartImport message sent");
        }
        catch (ApplicationException ae)
        {
            logger.error("startImport", ae);
            throw ae;
        }
        catch (NamingException ex)
        {
            logger.error("startImport", ex);
            throw new EJBException(ex);
        }
        catch (FinderException ex)
        {
            logger.error("startImport", ex);
            throw new EJBException(ex);
        }
        catch (JMSException ex)
        {
            logger.error("startImport", ex);
            throw new EJBException(ex);
        }
        catch (RemoteException e)
        {
            throw ExceptionLogUtil.createLoggableEJBException(e);
        }
    }
    
    /**
     * Use this method to start a check in background.
     * This calls the message bean.
     */
    public ImportJobRemote startCheck(MugenCaller caller, int fileid, BasicAttributes attrs) throws ApplicationException
    {
        ImportJobRemote job = null;
        try
        {
            logger.info("startCheck started");
            
            job = lookupImportJobBean().create(fileid,caller.getId(),fileid,caller.getPid());
            
            job.setAttributes(new ImportAttributes(attrs));
            logger.debug("AttrData="+new ImportAttributes(attrs).getData());
            
            /*
            ImportAttributes x = new ImportAttributes();
            x.put("namn","tobias");
            x.put("mail","tobias.terstad@biovitrum.com");
            job.setAttributes(x);
             */
            
            ImportMessage msg = new ImportMessage();
            msg.caller = caller;
            msg.jobid = job.getJobId();
            //msg.fileid = fileid;
            msg.attrs = attrs;
            msg.mode = ImportMessage.CHECK;
            
            sendJMSMessageToImpDestination(msg);
            
            logger.info("startCheck ended");
        }
        catch (Exception ce)
        {
            logger.error("startCheck", ce);
            throw new ApplicationException("Failed to start check of the file. ",ce);
        }
        return job;
    }
    
    /**
     * This is only started in a background process!
     */
    public void check(int jobid, BasicAttributes attribs, MugenCaller caller)
    {
        logger.info("Check started");
        ImportJobRemote job = null;
        try
        {
            
            job = lookupImportJobBean().findByPrimaryKey(new Integer(jobid));
            job.setStatus("Checking");
            
            // Store the db-file 
            File file = lookupResourceManagerBean().getDiskFile(job.getFile().getFileId(), caller);
            
            FormatHelper fh = new FormatHelper();
            
            DataFileReader reader = fh.getReader(file);
            Report report = reader.check();
            
            int items = reader.getNumberOfItems();
            logger.debug("number of items = "+items);
            job.setItems(items);
            
            // Delete file then done?
            file.delete();
            
            logger.info(new ReadableReport(report));
            
            if (!report.hasError())
                job.setStatus("CHK");
            else 
                job.setStatus("ERROR");
            
            
            job.setReportString(new ReadableReport(report).toString()); 
        }
        catch (Exception e)
        {
            logger.error("check failed", e);
            
            try
            {
                job.setStatus("ERROR");
                logger.info("status set to error");
            }
            catch (Exception e2)
            {
                logger.error("failed to set status", e2);
            }
            
        }
        finally
        {
            logger.info("Check finished");
        }
    }
    
    public void imp(int jobid, BasicAttributes attribs, MugenCaller caller)
    {
        logger.debug("Import started");
        UserTransaction ut = null;
        boolean rollback = true;
        try
        {
            ImportJobRemote job = lookupImportJobBean().findByPrimaryKey(new Integer(jobid));
            job.setStatus("Importing");
            
            // Store the db-file 
            File file = lookupResourceManagerBean().getDiskFile(job.getFile().getFileId(), caller);
            
            DataFileImporter importer = DataFileImporter.getImporter(file);
            
            
              
            ut = this.context.getUserTransaction();
            ut.begin();
            logger.debug("Transaction begins");
            
            
            // Set attributes
            importer.setAttributes(attribs);
            importer.init();
            importer.setCaller(caller);
            
            // Run the check on the file
            importer.imp(file, job);
            // Delete file then done?
            file.delete();
            
            // Get some sort of report
            Report report = importer.getReport();
            if (report==null)
                throw new ApplicationException("Report object is not valid");
            
            
            if (!report.hasError())
            {
                ut.commit();
                logger.info("Transaction Commit");
                job.setStatus("DONE");
            }
            else
            {
                ut.rollback();
                logger.warn("Transaction Rollback");
                job.setStatus("ERROR");
            }    
        
            // Print the report to log
            logger.info("Report:\n" + new ReadableReport(report));
            
            job.setReportString(new ReadableReport(report).toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Exception in importer code. Do rollback",e);
            try
            {
                ut.rollback();
            }
            catch (Exception e2)
            {
                logger.error("Rollback failed after exception", e2);
            }
        }
        finally
        {
            logger.debug("Import finished");        
        }
    }
    
    private javax.jms.Message createJMSMessageForImpDestination(javax.jms.Session session, java.lang.Object messageData) throws javax.jms.JMSException
    {
        javax.jms.ObjectMessage om = session.createObjectMessage();
        om.setObject((Serializable)messageData);
        return om;
    }
    
    private void sendJMSMessageToImpDestination(java.lang.Object messageData) throws javax.naming.NamingException, javax.jms.JMSException
    {
        javax.jms.ConnectionFactory cf = (javax.jms.ConnectionFactory) com.arexis.alab.imp.ServiceLocator.getInstance().getConnectionFactory("java:comp/env/jms/ImpDestinationFactory");
        javax.jms.Connection conn = null;
        javax.jms.Session s = null;
        try
        {
            conn = cf.createConnection();
            s = conn.createSession(false,s.AUTO_ACKNOWLEDGE);
            javax.jms.Destination destination = (javax.jms.Destination) com.arexis.alab.imp.ServiceLocator.getInstance().getDestination("java:comp/env/jms/ImpDestination");
            javax.jms.MessageProducer mp = s.createProducer(destination);
            mp.send(createJMSMessageForImpDestination(s,messageData));
        }
        finally
        {
            if (s != null)
            {
                s.close();
            }
            if (conn != null)
            {
                conn.close();
            }
        }
    }
    
    
    private com.arexis.mugen.resource.resourcemanager.ResourceManagerRemote lookupResourceManagerBean()
    {
        try {
            return ((com.arexis.mugen.resource.resourcemanager.ResourceManagerRemoteHome) com.arexis.alab.imp.ServiceLocator.getInstance().getRemoteHome("java:comp/env/ejb/ResourceManagerBean",com.arexis.mugen.resource.resourcemanager.ResourceManagerRemoteHome.class)).create();
        } catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        } catch(javax.ejb.CreateException ce) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ce);
            throw new RuntimeException(ce);
        } catch(java.rmi.RemoteException re) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,re);
            throw new RuntimeException(re);
        }
    }
    
    private com.arexis.alab.imp.importjob.ImportJobRemoteHome lookupImportJobBean()
    {
        try
        {
            return (com.arexis.alab.imp.importjob.ImportJobRemoteHome) com.arexis.alab.imp.ServiceLocator.getInstance().getRemoteHome("java:comp/env/ejb/ImportJobBean",com.arexis.alab.imp.importjob.ImportJobRemoteHome.class);
        }
        catch(javax.naming.NamingException ne)
        {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }
    
    /**
     * Get import jobs for a user in a project
     *
     * @param user is the user id
     * @param pid is the project id
     * @return a collection of ImImportJobDTO
     */
    public Collection getImportJobs(int user, int pid)
    {
        Collection arr = new ArrayList();
        try
        {
            Collection jobs = lookupImportJobBean().findByUserAndProject(pid,user);
            Iterator i = jobs.iterator();
            while (i.hasNext())
            {
                arr.add(new ImportJobDTO((ImportJobRemote)i.next()));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("getImportJob failed", e);
        }
        return arr;
    }
    
    public boolean inProgress(int user, int pid)
    {
        Collection jobs = getImportJobs(user, pid);
        Iterator i = jobs.iterator();
        while (i.hasNext())
        {
            ImportJobDTO job = (ImportJobDTO)i.next();
            if (job.getStatus().startsWith("Importing") || job.getStatus().startsWith("Checking"))
                return true;
        }
        return false;
    }
    
    
    public ImportJobDTO getImportJob(int jobid, MugenCaller caller) throws ApplicationException
    {
        try
        {
            ImportJobRemote job = lookupImportJobBean().findByPrimaryKey(new Integer(jobid));
            ImportJobDTO dto = new ImportJobDTO(job);
            
            return dto;
        }
        catch (RemoteException ex)
        {
            logger.error("Could not get job",ex);
            throw new ApplicationException("Could not get job.");
        }
        catch (FinderException ex)
        {
            logger.error("finder exception", ex);
            throw new EJBException("Could not find the job in database");
        }
        
    }
    
    /**
     * Remove all jobs for a user in a project. This removes the uploaded file
     * as well!
     */
    public void removeAllImportJobs(int user, int pid)
    {
        try
        {
            Collection jobs = lookupImportJobBean().findByUserAndProject(pid,user);
            Iterator i = jobs.iterator();
            while (i.hasNext())
            {
                ImportJobRemote job = (ImportJobRemote)i.next();
                FileRemote file = job.getFile();
                job.remove();
                file.remove();
            }
        }
        catch (Exception e)
        {
            logger.error("failed to remove all import jobs", e);
        }
    }
}
