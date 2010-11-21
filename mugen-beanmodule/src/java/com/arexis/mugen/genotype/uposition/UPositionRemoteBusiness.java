
package com.arexis.mugen.genotype.uposition;


/**
 * This is the business interface for UPosition enterprise bean.
 */
public interface UPositionRemoteBusiness {
    int getUmsid() throws java.rmi.RemoteException;

    int getUmid() throws java.rmi.RemoteException;

    double getValue() throws java.rmi.RemoteException;

    void setValue(double value) throws java.rmi.RemoteException;
    
}
