
package com.arexis.mugen.model.functionalsignificancetype;

import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.user.UserRemote;
import java.sql.Date;
import java.util.Collection;




/**
 * This is the business interface for FunctionalSignificanceType enterprise bean.
 */
public interface FunctionalSignificanceTypeRemoteBusiness {
    int getFstid() throws java.rmi.RemoteException;

    String getName() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    java.sql.Date getTs() throws java.rmi.RemoteException;

    UserRemote getUser() throws java.rmi.RemoteException;

    ProjectRemote getProject() throws java.rmi.RemoteException;

    Collection getFunctionalSignificances() throws ApplicationException, java.rmi.RemoteException;

    /**
     * Sets the caller for the functional significance
     * @param caller The caller
     */
    void setCaller(com.arexis.mugen.MugenCaller caller) throws java.rmi.RemoteException;
    
}
