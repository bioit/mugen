package com.arexis.mugen.ontologies.go;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class GoDTO implements Serializable {
    
    private String id="";
    
    private String name="";
    
    private String def="";
    private String def_ref="";
   
    private String is_obsolete="0";
    
    private String comm = "";
    
    private String namespace = "";
    
    private Collection synonyms = new ArrayList();
    
    private Collection is_as = new ArrayList();
    
    private Collection alt_ids = new ArrayList();
    
    private Collection xrefs = new ArrayList();
    
    private Collection relationships = new ArrayList();
    
    private Collection subsets = new ArrayList();
    
    private Collection considers = new ArrayList();
    
    private Collection replaced_bys = new ArrayList();
    
    //checking silly synonym duplicates
    GoDTO_SYN syn_ = null;
    
    public GoDTO() {
        //unique items
        this.id = id;
        this.name = name;
        this.def = def;
        this.def_ref = def_ref;
        this.is_obsolete = is_obsolete;
        this.comm = comm;
        this.namespace = namespace;
        
        //collections of items relevant to mp term
        this.synonyms = synonyms;
        this.is_as = is_as;
        this.alt_ids = alt_ids;
        this.xrefs = xrefs;
        this.relationships = relationships;
        this.subsets = subsets;
        this.considers = considers;
        this.replaced_bys = replaced_bys;
    
    }
    
    public void IdentifyLine(String line){
        
        //check for the mp term id
        if(line.regionMatches(true, 0, "id: GO:", 0, 7)){
            this.id = line.substring(7);
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
        
        //check for the term namespace
        if(line.regionMatches(true, 0, "namespace: ", 0, 11)){
            this.namespace = line.substring(11);
        }
        
        //check for the mp term synonym(s)
        if(line.regionMatches(true, 0, "synonym: ", 0, 9)){
            syn_ = new GoDTO_SYN(line.substring(10, line.lastIndexOf("[")-1).toLowerCase());
            
            if(!synonyms.contains(syn_))
                synonyms.add(syn_);
            //synonyms.add(new GoDTO_SYN(line.substring(10, line.lastIndexOf("[")-1)));
        }
        
        //check for the mp terms' is_a(s)
        if(line.regionMatches(true, 0, "is_a: GO:", 0, 9)){
            //is_as.add(new GoDTO_ISA(line.substring(9, line.indexOf("!")-1)));
            is_as.add(new GoDTO_ISA(line.substring(9)));
        }
        
        //check for alt-id(s)
        if(line.regionMatches(true, 0, "alt_id: GO:", 0, 11)){
            alt_ids.add(new GoDTO_ALT(line.substring(11)));
        }
        
        //check for xref(s)
        if(line.regionMatches(true, 0, "xref: ", 0, 6)){
            xrefs.add(new GoDTO_XREF(line.substring(6)));
        }
        
        //check for relationship(s)
        if(line.regionMatches(true, 0, "relationship: ", 0, 14)){
            //relationships.add(new GoDTO_REL(line.substring(14, line.indexOf("!")).trim()));
            relationships.add(new GoDTO_REL(line.substring(14).trim()));
        }
        
        //check for subset(s)
        if(line.regionMatches(true, 0, "subset:", 0, 7)){
            subsets.add(new GoDTO_SUBSET(line.substring(7)));
        }
        
        //check for considers
        if(line.regionMatches(true, 0, "consider: GO:", 0, 13)){
            considers.add(new GoDTO_CON(line.substring(13)));
        }
        
        //check for replaced_bys
        if(line.regionMatches(true, 0, "replaced_by: GO:", 0, 16)){
            replaced_bys.add(new GoDTO_REP(line.substring(16)));
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
    
    public String getNamespace(){
        return namespace;
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
    
    public Collection getRelationships(){
         return relationships;
    }
    
    public Collection getSubsets(){
         return subsets;
    }
    
    public Collection getConsiders(){
         return considers;
    }
    
    public Collection getReplaced_bys(){
         return replaced_bys;
    }
}
