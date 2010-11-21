
package com.arexis.mugen.project.user;

import com.arexis.mugen.MugenCaller;


/**
 * This is the home interface for User enterprise bean.
 */
public interface UserRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    UserRemote findByPrimaryKey(Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.project.user.UserRemote create(int id, java.lang.String usr, java.lang.String pwd, java.lang.String name, java.lang.String status) throws javax.ejb.CreateException, java.rmi.RemoteException;

    com.arexis.mugen.project.user.UserRemote findByUsr(java.lang.String usr) throws javax.ejb.FinderException, java.rmi.RemoteException;

    int getNumberOfUsers() throws java.rmi.RemoteException;

    com.arexis.mugen.project.user.UserRemote findByUserAndPwd(java.lang.String usr, java.lang.String pwd) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findAll(MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;

    
    
}
