
package com.arexis.mugen.phenotype.ontology;


/**
 * This is the business interface for PhenotypeOntologyPath enterprise bean.
 */
public interface PhenotypeOntologyPathRemoteBusiness {
    int getPpid() throws java.rmi.RemoteException;

    java.lang.String getPath() throws java.rmi.RemoteException;

    void setPath(String path) throws java.rmi.RemoteException;

    java.lang.String getPathtrans() throws java.rmi.RemoteException;

    java.lang.String getPathtreenode() throws java.rmi.RemoteException;
    
    java.lang.String getPathtreenodePLD() throws java.rmi.RemoteException;
    
    java.lang.String getPathtreenodeFullPLD() throws java.rmi.RemoteException;
}
