package com.arexis.mugen.ontologies.xp;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class XpDTO_REL implements Serializable {
   
    private String[] elements, schemabits;
    private String sch, relation = "";
    private int schid, apporder;
    
    public XpDTO_REL(String line, int apporder) {
        
        elements = line.split(" ");
        
        for(int i = 0; i < elements.length; i++){
            if(elements[i].contains(":")){
                schemabits = elements[i].split(":");
                sch = schemabits[0];
                schid = new Integer(schemabits[1]).intValue();
            }else{
                relation = elements[i];
            }
        }//for
        
        this.apporder = apporder;
    }
    
    public String getRelation(){
        return relation;
    }
    
    public String getSch(){
        return sch;
    }
    
    public int getSchid(){
        return schid;
    }
    
    public int getApporder(){
        return apporder;
    }
}
