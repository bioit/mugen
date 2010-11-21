
package com.arexis.mugen.genotype.uallele;

import com.arexis.mugen.genotype.umarker.UMarkerRemote;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.user.UserRemote;
import java.util.Collection;


/**
 * This is the business interface for UAllele enterprise bean.
 */
public interface UAlleleRemoteBusiness {
    int getUaid() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;

    String getName() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    int getUmid() throws java.rmi.RemoteException;

    void setUmid(int umid) throws java.rmi.RemoteException;

    java.sql.Date getTs() throws java.rmi.RemoteException;

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;

    void addHistory() throws java.rmi.RemoteException;

    Collection getHistory() throws java.rmi.RemoteException;

    UMarkerRemote getUMarker() throws java.rmi.RemoteException;

    /**
     * Returns the user which made the last update on the marker
     * @return The user
     */
    UserRemote getUser() throws java.rmi.RemoteException;
    
}
