
package com.arexis.mugen.project.project;

import com.arexis.mugen.MugenCaller;


/**
 * This is the home interface for Project enterprise bean.
 */
public interface ProjectRemoteHome extends javax.ejb.EJBHome {
    

    com.arexis.mugen.project.project.ProjectRemote findByPrimaryKey(java.lang.Integer pid)  throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    /**
     *
     */
    com.arexis.mugen.project.project.ProjectRemote findByPrimaryKey(java.lang.Integer key, MugenCaller caller)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.project.project.ProjectRemote create(int pid, java.lang.String name, java.lang.String comm, java.lang.String status,MugenCaller usr) throws javax.ejb.CreateException, java.rmi.RemoteException;

    java.util.Collection findByAll(MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findBySamplingUnit(int suid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByName(java.lang.String name, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
}
