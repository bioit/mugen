package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.species.gene.GeneRemote;
import com.arexis.mugen.phenotype.ontology.PhenotypeOntologyRemote;
import com.arexis.mugen.project.user.UserRemote;
import java.io.Serializable;

public class PhenoAssignDTO implements Serializable {
    
    private int phenoId00;
    private int phenoId01;
    private int phenoId02;
    private int phenoId03;
    private int phenoId04;
    private int phenoId05;
    private int phenoId06;
    private int phenoId07;
    private int phenoId08;
    
    private String phenoName00;
    private String phenoName01;
    private String phenoName02;
    private String phenoName03;
    private String phenoName04;
    private String phenoName05;
    private String phenoName06;
    private String phenoName07;
    private String phenoName08;
    
    private String phenoPath;
    
    public PhenoAssignDTO(int mpInt00, int mpInt01, int mpInt02, int mpInt03, int mpInt04, int mpInt05, int mpInt06, int mpInt07, int mpInt08){
        phenoId00 = mpInt00;
        phenoId01 = mpInt01;
        phenoId02 = mpInt02;
        phenoId03 = mpInt03;
        phenoId04 = mpInt04;
        phenoId05 = mpInt05;
        phenoId06 = mpInt06;
        phenoId07 = mpInt07;
        phenoId08 = mpInt08;
    }
    
    public PhenoAssignDTO(PhenotypeOntologyRemote pheno00,
            PhenotypeOntologyRemote pheno01, 
            PhenotypeOntologyRemote pheno02, 
            PhenotypeOntologyRemote pheno03, 
            PhenotypeOntologyRemote pheno04,
            PhenotypeOntologyRemote pheno05,
            PhenotypeOntologyRemote pheno06,
            PhenotypeOntologyRemote pheno07,
            PhenotypeOntologyRemote pheno08) {
        try {
            phenoId00 = pheno00.getId();
            phenoName00 = pheno00.getName();
            
            phenoId01 = pheno01.getId();
            phenoName01 = pheno01.getName();
            
            phenoId02 = pheno02.getId();
            phenoName02 = pheno02.getName();
            
            phenoId03 = pheno03.getId();
            phenoName03 = pheno03.getName();
            
            phenoId04 = pheno04.getId();
            phenoName04 = pheno04.getName();
            
            phenoId05 = pheno05.getId();
            phenoName05 = pheno05.getName();
            
            phenoId06 = pheno06.getId();
            phenoName06 = pheno06.getName();
            
            phenoId07 = pheno07.getId();
            phenoName07 = pheno07.getName();
            
            phenoId08 = pheno08.getId();
            phenoName08 = pheno08.getName();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getPhenoPath(){
        phenoPath = phenoName00;
        
        if(phenoId01!=0){
            phenoPath = phenoPath+" <b>&gt;</b> "+phenoName01; 
        }
        
        if(phenoId02!=0){
            phenoPath = phenoPath+" <b>&gt;</b> "+phenoName02; 
        }
        
        if(phenoId03!=0){
            phenoPath = phenoPath+" <b>&gt;</b> "+phenoName03; 
        }
        
        if(phenoId04!=0){
            phenoPath = phenoPath+" <b>&gt;</b> "+phenoName04; 
        }
        
        if(phenoId05!=0){
            phenoPath = phenoPath+" <b>&gt;</b> "+phenoName05; 
        }
        
        if(phenoId06!=0){
            phenoPath = phenoPath+" <b>&gt;</b> "+phenoName06; 
        }
        
        if(phenoId07!=0){
            phenoPath = phenoPath+" <b>&gt;</b> "+phenoName07; 
        }
        
        if(phenoId08!=0){
            phenoPath = phenoPath+" <b>&gt;</b> "+phenoName08; 
        }
        //return phenoName00+"->"+phenoName01+"->"+phenoName02+"->"+phenoName03+"->"+phenoName04+"->"+phenoName05+"->"+phenoName06+"->"+phenoName07+"->"+phenoName08;
        return phenoPath;
    }
    
    public int getPhenoId00(){
        return phenoId00;
    }
    
    public String getPhenoName00(){
        return phenoName00;
    }
    
    public int getPhenoId01(){
        return phenoId01;
    }
    
    public String getPhenoName01(){
        return phenoName01;
    }
    
    public int getPhenoId02(){
        return phenoId02;
    }
    
    public String getPhenoName02(){
        return phenoName02;
    }
    
    public int getPhenoId03(){
        return phenoId03;
    }
    
    public String getPhenoName03(){
        return phenoName03;
    }
    
    public int getPhenoId04(){
        return phenoId04;
    }
    
    public String getPhenoName04(){
        return phenoName04;
    }
    
    public int getPhenoId05(){
        return phenoId05;
    }
    
    public String getPhenoName05(){
        return phenoName05;
    }
    
    public int getPhenoId06(){
        return phenoId06;
    }
    
    public String getPhenoName06(){
        return phenoName06;
    }
    
    public int getPhenoId07(){
        return phenoId07;
    }
    
    public String getPhenoName07(){
        return phenoName07;
    }
    
    public int getPhenoId08(){
        return phenoId08;
    }
    
    public String getPhenoName08(){
        return phenoName08;
    }
}
