
package com.arexis.mugen.phenotype.phenotype;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.phenotype.variable.VariableRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.samplingunit.expobj.ExpObj;
import com.arexis.mugen.samplingunit.individual.IndividualRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import java.util.Collection;


/**
 * This is the business interface for Phenotype enterprise bean.
 */
public interface PhenotypeRemoteBusiness {
    String getValue() throws java.rmi.RemoteException;

    void setValue(java.lang.String value) throws java.rmi.RemoteException;

    String getReference() throws java.rmi.RemoteException;

    void setReference(java.lang.String reference) throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    java.sql.Date getDate() throws java.rmi.RemoteException;

    void setDate(java.sql.Date date) throws java.rmi.RemoteException;

    void setSuid(int suid) throws java.rmi.RemoteException;

    java.sql.Date getTs() throws java.rmi.RemoteException;

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;

    void addHistory() throws java.rmi.RemoteException;

    Collection getHistory() throws java.rmi.RemoteException;

    /**
     * Returns the user which made the last update on the marker
     * @return The user
     */
    UserRemote getUser() throws java.rmi.RemoteException;

    IndividualRemote getIndividual() throws java.rmi.RemoteException;

    VariableRemote getVariable() throws java.rmi.RemoteException;

    SamplingUnitRemote getSamplingUnit() throws java.rmi.RemoteException;

    ExpObj getExperimentalObject(MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
}
