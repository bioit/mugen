package org.mugen.dtos;

public class MugenPhenotypesDTO implements java.io.Serializable {
    
    private String phenotype;
    
    public MugenPhenotypesDTO(){}
    
    public String getPhenotype(){
        return phenotype;
    }
    
    public void setPhenotype(String phenotype){
        this.phenotype = phenotype;
    }
}
