
package com.arexis.mugen.export;

import com.arexis.arxframe.PageManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.project.ParamDataObject;
import java.util.Collection;


/**
 * This is the business interface for ExportManager enterprise bean.
 */
public interface ExportManagerRemoteBusiness {
    int getNumberOfFilters(ParamDataObject pdo) throws java.rmi.RemoteException, ApplicationException;

    Collection getFilters(ParamDataObject pdo, MugenCaller caller, PageManager pageManager) throws java.rmi.RemoteException, ApplicationException;

    GQLFilterDTO getFilter(int fid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateFilter(int fid, int sid, java.lang.String name, java.lang.String comm, java.lang.String expression, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int createFilter(java.lang.String name, java.lang.String comm, java.lang.String expression, int sid, int pid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeFilter(int fid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;
    
}
