
package com.arexis.mugen.adminmanager;


/**
 * This is the home interface for AdminManager enterprise bean.
 */
public interface AdminManagerRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.adminmanager.AdminManagerRemote create()  throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    
}
