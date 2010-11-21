
package com.arexis.mugen.phenotype.ontology;


/**
 * This is the business interface for PhenotypeXref enterprise bean.
 */
public interface PhenotypeXrefRemoteBusiness {
    int getId() throws java.rmi.RemoteException;

    java.lang.String getXref() throws java.rmi.RemoteException;

    void setXref(String xref) throws java.rmi.RemoteException;
    
}
