
package com.arexis.mugen.samplingunit.grouping;

import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;

/**
 * This is the home interface for Grouping enterprise bean.
 */
public interface GroupingRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.samplingunit.grouping.GroupingRemote findByPrimaryKey(java.lang.Integer gsid)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findBySamplingUnit(int suid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;    

    com.arexis.mugen.samplingunit.grouping.GroupingRemote create(int gsid, java.lang.String name, java.lang.String comm, SamplingUnitRemote samplingUnit, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    GroupingRemote findByNameAndSamplingUnit(String name, int suid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
