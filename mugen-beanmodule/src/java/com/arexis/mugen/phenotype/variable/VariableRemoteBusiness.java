
package com.arexis.mugen.phenotype.variable;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import java.util.Collection;


/**
 * This is the business interface for Variable enterprise bean.
 */
public interface VariableRemoteBusiness {
    String getType() throws java.rmi.RemoteException;

    void setType(java.lang.String type) throws java.rmi.RemoteException;

    String getUnit() throws java.rmi.RemoteException;

    void setUnit(java.lang.String unit) throws java.rmi.RemoteException;

    String getName() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    int getVid() throws java.rmi.RemoteException;

    void setSuid(int suid) throws java.rmi.RemoteException;

    java.sql.Date getTs() throws java.rmi.RemoteException;

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;

    void addHistory() throws java.rmi.RemoteException;

    Collection getHistory() throws java.rmi.RemoteException;

    /**
     * Returns the sampling unit
     * @return The sampling unit
     */
    SamplingUnitRemote getSamplingUnit() throws java.rmi.RemoteException;

    /**
     * Returns the user which made the last update on the marker
     * @return The user
     */
    UserRemote getUser() throws java.rmi.RemoteException;

    Collection getPhenotypes() throws ApplicationException, java.rmi.RemoteException;
    
}
