package com.arexis.mugen.ontologies.pato;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class PatoDTO_SYN implements Serializable {
   
    private String synonym;
    private String attribute;
    
    public PatoDTO_SYN(String passed_synonym) {
        //this.attribute = passed_synonym.substring(passed_synonym.lastIndexOf("\"")+2);
        //this.synonym = passed_synonym.substring(0, passed_synonym.lastIndexOf("\""));
        this.attribute = passed_synonym.substring(0, passed_synonym.indexOf(":"));
        this.synonym = passed_synonym.substring(passed_synonym.indexOf("\"")+1, passed_synonym.lastIndexOf("\""));
    }
    
    public String getSynonym(){
        return synonym;
    }
    
    public String getAttribute(){
        return attribute;
    }
}
