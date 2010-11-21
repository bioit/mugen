/*
 * UVariableDTO.java
 *
 * Created on July 27, 2005, 1:42 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.phenotype.phenotypemanager;

import com.arexis.mugen.phenotype.uvariable.UVariableRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.species.species.SpeciesRemote;
import java.io.Serializable;

/**
 * Datatransfer object for information regarding Unified Variables.
 * @author lami
 */
public class UVariableDTO implements Serializable {    
    private int pid, uvid, sid;
    private String name, comm, unit, user, type, speciesname;
    private java.sql.Date updated;
    
    /**
     * Create a new instance of UVariableDTO
     * @param sr The Species bean
     * @param vr The UVariable bean
     * @param ur The User bean
     */
    public UVariableDTO(UVariableRemote vr, UserRemote ur, SpeciesRemote sr) {
        try{
            pid = vr.getPid();
            uvid = vr.getUvid();
            sid = vr.getSid();
            name = vr.getName();
            comm = vr.getComm();
            unit = vr.getUnit();
            type = vr.getType();
            user = ur.getUsr();
            speciesname = sr.getName();
            updated = vr.getUpdated();
        }catch(Exception e){
            e.printStackTrace();
        }
    }   

    /**
     * Creates a new instance of UVariableDTO, used for history data since init is not complete
     * @param name The name
     * @param type The type
     * @param unit The unit
     * @param comm The comment
     * @param user The username of the user which made the last update
     * @param updated The date for the last update
     */
    public UVariableDTO(String name, String type, String unit, String comm, String user, java.sql.Date updated) {
        this.name = name;
        this.type = type;
        this.unit = unit;
        this.comm = comm;
        this.user = user;
        this.updated = updated;
    }    

    /**
     * Returns the project id
     * @return The project id
     */
    public int getPid() {
        return pid;
    }

    /**
     * Returns the unified variable id
     * @return The unified variable id
     */
    public int getUvid() {
        return uvid;
    }

    /**
     * Returns the species id
     * @return The species id
     */
    public int getSid() {
        return sid;
    }

    /**
     * Returns the name of the unified variable
     * @return The name
     */
    public String getName() {
        if(name == null)
            return "";
        return name;
    }

    /**
     * Returns the unified variable comment
     * @return The comment
     */
    public String getComm() {
        if(comm == null)
            return "";        
        return comm;
    }

    /**
     * Returns the unit for the unified variable
     * @return The unit
     */
    public String getUnit() {
        if(unit == null)
            return "";        
        return unit;
    }

    /**
     * Returns the username of the user which made the latest changes on the unified variable
     * @return The username
     */
    public String getUser() {
        if(user == null)
            return "";        
        return user;
    }

    /**
     * Returns the type for the unified variable
     * @return The type for the unified variable
     */
    public String getType() {
        if(type == null)
            return "";        
        return type;
    }
    /**
     * Returns the species name for the unified variable
     * @return The species name for the unified variable
     */
    public String getSpeciesname() {
        if(speciesname == null)
            return "";        
        return speciesname;
    }

    /**
     * Returns the date for when the last changes were made on the unified variable
     * @return The date for the latest changes
     */
    public String getUpdated() {
        if(updated == null)
            return "";        
        return updated.toString();
    }
}
