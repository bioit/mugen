package com.arexis.mugen.ontologies.cell;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class CellDTO_SUBSET implements Serializable {
   
    private String subset;
    
    public CellDTO_SUBSET(String subset) {
        this.subset = subset;
    }
    
    public String getSubset(){
        return subset;
    }
    
}
