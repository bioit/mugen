package com.arexis.mugen.ontologies.go;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class GoDTO_REP implements Serializable {
   
    private String replaced;
    
    public GoDTO_REP(String passed_replaced) {
        this.replaced = passed_replaced.trim();
    }
    
    public String getReplaced(){
        return replaced;
    }
    
    public int getReplacedInt(){
        return new Integer(replaced).intValue();
    }
    
}
