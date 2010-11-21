package com.arexis.mugen.ontologies.xp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class XpDTO implements Serializable {
    
    private String sch, relation, name = "", mpid="";
    
    private int xpid, schid, apporder = 1;
    
    private Collection relationships = new ArrayList();
    
    public XpDTO() {
        //unique items
        this.mpid = mpid;
        this.apporder = apporder;
        //collections of items relevant to mp term
        this.relationships = relationships;
    }
    
    public XpDTO(int xpid, int apporder, String sch, int schid, String relation) {
        this.xpid = xpid;
        this.apporder = apporder;
        this.sch = sch;
        this.schid = schid;
        this.relation = relation;
    }
    
    public void IdentifyLine(String line){
        
        //check for the mp term id
        if(line.regionMatches(true, 0, "id: MP:", 0, 7)){
            this.mpid = line.substring(7, line.indexOf("!")).trim();
        }
        
        //check for relationship(s)
        if(line.regionMatches(true, 0, "intersection_of: ", 0, 17)){
            relationships.add(new XpDTO_REL(line.substring(17, line.indexOf("!")).trim(), apporder));
            apporder++;
        }
        
    }
    
    public String getMpid() {
        return mpid;
    }
    
    public int getMpidAsInt() {
        return new Integer(mpid).intValue();
    }
    
    public Collection getRelationships(){
         return relationships;
    }
    
    public int getXpid(){
        return xpid;
    }
    
    public int getApporder(){
        return apporder;
    }
    
    public String getSch(){
        return sch;
    }
    
    public int getSchid(){
        return schid;
    }
    
    public String getRelation(){
        return relation;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
}
