
package com.arexis.mugen.export.filter;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.PermissionDeniedException;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.species.species.SpeciesRemote;
import java.util.Collection;


/**
 * This is the business interface for GQLFilter enterprise bean.
 */
public interface GQLFilterRemoteBusiness {
    java.sql.Date getUpdated() throws java.rmi.RemoteException;

    /**
     * Returns a collection of history log entries for the group
     * @return A collection of history log entries
     */
    Collection getHistory() throws java.rmi.RemoteException;

    /**
     * Writes a log entry to track changes history
     * @throws com.arexis.mugen.exceptions.PermissionDeniedException If the caller does not have GR_W privilege
     */
    void addHistory() throws PermissionDeniedException, java.rmi.RemoteException;

    UserRemote getUser() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;
    
    String getComm() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;
    
    String getName() throws java.rmi.RemoteException;

    void setExpression(java.lang.String expression) throws java.rmi.RemoteException;
    
    String getExpression() throws java.rmi.RemoteException;

    void setSid(int sid) throws java.rmi.RemoteException;
    
    SpeciesRemote getSpecies() throws java.rmi.RemoteException;    

    ProjectRemote getProject() throws java.rmi.RemoteException;

    int getFid() throws java.rmi.RemoteException;    

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;
}
