
package com.arexis.mugen.genotype.marker;

import com.arexis.arxframe.PageManager;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;



/**
 * This is the home interface for Marker enterprise bean.
 */
public interface MarkerRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.genotype.marker.MarkerRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.genotype.marker.MarkerRemote create(int mid, int suid, int cid, java.lang.String name, java.lang.String comm, java.lang.String alias, double position, java.lang.String p1, java.lang.String p2, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    com.arexis.mugen.genotype.marker.MarkerRemote findByNameSamplingUnit(java.lang.String name, int suid) throws javax.ejb.FinderException, java.rmi.RemoteException;    

    java.util.Collection findAllMarkers(int forSuid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByQuery(FormDataManager pdo, PageManager pageManager) throws javax.ejb.FinderException, java.rmi.RemoteException;

    int getNumberOfMarkers(FormDataManager pdo) throws java.rmi.RemoteException;

    java.util.Collection findByChromosome(int forCid, int forSuid) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
