
package com.arexis.mugen.model.availability;

import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;


/**
 * This is the home interface for Availability enterprise bean.
 */
public interface AvailabilityRemoteHome extends EJBHome {
    
    AvailabilityRemote findByPrimaryKey(AvailabilityPk key)  throws FinderException, RemoteException;

    AvailabilityRemote create(com.arexis.mugen.model.expmodel.ExpModelRemote model, com.arexis.mugen.model.repositories.RepositoriesRemote repository, com.arexis.mugen.model.availablegeneticbackgrounds.AvailableGeneticBackgroundRemote avgenback, com.arexis.mugen.model.strain.state.StrainStateRemote state, com.arexis.mugen.model.strain.type.StrainTypeRemote type) throws javax.ejb.CreateException, java.rmi.RemoteException;

    Collection findByModel(int eid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    Collection findByRepository(int rid) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
