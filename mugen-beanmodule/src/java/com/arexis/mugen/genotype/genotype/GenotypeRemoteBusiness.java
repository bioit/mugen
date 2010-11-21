
package com.arexis.mugen.genotype.genotype;

import com.arexis.mugen.genotype.allele.AlleleRemote;
import com.arexis.mugen.genotype.marker.MarkerRemote;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.samplingunit.individual.IndividualRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import java.util.Collection;


/**
 * This is the business interface for Genotype enterprise bean.
 */
public interface GenotypeRemoteBusiness {

    String getRaw1() throws java.rmi.RemoteException;

    void setRaw1(java.lang.String raw1) throws java.rmi.RemoteException;

    String getRaw2() throws java.rmi.RemoteException;

    void setRaw2(java.lang.String raw2) throws java.rmi.RemoteException;

    String getReference() throws java.rmi.RemoteException;

    void setReference(java.lang.String reference) throws java.rmi.RemoteException;

    int getLevel() throws java.rmi.RemoteException;

    void setLevel(int level) throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    AlleleRemote getAid1() throws java.rmi.RemoteException;

    void setAid1(int aid1) throws java.rmi.RemoteException;

    AlleleRemote getAid2() throws java.rmi.RemoteException;

    void setAid2(int aid2) throws java.rmi.RemoteException;

    java.sql.Date getUpdated() throws java.rmi.RemoteException;

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;

    Collection getHistory() throws java.rmi.RemoteException;

    void addHistory() throws java.rmi.RemoteException;

    IndividualRemote getIndividual() throws java.rmi.RemoteException;

    SamplingUnitRemote getSamplingUnit() throws java.rmi.RemoteException;

    UserRemote getUser() throws java.rmi.RemoteException;

    MarkerRemote getMarker() throws java.rmi.RemoteException;

    Collection getAlleles() throws java.rmi.RemoteException;
    
}
