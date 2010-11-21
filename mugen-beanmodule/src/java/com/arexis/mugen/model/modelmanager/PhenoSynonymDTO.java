package com.arexis.mugen.model.modelmanager;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
//import com.arexis.mugen.phenotype.ontology.PhenotypeSynonymRemote;

public class PhenoSynonymDTO implements Serializable {
   
    private String synonym;
    private String attribute;
    
    public PhenoSynonymDTO(String passed_synonym) {
        //System.out.println("synonym -> "+passed_synonym);
        this.attribute = passed_synonym.substring(passed_synonym.lastIndexOf("\"")+2);
        this.synonym = passed_synonym.substring(0, passed_synonym.lastIndexOf("\""));
    }
    
    public PhenoSynonymDTO(String synonym, String attribute) {
        this.attribute = attribute;
        this.synonym = synonym;
    }
    
    public String getSynonym(){
        return synonym;
    }
    
    public String getAttribute(){
        return attribute;
    }
}
