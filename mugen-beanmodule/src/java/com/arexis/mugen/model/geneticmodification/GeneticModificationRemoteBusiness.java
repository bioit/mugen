
package com.arexis.mugen.model.geneticmodification;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.resource.file.FileRemote;
import java.util.Collection;
import java.util.Date;


/**
 * This is the business interface for GenticModification enterprise bean.
 */
public interface GeneticModificationRemoteBusiness {
    int getGmid() throws java.rmi.RemoteException;

    String getName() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    Date getTs() throws java.rmi.RemoteException;

    UserRemote getUser() throws java.rmi.RemoteException;
     
    Collection getGeneOntologies() throws java.rmi.RemoteException, ApplicationException;
    
    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;
    
    void addFile(FileRemote file) throws java.rmi.RemoteException;

    void removeFile(FileRemote file) throws java.rmi.RemoteException; 
    
    Collection getFiles()  throws ApplicationException,  java.rmi.RemoteException; 
}
