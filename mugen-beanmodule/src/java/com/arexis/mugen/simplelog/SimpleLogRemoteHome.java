
package com.arexis.mugen.simplelog;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;


/**
 * This is the home interface for SimpleLog enterprise bean.
 */
public interface SimpleLogRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.simplelog.SimpleLogRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.simplelog.SimpleLogRemote create(java.lang.String txt, java.lang.String mgnaction, java.lang.String mgnuser, java.lang.String remoteadd, java.lang.String remotehost) throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    java.util.Collection findByFormDataManager(FormDataManager fdm, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByNoRestriction() throws javax.ejb.FinderException, java.rmi.RemoteException;
}
