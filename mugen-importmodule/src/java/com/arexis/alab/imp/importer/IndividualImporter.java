/*
 * IndividualImporter.java
 *
 * Created on March 7, 2006, 2:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.arexis.alab.imp.importer;

import com.arexis.agdbfiles.DataFileObject;
import com.arexis.agdbfiles.Report;
import com.arexis.agdbfiles.grouping.Grp;
import com.arexis.agdbfiles.individual.Individual;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.exceptions.IndividualNotFoundException;
import com.arexis.mugen.exceptions.SamplingUnitNotFoundException;
import com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitManagerRemote;
import java.util.ArrayList;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

/**
 * Import individual data to the database
 * @author heto
 */
public class IndividualImporter extends DataFileImporter {
    
    /** The sampling unit id */
    private int suid;  
    
    /** The sampling unit manager */
    private SamplingUnitManagerRemote samplingUnitManager;
    
    /** The logger object */
    private static Logger logger = Logger.getLogger(IndividualImporter.class);
    
    /** Create a new instance of the IndividualImporter */
    public IndividualImporter()
    {
        super();
        suid = 0;
    }
    
    /**
     * Initialize the importer
     */
    public void init()
    {
        super.init();
        try {
            suid = new Integer((String)attrs.get("suid").get()).intValue();
            samplingUnitManager = lookupSamplingUnitManagerBean();
        } catch (NumberFormatException ex) {
            logger.error("Suid could not be converted to an int", ex);
        } catch (NamingException ex) {
            logger.error("Could not lookup and find the sampling unit manager", ex);
        }
    }
    
    /**
     * Import a Individual object to the database.
     * @param dfo is the Indiviual object
     * @throws com.arexis.mugen.exceptions.ApplicationException if an error occurs and 
     * the import should be aborted.
     */
    public void imp(DataFileObject dfo) throws ApplicationException //throws NamingException, RemoteException, ApplicationException
    {
        Individual ind = (Individual)dfo;
        try
        {
            String mode = (String)attrs.get("mode").get();
            //logger.info("test logger: Mode = "+mode);
            if (mode!=null && mode.equals("create"))
            {
                if (samplingUnitManager.individualExists(ind.getIdentity(), suid, caller))
                    throw new ApplicationException("Individual "+ind.getIdentity()+" already exists");
                
                samplingUnitManager.createNewIndividual(suid, caller,
                        ind.getIdentity(),
                        ind.getAlias(),
                        ind.getSex(),
                        ind.getFather(),
                        ind.getMother(),
                        ind.getBirthDate(),
                        ind.getComment());

                for (int j=0;j<ind.getGroups().size();j++)
                {
                    Grp grp = (Grp)ind.getGroups().get(j);
                    try
                    {
                        samplingUnitManager.createGroupInfo(grp.grouping, grp.group, suid, ind.getIdentity(), caller);
                    }
                    catch (Exception e)
                    {
                        logger.error("Failed to create group info",e);
                    }
                }
            }
            else if (mode!=null && mode.equals("update"))
            {
                String status = "E";
                if (!samplingUnitManager.individualExists(ind.getIdentity(), suid, caller))
                    throw new ApplicationException("Individual "+ind.getIdentity()+" does not exist");

                samplingUnitManager.updateIndividualFromIdentity(suid,
                        ind.getIdentity(),
                        ind.getAlias(),
                        ind.getSex(),
                        status,
                        ind.getFather(),
                        ind.getMother(),
                        ind.getBirthDate(),
                        ind.getComment(),
                        caller);
                
                report.add("Ignoring group and groupings info. Not implemented in update mode", Report.WARN, dfo);
            }
            else if (mode!=null && mode.equals("create_or_update"))
            {
                samplingUnitManager.createOrUpdateIndividual(suid, 
                        caller, 
                        ind.getIdentity(), 
                        ind.getAlias(),
                        ind.getSex(),
                        ind.getFather(),
                        ind.getMother(),
                        ind.getBirthDate(),
                        ind.getComment());
                
                report.add("Ignoring group and groupings info. Not implemented in create or update mode", Report.WARN, dfo);
            }
        }
        catch (IndividualNotFoundException e)
        {
            report.add("Cannot find individual", Report.ERROR, dfo);
        }
        catch (SamplingUnitNotFoundException e)
        {
            report.add("Cannot find the sampling unit for this import", Report.ERROR);
            throw e;
        }
        catch (ApplicationException e)
        {
            report.add(e.getMessage(), Report.ERROR, dfo);
        }
        catch (Exception e)
        {
            report.add("Failed to import data", Report.ERROR, dfo);
            throw new ApplicationException("Could not import individual data.", e);
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
}
