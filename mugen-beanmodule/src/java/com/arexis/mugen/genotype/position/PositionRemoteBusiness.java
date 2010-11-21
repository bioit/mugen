
package com.arexis.mugen.genotype.position;

import com.arexis.mugen.genotype.marker.MarkerRemote;
import com.arexis.mugen.genotype.markerset.MarkerSetRemote;


/**
 * This is the business interface for Position enterprise bean.
 */
public interface PositionRemoteBusiness {
    
    double getValue() throws java.rmi.RemoteException;

    void setValue(double value) throws java.rmi.RemoteException;

    MarkerSetRemote getMarkerSet() throws java.rmi.RemoteException;

    MarkerRemote getMarker() throws java.rmi.RemoteException;
    
}
