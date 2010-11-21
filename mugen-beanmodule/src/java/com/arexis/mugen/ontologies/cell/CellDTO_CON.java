package com.arexis.mugen.ontologies.cell;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class CellDTO_CON implements Serializable {
   
    private String consider;
    
    public CellDTO_CON(String passed_consider) {
        this.consider = passed_consider.trim();
    }
    
    public String getConsider(){
        return consider;
    }
    
    public int getConsiderInt(){
        return new Integer(consider).intValue();
    }
    
}
