
package com.arexis.mugen.genotype.marker;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.species.chromosome.ChromosomeRemote;
import java.util.Collection;


/**
 * This is the business interface for Marker enterprise bean.
 */
public interface MarkerRemoteBusiness {
    int getMid() throws java.rmi.RemoteException;

    String getName() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;

    String getAlias() throws java.rmi.RemoteException;

    void setAlias(java.lang.String alias) throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    String getP1() throws java.rmi.RemoteException;

    void setP1(java.lang.String p1) throws java.rmi.RemoteException;

    String getP2() throws java.rmi.RemoteException;

    void setP2(java.lang.String p2) throws java.rmi.RemoteException;

    void setPosition(double position) throws java.rmi.RemoteException;

    double getPosition() throws java.rmi.RemoteException;

    SamplingUnitRemote getSamplingUnit() throws java.rmi.RemoteException;

    void setSuid(int suid) throws java.rmi.RemoteException;

    ChromosomeRemote getChromosome() throws java.rmi.RemoteException;

    void setCid(int cid) throws java.rmi.RemoteException;    

    UserRemote getUser() throws java.rmi.RemoteException;

    java.sql.Date getTs() throws java.rmi.RemoteException;

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;

    void addHistory() throws java.rmi.RemoteException;

    Collection getHistory() throws java.rmi.RemoteException;

    Collection getAlleles() throws java.rmi.RemoteException;
}
