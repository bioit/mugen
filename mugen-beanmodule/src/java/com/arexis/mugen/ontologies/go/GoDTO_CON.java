package com.arexis.mugen.ontologies.go;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class GoDTO_CON implements Serializable {
   
    private String consider;
    
    public GoDTO_CON(String passed_consider) {
        this.consider = passed_consider.trim();
    }
    
    public String getConsider(){
        return consider;
    }
    
    public int getConsiderInt(){
        return new Integer(consider).intValue();
    }
    
}
