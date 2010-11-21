package com.arexis.mugen.ontologies.go;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class GoDTO_XREF implements Serializable {
   
    private String xref;
    
    public GoDTO_XREF(String passed_xref) {
        this.xref = passed_xref;
    }
    
    public String getXref(){
        return xref;
    }
    
}
