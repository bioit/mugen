
package com.arexis.mugen.model.functionalsignificance;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.expmodel.ExpModelRemote;
import com.arexis.mugen.model.functionalsignificancetype.FunctionalSignificanceTypeRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.resource.file.FileRemote;
import java.sql.Date;


/**
 * This is the business interface for FunctionalSignificance enterprise bean.
 */
public interface FunctionalSignificanceRemoteBusiness {
    int getFsid() throws java.rmi.RemoteException;

    String getName() throws java.rmi.RemoteException;

    void setName(java.lang.String name) throws java.rmi.RemoteException;

    String getComm() throws java.rmi.RemoteException;

    void setComm(java.lang.String comm) throws java.rmi.RemoteException;

    Date getTs() throws java.rmi.RemoteException;

    UserRemote getUser() throws java.rmi.RemoteException;
    
    void setFile(FileRemote file) throws ApplicationException, java.rmi.RemoteException;

    FileRemote getFile() throws java.rmi.RemoteException;

    void setType(com.arexis.mugen.model.functionalsignificancetype.FunctionalSignificanceTypeRemote fst) throws ApplicationException, java.rmi.RemoteException;

    FunctionalSignificanceTypeRemote getType() throws ApplicationException, java.rmi.RemoteException;

    void setCaller(MugenCaller caller) throws java.rmi.RemoteException;

    ExpModelRemote getModel() throws ApplicationException, java.rmi.RemoteException;
    
}
