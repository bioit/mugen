package com.arexis.mugen.search;

import com.arexis.mugen.species.gene.GeneRemote;

public class GeneSearchResult extends SearchResult {
    
    public GeneSearchResult(GeneRemote gene, String workflow) {
        try {
            this.workflow = workflow;
            this.name = gene.getName().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            this.comment = gene.getComm().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            type = "Gene";
            project = gene.getProject().getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
}
