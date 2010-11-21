
package com.arexis.mugen.model.strain.strain;

import com.arexis.mugen.MugenCaller;
import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;


/**
 * This is the home interface for Strain enterprise bean.
 */
public interface StrainRemoteHome extends EJBHome
{
    
    StrainRemote findByPrimaryKey(Integer key)  throws FinderException, RemoteException;

    StrainRemote create(int strainid, String designation, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    Collection findByProject(int pid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    Collection findByFormDataManager(com.arexis.form.FormDataManager fdm, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
