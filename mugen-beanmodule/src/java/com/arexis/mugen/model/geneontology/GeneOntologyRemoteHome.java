
package com.arexis.mugen.model.geneontology;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.model.geneticmodification.GeneticModificationRemote;


/**
 * This is the home interface for GeneOntology enterprise bean.
 */
public interface GeneOntologyRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.model.geneontology.GeneOntologyRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByGeneticModification(int gmid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.model.geneontology.GeneOntologyRemote create(int goid, java.lang.String linkid, java.lang.String name, String comm, GeneticModificationRemote gm, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    
}
