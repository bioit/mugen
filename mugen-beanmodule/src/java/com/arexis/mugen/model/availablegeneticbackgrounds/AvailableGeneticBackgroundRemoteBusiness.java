
package com.arexis.mugen.model.availablegeneticbackgrounds;

import com.arexis.mugen.exceptions.ApplicationException;


/**
 * This is the business interface for AvailableGeneticBackground enterprise bean.
 */
public interface AvailableGeneticBackgroundRemoteBusiness {
    int getAid() throws java.rmi.RemoteException;

    int getPid() throws java.rmi.RemoteException;

    void setPid(int pid) throws java.rmi.RemoteException;

    java.lang.String getAvbackname() throws java.rmi.RemoteException;

    void setAvbackname(String avbackname) throws java.rmi.RemoteException;

    com.arexis.mugen.project.project.ProjectRemote getProject() throws ApplicationException, java.rmi.RemoteException;
    
}
