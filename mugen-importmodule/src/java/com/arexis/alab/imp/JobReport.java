/*
 * Report.java
 *
 * Created on March 8, 2006, 10:29 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.arexis.alab.imp;

import java.util.ArrayList;

/**
 *
 * @author heto
 */
public class JobReport {
    
    private ArrayList exceptions;
    
    private ArrayList info;
    private ArrayList warning;
    private ArrayList error;
    
    /** Creates a new instance of Report */
    public JobReport() {
        exceptions = new ArrayList();
        
        info = new ArrayList();
        warning = new ArrayList();
        error = new ArrayList();
    }
    
    public void add(ImportException ie)
    {
        add(ie, "ERROR");
    }
    
    public void add(ImportException ie, String level)
    {
        if (level.equalsIgnoreCase("INFO"))
        {
            info.add(ie);
        }
        else if (level.equalsIgnoreCase("WARNING"))
        {
            warning.add(ie);
        }
        else // (level.equalsIgnoreCase("ERROR"))
        {
            error.add(ie);
        }   
        
        //exceptions.add(ie);
    }
    
    public String toString()
    {
        StringBuffer out = new StringBuffer();
        
        out.append("Summary\nInfo:"+info.size()+"\nWarning:"+warning.size()+"\nError:"+error.size()+"\n\n");
        
        // Info
        out.append("Info:"+info.size()+"\n");
        for (int i=0;i<info.size();i++)
        {
            ImportException ie = ((ImportException)info.get(i));
            out.append(ie.getRow()+" - "+ie.getMessage()+"\n");
        }
        
        // Warning
        out.append("Warning:"+warning.size()+"\n");
        for (int i=0;i<warning.size();i++)
        {
            ImportException ie = ((ImportException)warning.get(i));
            out.append(ie.getRow()+" - "+ie.getMessage()+"\n");
        }
        
        // Error
        out.append("Error:"+error.size()+"\n");
        for (int i=0;i<error.size();i++)
        {
            ImportException ie = ((ImportException)error.get(i));
            out.append(ie.getRow()+" - "+ie.getMessage()+"\n");
        }
        return out.toString();
    }
    
    public boolean hasInfo()
    {
        if (info.size()>0)
            return true;
        else
            return false;
    }
    
    public boolean hasWarning()
    {
        if (warning.size()>0)
            return true;
        else
            return false;
    }
    
    public boolean hasError()
    {
        if (error.size()>0)
            return true;
        else
            return false;
    }
}
