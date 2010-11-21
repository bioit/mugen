
package com.arexis.mugen.model.strain.type;

import com.arexis.mugen.MugenCaller;
import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;


/**
 * This is the home interface for StrainType enterprise bean.
 */
public interface StrainTypeRemoteHome extends EJBHome
{
    
    StrainTypeRemote findByPrimaryKey(Integer key)  throws FinderException, RemoteException;

    StrainTypeRemote create(int id, String name, String abbreviation, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    Collection findByProject(int pid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;

    Collection findByStrain(int strainid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
