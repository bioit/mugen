package com.arexis.mugen.model.modelmanager;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import com.arexis.mugen.phenotype.ontology.PhenotypeAltIdRemote;

public class PhenoAltIdDTO implements Serializable {
   
    private int id;
    private String alt_id;
    
    public PhenoAltIdDTO(String passed_alt_id) {
        this.alt_id = passed_alt_id;
    }
    
    public PhenoAltIdDTO(PhenotypeAltIdRemote potalt) {
        try {
            this.alt_id = potalt.getAltId();
            this.id = potalt.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int getInt() {
        return id;
    }
    
    public String getAltId(){
        return alt_id;
    }
    
}
