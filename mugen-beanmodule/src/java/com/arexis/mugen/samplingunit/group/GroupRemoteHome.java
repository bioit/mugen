
package com.arexis.mugen.samplingunit.group;

import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.samplingunit.grouping.GroupingRemote;


/**
 * This is the home interface for Group enterprise bean.
 */
public interface GroupRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.samplingunit.group.GroupRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByGrouping(int gsid, MugenCaller caller, int suid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.samplingunit.group.GroupRemote create(int gid, java.lang.String name, GroupingRemote grouping, String comm, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    GroupRemote findByNameAndGrouping(String name, int gsid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
}
