
package com.arexis.mugen.species.gene;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.species.chromosome.ChromosomeRemote;
import java.sql.Date;
import java.util.Collection;


/**
 * This is the business interface for GeneAffected enterprise bean.
 */
public interface GeneRemoteBusiness {
    int getGaid() throws java.rmi.RemoteException;

    String getName() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    Date getTs() throws java.rmi.RemoteException;

    UserRemote getUser() throws java.rmi.RemoteException;

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;

    Collection getModels() throws java.rmi.RemoteException;

    ProjectRemote getProject() throws ApplicationException, java.rmi.RemoteException;
    
    java.lang.String getMgiid() throws java.rmi.RemoteException;

    void setMgiid(String mgiid) throws java.rmi.RemoteException;

    java.lang.String getGenesymbol() throws java.rmi.RemoteException;

    void setGenesymbol(String genesymbol) throws java.rmi.RemoteException;

    java.lang.String getGeneexpress() throws java.rmi.RemoteException;

    void setGeneexpress(String geneexpress) throws java.rmi.RemoteException;

    java.lang.String getIdgene() throws java.rmi.RemoteException;

    void setIdgene(String idgene) throws java.rmi.RemoteException;

    java.lang.String getIdensembl() throws java.rmi.RemoteException;

    void setIdensembl(String idensembl) throws java.rmi.RemoteException;

    com.arexis.mugen.species.chromosome.ChromosomeRemote getChromosome() throws java.rmi.RemoteException;

    void setChromosome(ChromosomeRemote chromosome) throws java.rmi.RemoteException;

    java.util.Collection getAlleles() throws java.rmi.RemoteException;

    int getModelscount() throws ApplicationException, java.rmi.RemoteException;

    int getAllelescount() throws ApplicationException, java.rmi.RemoteException;
    
}
