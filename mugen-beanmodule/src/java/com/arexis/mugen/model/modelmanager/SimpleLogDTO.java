package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.model.availability.AvailabilityRemote;
import com.arexis.mugen.simplelog.SimpleLogRemote;
import java.io.Serializable;

public class SimpleLogDTO implements Serializable {
    
    private int logid;
    private String txt, mgnaction, mgnuser, remoteadd, remotehost, ts;
    
public SimpleLogDTO(SimpleLogRemote sl) {
        try{
            this.logid =sl.getLogid();
            this.txt = sl.getTxt();
            this.mgnaction = sl.getMgnaction();
            this.mgnuser = sl.getMgnuser();
            this.remoteadd = sl.getRemoteadd();
            this.remotehost = sl.getRemotehost();
            this.ts = sl.getTs().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getLogid(){
        return logid;
    }
    
    public String getTxt(){
        return txt;
    }

    public String getMgnaction(){
        return mgnaction;
    }
    
    public String getMgnuser(){
        return mgnuser;
    }
    
    public String getRemoteadd(){
        return remoteadd;
    }
    
    public String getRemotehost(){
        return remotehost;
    }
    
    public java.lang.String getTs(){
        return ts;
    }
    
}
