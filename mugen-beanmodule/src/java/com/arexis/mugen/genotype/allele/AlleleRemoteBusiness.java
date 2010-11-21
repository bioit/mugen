
package com.arexis.mugen.genotype.allele;

import com.arexis.mugen.genotype.marker.MarkerRemote;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.user.UserRemote;
import java.util.Collection;


/**
 * This is the business interface for Allele enterprise bean.
 */
public interface AlleleRemoteBusiness {
    int getAid() throws java.rmi.RemoteException;

    String getName() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    MarkerRemote getMarker() throws java.rmi.RemoteException;

    void setMid(int mid) throws java.rmi.RemoteException;
    
    java.sql.Date getUpdated() throws java.rmi.RemoteException;    
    
    UserRemote getUser() throws java.rmi.RemoteException;    

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;

    Collection getHistory() throws java.rmi.RemoteException;

    void addHistory() throws java.rmi.RemoteException;
}
