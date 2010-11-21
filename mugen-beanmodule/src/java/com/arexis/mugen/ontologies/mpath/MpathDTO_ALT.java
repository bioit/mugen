package com.arexis.mugen.ontologies.mpath;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class MpathDTO_ALT implements Serializable {
   
    private String alt_id;
    
    public MpathDTO_ALT(String passed_alt_id) {
        this.alt_id = passed_alt_id;
    }
    
    public String getAltId(){
        return alt_id;
    }
    
}
