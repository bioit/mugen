/*
 * FuncSigSearchResult.java
 *
 * Created on March 3, 2006, 2:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.arexis.mugen.search;

import com.arexis.mugen.model.functionalsignificancetype.FunctionalSignificanceTypeRemote;

/**
 *
 * @author heto
 */
public class FuncSigTypeSearchResult extends SearchResult {
    
    /** Creates a new instance of FuncSigSearchResult */
    public FuncSigTypeSearchResult() {
    }
    
    /**
     * Creates a new instance of FuncSigSearchResult 
     */
    public FuncSigTypeSearchResult(FunctionalSignificanceTypeRemote fst, String workflow) 
    {
        try
        {
            this.workflow = workflow;
            this.name = fst.getName();
            this.comment = fst.getComm();
            type = "Functional Significance Type";            
            project = fst.getProject().getName();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
