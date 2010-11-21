package com.arexis.mugen.ontologies.emap;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class EmapDTO_SYN implements Serializable {
   
    private String synonym;
    private String attribute;
    
    public EmapDTO_SYN(String passed_synonym) {
        this.attribute = passed_synonym.substring(passed_synonym.lastIndexOf("\"")+2);
        this.synonym = passed_synonym.substring(0, passed_synonym.lastIndexOf("\""));
    }
    
    public String getSynonym(){
        return synonym;
    }
    
    public String getAttribute(){
        return attribute;
    }
}
