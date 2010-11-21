
package com.arexis.mugen.species.lallele;

import com.arexis.mugen.species.lmarker.LMarkerRemote;


/**
 * This is the business interface for LAlleles enterprise bean.
 */
public interface LAlleleRemoteBusiness {
    /**
     * Returns the id of the LAllele
     * @return int the LAllele id
     */
    int getLaid() throws java.rmi.RemoteException;

    /**
     * Returns the name of this LAllele
     * @return The name of the LAllele
     */
    String getName() throws java.rmi.RemoteException;

    /**
     * Sets the name of the LAllele
     * @param name The new name of the LAllele
     */
    void setName(java.lang.String name) throws java.rmi.RemoteException;

    /**
     * Returns a comment for the LAllele
     * @return The comment for the LAllele
     */
    String getComm() throws java.rmi.RemoteException;

    /**
     * Sets the comment for the LAllele
     * @param comm The new comment
     */
    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    void setLmid(int lmid) throws java.rmi.RemoteException;

    LMarkerRemote getLMarker() throws java.rmi.RemoteException;
    
}
