
package com.arexis.mugen.samplingunit.samplingunitmanager;


/**
 * This is the home interface for SamplingUnitManager enterprise bean.
 */
public interface SamplingUnitManagerRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitManagerRemote create()  throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    
}
