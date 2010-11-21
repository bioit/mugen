
package com.arexis.mugen.model.expmodel;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.search.Keyword;
import java.util.Collection;

public interface ExpModelRemoteHome extends javax.ejb.EJBHome {
    com.arexis.mugen.model.expmodel.ExpModelRemote findByPrimaryKey(java.lang.Integer eid)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.model.expmodel.ExpModelRemote create(int eid, java.lang.String identity, SamplingUnitRemote samplingUnit, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    java.util.Collection findBySamplingUnit(int suid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByAllele(int allid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByGene(int gaid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByGenBackValue(int bid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;

    Collection findByResearchApplication(int raid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByKeyword(Keyword keyword, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByFormDataManager(FormDataManager fdm, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByIMSRSubmission(int suid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByMP(String potid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByUniqueIMSRSubmission(MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByModelsThatNeedDissUpdate(MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByFormDataManagerForDissUpdate(FormDataManager fdm, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByBackrossingListGeneration(MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByJAXID(MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByEMMAID(MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByJAXEMMAID(MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    com.arexis.mugen.model.expmodel.ExpModelRemote findByStrainID(int strainid)  throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByWebServiceRequest() throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByWebServiceKeywordRequest(String keyword) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByWebServiceEnsemblRequest(String ensembl) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
}
