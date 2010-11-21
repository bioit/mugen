
package com.arexis.mugen.samplingunit.samplingunit;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.species.species.SpeciesRemote;


/**
 * This is the home interface for SamplingUnit enterprise bean.
 */
public interface SamplingUnitRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote create(Integer suid, java.lang.String name, java.lang.String comm, SpeciesRemote species, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    java.util.Collection findByProject(int pid) throws javax.ejb.FinderException, java.rmi.RemoteException;        

    java.util.Collection findByProjectSpecies(int pid, int sid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    SamplingUnitRemote findByName(String name, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
