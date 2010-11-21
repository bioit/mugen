package com.arexis.mugen.ontologies.cell;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class CellDTO_REP implements Serializable {
   
    private String replaced;
    
    public CellDTO_REP(String passed_replaced) {
        this.replaced = passed_replaced.trim();
    }
    
    public String getReplaced(){
        return replaced;
    }
    
    public int getReplacedInt(){
        return new Integer(replaced).intValue();
    }
    
}
