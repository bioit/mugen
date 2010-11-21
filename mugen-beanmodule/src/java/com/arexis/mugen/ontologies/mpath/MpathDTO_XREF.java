package com.arexis.mugen.ontologies.mpath;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class MpathDTO_XREF implements Serializable {
   
    private String xref;
    
    public MpathDTO_XREF(String passed_xref) {
        this.xref = passed_xref;
    }
    
    public String getXref(){
        return xref;
    }
    
}
