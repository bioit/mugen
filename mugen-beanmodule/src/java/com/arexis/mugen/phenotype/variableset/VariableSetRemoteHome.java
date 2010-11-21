
package com.arexis.mugen.phenotype.variableset;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import java.util.Collection;



/**
 * This is the home interface for VariableSet enterprise bean.
 */
public interface VariableSetRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.phenotype.variableset.VariableSetRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.phenotype.variableset.VariableSetRemote create(int vsid, int suid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    com.arexis.mugen.phenotype.variableset.VariableSetRemote findByNameSamplingUnit(java.lang.String name, java.lang.Integer suid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findAllVariableSets(int forSuid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByQuery(FormDataManager formdata) throws javax.ejb.FinderException, java.rmi.RemoteException;

    Collection findByName(java.lang.String name) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
