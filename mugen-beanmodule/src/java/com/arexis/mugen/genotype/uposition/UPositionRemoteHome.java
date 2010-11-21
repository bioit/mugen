
package com.arexis.mugen.genotype.uposition;

import com.arexis.mugen.genotype.umarker.UMarkerRemote;
import com.arexis.mugen.genotype.umarkerset.UMarkerSetRemote;
/**
 * This is the home interface for UPosition enterprise bean.
 */
public interface UPositionRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.genotype.uposition.UPositionRemote findByPrimaryKey(com.arexis.mugen.genotype.uposition.UPositionPk upk)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.genotype.uposition.UPositionRemote create(UMarkerRemote umr, UMarkerSetRemote umsr, double value) throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    
}
