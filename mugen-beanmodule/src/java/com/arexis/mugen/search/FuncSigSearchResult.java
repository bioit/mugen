/*
 * FuncSigSearchResult.java
 *
 * Created on March 3, 2006, 2:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.arexis.mugen.search;

import com.arexis.mugen.model.functionalsignificance.FunctionalSignificanceRemote;
import com.arexis.mugen.project.project.ProjectRemote;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author heto
 */
public class FuncSigSearchResult extends SearchResult {
    
    /** Creates a new instance of FuncSigSearchResult */
    public FuncSigSearchResult() {
    }
    
    /**
     * Creates a new instance of FuncSigSearchResult 
     */
    public FuncSigSearchResult(FunctionalSignificanceRemote fs, String workflow) 
    {
        try
        {
            this.workflow = workflow;
            this.name = fs.getName();
            this.comment = fs.getComm();
            type = "Functional Significance";            
            project = fs.getType().getProject().getName();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }    
    
}
