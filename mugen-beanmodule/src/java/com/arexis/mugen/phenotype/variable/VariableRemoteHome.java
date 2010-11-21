
package com.arexis.mugen.phenotype.variable;

import com.arexis.arxframe.PageManager;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;



/**
 * This is the home interface for Variable enterprise bean.
 */
public interface VariableRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.phenotype.variable.VariableRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.phenotype.variable.VariableRemote create(int vid, int suid, java.lang.String name, java.lang.String type, java.lang.String unit, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    java.util.Collection findAllVariables(int suid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByNotInVariableSet(int vsid, int forSuid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByQuery(FormDataManager formdata, PageManager pageManager) throws javax.ejb.FinderException, java.rmi.RemoteException;

    int getNumberOfVariables(FormDataManager formdata) throws java.rmi.RemoteException;

    java.util.Collection findByVariableSet(int vsid) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
