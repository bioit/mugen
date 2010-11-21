
package com.arexis.mugen.phenotype.uvariable;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;



/**
 * This is the home interface for UVariable enterprise bean.
 */
public interface UVariableRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.phenotype.uvariable.UVariableRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.phenotype.uvariable.UVariableRemote create(int uvid, int pid, int sid, java.lang.String name, java.lang.String comm, java.lang.String unit, java.lang.String type, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    com.arexis.mugen.phenotype.uvariable.UVariableRemote findByNameProjectSpecies(java.lang.String name, int pid, int sid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findAllUVariables(int forPid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByNotInUVariableSet(int nonUvsid, int forPid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByQuery(FormDataManager formdata) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
