package com.arexis.mugen.ontologies.pato;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class PatoDTO implements Serializable {
    
    private String id="";
    
    private String name="";
    
    private String def="";
    private String def_ref="";
   
    private String is_obsolete="0";
    
    private String comm = "";
    
    private Collection synonyms = new ArrayList();
    private String synonyms_as_string="";
    
    private Collection is_as = new ArrayList();
    private String is_as_string = "";
    
    private Collection alt_ids = new ArrayList();
    private String alt_ids_as_string = "";
    
    private Collection xrefs = new ArrayList();
    private String xrefs_as_string = "";
    
    private Collection subsets = new ArrayList();
    private String subsets_as_string = "";
    
    public PatoDTO() {
        //unique items of mp term
        this.id = id;
        this.name = name;
        this.def = def;
        this.def_ref = def_ref;
        this.is_obsolete = is_obsolete;
        this.comm = comm;
        
        //collections of items relevant to mp term
        this.synonyms = synonyms;
        this.is_as = is_as;
        this.alt_ids = alt_ids;
        this.xrefs = xrefs;
        this.subsets = subsets;
    }
    
    public void IdentifyLine(String line){
        
        //check for the mp term id
        if(line.regionMatches(true, 0, "id: PATO:", 0, 9)){
            this.id = line.substring(9);
        }
        
        //check for the mp term name 
        if(line.regionMatches(true, 0, "name: ", 0, 6)){
            this.name = line.substring(6);
        }
        
        //check for the mp term definition and the definition's reference
        if(line.regionMatches(true, 0, "def: ", 0, 5)){
            this.def = line.substring(5, line.indexOf("[")).trim();
            this.def = def.substring(1, def.length()-1);
            this.def_ref = line.substring(line.indexOf("[")+1, line.lastIndexOf("]"));
        }
        
        //check for the mp term is_obsolete status
        if(line.regionMatches(true, 0, "is_obsolete: ", 0, 13)){
            if(line.substring(13).compareTo("true")!=0){
                this.is_obsolete = "0";
            }else{
                this.is_obsolete = "1";
            }
        }
        
        //check for the mp term comment
        if(line.regionMatches(true, 0, "comment: ", 0, 9)){
            this.comm = line.substring(9);
        }
        
        //check for the mp term synonym(s)
        /*---old version
        if(line.regionMatches(true, 0, "synonym: ", 0, 9)){
            synonyms.add(new PatoDTO_SYN(line.substring(10, line.lastIndexOf("[")-1)));
        }
         */
        if(line.contains("_synonym:")){
            synonyms.add(new PatoDTO_SYN(line));
        }
        
        //check for the mp terms' is_a(s)
        if(line.regionMatches(true, 0, "is_a: PATO:", 0, 11)){
            is_as.add(new PatoDTO_ISA(line.substring(11, line.indexOf("!")-1)));
        }
        
        //check for alt-id(s)
        if(line.regionMatches(true, 0, "alt_id: PATO:", 0, 13)){
            alt_ids.add(new PatoDTO_ALT(line.substring(13)));
        }
        
        //check for xref(s)
        if(line.regionMatches(true, 0, "xref: ", 0, 6)){
            xrefs.add(new PatoDTO_XREF(line.substring(6)));
        }
        
        //check for subset(s)
        if(line.regionMatches(true, 0, "subset:", 0, 7)){
            subsets.add(new PatoDTO_SUBSET(line.substring(7)));
        }
        
    }
    
    public String getId() {
        return id;
    }
    
    public int getIdAsInt() {
        return new Integer(id).intValue();
    }
    
    public String getName(){
        return name;
    }
    
    public String getDef(){
        return def;
    }
    
    public String getDefRef(){
        return def_ref;
    }
    
    public String getIsObsolete(){
        return is_obsolete;
    }
    
    public int getIsObsoleteAsInt(){
        return new Integer(is_obsolete).intValue();
    }
    
    public String getComm(){
        return comm;
    }
    
    public Collection getSynonyms(){
        return synonyms;
    }
    
    public Collection getIsAs(){
        return is_as;
    }
    
    public Collection getAltIds(){
        return alt_ids;
    }
    
    public Collection getXrefs(){
        return xrefs;
    }
    
    public Collection getSubsets(){
        return subsets;
    }
}
