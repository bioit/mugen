
package com.arexis.mugen.genotype.allele;

import com.arexis.mugen.MugenCaller;


/**
 * This is the home interface for Allele enterprise bean.
 */
public interface AlleleRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.genotype.allele.AlleleRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.genotype.allele.AlleleRemote create(int aid, int mid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    com.arexis.mugen.genotype.allele.AlleleRemote findByNameMarker(java.lang.String name, int mid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findAllAllelesForMarker(int forMid) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
