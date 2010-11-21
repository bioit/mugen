package com.arexis.mugen.model.modelmanager;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class updateMPDTO_xref implements Serializable {
   
    private String xref;
    
    public updateMPDTO_xref(String passed_xref) {
        this.xref = passed_xref;
    }
    
    public String getXref(){
        return xref;
    }
    
}
