
package com.arexis.mugen.model.reference;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.resource.file.FileRemote;
import com.arexis.mugen.resource.link.LinkRemote;
import java.sql.Date;


/**
 * This is the business interface for Reference enterprise bean.
 */
public interface ReferenceRemoteBusiness {
    int getRefid() throws java.rmi.RemoteException;

    String getName() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    Date getTs() throws java.rmi.RemoteException;
    
    FileRemote getFile()  throws java.rmi.RemoteException;
    
    LinkRemote getLink()  throws java.rmi.RemoteException;
    
    void setFile(FileRemote file)  throws ApplicationException, java.rmi.RemoteException;
    
    void setLink(LinkRemote link)  throws ApplicationException, java.rmi.RemoteException;    

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;

    UserRemote getUser() throws ApplicationException, java.rmi.RemoteException;
}
