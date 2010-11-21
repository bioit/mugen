package com.arexis.mugen.ontologies.imr;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class ImrDTO_CON implements Serializable {
   
    private String consider;
    
    public ImrDTO_CON(String passed_consider) {
        this.consider = passed_consider.trim();
    }
    
    public String getConsider(){
        return consider;
    }
    
    public int getConsiderInt(){
        return new Integer(consider).intValue();
    }
    
}
