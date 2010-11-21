package com.arexis.mugen.model.usercomments;

import java.rmi.RemoteException;
import javax.ejb.FinderException;
import java.util.Collection;

public interface UserCommsRemoteHome extends javax.ejb.EJBHome {
    
    com.arexis.mugen.model.usercomments.UserCommsRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, RemoteException;
    
    java.util.Collection findByModel(int eid) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    com.arexis.mugen.model.usercomments.UserCommsRemote create(int commid, int userid) throws javax.ejb.CreateException, java.rmi.RemoteException;
}
