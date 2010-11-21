
package com.arexis.mugen.species.lallele;

import com.arexis.mugen.exceptions.DbException;


/**
 * This is the home interface for LAllele enterprise bean.
 */
public interface LAlleleRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.species.lallele.LAlleleRemote findByPrimaryKey(java.lang.Integer aKey)  throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    com.arexis.mugen.species.lallele.LAlleleRemote create(int laid,
            java.lang.String name, java.lang.String comm, int lmid) 
            throws javax.ejb.CreateException, java.rmi.RemoteException, DbException;   

    com.arexis.mugen.species.lallele.LAlleleRemote findByNameAndLMarker(String name, int lmid) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
