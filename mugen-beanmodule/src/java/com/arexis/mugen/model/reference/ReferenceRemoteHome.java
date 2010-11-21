
package com.arexis.mugen.model.reference;

import com.arexis.mugen.MugenCaller;


/**
 * This is the home interface for Reference enterprise bean.
 */
public interface ReferenceRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.model.reference.ReferenceRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.model.reference.ReferenceRemote create(int refid, int pid, java.lang.String name, String comm, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    java.util.Collection findByModel(int eid) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
}
