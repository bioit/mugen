
package com.arexis.mugen.phenotype.ontology;


/**
 * This is the business interface for PhenotypeSynonym enterprise bean.
 */
public interface PhenotypeSynonymRemoteBusiness {
    int getId() throws java.rmi.RemoteException;

    java.lang.String getSynonym() throws java.rmi.RemoteException;

    void setSynonym(String synonym) throws java.rmi.RemoteException;
    
}
