/*
 * AvailableGeneticBackgroundDTO.java
 *
 * Created on 11 Ιούλιος 2006, 10:38 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.model.availablegeneticbackgrounds.AvailableGeneticBackgroundRemote;
import java.io.Serializable;

public class AvailableGeneticBackgroundDTO implements Serializable {
    private int aid, pid;
    private String avbackname, avbacknamesss, avbacknamess;
    
public AvailableGeneticBackgroundDTO(AvailableGeneticBackgroundRemote avback) {
        try{
            aid = avback.getAid();
            avbackname = avback.getAvbackname();
            avbacknamesss = avbackname.replaceAll("<","&lt;").replaceAll(">","&gt;");
            avbacknamess = avbacknamesss.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            pid = avback.getPid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getAid(){
        return aid;
    }
    
    public java.lang.String getAvbackname(){
        return avbackname;
    }
    
    public java.lang.String getAvbacknamesss(){
        return avbacknamesss;
    }
    
    public java.lang.String getAvbacknamess(){
        return avbacknamess;
    }
    
    public int getPid(){
        return pid;
    }
}
