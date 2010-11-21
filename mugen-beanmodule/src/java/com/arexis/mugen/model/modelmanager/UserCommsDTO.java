package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.model.availability.AvailabilityRemote;
import com.arexis.mugen.simplelog.SimpleLogRemote;
import com.arexis.mugen.model.usercomments.UserCommsRemote;
import java.io.Serializable;

public class UserCommsDTO implements Serializable {
    
    private int commid, userid;
    private String comm, username, ts;
    
public UserCommsDTO(UserCommsRemote usrcomm) {
        try{
            
            this.commid = usrcomm.getCommid();
            this.userid = usrcomm.getUserid();
            this.username = usrcomm.getUsername();
            this.comm = usrcomm.getComm();
            this.ts = usrcomm.getTs().toString();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCommid(){
        return commid;
    }
    
    public int getUserid(){
        return userid;
    }

    public String getUsername(){
        if(username.compareTo("Public")==0){
            username = "Visitor";
        }
        return username;
    }
    
    public String getComm(){
        return comm;
    }
    
    public java.lang.String getTs(){
        return ts;
    }
    
}
