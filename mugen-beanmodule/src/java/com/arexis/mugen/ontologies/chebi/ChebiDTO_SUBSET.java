package com.arexis.mugen.ontologies.chebi;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class ChebiDTO_SUBSET implements Serializable {
   
    private String subset;
    
    public ChebiDTO_SUBSET(String subset) {
        this.subset = subset;
    }
    
    public String getSubset(){
        return subset;
    }
    
}
