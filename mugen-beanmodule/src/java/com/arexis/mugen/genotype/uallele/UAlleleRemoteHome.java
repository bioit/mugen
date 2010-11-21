
package com.arexis.mugen.genotype.uallele;

import com.arexis.mugen.MugenCaller;


/**
 * This is the home interface for UAllele enterprise bean.
 */
public interface UAlleleRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.genotype.uallele.UAlleleRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.genotype.uallele.UAlleleRemote create(int uaid, int umid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    com.arexis.mugen.genotype.uallele.UAlleleRemote findByNameMarker(java.lang.String name, int umid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findAllUAllelesForUMarker(int forUmid) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
