
package com.arexis.mugen.species.lmarker;

import com.arexis.mugen.exceptions.DbException;


/**
 * This is the home interface for LMarker enterprise bean.
 */
public interface LMarkerRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.species.lmarker.LMarkerRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    com.arexis.mugen.species.lmarker.LMarkerRemote create(int lmid, java.lang.String name,
            java.lang.String alias, java.lang.String comm, java.lang.String p1,
            java.lang.String p2, double position,
            int sid, int cid) 
            throws javax.ejb.CreateException, java.rmi.RemoteException, DbException;

    com.arexis.mugen.species.lmarker.LMarkerRemote findByNameAndSpecies(java.lang.String name, java.lang.Integer sid) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
