
package com.arexis.mugen.phenotype.uvariable;

import com.arexis.mugen.MugenCaller;
import java.util.Collection;


/**
 * This is the business interface for UVariable enterprise bean.
 */
public interface UVariableRemoteBusiness {
    String getName() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;

    int getUvid() throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    String getUnit() throws java.rmi.RemoteException;

    void setUnit(java.lang.String unit) throws java.rmi.RemoteException;

    String getType() throws java.rmi.RemoteException;

    void setType(java.lang.String type) throws java.rmi.RemoteException;

    int getPid() throws java.rmi.RemoteException;

    void setSid(int sid) throws java.rmi.RemoteException;

    int getSid() throws java.rmi.RemoteException;

    void setPid(int pid) throws java.rmi.RemoteException;

    java.sql.Date getUpdated() throws java.rmi.RemoteException;

    int getId() throws java.rmi.RemoteException;

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;

    Collection getHistory() throws java.rmi.RemoteException;

    void addHistory() throws java.rmi.RemoteException;
    
}
