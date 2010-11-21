
package com.arexis.mugen.model.usercomments;

public interface UserCommsRemoteBusiness {
    
    int getCommid() throws java.rmi.RemoteException;
    
    public int getUserid() throws java.rmi.RemoteException;
    
    public String getUsername() throws java.rmi.RemoteException;
    
    public void setUserid(int userid) throws java.rmi.RemoteException;
    
    public String getComm() throws java.rmi.RemoteException;
    
    public void setComm(String comm) throws java.rmi.RemoteException;
    
    public java.sql.Date getTs() throws java.rmi.RemoteException;
    
    public void connectModel(int eid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;
}
