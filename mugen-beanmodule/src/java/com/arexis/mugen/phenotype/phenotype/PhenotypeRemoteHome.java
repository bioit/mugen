
package com.arexis.mugen.phenotype.phenotype;
import com.arexis.arxframe.PageManager;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.phenotype.variable.VariableRemote;
import com.arexis.mugen.samplingunit.individual.IndividualRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;

/**
 * This is the home interface for Phenotype enterprise bean.
 */
public interface PhenotypeRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.phenotype.phenotype.PhenotypeRemote findByPrimaryKey(com.arexis.mugen.phenotype.phenotype.PhenotypePk pk)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.phenotype.phenotype.PhenotypeRemote create(int eid, VariableRemote varRem, SamplingUnitRemote suRem, String value, String reference, String comm, java.sql.Date date, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    java.util.Collection findBySamplingUnit(int suid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByQuery(FormDataManager formdata, int pid, PageManager pageManager) throws javax.ejb.FinderException, java.rmi.RemoteException;

    int getNumberOfPhenotypes(FormDataManager formdata, int pid) throws java.rmi.RemoteException;

    java.util.Collection findByVariable(int vid) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    
}
