
package com.arexis.mugen.model.geneticmodification;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.model.expmodel.ExpModelRemote;
import com.arexis.mugen.search.Keyword;
import java.util.Collection;


/**
 * This is the home interface for GenticModification enterprise bean.
 */
public interface GeneticModificationRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.model.geneticmodification.GeneticModificationRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByModel(int eid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.model.geneticmodification.GeneticModificationRemote create(int gmid, String name, String comm, ExpModelRemote model, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    Collection findByKeyword(Keyword keyword, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
