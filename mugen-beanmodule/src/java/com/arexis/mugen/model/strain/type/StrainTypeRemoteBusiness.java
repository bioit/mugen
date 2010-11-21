
package com.arexis.mugen.model.strain.type;

import com.arexis.mugen.MugenCaller;


/**
 * This is the business interface for StrainType enterprise bean.
 */
public interface StrainTypeRemoteBusiness
{
    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;

    void setAbbreviation(String abbreviation) throws java.rmi.RemoteException;

    java.lang.String getAbbreviation() throws java.rmi.RemoteException;

    void setName(String name) throws java.rmi.RemoteException;

    java.lang.String getName() throws java.rmi.RemoteException;

    int getId() throws java.rmi.RemoteException;
    
}
