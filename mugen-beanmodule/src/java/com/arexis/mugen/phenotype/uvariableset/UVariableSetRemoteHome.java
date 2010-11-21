
package com.arexis.mugen.phenotype.uvariableset;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;



/**
 * This is the home interface for UVariableSet enterprise bean.
 */
public interface UVariableSetRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.phenotype.uvariableset.UVariableSetRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.phenotype.uvariableset.UVariableSetRemote create(int uvsid, int pid, int sid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    java.util.Collection findAllUVariableSets(int forSid, int forPid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByQuery(FormDataManager formdata) throws javax.ejb.FinderException, java.rmi.RemoteException;

    
    
}
