package com.arexis.mugen.ontologies.ma;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class MaDTO_XREF implements Serializable {
   
    private String xref;
    
    public MaDTO_XREF(String passed_xref) {
        this.xref = passed_xref;
    }
    
    public String getXref(){
        return xref;
    }
    
}
