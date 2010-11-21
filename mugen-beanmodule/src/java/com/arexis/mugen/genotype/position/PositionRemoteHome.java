
package com.arexis.mugen.genotype.position;

import com.arexis.mugen.genotype.marker.MarkerRemote;
import com.arexis.mugen.genotype.markerset.MarkerSetRemote;
/**
 * This is the home interface for Position enterprise bean.
 */
public interface PositionRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.genotype.position.PositionRemote findByPrimaryKey(com.arexis.mugen.genotype.position.PositionPk pk)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.genotype.position.PositionRemote create(MarkerRemote markRem, MarkerSetRemote markSetRem, double value) throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    
}
