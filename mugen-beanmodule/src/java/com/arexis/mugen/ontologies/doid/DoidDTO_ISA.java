package com.arexis.mugen.ontologies.doid;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class DoidDTO_ISA implements Serializable {
   
    private String is_a;
    
    public DoidDTO_ISA(String passed_is_a) {
        this.is_a = passed_is_a;
    }
    
    public String getIsA(){
        return is_a;
    }
    
    public int getIsAInt(){
        return new Integer(is_a).intValue();
    }
    
}
