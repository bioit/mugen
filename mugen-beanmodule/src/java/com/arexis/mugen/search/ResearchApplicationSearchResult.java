/*
 * FuncSigSearchResult.java
 *
 * Created on March 3, 2006, 2:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.arexis.mugen.search;

import com.arexis.mugen.model.researchapplication.ResearchApplicationRemote;

/**
 *
 * @author heto
 */
public class ResearchApplicationSearchResult extends SearchResult {
    
    /** Creates a new instance of FuncSigSearchResult */
    public ResearchApplicationSearchResult() {
    }
    
    /**
     * Creates a new instance of FuncSigSearchResult 
     */
    public ResearchApplicationSearchResult(ResearchApplicationRemote ra, String workflow) 
    {
        try
        {
            this.workflow = workflow;
            this.name = ra.getName();
            this.comment = ra.getComm();
            type = "Research Application";            
            project = ra.getProject().getName();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }    
    
}
