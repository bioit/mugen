package com.arexis.mugen.ontologies.chebi;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class ChebiDTO_REP implements Serializable {
   
    private String replaced;
    
    public ChebiDTO_REP(String passed_replaced) {
        this.replaced = passed_replaced.trim();
    }
    
    public String getReplaced(){
        return replaced;
    }
    
    public int getReplacedInt(){
        return new Integer(replaced).intValue();
    }
    
}
