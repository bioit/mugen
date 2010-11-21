
package com.arexis.mugen.species.lmarker;
import com.arexis.mugen.species.chromosome.ChromosomeRemote;
import com.arexis.mugen.species.species.SpeciesRemote;


/**
 * This is the business interface for LMarker enterprise bean.
 */
public interface LMarkerRemoteBusiness {
    int getLmid() throws java.rmi.RemoteException;

    String getName() throws java.rmi.RemoteException;
    void setName(java.lang.String name) throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;
    void setComm(java.lang.String comm) throws java.rmi.RemoteException; 
    
    void setP1(java.lang.String p1) throws java.rmi.RemoteException; 
    String getP1() throws java.rmi.RemoteException; 
    
    void setP2(java.lang.String p2) throws java.rmi.RemoteException; 
    String getP2() throws java.rmi.RemoteException; 
    
    void setPosition(double position) throws java.rmi.RemoteException; 

    double getPosition() throws java.rmi.RemoteException;  
    
    String getAlias() throws java.rmi.RemoteException;
    void setAlias(java.lang.String name) throws java.rmi.RemoteException;    

    void setSid(int sid) throws java.rmi.RemoteException;

    void setCid(int cid) throws java.rmi.RemoteException;

    /**
     * Get the species for this LMarker
     * @return A collection of species that belongs to the sampling unit
     */
    SpeciesRemote getSpecies() throws java.rmi.RemoteException;

    /**
     * Get the species for this LMarker
     * @return A collection of species that belongs to the sampling unit
     */
    ChromosomeRemote getChromosome() throws java.rmi.RemoteException;
}
