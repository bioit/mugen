
package com.arexis.mugen.phenotype.phenotypemanager;


/**
 * This is the home interface for PhenotypeManager enterprise bean.
 */
public interface PhenotypeManagerRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.phenotype.phenotypemanager.PhenotypeManagerRemote create()  throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    
}
