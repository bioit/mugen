
package com.arexis.mugen.resource.resourcemanager;


/**
 * This is the home interface for ResourceManager enterprise bean.
 */
public interface ResourceManagerRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.resource.resourcemanager.ResourceManagerRemote create()  throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    
}
