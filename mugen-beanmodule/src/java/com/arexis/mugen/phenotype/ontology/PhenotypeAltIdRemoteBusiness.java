
package com.arexis.mugen.phenotype.ontology;


/**
 * This is the business interface for PhenotypeAltId enterprise bean.
 */
public interface PhenotypeAltIdRemoteBusiness {
    
    int getId() throws java.rmi.RemoteException;

    java.lang.String getAltId() throws java.rmi.RemoteException;

    void setAltId(String alt_id) throws java.rmi.RemoteException;
    
}
