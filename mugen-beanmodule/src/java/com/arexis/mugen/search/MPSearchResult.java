package com.arexis.mugen.search;

import com.arexis.mugen.phenotype.ontology.PhenotypeOntologyRemote;

public class MPSearchResult extends SearchResult {
    
    public MPSearchResult(PhenotypeOntologyRemote mp, String workflow) {
        try {
            this.workflow = workflow;
            this.name = mp.getName();
            this.comment = mp.getComm();
            type = "MP Term";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
}
