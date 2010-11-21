
package com.arexis.mugen.genotype.umarkerset;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;



/**
 * This is the home interface for UMarkerSet enterprise bean.
 */
public interface UMarkerSetRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.genotype.umarkerset.UMarkerSetRemote findByPrimaryKey(java.lang.Integer aKey)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.genotype.umarkerset.UMarkerSetRemote create(int umsid, String name, String comm, int pid, int sid, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    com.arexis.mugen.genotype.umarkerset.UMarkerSetRemote findByNameProjectSpecies(java.lang.String name, int pid, int sid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findUMarkerSets(int forSid, int forPid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByQuery(FormDataManager fdm) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
