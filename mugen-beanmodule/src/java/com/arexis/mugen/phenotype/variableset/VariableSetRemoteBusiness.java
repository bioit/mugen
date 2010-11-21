
package com.arexis.mugen.phenotype.variableset;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import java.util.Collection;


/**
 * This is the business interface for VariableSet enterprise bean.
 */
public interface VariableSetRemoteBusiness {
    int getVsid() throws java.rmi.RemoteException;
    
    String getName() throws java.rmi.RemoteException;
    
    void setName(java.lang.String name) throws java.rmi.RemoteException;
    
    String getComm() throws java.rmi.RemoteException;
    
    void setComm(java.lang.String comm) throws java.rmi.RemoteException;
    
    int getSuid() throws java.rmi.RemoteException;
    
    void setSuid(int suid) throws java.rmi.RemoteException;
    
    int getId() throws java.rmi.RemoteException;
    
    java.sql.Date getUpdated() throws java.rmi.RemoteException;
    
    Collection getAllVariablesInSet() throws java.rmi.RemoteException;
    
    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;
    
    void addHistory() throws java.rmi.RemoteException;
    
    Collection getHistory() throws java.rmi.RemoteException;

    int getNumberOfVariables() throws java.rmi.RemoteException;

    UserRemote getUser() throws java.rmi.RemoteException;

    SamplingUnitRemote getSamplingUnit() throws java.rmi.RemoteException;

    void addVariable(int vid, MugenCaller caller) throws java.rmi.RemoteException;

    void removeVariable(int vid, MugenCaller caller) throws java.rmi.RemoteException;

    Collection getVariables() throws ApplicationException, java.rmi.RemoteException;
    
}
