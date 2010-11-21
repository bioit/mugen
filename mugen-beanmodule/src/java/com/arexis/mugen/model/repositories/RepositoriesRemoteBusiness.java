
package com.arexis.mugen.model.repositories;

import com.arexis.mugen.exceptions.ApplicationException;


/**
 * This is the business interface for Repositories enterprise bean.
 */
public interface RepositoriesRemoteBusiness {
    int getRid() throws java.rmi.RemoteException;

    int getPid() throws java.rmi.RemoteException;

    void setPid(int pid) throws java.rmi.RemoteException;

    java.lang.String getReponame() throws java.rmi.RemoteException;

    void setReponame(String reponame) throws java.rmi.RemoteException;

    com.arexis.mugen.project.project.ProjectRemote getProject() throws ApplicationException, java.rmi.RemoteException;

    java.lang.String getRepourl() throws java.rmi.RemoteException;

    void setRepourl(String repourl) throws java.rmi.RemoteException;
    
}
