package com.arexis.mugen.model.geneticbackground;

import com.arexis.mugen.exceptions.ApplicationException;

public interface GeneticBackgroundValuesRemoteBusiness {
    int getBid() throws java.rmi.RemoteException;

    java.lang.String getBackname() throws java.rmi.RemoteException;

    void setBackname(String backname) throws java.rmi.RemoteException;

    void setPid(int pid) throws java.rmi.RemoteException;

    int getPid() throws java.rmi.RemoteException;

    com.arexis.mugen.project.project.ProjectRemote getProject() throws ApplicationException, java.rmi.RemoteException;

    int getExpModelsCount() throws java.rmi.RemoteException;
    
}
