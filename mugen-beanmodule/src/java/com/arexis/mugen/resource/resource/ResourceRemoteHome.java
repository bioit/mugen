
package com.arexis.mugen.resource.resource;

import com.arexis.mugen.MugenCaller;
import java.util.Collection;


/**
 * This is the home interface for Resource enterprise bean.
 */
public interface ResourceRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.resource.resource.ResourceRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByCategory(int category) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByProject(int project) throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.resource.resource.ResourceRemote create(int resourceId, int projectId, int fileId, int linkId, int categoryId, java.lang.String name, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    java.util.Collection findByProcess(int processId) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByPathway(int pathwayId) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByProtein(int proteinId) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByComplex(int complexId) throws javax.ejb.FinderException, java.rmi.RemoteException;

    Collection findBySamplingUnit(int suid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;

    Collection findByModel(int eid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
