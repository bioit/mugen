package com.arexis.mugen.ontologies.doid;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class DoidDTO_REL implements Serializable {
   
    private String relation;
    private int id_b;
    
    public DoidDTO_REL(String relationship) {
        this.relation = relationship.substring(0, relationship.lastIndexOf(" ")).trim();
        this.id_b = new Integer(relationship.substring(relationship.lastIndexOf(":")+1)).intValue();
    }
    
    public String getRelation(){
        return relation;
    }
    
    public int getIdB(){
        return id_b;
    }
}
