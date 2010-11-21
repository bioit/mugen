/*
 * VariableDTO.java
 *
 * Created on July 27, 2005, 1:42 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.phenotype.phenotypemanager;

import com.arexis.mugen.phenotype.variable.VariableRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.species.species.SpeciesRemote;
import java.io.Serializable;

/**
 * Datatransfer object for information regarding Variables.
 * @author lami
 */
public class VariableDTO implements Serializable {    
    private int vid, suid;
    private String name, comm, unit, user, type, speciesname;
    private java.sql.Date updated;
    
    /**
     * Create a new instance of VariableDTO
     * @param vr The Variable bean
     */
    public VariableDTO(VariableRemote vr) {
        try{
            
            UserRemote ur = vr.getUser();
            SamplingUnitRemote sur = vr.getSamplingUnit();         
            SpeciesRemote sr = sur.getSpecies();   
            
            vid = vr.getVid();
            suid = sur.getSuid();
            
            name = vr.getName();
            comm = vr.getComm();
            unit = vr.getUnit();
            type = vr.getType();
            updated = vr.getTs();
            user = ur.getUsr();
            setSpeciesname(sr.getName());
        }catch(Exception e){
            e.printStackTrace();
        }
    }   

    /**
     * Creates a new instance of VariableDTO, used for history data since init is not complete
     * @param name The name
     * @param type The type
     * @param unit The unit
     * @param comm The comment
     * @param user The username of the user which made the last update
     * @param updated The date for the last update
     */
    public VariableDTO(String name, String type, String unit, String comm, String user, java.sql.Date updated) {
        this.name = name;
        this.type = type;
        this.unit = unit;
        this.comm = comm;
        this.user = user;
        this.updated = updated;
    }

    /**
     * Returns the variable id
     * @return The variable id
     */
    public int getVid() {
        return vid;
    }

    /**
     * Sets the variable id
     * @param vid The variable id
     */
    public void setVid(int vid) {
        this.vid = vid;
    }

    /**
     * Returns the sampling unit id
     * @return The sampling unit id
     */
    public int getSuid() {
        return suid;
    }

    /**
     * Sets the samping unit id
     * @param suid The sampling unit id
     */
    public void setSuid(int suid) {
        this.suid = suid;
    }

    /**
     * Returns the name of the variable
     * @return The variable name
     */
    public String getName() {
        if(name == null)
            return "";
        return name;
    }

    /**
     * Sets the variable name
     * @param name The variable name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the comment for the variable
     * @return The comment for the variable
     */
    public String getComm() {
        if(comm == null)
            return "";        
        return comm;
    }

    /**
     * Sets the comment for the variable
     * @param comm The comment for the variable
     */
    public void setComm(String comm) {
        this.comm = comm;
    }

    /**
     * Returns the unit for the variable
     * @return The unit for the variable
     */
    public String getUnit() {
        if(unit == null)
            return "";        
        return unit;
    }

    /**
     * Sets the unit for the variable
     * @param unit The unit for the variable
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Returns the username of the user which performed the last changes to the variable
     * @return The username of the user which performed the last changes on the variable
     */
    public String getUser() {
        if(user == null)
            return "";        
        return user;
    }

    /**
     * Sets the username of the user which performed the last changes to the variable
     * @param user The username of the user which performed the last changes to the variable
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Returns the type of the variable
     * @return The type of the variable
     */
    public String getType() {
        if(type == null)
            return "";        
        return type;
    }

    /**
     * Sets the type for the variable
     * @param type The type for the variable
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the date for when the variable was last updated
     * @return The date for when the variable was last updated
     */
    public String getUpdated() {
        if(updated == null)
            return "";        
        return updated.toString();
    }

    /**
     * Sets the date for when the variable was last updated
     * @param updated The date for when the variable was last updated
     */
    public void setUpdated(java.sql.Date updated) {
        this.updated = updated;
    }

    /**
     * Returns the name of the species from which this variable comes from
     * @return The species name
     */
    public String getSpeciesname() {
        return speciesname;
    }

    /**
     * Sets the species name from which the variable comes from
     * @param speciesname The species name
     */
    public void setSpeciesname(String speciesname) {
        this.speciesname = speciesname;
    }
}
