package com.arexis.mugen.model.modelmanager;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class updateMPDTO_alt implements Serializable {
   
    private String alt_id;
    
    public updateMPDTO_alt(String passed_alt_id) {
        this.alt_id = passed_alt_id;
    }
    
    public String getAltId(){
        return alt_id;
    }
    
}
