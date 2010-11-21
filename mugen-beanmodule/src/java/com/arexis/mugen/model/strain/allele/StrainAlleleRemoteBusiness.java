
package com.arexis.mugen.model.strain.allele;

import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.strain.mutationtype.MutationTypeRemote;
import com.arexis.mugen.species.gene.GeneRemote;


/**
 * This is the business interface for StrainAllele enterprise bean.
 */
public interface StrainAlleleRemoteBusiness
{
    int getId() throws java.rmi.RemoteException;

    java.lang.String getSymbol() throws java.rmi.RemoteException;

    void setSymbol(String symbol) throws java.rmi.RemoteException;

    java.lang.String getName() throws java.rmi.RemoteException;

    void setName(String name) throws java.rmi.RemoteException;

    String getMgiId() throws java.rmi.RemoteException;

    void setMgiId(String imsrid) throws java.rmi.RemoteException;

    com.arexis.mugen.species.gene.GeneRemote getGene() throws java.rmi.RemoteException;

    java.util.Collection getMutationTypes() throws java.rmi.RemoteException;

    void addMutationType(MutationTypeRemote mutationType) throws ApplicationException, java.rmi.RemoteException;

    void removeMutationType(MutationTypeRemote mutationType) throws ApplicationException, java.rmi.RemoteException;

    void setGene(GeneRemote gene) throws java.rmi.RemoteException;

    java.lang.String getAttributes() throws java.rmi.RemoteException;

    void setAttributes(String attributes) throws java.rmi.RemoteException;

    void setGeneToNULL(int strainallele) throws ApplicationException, java.rmi.RemoteException;
    
}
