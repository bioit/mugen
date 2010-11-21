
package com.arexis.alab.imp.importjob;

import com.arexis.alab.imp.JobReport;


/**
 * This is the business interface for ImportJob enterprise bean.
 */
public interface ImportJobRemoteBusiness {
    int getJobId() throws java.rmi.RemoteException;

    com.arexis.mugen.project.user.UserRemote getUser() throws java.rmi.RemoteException;

    com.arexis.mugen.resource.file.FileRemote getFile() throws java.rmi.RemoteException;

    com.arexis.mugen.project.project.ProjectRemote getProject() throws java.rmi.RemoteException;

    void setAttributes(ImportAttributes attrs) throws java.rmi.RemoteException;

    com.arexis.alab.imp.importjob.ImportAttributes getAttributes() throws java.rmi.RemoteException;

    void setReport(JobReport rep) throws java.rmi.RemoteException;

    /**
     * Get the report of an import. The report contains text in multiple lines.
     */
    java.lang.String getReportString() throws java.rmi.RemoteException;

    java.lang.String getStatus() throws java.rmi.RemoteException;

    void setStatus(String status) throws java.rmi.RemoteException;

    void setItems(int items) throws java.rmi.RemoteException;

    int getItems() throws java.rmi.RemoteException;

    int getProgress() throws java.rmi.RemoteException;

    void setProgress(int progress) throws java.rmi.RemoteException;

    /**
     * Set the report of an import. The report contains text in multiple lines.
     * 
     * @param report The report to store in the database. Multiline of text.
     */
    void setReportString(String report) throws java.rmi.RemoteException;
    
}
