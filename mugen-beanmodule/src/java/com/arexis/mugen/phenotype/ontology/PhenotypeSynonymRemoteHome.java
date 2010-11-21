
package com.arexis.mugen.phenotype.ontology;

import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;


/**
 * This is the home interface for PhenotypeSynonym enterprise bean.
 */
public interface PhenotypeSynonymRemoteHome extends EJBHome {
    
    PhenotypeSynonymRemote findByPrimaryKey(Integer key)  throws FinderException, RemoteException;
    
    PhenotypeSynonymRemote create(int id, String synonym) throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    java.util.Collection findByNoRestriction() throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByPhenoId(int mpid) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    PhenotypeSynonymRemote findBySynonym(String synonym)  throws FinderException, RemoteException;
}
