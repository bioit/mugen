package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.model.geneticbackground.GeneticBackgroundValuesRemote;
import com.arexis.mugen.model.geneticbackground.GeneticBackgroundValuesRemoteHome;
import com.arexis.mugen.model.geneticbackground.GeneticBackgroundRemote;
import com.arexis.mugen.project.user.UserRemote;
import java.io.Serializable;

import com.arexis.mugen.servicelocator.ServiceLocator;

public class GeneticBackgroundDTO implements Serializable {
    private int gbid, eid, dna_origin, targeted_back, host_back, backcrossing_strain;
    private String backcrosses, dna_origin_name, dna_origin_namess, targeted_back_name, targeted_back_namess, host_back_name, host_back_namess, backcrossing_strain_name, backcrossing_strain_namess;
    
public GeneticBackgroundDTO(GeneticBackgroundRemote genback) {
        try{
            gbid = genback.getGbid();
            eid = genback.getEid();
            dna_origin = genback.getDna_origin();
            targeted_back = genback.getTargeted_back();
            host_back = genback.getHost_back();
            backcrossing_strain = genback.getBackcrossing_strain();
            backcrosses = genback.getBackcrosses();
            
            dna_origin_name = genback.getBackNameFromBackId(dna_origin);
            dna_origin_namess = dna_origin_name.replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            targeted_back_name = genback.getBackNameFromBackId(targeted_back);
            targeted_back_namess = targeted_back_name.replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            host_back_name = genback.getBackNameFromBackId(host_back);
            host_back_namess = host_back_name.replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            backcrossing_strain_name = genback.getBackNameFromBackId(backcrossing_strain);
            backcrossing_strain_namess = backcrossing_strain_name.replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
                  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getGbid(){
        return gbid;
    }
    
    public int getEid(){
        return eid;
    }
    
    public int getDna_origin(){
        return dna_origin;
    }
    
    public int getTargeted_back(){
        return targeted_back;
    }
    
    public int getHost_back(){
        return host_back;
    }
    
    public int getBackcrossing_strain(){
        return backcrossing_strain;
    }
    
    public java.lang.String getBackcrosses(){
        return backcrosses;
    }
    
    public String getDna_origin_name(){
        return dna_origin_name;
    }
    
    public String getDna_origin_namess(){
        return dna_origin_namess;
    }
    
    public String getTargeted_back_name(){
        return targeted_back_name;
    }
    
    public String getTargeted_back_namess(){
        return targeted_back_namess;
    }
    
    public String getHost_back_name(){
        return host_back_name;
    }
    
    public String getHost_back_namess(){
        return host_back_namess;
    }
    
    public String getBackcrossing_strain_name(){
        return backcrossing_strain_name;
    }
    
    public String getBackcrossing_strain_namess(){
        return backcrossing_strain_namess;
    }
}
