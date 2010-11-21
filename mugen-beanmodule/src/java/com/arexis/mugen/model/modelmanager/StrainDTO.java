package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.model.strain.state.StrainStateRemote;
import com.arexis.mugen.model.strain.strain.StrainRemote;
import com.arexis.mugen.model.strain.type.StrainTypeRemote;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class StrainDTO implements Serializable, Comparable
{
    private int strainid, eid;
    
    private String mgiid, mgiIdURL, emmaid, emmaidURL, designation, designationdisplay, strainTypeName, strainStateNames;
    
    public StrainDTO(StrainRemote strain) {
        try {
            strainid = strain.getStrainid();
            
            eid = strain.getExpModel();
            
            designation = strain.getDesignation().replaceAll("<","&lt;").replaceAll(">","&gt;");
            designation = designation.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            designationdisplay = designation.replaceAll("\\{","<i>").replaceAll("}","</i>");
            
            mgiid = strain.getMgiId();
            
            emmaid = strain.getEmmaid();
            
            Collection types = strain.getTypes();
            
            int j=0;
            StrainTypeRemote type;
            Iterator i = types.iterator();
            while (i.hasNext()) {
                type = (StrainTypeRemote)i.next();
                if (j!=0)
                    strainTypeName += ", ";
                strainTypeName += type.getName();
                j++;
            }
            
            Collection states = strain.getStates();
            j=0;
            StrainStateRemote state;
            i = states.iterator();
            while (i.hasNext()) {
                state = (StrainStateRemote)i.next();
                if (j!=0)
                    strainStateNames += ", ";
                strainStateNames += state.getName();
                j++;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /** Creates a new instance of non-superscripted StrainDTO */
    public StrainDTO(StrainRemote strain, String superscript) {
        try {
            strainid = strain.getStrainid();
            
            eid = strain.getExpModel();
            
            designation = strain.getDesignation();
            
            mgiid = strain.getMgiId();
            
            emmaid = strain.getEmmaid();
            
            Collection types = strain.getTypes();
            
            int j=0;
            StrainTypeRemote type;
            Iterator i = types.iterator();
            while (i.hasNext()) {
                type = (StrainTypeRemote)i.next();
                if (j!=0)
                    strainTypeName += ", ";
                strainTypeName += type.getName();
                j++;
            }
            
            Collection states = strain.getStates();
            j=0;
            StrainStateRemote state;
            i = states.iterator();
            while (i.hasNext()) {
                state = (StrainStateRemote)i.next();
                if (j!=0)
                    strainStateNames += ", ";
                strainStateNames += state.getName();
                j++;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }//non-superscripted constructor.
    
    public int compareTo(Object anotherObj) {
        if(!(anotherObj instanceof StrainDTO))
            throw new ClassCastException("Object is of wrong class. StrainDTO object expected but not found.");
        return getDesignation().compareTo(((StrainDTO)anotherObj).getDesignation());
    }
    
    public boolean equals(Object obj) {
        if (((StrainDTO)obj).strainid == this.strainid)
            return true;
        else 
            return false;
    }

    public int getStrainId() {
        return strainid;
    }
    
    public int getEid() {
        return eid;
    }

    public String getDesignation() {
        return designation;
    }
    
    public String getDesignationdisplay() {
        return designationdisplay;
    }

    public String getStrainTypeNames() {
        return strainTypeName;
    }
    
    public String getStrainStateNames() {
        return strainStateNames;
    }
    
    public String getMgiId() {
        return mgiid;
    }
    
    public String getMgiIdURL() {
        if(!mgiid.equals("0")){
            mgiIdURL = "<a href=\"http://jaxmice.jax.org/strain/"+mgiid+".html\" target=\"_blank\">"+mgiid+"</a>";
        } else {
            mgiIdURL = "";
        }
        return mgiIdURL;
    }
    
    public String getEmmaid() {
        return emmaid;
    }
    
    public String getEmmaidURL() {
        if(!emmaid.equals("0")){
            emmaidURL = "<a href=\"http://strains.emmanet.org/strains/strain_"+new Integer(emmaid).intValue()+".utf8.php\" target=\"_blank\">EM:"+emmaid+"</a>";
        } else {
            emmaidURL = "";
        }
        return emmaidURL;
    }
}
