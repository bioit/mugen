
package com.arexis.alab.importerbean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;


/**
 * This is the home interface for ImportProcessor enterprise bean.
 */
public interface ImporterRemoteHome extends EJBHome {
    
    ImporterRemote create()  throws CreateException, RemoteException;
    
    
}
