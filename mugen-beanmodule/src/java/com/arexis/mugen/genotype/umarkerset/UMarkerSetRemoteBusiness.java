
package com.arexis.mugen.genotype.umarkerset;

import com.arexis.mugen.MugenCaller;
import java.util.Collection;


/**
 * This is the business interface for UMarkerSet enterprise bean.
 */
public interface UMarkerSetRemoteBusiness {
    int getUmsid() throws java.rmi.RemoteException;

    String getName() throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    String getAlias() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    void setAlias(java.lang.String alias) throws java.rmi.RemoteException;

    void setContext(javax.ejb.EntityContext aContext) throws java.rmi.RemoteException;

    int getPid() throws java.rmi.RemoteException;

    int getSid() throws java.rmi.RemoteException;

    void setPid(int pid) throws java.rmi.RemoteException;

    void setSid(int sid) throws java.rmi.RemoteException;

    java.sql.Date getTs() throws java.rmi.RemoteException;

    int getId() throws java.rmi.RemoteException;

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;

    void addHistory() throws java.rmi.RemoteException;

    Collection getHistory() throws java.rmi.RemoteException;
}
