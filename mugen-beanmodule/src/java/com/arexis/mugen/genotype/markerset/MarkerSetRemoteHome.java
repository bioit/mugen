
package com.arexis.mugen.genotype.markerset;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;



/**
 * This is the home interface for MarkerSet enterprise bean.
 */
public interface MarkerSetRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.genotype.markerset.MarkerSetRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.genotype.markerset.MarkerSetRemote create(int msid, int suid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    com.arexis.mugen.genotype.markerset.MarkerSetRemote findByNameSamplingUnit(java.lang.String name, int suid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findMarkerSets(int forSuid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByQuery(FormDataManager fdm) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
