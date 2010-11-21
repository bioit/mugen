
package com.arexis.alab.imp.importjob;

import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;


/**
 * This is the home interface for ImportJob enterprise bean.
 */
public interface ImportJobRemoteHome extends EJBHome {
    
    ImportJobRemote findByPrimaryKey(Integer key)  throws FinderException, RemoteException;

    Collection findByUserAndProject(int pid, int userid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    ImportJobRemote create(int jobid, int userid, int fileid, int pid) throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    
}
