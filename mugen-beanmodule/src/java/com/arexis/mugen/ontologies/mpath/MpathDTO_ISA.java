package com.arexis.mugen.ontologies.mpath;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class MpathDTO_ISA implements Serializable {
   
    private String is_a;
    
    public MpathDTO_ISA(String passed_is_a) {
        this.is_a = passed_is_a;
    }
    
    public String getIsA(){
        return is_a;
    }
    
    public int getIsAInt(){
        return new Integer(is_a).intValue();
    }
    
}
