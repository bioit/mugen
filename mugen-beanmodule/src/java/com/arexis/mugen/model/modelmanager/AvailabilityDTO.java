package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.model.availability.AvailabilityRemote;
import java.io.Serializable;

public class AvailabilityDTO implements Serializable {
    private int eid, rid, aid, stateid, typeid;
    private String reponame, repourl, avbackname, avbacknamess, statename, typename;
    private String stateabbr, typeabbr;
    
public AvailabilityDTO(AvailabilityRemote availability) {
        try{
            eid = availability.getEid();
            rid = availability.getRid();
            aid = availability.getAid();
            stateid = availability.getStateid();
            typeid = availability.getTypeid();
            reponame = availability.getRepositoryName();
            repourl = availability.getRepositoryURL();
            avbackname = availability.getAvailableGeneticBackgroundName();
            avbacknamess = avbackname.replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            statename = availability.getStateName();
            typename = availability.getTypeName();
            stateabbr = availability.getStateAbbr();
            typeabbr = availability.getTypeAbbr();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getEid(){
        return eid;
    }
    
    public int getRid(){
        return rid;
    }

    public int getAid(){
        return aid;
    }
    
    public int getStateid(){
        return stateid;
    }
    
    public int getTypeid(){
        return typeid;
    }
    
    public java.lang.String getReponame(){
        return reponame;
    }
    
    public java.lang.String getRepourl(){
        return repourl;
    }
    
    public java.lang.String getAvbackname(){
        return avbackname;
    }
    
    public java.lang.String getAvbacknamess(){
        return avbacknamess;
    }
    
    public java.lang.String getStatename(){
        return statename;
    }
    
    public java.lang.String getTypename(){
        return typename;
    }
    
    public java.lang.String getStateabbr(){
        return stateabbr;
    }
    
    public java.lang.String getTypeabbr(){
        return typeabbr;
    }
}
