/*
 * GroupingImporter.java
 *
 * Created on March 7, 2006, 2:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.arexis.alab.imp.importer;

import com.arexis.agdbfiles.DataFileObject;
import com.arexis.agdbfiles.Report;
import com.arexis.agdbfiles.grouping.Grouping;
import com.arexis.agdbfiles.grouping.Grp;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitManagerRemote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

/**
 * Import grouping data to the database
 * @author heto
 */
public class GroupingImporter extends DataFileImporter {
    
    /** The sampling unit id this operation works on */
    private int suid;
    
    /** The sampling unit manager */
    private SamplingUnitManagerRemote samplingUnitManager;
    
    /** Logger object */
    private static Logger logger = Logger.getLogger(GroupingImporter.class);
    
    public GroupingImporter()
    {
        super();
        suid = 0;
    }
    
    public void init()
    {
        super.init();
        try {
            suid = new Integer((String)attrs.get("suid").get()).intValue();
            samplingUnitManager = lookupSamplingUnitManagerBean();
        } catch (NumberFormatException ex) {
            logger.error("Init failed, could not get suid as integer",ex);
        } catch (NamingException ex) {
            logger.error("Could not lookup samplingUnitManager",ex);
        }
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
    
    public void imp(DataFileObject dfo) throws ApplicationException
    {
        logger.debug("importing grouping information");
        Grouping grouping = (Grouping)dfo;
        try
        {        
            String mode = (String)attrs.get("mode").get();

            if (mode!=null && mode.equals("create"))
            {
                logger.debug("grouping.getGroups().size()="+grouping.getGroups().size());
                for (int j=0;j<grouping.getGroups().size();j++)
                {
                    Grp grp = (Grp)grouping.getGroups().get(j);
            
                    samplingUnitManager.createGroupInfo(grp.grouping, grp.group, suid, grouping.getIdentity(), caller);        
                }
                //logger.debug("Done creating groups and groupings");
            }
            else
            {
                throw new ApplicationException("Mode "+mode+" is not implemented in GroupingImporter");
            }
        }
        catch (RemoteException e)
        {
            logger.error("Failed to import grouping data", e);
            report.add("Failed to import grouping data",Report.ERROR);
            throw new ApplicationException("Remote failure", e);
        }
        catch (NamingException e)
        {
            logger.error("Failed to import grouping data.", e);
            throw new ApplicationException("Failed to get attributes");
        }
        catch (ApplicationException e)
        {
            report.add(e.getMessage(), Report.ERROR, dfo);
        }
    }
}
