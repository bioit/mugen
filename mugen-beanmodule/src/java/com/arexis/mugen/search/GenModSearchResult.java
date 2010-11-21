/*
 * FuncSigSearchResult.java
 *
 * Created on March 3, 2006, 2:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.arexis.mugen.search;

import com.arexis.mugen.model.geneticmodification.GeneticModificationRemote;

/**
 *
 * @author heto
 */
public class GenModSearchResult extends SearchResult {
    
    /** Creates a new instance of FuncSigSearchResult */
    public GenModSearchResult() {
    }
    
    /**
     * Creates a new instance of FuncSigSearchResult 
     */
    public GenModSearchResult(GeneticModificationRemote gm, String workflow) 
    {
        try
        {
            this.workflow = workflow;
            this.name = gm.getName();
            this.comment = gm.getComm();
            type = "Genetic Modification (Model)";            
            project = "-";
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }    
    
}
