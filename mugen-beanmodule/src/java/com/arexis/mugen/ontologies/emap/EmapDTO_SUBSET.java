package com.arexis.mugen.ontologies.emap;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class EmapDTO_SUBSET implements Serializable {
   
    private String subset;
    
    public EmapDTO_SUBSET(String subset) {
        this.subset = subset;
    }
    
    public String getSubset(){
        return subset;
    }
    
}
