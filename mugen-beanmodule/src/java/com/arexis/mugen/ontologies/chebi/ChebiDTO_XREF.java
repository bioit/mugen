package com.arexis.mugen.ontologies.chebi;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class ChebiDTO_XREF implements Serializable {
   
    private String xref;
    
    public ChebiDTO_XREF(String passed_xref) {
        this.xref = passed_xref;
    }
    
    public String getXref(){
        return xref;
    }
    
}
