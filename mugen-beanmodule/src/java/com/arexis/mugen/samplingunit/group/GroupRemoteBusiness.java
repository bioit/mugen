
package com.arexis.mugen.samplingunit.group;
import com.arexis.mugen.exceptions.PermissionDeniedException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.samplingunit.grouping.GroupingRemote;
import com.arexis.mugen.samplingunit.individual.IndividualRemote;
import java.util.Collection;


/**
 * This is the business interface for Group enterprise bean.
 */
public interface GroupRemoteBusiness {
    int getGid() throws java.rmi.RemoteException;

    String getName() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    Collection getIndividuals() throws java.rmi.RemoteException;

    void addIndividual(IndividualRemote individual) throws java.rmi.RemoteException;

    void removeIndividual(IndividualRemote individual) throws java.rmi.RemoteException;

    java.sql.Date getDate() throws java.rmi.RemoteException;

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;
    
    void addHistory() throws java.rmi.RemoteException, PermissionDeniedException;
    
    Collection getHistory() throws java.rmi.RemoteException, PermissionDeniedException;    

    GroupingRemote getGrouping() throws java.rmi.RemoteException;

    UserRemote getUser() throws java.rmi.RemoteException;

    int getNumberOfIndividuals() throws java.rmi.RemoteException;
}
