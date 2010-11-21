package com.arexis.mugen.phenotype.ontology;

import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface PhenotypeAltIdRemoteHome extends EJBHome {
    
    PhenotypeAltIdRemote findByPrimaryKey(Integer key)  throws FinderException, RemoteException;
    
    PhenotypeAltIdRemote create(int id, String alt_id) throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    java.util.Collection findByNoRestriction() throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByPhenoId(int pheno_id) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    PhenotypeAltIdRemote findByAltId(String alt_id)  throws FinderException, RemoteException;
}
