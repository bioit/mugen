package com.arexis.mugen.model.modelmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class PhenoTermParseDTO implements Serializable {
    
    private String pheno_term;
    
    public PhenoTermParseDTO(String passed_term ) {
        this.pheno_term = passed_term;
    }
    
    public String getPhenoTerm() {
        return pheno_term;
    }
    
}
