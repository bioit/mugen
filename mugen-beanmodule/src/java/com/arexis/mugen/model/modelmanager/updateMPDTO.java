package com.arexis.mugen.model.modelmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class updateMPDTO implements Serializable {
    
    private String pheno_id="";
    
    private String pheno_name="";
    
    private String pheno_def="";
    private String pheno_def_ref="";
   
    private String pheno_is_obsolete="0";
    
    private String comm = "";
    
    private Collection synonyms = new ArrayList();
    private String synonyms_as_string="";
    
    private Collection is_as = new ArrayList();
    private String is_as_string = "";
    
    private Collection alt_ids = new ArrayList();
    private String alt_ids_as_string = "";
    
    private Collection xrefs = new ArrayList();
    private String xrefs_as_string = "";
    
    public updateMPDTO() {
        //unique items of mp term
        this.pheno_id = pheno_id;
        this.pheno_name = pheno_name;
        this.pheno_def = pheno_def;
        this.pheno_def_ref = pheno_def_ref;
        this.pheno_is_obsolete = pheno_is_obsolete;
        this.comm = comm;
        
        //collections of items relevant to mp term
        this.synonyms = synonyms;
        this.is_as = is_as;
        this.alt_ids = alt_ids;
        this.xrefs = xrefs;
    
    }
    
    public void IdentifyLine(String line){
        
        //check for the mp term id
        if(line.regionMatches(true, 0, "id: MP:", 0, 7)){
            this.pheno_id = line.substring(7);
        }
        
        //check for the mp term name 
        if(line.regionMatches(true, 0, "name: ", 0, 6)){
            this.pheno_name = line.substring(6);
        }
        
        //check for the mp term definition and the definition's reference
        if(line.regionMatches(true, 0, "def: ", 0, 5)){
            this.pheno_def = line.substring(5, line.indexOf("[")).trim();
            this.pheno_def = pheno_def.substring(1, pheno_def.length()-1);
            this.pheno_def_ref = line.substring(line.indexOf("[")+1, line.lastIndexOf("]"));
        }
        
        //check for the mp term is_obsolete status
        if(line.regionMatches(true, 0, "is_obsolete: ", 0, 13)){
            if(line.substring(13).compareTo("true")!=0){
                this.pheno_is_obsolete = "0";
            }else{
                this.pheno_is_obsolete = "1";
            }
        }
        
        //check for the mp term comment
        if(line.regionMatches(true, 0, "comment: ", 0, 9)){
            this.comm = line.substring(9);
        }
        
        //check for the mp term synonym(s)
        if(line.regionMatches(true, 0, "synonym: ", 0, 9)){
            synonyms.add(new updateMPDTO_syn(line.substring(10, line.lastIndexOf("[")-1)));
        }
        
        //check for the mp terms' is_a(s)
        if(line.regionMatches(true, 0, "is_a: MP:", 0, 9)){
            is_as.add(new updateMPDTO_isa(line.substring(9, line.indexOf("!")-1)));
        }
        
        //check for alt-id(s)
        if(line.regionMatches(true, 0, "alt_id: MP:", 0, 11)){
            alt_ids.add(new updateMPDTO_alt(line.substring(11)));
        }
        
        //check for xref(s)
        if(line.regionMatches(true, 0, "xref: ", 0, 6)){
            xrefs.add(new updateMPDTO_xref(line.substring(6)));
        }
        
    }
    
    public String getPhenoId() {
        return pheno_id;
    }
    
    public int getPhenoIdAsInt() {
        return new Integer(pheno_id).intValue();
    }
    
    public String getPhenoName(){
        return pheno_name;
    }
    
    public String getPhenoDef(){
        return pheno_def;
    }
    
    public String getPhenoDefRef(){
        return pheno_def_ref;
    }
    
    public String getIsObsolete(){
        return pheno_is_obsolete;
    }
    
    public int getIsObsoleteAsInt(){
        return new Integer(pheno_is_obsolete).intValue();
    }
    
    public String getComm(){
        return comm;
    }
    
    public Collection getSynonyms(){
        return synonyms;
    }
    
    /*public String getSynonymsString(){
        return synonyms_as_string;
    }*/
    
    public Collection getIsAs(){
        return is_as;
    }
    
    /*public String getIsAsString(){
        return is_as_string;
    }*/
    
    public Collection getAltIds(){
        return alt_ids;
    }
    
    /*public String getAltIdsString(){
        return alt_ids_as_string;
    }*/
    
    public Collection getXrefs(){
        return xrefs;
    }
    
    /*public String getXrefsString(){
        return xrefs_as_string;    
    }*/
}
