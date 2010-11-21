
package com.arexis.mugen.genotype.umarker;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.species.chromosome.ChromosomeRemote;
import com.arexis.mugen.species.species.SpeciesRemote;
import java.util.Collection;


/**
 * This is the business interface for UMarker enterprise bean.
 */
public interface UMarkerRemoteBusiness {
    int getUmid() throws java.rmi.RemoteException;

    String getName() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;

    double getPosition() throws java.rmi.RemoteException;

    void setPosition(double position) throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    String getAlias() throws java.rmi.RemoteException;

    void setAlias(java.lang.String alias) throws java.rmi.RemoteException;

    int getPid() throws java.rmi.RemoteException;

    void setPid(int pid) throws java.rmi.RemoteException;

    int getCid() throws java.rmi.RemoteException;

    void setCid(int cid) throws java.rmi.RemoteException;

    int getSid() throws java.rmi.RemoteException;

    void setSid(int sid) throws java.rmi.RemoteException;

    java.sql.Date getTs() throws java.rmi.RemoteException;

    int getId() throws java.rmi.RemoteException;

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;

    void addHistory() throws java.rmi.RemoteException;

    Collection getHistory() throws java.rmi.RemoteException;

    SpeciesRemote getSpecies() throws java.rmi.RemoteException;

    ChromosomeRemote getChromosome() throws java.rmi.RemoteException;

    /**
     * Returns the user which made the last update on the marker
     * @return The user
     */
    UserRemote getUser() throws java.rmi.RemoteException;
    
}
