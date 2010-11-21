package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.model.geneticbackground.GeneticBackgroundValuesRemote;
import com.arexis.mugen.model.geneticbackground.GeneticBackgroundValuesRemoteHome;
import com.arexis.mugen.model.geneticbackground.GeneticBackgroundRemote;
import com.arexis.mugen.project.user.UserRemote;
import java.io.Serializable;

import com.arexis.mugen.servicelocator.ServiceLocator;

public class GeneticBackgroundValuesDTO implements Serializable {
    private int bid, pid, modelcount;
    private String backname, ssbackname;
    
    
public GeneticBackgroundValuesDTO(GeneticBackgroundValuesRemote genbackValues) {
        try{
            bid = genbackValues.getBid();
            backname = genbackValues.getBackname();
            ssbackname = genbackValues.getBackname().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            pid = genbackValues.getPid();
            modelcount = genbackValues.getExpModelsCount();
            /*
             *name = gene.getName().replaceAll("<","&lt;").replaceAll(">","&gt;");
            name = name.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getBid() {
        return bid;
    }
    
    public java.lang.String getBackname() {
        return backname;
    }
    
    public java.lang.String getSsbackname() {
        return ssbackname;
    }
    
    public int getPid() {
        return pid;
    }
    
    public int getModelcount() {
        return modelcount;
    }
}
