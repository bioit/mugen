
package com.arexis.mugen.genotype.genotypemanager;


/**
 * This is the home interface for GenotypeManager enterprise bean.
 */
public interface GenotypeManagerRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.genotype.genotypemanager.GenotypeManagerRemote create()  throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    
}
