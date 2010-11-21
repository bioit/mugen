
package com.arexis.mugen.model.strain.mutationtype;

import com.arexis.mugen.MugenCaller;
import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;


/**
 * This is the home interface for MutationType enterprise bean.
 */
public interface MutationTypeRemoteHome extends EJBHome
{
    
    MutationTypeRemote findByPrimaryKey(Integer key)  throws FinderException, RemoteException;

    Collection findByProject(int pid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;

    Collection findByStrainAllele(int id, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    Collection findByStrainAlleleUnassignment(int strainalleleid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
}
