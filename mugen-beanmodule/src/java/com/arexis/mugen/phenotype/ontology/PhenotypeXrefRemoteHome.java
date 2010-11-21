
package com.arexis.mugen.phenotype.ontology;

import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;


/**
 * This is the home interface for PhenotypeXref enterprise bean.
 */
public interface PhenotypeXrefRemoteHome extends EJBHome {
    
    PhenotypeXrefRemote findByPrimaryKey(Integer key)  throws FinderException, RemoteException;
    
    PhenotypeXrefRemote create(int id, String xref) throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    java.util.Collection findByNoRestriction() throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    PhenotypeXrefRemote findByXrefName(String xref)  throws FinderException, RemoteException;
}
