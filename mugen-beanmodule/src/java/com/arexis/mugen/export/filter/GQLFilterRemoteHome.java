
package com.arexis.mugen.export.filter;

import com.arexis.arxframe.PageManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.ParamDataObject;



/**
 * This is the home interface for GQLFilter enterprise bean.
 */
public interface GQLFilterRemoteHome extends javax.ejb.EJBHome {    
    com.arexis.mugen.export.filter.GQLFilterRemote create(int fid, String name, String comm, String expression, int sid, int pid, MugenCaller caller)  throws javax.ejb.CreateException, java.rmi.RemoteException;

    com.arexis.mugen.export.filter.GQLFilterRemote findByPrimaryKey(Integer key) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByQuery(ParamDataObject pdo, PageManager pageManager) throws javax.ejb.FinderException, java.rmi.RemoteException;

    int getNumberOfFilters(ParamDataObject pdo) throws java.rmi.RemoteException;
    
    
}
