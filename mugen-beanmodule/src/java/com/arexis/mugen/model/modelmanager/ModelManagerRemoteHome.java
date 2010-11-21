
package com.arexis.mugen.model.modelmanager;


/**
 * This is the home interface for ModelManager enterprise bean.
 */
public interface ModelManagerRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.model.modelmanager.ModelManagerRemote create()  throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    
}
