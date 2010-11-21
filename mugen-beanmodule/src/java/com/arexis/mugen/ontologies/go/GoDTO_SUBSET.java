package com.arexis.mugen.ontologies.go;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class GoDTO_SUBSET implements Serializable {
   
    private String subset;
    
    public GoDTO_SUBSET(String subset) {
        this.subset = subset;
    }
    
    public String getSubset(){
        return subset;
    }
    
}
