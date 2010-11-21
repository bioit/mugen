
package com.arexis.mugen.phenotype.ontology;

import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface PhenotypeOntologyPathRemoteHome extends EJBHome {
    
    PhenotypeOntologyPathRemote findByPrimaryKey(Integer key)  throws FinderException, RemoteException;
    
    java.util.Collection findByModel(int eid) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByRoot(String root) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByEndNode(String endnode) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByAllele(int allid) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByGene(int gaid) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    PhenotypeOntologyPathRemote create(int ppid, String path) throws javax.ejb.CreateException, java.rmi.RemoteException;
}
