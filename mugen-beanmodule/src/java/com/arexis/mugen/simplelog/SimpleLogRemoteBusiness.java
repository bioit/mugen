
package com.arexis.mugen.simplelog;


/**
 * This is the business interface for SimpleLog enterprise bean.
 */
public interface SimpleLogRemoteBusiness {
    int getLogid() throws java.rmi.RemoteException;

    java.sql.Timestamp getTs() throws java.rmi.RemoteException;

    java.lang.String getTxt() throws java.rmi.RemoteException;

    void setTxt(String txt) throws java.rmi.RemoteException;

    java.lang.String getMgnaction() throws java.rmi.RemoteException;

    void setMgnaction(String mgnaction) throws java.rmi.RemoteException;

    java.lang.String getMgnuser() throws java.rmi.RemoteException;

    void setMgnuser(String mgnuser) throws java.rmi.RemoteException;

    java.lang.String getRemoteadd() throws java.rmi.RemoteException;

    void setRemoteadd(String remoteadd) throws java.rmi.RemoteException;

    java.lang.String getRemotehost() throws java.rmi.RemoteException;

    void setRemotehost(String remotehost) throws java.rmi.RemoteException;
    
}
