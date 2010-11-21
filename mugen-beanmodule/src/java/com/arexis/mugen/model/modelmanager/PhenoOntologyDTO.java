package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.species.gene.GeneRemote;
import com.arexis.mugen.phenotype.ontology.PhenotypeOntologyRemote;
import com.arexis.mugen.project.user.UserRemote;
import java.io.Serializable;

public class PhenoOntologyDTO implements Serializable {
    
    private int phenoId;
    
    private String phenoName, def, defref, mpid, comm, pldfull;
    
    private String isaline;
    
    public PhenoOntologyDTO(PhenotypeOntologyRemote pheno) {
        try {
            this.phenoId = pheno.getId();
            this.phenoName = pheno.getName();
            this.def = pheno.getDef();
            this.defref = pheno.getDefRef();
            this.comm = pheno.getComm();
            this.mpid = getMpid();
            this.isaline = this.mpid + " ! " + this.phenoName;
            this.pldfull = pheno.getStringFullPLD();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int getPhenoId() {
        return phenoId;
    }
    
    public String getMpid(){
        mpid = new Integer(this.phenoId).toString();
        int digits = 7 - mpid.length();
        
        while (digits!=0){
            mpid = "0" + mpid;
            digits--;
        }
        
        mpid = "MP:" + mpid;
        
        return mpid;
    }
    
    public String getPhenoName() {
        return phenoName;
    }
    
    public String getIsaline(){
        return isaline;
    }
    
    public String getDef() {
        return def;
    }
    
    public String getDefref() {
        return defref;
    }
    
    public String getComm() {
        return comm;
    }
    
    public String getPldfull() {
        return pldfull;
    }
}
