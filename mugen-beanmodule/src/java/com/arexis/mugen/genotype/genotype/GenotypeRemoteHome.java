
package com.arexis.mugen.genotype.genotype;

import com.arexis.arxframe.PageManager;
import com.arexis.mugen.genotype.marker.MarkerRemote;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.ParamDataObject;
import com.arexis.mugen.samplingunit.individual.IndividualRemote;
/**
 * This is the home interface for Genotype enterprise bean.
 */
public interface GenotypeRemoteHome extends javax.ejb.EJBHome {
    
    
    
    /**
     *
     */
    com.arexis.mugen.genotype.genotype.GenotypeRemote findByPrimaryKey(com.arexis.mugen.genotype.genotype.GenotypePk pk)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.genotype.genotype.GenotypeRemote create(IndividualRemote indRem, MarkerRemote markRem, String raw1, String raw2, String comm, String reference, int level, int aid1, int aid2, int suid, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;

    java.util.Collection findAllGenotypes(int forSuid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByQuery(ParamDataObject pdo, PageManager pageManager) throws javax.ejb.FinderException, java.rmi.RemoteException;

    int getNumberOfGenotypes(ParamDataObject pdo) throws java.rmi.RemoteException;
    
    
}
