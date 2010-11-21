
package com.arexis.mugen.project.projectmanager;


/**
 * This is the home interface for ProjectManager enterprise bean.
 */
public interface ProjectManagerRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.project.projectmanager.ProjectManagerRemote create()  throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    
}
