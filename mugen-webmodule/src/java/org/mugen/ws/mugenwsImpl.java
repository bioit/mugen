package org.mugen.ws;

import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.modelmanager.ModelManagerRemote;
import com.arexis.mugen.servicelocator.ServiceLocator;
import org.mugen.dtos.*;

public class mugenwsImpl implements mugenwsSEI {
    
    public java.lang.String getProjectName() throws java.rmi.RemoteException {
        ModelManagerRemote modelManager = lookupModelManagerBean();
        return modelManager.getProjectName();
    }
    
    public java.lang.String[] getMugenMice() throws java.rmi.RemoteException {
        ModelManagerRemote modelManager = lookupModelManagerBean();
        
        try {
            String[] models = modelManager.getMugenMice();
            return models;
        } catch (com.arexis.mugen.exceptions.ApplicationException e){
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public java.lang.String[] getMugenMiceByEnsembl(String ensembl) throws java.rmi.RemoteException {
        ModelManagerRemote modelManager = lookupModelManagerBean();
        
        try {
            String[] models = modelManager.getMugenMiceByEnsembl(ensembl);
            return models;
        } catch (com.arexis.mugen.exceptions.ApplicationException e){
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public MugenModelDTO[] getMugenMiceDTO() throws java.rmi.RemoteException {
        ModelManagerRemote modelManager = lookupModelManagerBean();
        try {
            MugenModelDTO[] models = modelManager.getMugenMiceDTO();
            return models;
        } catch (com.arexis.mugen.exceptions.ApplicationException e){
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public MugenModelDTO[] getMugenMiceDTOByKey(String key) throws java.rmi.RemoteException {
        ModelManagerRemote modelManager = lookupModelManagerBean();
        try {
            MugenModelDTO[] models = modelManager.getMugenMiceDTOByKey(key);
            return models;
        } catch (com.arexis.mugen.exceptions.ApplicationException e){
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public MugenGeneDTO[] getMugenGenesByModel(int eid) throws java.rmi.RemoteException { 
        ModelManagerRemote modelManager = lookupModelManagerBean();
        try {
            MugenGeneDTO[] genes = modelManager.getMugenGenesByModel(eid);
            return genes;
        } catch (com.arexis.mugen.exceptions.ApplicationException e){
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public MugenAvailabilityDTO[] getMugenAvailabilityByModel(int eid) throws java.rmi.RemoteException {
        ModelManagerRemote modelManager = lookupModelManagerBean();
        try {
            MugenAvailabilityDTO[] avs = modelManager.getMugenAvailabilityByModel(eid);
            return avs;
        } catch (com.arexis.mugen.exceptions.ApplicationException e){
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public MugenBackgroundDTO[] getMugenBackgroundByModel(int eid) throws java.rmi.RemoteException {
        ModelManagerRemote modelManager = lookupModelManagerBean();
        try {
            MugenBackgroundDTO[] backs = modelManager.getMugenBackgroundByModel(eid);
            return backs;
        } catch (com.arexis.mugen.exceptions.ApplicationException e){
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public MugenPhenotypesDTO[] getMugenPhenotypesByModel(int eid) throws java.rmi.RemoteException {
        ModelManagerRemote modelManager = lookupModelManagerBean();
        try {
            MugenPhenotypesDTO[] phenos = modelManager.getMugenPhenotypesByModel(eid);
            return phenos;
        } catch (com.arexis.mugen.exceptions.ApplicationException e){
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private com.arexis.mugen.model.modelmanager.ModelManagerRemote lookupModelManagerBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/ModelManagerBean");
            com.arexis.mugen.model.modelmanager.ModelManagerRemoteHome rv = (com.arexis.mugen.model.modelmanager.ModelManagerRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.model.modelmanager.ModelManagerRemoteHome.class);
            return rv.create();
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
