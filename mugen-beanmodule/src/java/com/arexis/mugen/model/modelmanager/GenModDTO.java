/*
 * GenModDTO.java
 *
 * Created on December 14, 2005, 2:18 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.model.geneticmodification.GeneticModificationRemote;
import java.io.Serializable;

/**
 * Data transfer object for a genetic modification
 * @author lami
 */
public class GenModDTO implements Serializable {
    private String name, comm, user, ts;
    private int gmid, userId;
    
    /**
     * Creates a new instance of GenModDTO
     * @param genmod The genetic modification
     */
    public GenModDTO(GeneticModificationRemote genmod) {
        try {
            this.name = genmod.getName();
            this.comm = genmod.getComm();
            this.ts = genmod.getTs().toString();
            this.user = genmod.getUser().getUsr();
            this.userId = genmod.getUser().getId();
            this.gmid = genmod.getGmid();
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }

    /**
     * Returns the name of the genetic modification
     * @return The name of the genetic modification
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the comment for the genetic modification
     * @return The comment
     */
    public String getComm() {
        return comm;
    }

    /**
     * Returns the username of the user that made the last changes on the genetic modification
     * @return The username of the user that made the last changes on the genetic modification
     */
    public String getUser() {
        return user;
    }

    /**
     * Returns the date for when the genetic modification was last changed
     * @return The date for when the last changes were made on the genetic modification
     */
    public String getTs() {
        return ts;
    }

    /**
     * Returns the id of the genetic modification
     * @return The id of the genetic modification
     */
    public int getGmid() {
        return gmid;
    }

    /**
     * Returns the id of the user that made the last changes on the genetic modification
     * @return The id of the user that made the latest changes on the genetic modification
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the id of the user that made the latest changes on the genetic modification
     * @param userId The user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
}
