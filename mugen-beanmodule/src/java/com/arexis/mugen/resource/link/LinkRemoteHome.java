
package com.arexis.mugen.resource.link;

import com.arexis.mugen.MugenCaller;


/**
 * This is the home interface for Link enterprise bean.
 */
public interface LinkRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.resource.link.LinkRemote findByPrimaryKey(java.lang.Integer linkid)  throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
    
    /**
     *
     */
    com.arexis.mugen.resource.link.LinkRemote create(int linkId, String name, String url, String comm, MugenCaller caller)  throws javax.ejb.CreateException, java.rmi.RemoteException;

    java.util.Collection findByProject(int pid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findBySamplingUnit(int suid) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
