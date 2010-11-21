
package com.arexis.mugen.samplingunit.grouping;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.exceptions.PermissionDeniedException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import java.util.Collection;


/**
 * This is the business interface for Grouping enterprise bean.
 */
public interface GroupingRemoteBusiness {
    int getGsid() throws java.rmi.RemoteException;

    String getName() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    java.sql.Date getTs() throws java.rmi.RemoteException;
    
    Collection getGroups(MugenCaller caller) throws java.rmi.RemoteException;

    int getSuid() throws java.rmi.RemoteException;

    void setSuid(int suid) throws java.rmi.RemoteException;

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;
    
    void addHistory() throws java.rmi.RemoteException, PermissionDeniedException;
    
    Collection getHistory() throws java.rmi.RemoteException, PermissionDeniedException;
    
    SamplingUnitRemote getSamplingUnit() throws java.rmi.RemoteException;

    /**
     * Returns the number of groups that belongs to a certain grouping
     * @param groupingsId The id which the groups should belong to
     * @throws com.arexis.mugen.exceptions.ApplicationException If the number of groups could not be retrieved
     * @return The number of groups in a certain grouping
     */
    int getNumberOfGroups() throws ApplicationException, java.rmi.RemoteException;

    /**
     * Returns the user which made the last update
     * @return The user
     */
    UserRemote getUser() throws java.rmi.RemoteException;
    
}
