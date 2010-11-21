package com.arexis.mugen.ontologies.pato;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class PatoDTO_ALT implements Serializable {
   
    private String alt_id;
    
    public PatoDTO_ALT(String passed_alt_id) {
        this.alt_id = passed_alt_id;
    }
    
    public String getAltId(){
        return alt_id;
    }
    
}
