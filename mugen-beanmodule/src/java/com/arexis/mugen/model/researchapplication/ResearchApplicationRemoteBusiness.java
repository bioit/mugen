
package com.arexis.mugen.model.researchapplication;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.user.UserRemote;
import java.util.Collection;


/**
 * This is the business interface for ResearchApplication enterprise bean.
 */
public interface ResearchApplicationRemoteBusiness {
    int getRaid() throws java.rmi.RemoteException;

    String getName() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    ProjectRemote getProject() throws java.rmi.RemoteException;

    UserRemote getUser() throws java.rmi.RemoteException;

    java.sql.Date getTs() throws java.rmi.RemoteException;
    
    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;         

    Collection getModels() throws ApplicationException, java.rmi.RemoteException;

    MugenCaller getCaller() throws java.rmi.RemoteException;
    
}
