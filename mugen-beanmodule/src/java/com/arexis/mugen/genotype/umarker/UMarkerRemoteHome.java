
package com.arexis.mugen.genotype.umarker;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;



/**
 * This is the home interface for UMarker enterprise bean.
 */
public interface UMarkerRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.genotype.umarker.UMarkerRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.genotype.umarker.UMarkerRemote create(int umid, java.lang.String name, java.lang.String alias, java.lang.String comm, double position, int pid, int sid, int cid, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    com.arexis.mugen.genotype.umarker.UMarkerRemote findByNameProjectSpecies(java.lang.String name, int pid, int sid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findUMarkers(int forCid, int forPid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findUMarkers(int forPid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByQuery(FormDataManager fdm) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
