/*
 * ImportJobDTO.java
 *
 * Created on April 25, 2006, 10:38 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.arexis.alab.importerbean;

import com.arexis.alab.imp.importjob.ImportJobRemote;
import java.io.Serializable;

/**
 *
 * @author heto
 */
public class ImportJobDTO implements Serializable {
    
    private int jobid;
    private int pid;
    
    private int fileid;
    private String filename;
          
    private int userid;
    
    private String report;
    private String status;
    private int progress;
    
    /**
     * Creates a new instance of ImportJobDTO
     */
    public ImportJobDTO(ImportJobRemote job) {
        try
        {
            jobid = job.getJobId();
            pid = job.getProject().getPid();
            fileid = job.getFile().getFileId();
            filename = job.getFile().getName();
            userid = job.getUser().getId();
            report = job.getReportString();
            if (report!=null)
                report = report.replaceAll("\n","<br>");
            status = job.getStatus();
            progress = job.getProgress();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public int getJobId() {
        return jobid;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public String getProgress()
    {
        if (progress==0 || progress==100)
            return "";
        else
            return new Integer(progress).toString()+"%";
    }

    public int getPid() {
        return pid;
    }

    public int getFileid() {
        return fileid;
    }

    public int getUserid() {
        return userid;
    }
    
    public String getFileName() {
        return filename;
    }
    
    public String getReport() {
        return report;
    }
    
}
