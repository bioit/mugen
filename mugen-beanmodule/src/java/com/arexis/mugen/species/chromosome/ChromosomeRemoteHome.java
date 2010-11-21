
package com.arexis.mugen.species.chromosome;

import com.arexis.mugen.MugenCaller;
import java.util.Collection;


/**
 * This is the home interface for Chromosome enterprise bean.
 */
public interface ChromosomeRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.species.chromosome.ChromosomeRemote findByPrimaryKey(Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    com.arexis.mugen.species.chromosome.ChromosomeRemote create(int cid, String name, String abbr, String comm, int sid) throws javax.ejb.CreateException, java.rmi.RemoteException;

    com.arexis.mugen.species.chromosome.ChromosomeRemote findBySpeciesAndName(java.lang.String name, int sid) throws javax.ejb.FinderException, java.rmi.RemoteException;   

    Collection findBySpecies(int sid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
