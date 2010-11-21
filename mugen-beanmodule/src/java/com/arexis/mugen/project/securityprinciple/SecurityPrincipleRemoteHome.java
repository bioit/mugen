
package com.arexis.mugen.project.securityprinciple;

import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.role.RoleRemote;


/**
 * This is the home interface for SecurityPrinciple enterprise bean.
 */
public interface SecurityPrincipleRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.project.securityprinciple.SecurityPrincipleRemote findByPrimaryKey(SecurityPrinciplePk pk)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.project.securityprinciple.SecurityPrincipleRemote create(ProjectRemote project, UserRemote user, RoleRemote role) throws javax.ejb.CreateException, java.rmi.RemoteException;

    java.util.Collection findByProject(int pid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByUser(int id) throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.project.securityprinciple.SecurityPrincipleRemote findByUserProject(int id, int pid) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
