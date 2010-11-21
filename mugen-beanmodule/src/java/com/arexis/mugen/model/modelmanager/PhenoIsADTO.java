package com.arexis.mugen.model.modelmanager;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class PhenoIsADTO implements Serializable {
   
    private String is_a;
    
    public PhenoIsADTO(String passed_is_a) {
        this.is_a = passed_is_a;
    }
    
    public String getIsA(){
        return is_a;
    }
    
}
