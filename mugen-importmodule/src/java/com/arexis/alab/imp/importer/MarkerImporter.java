/*
 * MarkerImporter.java
 *
 * Created on March 8, 2006, 11:24 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.arexis.alab.imp.importer;

import com.arexis.agdbfiles.DataFileObject;
import com.arexis.agdbfiles.Report;
import com.arexis.agdbfiles.marker.Allele;
import com.arexis.agdbfiles.marker.Marker;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.exceptions.CreateException;
import com.arexis.mugen.genotype.genotypemanager.GenotypeManagerRemote;
import java.util.ArrayList;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

/**
 * Import marker data to the database
 * @author heto
 */
public class MarkerImporter extends DataFileImporter {
    
    /** The logger object */
    private static Logger logger = Logger.getLogger(MarkerImporter.class);
    
    /** The sampling unit id */
    private int suid;
    
    /** Creates a new instance of MarkerImporter */
    public MarkerImporter() {
        super();
        suid = 0;
    }
    
    /** The genotype manager object */
    private GenotypeManagerRemote genotypeManager;
    
    /**
     * Initialize this import
     */
    public void init()
    {
        super.init();
        try {
            suid = new Integer((String)attrs.get("suid").get()).intValue();
            genotypeManager = lookupGenotypeManagerBean();
        } catch (NumberFormatException ex) {
            logger.error("Failed to init. suid could not be parsed to integer", ex);
        } catch (NamingException ex) {
            logger.error("Failed to lookup genotype manager", ex);
        }
    }
    
    /**
     * Import an Marker object to the database.
     * @param dfo is the DataFileObject as an Marker.
     * @throws com.arexis.mugen.exceptions.ApplicationException if a serious error occurs and
     * the import needs to be aborted.
     */
    public void imp(DataFileObject dfo) throws ApplicationException
    {
        Marker m = (Marker)dfo;
        try
        {        
            String mode = (String)attrs.get("mode").get();
            logger.debug("importing marker data to suid="+suid);

            if (mode!=null && mode.equals("create"))
            {
                if (genotypeManager.markerExists(m.getMarker(), suid, caller))
                    throw new ApplicationException("Marker "+m.getMarker()+" already exists");
                
                int mid = genotypeManager.createMarkerImport(m.getMarker(),
                        m.getComment(),
                        m.getPrimer1(),
                        m.getPrimer2(),
                        m.getPosition(),
                        m.getAlias(),
                        m.getChromosome(),
                        suid, caller);

                ArrayList alleles = m.getAlleles(); 
                for (int i=0;i<alleles.size();i++)
                {
                    Allele a = (Allele)alleles.get(i);
                    genotypeManager.createAllele(caller, a.getValue(), a.getComment(), mid);
                }
            }
            else if (mode!=null && mode.equals("update"))
            {
                if (!genotypeManager.markerExists(m.getMarker(), suid, caller))
                    throw new ApplicationException("Marker "+m.getMarker()+" does not exists");
                
                genotypeManager.updateMarkerImport(m.getMarker(),
                        m.getComment(),
                        m.getPrimer1(),
                        m.getPrimer2(),
                        m.getPosition(),
                        m.getAlias(),
                        m.getChromosome(),
                        suid, caller);
                
                ArrayList alleles = m.getAlleles(); 
                if (alleles.size()>0)
                    report.add("Ignoring allele in create or update. Not implemented", Report.WARN, dfo);                
            }
            else if (mode!=null && mode.equals("create_or_update"))
            {
                genotypeManager.createOrUpdateMarkerImport(m.getMarker(),
                        m.getComment(),
                        m.getPrimer1(),
                        m.getPrimer2(),
                        m.getPosition(),
                        m.getAlias(),
                        m.getChromosome(),
                        suid, caller);
                
                ArrayList alleles = m.getAlleles();
                if (alleles.size()>0)
                    report.add("Ignoring allele in create or update. Not implemented", Report.WARN, dfo);
            }
        }
        catch (CreateException e)
        {
            report.add(e.getMessage(), Report.ERROR, dfo);
            report.add("Aborting...", Report.ERROR);
            throw new ApplicationException("Failed to import marker data. Aborting...");
        }
        catch (ApplicationException e)
        {
            report.add(e.getMessage(), Report.ERROR, dfo);
        }
        catch (Exception e)
        {
            report.add("Failed to import data", report.ERROR, dfo);
            throw new ApplicationException("Failed to import marker data.");
        }
    }

    private com.arexis.mugen.genotype.genotypemanager.GenotypeManagerRemote lookupGenotypeManagerBean()
    {
        try {
            return ((com.arexis.mugen.genotype.genotypemanager.GenotypeManagerRemoteHome) com.arexis.alab.imp.ServiceLocator.getInstance().getRemoteHome("java:comp/env/ejb/GenotypeManagerBean",com.arexis.mugen.genotype.genotypemanager.GenotypeManagerRemoteHome.class)).create();
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
