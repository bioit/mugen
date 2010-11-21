package org.mugen.ws;

import com.arexis.mugen.exceptions.ApplicationException;
import org.mugen.dtos.*;

public interface mugenwsSEI extends java.rmi.Remote {
    
    public java.lang.String getProjectName() throws java.rmi.RemoteException;

    public java.lang.String[] getMugenMice() throws java.rmi.RemoteException;

    public MugenModelDTO[] getMugenMiceDTO() throws java.rmi.RemoteException;
    
    public MugenModelDTO[] getMugenMiceDTOByKey(String key) throws java.rmi.RemoteException;

    public MugenGeneDTO[] getMugenGenesByModel(int eid) throws java.rmi.RemoteException;

    public MugenAvailabilityDTO[] getMugenAvailabilityByModel(int eid) throws java.rmi.RemoteException;

    public MugenBackgroundDTO[] getMugenBackgroundByModel(int eid) throws java.rmi.RemoteException;

    public MugenPhenotypesDTO[] getMugenPhenotypesByModel(int eid) throws java.rmi.RemoteException;

    /**
     * Web service operation
     * @param ensembl 
     * @throws java.rmi.RemoteException 
     * @return 
     */
    public java.lang.String[] getMugenMiceByEnsembl(String ensembl) throws java.rmi.RemoteException;

}
