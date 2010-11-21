package com.arexis.mugen.search;

import com.arexis.mugen.phenotype.ontology.PhenotypeOntologyRemote;
import com.arexis.mugen.model.strain.allele.StrainAlleleRemote;

public class AlleleSearchResult extends SearchResult {
    
    public AlleleSearchResult(StrainAlleleRemote allele, String workflow) {
        try {
            this.workflow = workflow;
            this.name = allele.getSymbol().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            this.comment = allele.getName().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            type = "Allele";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
}
