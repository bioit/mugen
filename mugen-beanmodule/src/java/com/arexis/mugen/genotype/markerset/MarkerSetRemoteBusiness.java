
package com.arexis.mugen.genotype.markerset;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import java.util.Collection;


/**
 * This is the business interface for MarkerSet enterprise bean.
 */
public interface MarkerSetRemoteBusiness {
    int getMsid() throws java.rmi.RemoteException;

    String getName() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    void setSuid(int suid) throws java.rmi.RemoteException;

    java.sql.Date getTs() throws java.rmi.RemoteException;

    int getId() throws java.rmi.RemoteException;

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;

    void addHistory() throws java.rmi.RemoteException;

    Collection getHistory() throws java.rmi.RemoteException;

    /**
     * Returns the sampling unit
     * @return The sampling unit
     */
    SamplingUnitRemote getSamplingUnit() throws java.rmi.RemoteException;

    /**
     * Returns the user which made the last update on the markerset
     * @return The user
     */
    UserRemote getUser() throws java.rmi.RemoteException;

    Collection getMarkers(int msid) throws java.rmi.RemoteException;
    
}
