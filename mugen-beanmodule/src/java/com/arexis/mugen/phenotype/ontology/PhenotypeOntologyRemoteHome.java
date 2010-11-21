
package com.arexis.mugen.phenotype.ontology;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.search.Keyword;
import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface PhenotypeOntologyRemoteHome extends EJBHome {
    
    PhenotypeOntologyRemote findByPrimaryKey(Integer key)  throws FinderException, RemoteException;
    
    java.util.Collection findByKeyword(Keyword keyword, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    PhenotypeOntologyRemote create(int id, String name, String def, String def_ref, int is_obsolete) throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    java.util.Collection findByLevelOne()  throws FinderException, RemoteException;
    
    java.util.Collection findByLowerLevel(int root_pheno_id)  throws FinderException, RemoteException;
    
    java.util.Collection findByIsA(int pheno_id)  throws FinderException, RemoteException;
    
}
