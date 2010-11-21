package com.arexis.mugen.ontologies.imr;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class ImrDTO_SYN implements Serializable {
   
    private String synonym;
    private String attribute;
    
    public ImrDTO_SYN(String passed_synonym) {
        //this.attribute = passed_synonym.substring(passed_synonym.lastIndexOf("\"")+2);
        //this.synonym = passed_synonym.substring(0, passed_synonym.lastIndexOf("\""));
        this.attribute = passed_synonym.substring(0, passed_synonym.indexOf("_"));
        this.synonym = passed_synonym.substring(passed_synonym.indexOf("\"")+1, passed_synonym.lastIndexOf("\""));
    }
    
    public String getSynonym(){
        return synonym;
    }
    
    public String getAttribute(){
        return attribute;
    }
}
