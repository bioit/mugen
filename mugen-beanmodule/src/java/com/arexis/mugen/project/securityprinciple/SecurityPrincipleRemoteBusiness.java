
package com.arexis.mugen.project.securityprinciple;

import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.role.RoleRemote;
import com.arexis.mugen.project.user.UserRemote;


/**
 * This is the business interface for SecurityPrinciple enterprise bean.
 */
public interface SecurityPrincipleRemoteBusiness {
    int getPid() throws java.rmi.RemoteException;

    int getId() throws java.rmi.RemoteException;

    int getRid() throws java.rmi.RemoteException;

    RoleRemote getRole() throws java.rmi.RemoteException;

    UserRemote getUser() throws java.rmi.RemoteException;

    ProjectRemote getProject() throws java.rmi.RemoteException;
    
}
