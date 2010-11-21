
package com.arexis.mugen.samplingunit.individual;

import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.exceptions.PermissionDeniedException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import java.util.Collection;
import javax.ejb.FinderException;


/**
 * This is the business interface for Individual enterprise bean.
 */
public interface IndividualRemoteBusiness {
    

    String getIdentity() throws java.rmi.RemoteException;

    void setIdentity(java.lang.String identity) throws java.rmi.RemoteException;

    String getAlias() throws java.rmi.RemoteException;

    void setAlias(java.lang.String alias) throws java.rmi.RemoteException;

    String getSex() throws java.rmi.RemoteException;

    void setSex(java.lang.String sex) throws java.rmi.RemoteException,ApplicationException;

    java.sql.Date getBirthDate() throws java.rmi.RemoteException;

    void setBirthDate(java.sql.Date birthDate) throws java.rmi.RemoteException;

    String getStatus() throws java.rmi.RemoteException;

    void setStatus(java.lang.String status) throws java.rmi.RemoteException;

    java.sql.Date getTs() throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    void setFatherId(int fatherId) throws java.rmi.RemoteException;

    IndividualRemote getFather() throws java.rmi.RemoteException;

    IndividualRemote getMother() throws java.rmi.RemoteException;

    void setMotherId(int motherId) throws java.rmi.RemoteException;

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException, PermissionDeniedException;

    Collection getHistory() throws java.rmi.RemoteException;

    void addHistory() throws java.rmi.RemoteException, PermissionDeniedException;

    int checkIndividual() throws java.rmi.RemoteException;

    /**
     * Returns the user which made the last update on the allele
     * @return The user
     */
    UserRemote getUser() throws java.rmi.RemoteException;

    Collection getParents() throws java.rmi.RemoteException;

    Collection getChildren() throws java.rmi.RemoteException, FinderException ;

    SamplingUnitRemote getSamplingUnit() throws java.rmi.RemoteException, ApplicationException ;

    int getIid() throws java.rmi.RemoteException;
    
    int getEid() throws java.rmi.RemoteException;
    
}
