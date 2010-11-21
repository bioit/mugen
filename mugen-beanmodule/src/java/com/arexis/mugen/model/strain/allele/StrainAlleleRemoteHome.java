
package com.arexis.mugen.model.strain.allele;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.model.strain.strain.StrainRemote;
import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import com.arexis.mugen.search.Keyword;


public interface StrainAlleleRemoteHome extends EJBHome
{
    
    StrainAlleleRemote findByPrimaryKey(Integer key)  throws FinderException, RemoteException;

    Collection findByStrain(int strainid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    Collection findByGene(int gaid, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    Collection findByKeyword(Keyword key, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;

    StrainAlleleRemote create(int id, String symbol, String name, StrainRemote strain, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    
}
