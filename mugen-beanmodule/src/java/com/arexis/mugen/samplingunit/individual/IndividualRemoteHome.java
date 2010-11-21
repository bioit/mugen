
package com.arexis.mugen.samplingunit.individual;
import com.arexis.arxframe.PageManager;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;


/**
 * This is the home interface for Individual enterprise bean.
 */
public interface IndividualRemoteHome extends javax.ejb.EJBHome {
    /**
     *
     */
    com.arexis.mugen.samplingunit.individual.IndividualRemote findByPrimaryKey(java.lang.Integer iid)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByGroup(int gid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findBySamplingUnit(int suid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.samplingunit.individual.IndividualRemote create(int iid, String identity, SamplingUnitRemote samplingUnit, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    java.util.Collection findMale(int suid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findFemale(int suid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByNonGroup(int nonGid, int inSuid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByQuery(FormDataManager fdm, PageManager pageManager) throws javax.ejb.FinderException, java.rmi.RemoteException, ApplicationException;

    int getNumberOfIndividuals(FormDataManager fdm) throws java.rmi.RemoteException, ApplicationException ;

    java.util.Collection findByQuery(FormDataManager fdm) throws javax.ejb.FinderException, java.rmi.RemoteException;

    IndividualRemote findByIdentityAndSamplingUnit(String identity, int suid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
