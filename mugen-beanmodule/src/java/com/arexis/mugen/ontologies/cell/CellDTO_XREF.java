package com.arexis.mugen.ontologies.cell;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class CellDTO_XREF implements Serializable {
   
    private String xref;
    
    public CellDTO_XREF(String passed_xref) {
        this.xref = passed_xref;
    }
    
    public String getXref(){
        return xref;
    }
    
}
