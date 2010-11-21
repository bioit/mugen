
package com.arexis.mugen.resource.link;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.user.UserRemote;


/**
 * This is the business interface for Link enterprise bean.
 */
public interface LinkRemoteBusiness {
    String getName() throws java.rmi.RemoteException;

    String getUrl() throws java.rmi.RemoteException;

    String getComment() throws java.rmi.RemoteException;

    int getLinkId() throws java.rmi.RemoteException;

    UserRemote getUser() throws java.rmi.RemoteException;

    java.sql.Date getTs() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;

    void setComment(java.lang.String comm) throws java.rmi.RemoteException;

    void setUrl(java.lang.String url) throws java.rmi.RemoteException;

    ProjectRemote getProject() throws java.rmi.RemoteException;

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;
    
}
