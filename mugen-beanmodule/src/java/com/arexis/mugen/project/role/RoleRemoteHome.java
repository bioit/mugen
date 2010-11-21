
package com.arexis.mugen.project.role;

import com.arexis.mugen.project.project.ProjectRemote;


/**
 * This is the home interface for Role enterprise bean.
 */
public interface RoleRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.project.role.RoleRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.project.role.RoleRemote create(int rid, ProjectRemote project, java.lang.String name, java.lang.String comm) throws javax.ejb.CreateException, java.rmi.RemoteException;

    java.util.Collection findByProject(int pid) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
