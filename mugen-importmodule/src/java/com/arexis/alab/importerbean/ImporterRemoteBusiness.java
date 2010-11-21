
package com.arexis.alab.importerbean;

import com.arexis.alab.imp.importjob.ImportJobRemote;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import java.util.Collection;
import javax.naming.directory.BasicAttributes;


/**
 * This is the business interface for ImportProcessor enterprise bean.
 */
public interface ImporterRemoteBusiness {

    void imp(int fileid, BasicAttributes attribs, MugenCaller caller) throws java.rmi.RemoteException;

    void check(int fileid, BasicAttributes attribs, MugenCaller caller) throws java.rmi.RemoteException;

    Collection getImportJobs(int user, int pid) throws java.rmi.RemoteException;

    ImportJobRemote startCheck(MugenCaller caller, int fileid, BasicAttributes attrs) throws java.rmi.RemoteException, ApplicationException;

    /**
     * 
     * Use this method to start a check in background. 
     * This calls the message bean.
     */
    void startImport(int jobid, MugenCaller caller) throws java.rmi.RemoteException, ApplicationException;

    /**
     * Remove all jobs for a user in a project. This removes the uploaded file 
     * as well!
     */
    void removeAllImportJobs(int user, int pid) throws java.rmi.RemoteException;

    com.arexis.alab.importerbean.ImportJobDTO getImportJob(int jobid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    boolean inProgress(int user, int pid) throws java.rmi.RemoteException;
    
}
