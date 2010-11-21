
package com.arexis.mugen.export;


/**
 * This is the home interface for ExportManager enterprise bean.
 */
public interface ExportManagerRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.export.ExportManagerRemote create()  throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    
}
