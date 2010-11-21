package com.arexis.mugen.ontologies.doid;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class DoidDTO_SUBSET implements Serializable {
   
    private String subset;
    
    public DoidDTO_SUBSET(String subset) {
        this.subset = subset;
    }
    
    public String getSubset(){
        return subset;
    }
    
}
