package com.arexis.mugen.model.modelmanager;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class updateMPDTO_syn implements Serializable {
   
    private String synonym;
    private String attribute;
    
    public updateMPDTO_syn(String passed_synonym) {
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
