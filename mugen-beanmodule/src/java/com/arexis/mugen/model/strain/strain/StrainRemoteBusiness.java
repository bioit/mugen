
package com.arexis.mugen.model.strain.strain;

import com.arexis.mugen.model.strain.state.StrainStateRemote;
import com.arexis.mugen.model.strain.type.StrainTypeRemote;
import java.rmi.RemoteException;


/**
 * This is the business interface for Strain enterprise bean.
 */
public interface StrainRemoteBusiness
{
    int getStrainid() throws java.rmi.RemoteException;

    java.lang.String getDesignation() throws java.rmi.RemoteException;

    void setDesignation(String designation) throws java.rmi.RemoteException;

    java.util.Collection getStrainAlleles() throws java.rmi.RemoteException;

    void addType(StrainTypeRemote type) throws RemoteException, java.rmi.RemoteException;

    void removeType(StrainTypeRemote type) throws RemoteException, java.rmi.RemoteException;

    java.util.Collection getTypes() throws java.rmi.RemoteException;

    java.util.Collection getStates() throws java.rmi.RemoteException;

    void addState(StrainStateRemote state) throws RemoteException, java.rmi.RemoteException;

    void removeState(StrainStateRemote state) throws RemoteException, java.rmi.RemoteException;

    String getMgiId() throws java.rmi.RemoteException;

    void setMgiId(String mgiid) throws java.rmi.RemoteException;

    java.lang.String getEmmaid() throws java.rmi.RemoteException;

    void setEmmaid(String emmaid) throws java.rmi.RemoteException;

    int getExpModel() throws java.rmi.RemoteException;
    
}
