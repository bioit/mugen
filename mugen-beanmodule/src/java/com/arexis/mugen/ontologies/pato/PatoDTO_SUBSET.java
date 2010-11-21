package com.arexis.mugen.ontologies.pato;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class PatoDTO_SUBSET implements Serializable {
   
    private String subset;
    
    public PatoDTO_SUBSET(String subset) {
        this.subset = subset;
    }
    
    public String getSubset(){
        return subset;
    }
    
}
