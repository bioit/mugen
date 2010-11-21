package com.arexis.alab.imp;

import com.arexis.alab.importerbean.ImporterRemote;
import java.rmi.RemoteException;
import javax.ejb.*;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.apache.log4j.Logger;

/**
 * This is the bean class for the ImpBean enterprise bean.
 * Created Mar 7, 2006 8:44:24 AM
 * @author heto
 */
public class ImpBean implements MessageDrivenBean, MessageListener {
    private MessageDrivenContext context;
    
    private static Logger logger = Logger.getLogger(ImpBean.class);
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click on the + sign on the left to edit the code.">
    
    /**
     * @see javax.ejb.MessageDrivenBean#setMessageDrivenContext(javax.ejb.MessageDrivenContext)
     */
    public void setMessageDrivenContext(MessageDrivenContext aContext) {
        context = aContext;
    }
    
    /**
     * See section 15.4.4 of the EJB 2.0 specification
     * See section 15.7.3 of the EJB 2.1 specification
     */
    public void ejbCreate() {
        // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise bean, Web services)
    }
    
    /**
     * @see javax.ejb.MessageDrivenBean#ejbRemove()
     */
    public void ejbRemove() {
        // TODO release any resource acquired in ejbCreate.
        // The code here should handle the possibility of not getting invoked
        // See section 15.7.3 of the EJB 2.1 specification
    }
    
    // </editor-fold>
    
    public void onMessage(Message aMessage) {
        // TODO handle incoming message
        // typical implementation will delegate to session bean or application service
        
        ObjectMessage om = (ObjectMessage)aMessage;
        long ts = System.currentTimeMillis();
        
        logger.debug(ts+": ImpBean MDB invoked");
        
        
        try {
            ImportMessage im = (ImportMessage)om.getObject();
            
            ImporterRemote importer = lookupImporterBean();
            
            if (im.mode == ImportMessage.CHECK)
                importer.check(im.jobid, im.attrs, im.caller);
            if (im.mode == ImportMessage.IMPORT)
                importer.imp(im.jobid, im.attrs, im.caller);
        
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
        
        logger.debug(ts+": ImpBean MDB complete!");
        
    }

    private com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitManagerRemote lookupSamplingUnitManagerBean() {
        try {
            return ((com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitManagerRemoteHome) com.arexis.alab.imp.ServiceLocator.getInstance().getRemoteHome("java:comp/env/ejb/SamplingUnitManagerBean",com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitManagerRemoteHome.class)).create();
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

    private com.arexis.mugen.model.modelmanager.ModelManagerRemote lookupModelManagerBean() {
        try {
            return ((com.arexis.mugen.model.modelmanager.ModelManagerRemoteHome) com.arexis.alab.imp.ServiceLocator.getInstance().getRemoteHome("java:comp/env/ejb/ModelManagerBean",com.arexis.mugen.model.modelmanager.ModelManagerRemoteHome.class)).create();
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

    private com.arexis.alab.importerbean.ImporterRemote lookupImporterBean() {
        try {
            return ((com.arexis.alab.importerbean.ImporterRemoteHome) com.arexis.alab.imp.ServiceLocator.getInstance().getRemoteHome("java:comp/env/ejb/ImporterBean",com.arexis.alab.importerbean.ImporterRemoteHome.class)).create();
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
    
}
