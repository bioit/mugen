
package com.arexis.mugen.model.geneontology;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.model.geneticmodification.GeneticModificationRemote;
import com.arexis.mugen.project.user.UserRemote;
import java.util.Date;


/**
 * This is the business interface for GeneOntology enterprise bean.
 */
public interface GeneOntologyRemoteBusiness {
    int getGoid() throws java.rmi.RemoteException;

    String getName() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    String getLinkid() throws java.rmi.RemoteException;

    void setLinkid(java.lang.String linkid) throws java.rmi.RemoteException;

    Date getTs() throws java.rmi.RemoteException;

    UserRemote getUser() throws java.rmi.RemoteException;

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;

    GeneticModificationRemote getGeneticModification() throws java.rmi.RemoteException;
    
}
