package com.arexis.mugen.ontologies.mpath;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class MpathDTO_REP implements Serializable {
   
    private String replaced;
    
    public MpathDTO_REP(String passed_replaced) {
        this.replaced = passed_replaced.trim();
    }
    
    public String getReplaced(){
        return replaced;
    }
    
    public int getReplacedInt(){
        return new Integer(replaced).intValue();
    }
    
}
