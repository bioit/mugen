package com.arexis.mugen.phenotype.ontology;

public interface PhenotypeOntologyRemoteBusiness {
    int getId() throws java.rmi.RemoteException;

    java.lang.String getName() throws java.rmi.RemoteException;

    void setName(String name) throws java.rmi.RemoteException;

    java.lang.String getDef() throws java.rmi.RemoteException;

    void setRef(String def) throws java.rmi.RemoteException;

    java.lang.String getDefRef() throws java.rmi.RemoteException;

    void setDefRef(String def_ref) throws java.rmi.RemoteException;

    int getIsObsolete() throws java.rmi.RemoteException;

    void setIsObsolete(int is_obsolete) throws java.rmi.RemoteException;

    void addXref(PhenotypeXrefRemote xref) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void addAltId(PhenotypeAltIdRemote altid) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void addSynonym(PhenotypeSynonymRemote synonym, String attribute) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void addIsA(int is_a) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    java.lang.String getComm() throws java.rmi.RemoteException;

    void setComm(String comm) throws java.rmi.RemoteException;

    java.util.Collection getPLD() throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    java.lang.String getStringFullPLD() throws java.rmi.RemoteException;
    
}
