
package com.arexis.mugen.model.researchapplication;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.search.Keyword;
import java.util.Collection;


/**
 * This is the home interface for ResearchApplication enterprise bean.
 */
public interface ResearchApplicationRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.model.researchapplication.ResearchApplicationRemote findByPrimaryKey(Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByProject(int pid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.model.researchapplication.ResearchApplicationRemote create(java.lang.String name, java.lang.String comm, int pid, int raid, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    Collection findByName(String name) throws javax.ejb.FinderException, java.rmi.RemoteException;

    Collection findByKeyword(Keyword keyword, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
}
