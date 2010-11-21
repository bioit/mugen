/*
 * UVariableSetDTO.java
 *
 * Created on July 27, 2005, 1:42 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.phenotype.phenotypemanager;

import com.arexis.mugen.phenotype.uvariableset.UVariableSetRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.species.species.SpeciesRemote;
import java.io.Serializable;

/**
 * Datatransfer object for information regarding Unified Variable sets.
 * @author lami
 */
public class UVariableSetDTO implements Serializable, Comparable {    
    private int pid, uvsid, sid;
    private String name, comm, user, speciesname;
    private java.sql.Date updated;
    
    /**
     * Create a new instance of UVariableSetDTO
     * @param sr The Species bean
     * @param vr The UVariableSet bean
     * @param ur The User bean
     */
    public UVariableSetDTO(UVariableSetRemote vr, UserRemote ur, SpeciesRemote sr) {
        try{
            pid = vr.getPid();
            uvsid = vr.getUvsid();
            sid = vr.getSid();
            name = vr.getName();
            comm = vr.getComm();
            user = ur.getUsr();
            speciesname = sr.getName();
            updated = vr.getUpdated();
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
    
    /**
     * Creates a new (not fully inited) instance of UVariableSetDTO used for history information
     * @param name The name
     * @param comm The comment
     * @param user The username of the user that made the last update
     * @param updated The date for the last update
     */
    public UVariableSetDTO(String name, String comm, String user, java.sql.Date updated) {
        this.name = name;
        this.comm = comm;
        this.user = user;
        this.updated = updated;
    }   
    
    /**
     * Creates a new instance of UVariableSetDTO
     * @param uvsid The id of the unified variable set
     */
    public UVariableSetDTO(int uvsid) {
        this.uvsid = uvsid;
    }

    /**
     * Returns the project id
     * @return The project id
     */
    public int getPid() {
        return pid;
    }

    /**
     * Returns the unified variable set id
     * @return The unified variable set id
     */
    public int getUvsid() {
        return uvsid;
    }

    /**
     * Returns the species id
     * @return The species id
     */
    public int getSid() {
        return sid;
    }

    /**
     * Returns the name of the unified variable set
     * @return The name
     */
    public String getName() {
        if(name == null)
            return "";
        return name;
    }

    /**
     * Returns the comment for the unified variable set
     * @return The comment
     */
    public String getComm() {
        if(comm == null)
            return "";        
        return comm;
    }

    /**
     * Returns the username of the user which made the latest changes to the unified variable set
     * @return The username
     */
    public String getUser() {
        if(user == null)
            return "";        
        return user;
    }

    /**
     * Returns the species name
     * @return The species name
     */
    public String getSpeciesname() {
        if(speciesname == null)
            return "";        
        return speciesname;
    }

    /**
     * Returns the date for when the unified variable set was last updated
     * @return The date for the last update
     */
    public String getUpdated() {
        if(updated == null)
            return "";        
        return updated.toString();
    }
    
    /**
     * Compares this object to another object
     * @param anotherObj The object to compare with
     * @throws java.lang.ClassCastException If the object to compare with was not of the same type
     * @return Wether or not the two objects are equal
     */
    public int compareTo(Object anotherObj) throws ClassCastException {
        if(!(anotherObj instanceof UVariableSetDTO))
            throw new ClassCastException("Object is of wrong class. UVariableSetDTO object expected but not found.");
        return uvsid - ((UVariableSetDTO)anotherObj).getUvsid();
    }     
}
