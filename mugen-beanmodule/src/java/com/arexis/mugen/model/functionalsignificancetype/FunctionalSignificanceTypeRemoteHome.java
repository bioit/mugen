
package com.arexis.mugen.model.functionalsignificancetype;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.search.Keyword;
import java.util.Collection;


/**
 * This is the home interface for FunctionalSignificanceType enterprise bean.
 */
public interface FunctionalSignificanceTypeRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.model.functionalsignificancetype.FunctionalSignificanceTypeRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.model.functionalsignificancetype.FunctionalSignificanceTypeRemote create(int fstid, java.lang.String name, int pid, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    java.util.Collection findByProject(int pid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    Collection findByKeyword(Keyword keyword, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
