package com.arexis.mugen.ontologies.pato;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class PatoDTO_XREF implements Serializable {
   
    private String xref;
    
    public PatoDTO_XREF(String passed_xref) {
        this.xref = passed_xref;
    }
    
    public String getXref(){
        return xref;
    }
    
}
