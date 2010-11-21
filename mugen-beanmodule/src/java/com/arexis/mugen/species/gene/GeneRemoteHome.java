
package com.arexis.mugen.species.gene;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.search.Keyword;
import com.arexis.mugen.species.chromosome.ChromosomeRemote;
import java.util.Collection;

public interface GeneRemoteHome extends javax.ejb.EJBHome {
    
    com.arexis.mugen.species.gene.GeneRemote findByPrimaryKey(java.lang.Integer key)  throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByModel(int eid) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByName(java.lang.String name) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByMP(java.lang.String potid) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findByEnsemblid() throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByKeyword(Keyword keyword, MugenCaller caller) throws javax.ejb.FinderException, java.rmi.RemoteException;

    java.util.Collection findByProject(int pid, MugenCaller caller, boolean all) throws javax.ejb.FinderException, java.rmi.RemoteException;

    com.arexis.mugen.species.gene.GeneRemote create(int gaid, java.lang.String name, String comm, String mgiid, String genesymbol, String geneexpress, String idgene, String idensembl, ChromosomeRemote chr, ProjectRemote project, MugenCaller caller) throws javax.ejb.CreateException, java.rmi.RemoteException;
    
    java.util.Collection findUnassignedGenes(int eid, int pid) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findUnassignedGenesForTransgenic(int eid, int strainid, int pid) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
    java.util.Collection findStandAloneGenes(int pid) throws javax.ejb.FinderException, java.rmi.RemoteException;
    
}
