
package com.arexis.mugen.model.functionalsignificance;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.model.expmodel.ExpModelRemote;
import com.arexis.mugen.model.functionalsignificancetype.FunctionalSignificanceTypeRemote;
import com.arexis.mugen.search.Keyword;
import java.util.Collection;


/**
 * This is the home interface for FunctionalSignificance enterprise bean.
 */
public interface FunctionalSignificanceRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.model.functionalsignificance.FunctionalSignificanceRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.model.functionalsignificance.FunctionalSignificanceRemote create(int fsid, java.lang.String name, String comm, ExpModelRemote model, MugenCaller caller, FunctionalSignificanceTypeRemote type) throws javax.ejb.CreateException, java.rmi.RemoteException;

    java.util.Collection findByModel(int eid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findBySamplingUnit(int suid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByType(int type) throws javax.ejb.FinderException, java.rmi.RemoteException;

    Collection findByKeyword(Keyword keyword, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
