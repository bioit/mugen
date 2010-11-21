/*
 * UMarkerSetDTO.java
 *
 * Created on August 30, 2005, 12:07 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.genotype.genotypemanager;
import com.arexis.mugen.genotype.umarkerset.UMarkerSetRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.species.species.SpeciesRemote;
import java.io.Serializable;

/**
 * Data transfer object for unified marker set information
 * @author lami
 */
public class UMarkerSetDTO implements Serializable, Comparable {
    private String name, comm, user, species;
    private int umsid;
    private java.sql.Date updated;
    
    
    /**
     * Creates a new instance of UMarkerSetDTO
     * @param sr The species bean
     * @param ur The user bean
     * @param mr The unified marker set bean
     */
    public UMarkerSetDTO(UMarkerSetRemote mr, UserRemote ur, SpeciesRemote sr) {
        try{
            umsid = mr.getUmsid();
            name = mr.getName();
            comm = mr.getComm();
            updated = mr.getTs();
            user = ur.getUsr();
            species = sr.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Creates a new (not fully inited) instance of UMarkerSetDTO used for history info
     * @param name The unified marker set name
     * @param comm The unified marker set comment
     * @param user The username of the user that made the changes
     * @param updated The date for when the changes were made
     */
    public UMarkerSetDTO(String name, String comm, String user, java.sql.Date updated) {
        this.name = name;
        this.comm = comm;
        this.user = user;
        this.updated = updated;
    }    
    
    /**
     * Create a new instance of UMarkerSetDTO
     * @param umsid The unified marker set id
     */
    public UMarkerSetDTO(int umsid) {
        this.umsid = umsid;
    }
    
    /**
     * Returns the name of the species for the unified marker set
     * @return The species name
     */
    public String getSpecies() {
        if(species == null)
            return "";
        return species;
    }

    /**
     * Compares this object with another object
     * @param anotherObj The object to compare with
     * @return True if equal, false otherwise
     */
    public boolean equals(Object anotherObj) {
        if(!(anotherObj instanceof UMarkerSetDTO))
            throw new ClassCastException("Object is of wrong class. UMarkerSetDTO object expected but not found.");
        return umsid == ((UMarkerSetDTO)anotherObj).getUmsid();        
    }    
    
    /**
     * Returns the name of the unified marker set
     * @return The name
     */
    public String getName() {
        if(name == null)
            return "";        
        return name;
    }

    /**
     * Returns the comment for the unified marker set
     * @return The unified marker set name
     */
    public String getComm() {
        if(comm == null)
            return "";        
        return comm;
    }

    /**
     * Returns the username of the user which made the last changes to the unified marker set
     * @return The username of the user which made the last changes to the unified marker set
     */
    public String getUser() {
        if(user == null)
            return "";        
        return user;
    }

    /**
     * Returns the unified marker set id
     * @return The unified marker set id
     */
    public int getUmsid() {
        return umsid;
    }

    /**
     * Returns the date for when the last changes were made to the unified marker set
     * @return The date for when the last changes were made to the unified marker set
     */
    public String getUpdated() {
        if(updated == null)
            return "";
        return updated.toString();
    }    
    
    /**
     * Compares this object with another object
     * @param anotherObj The object to compare with
     * @throws java.lang.ClassCastException If the object to compare with is not of the same type
     * @return Wether or not the two objects are equal, see comparable interface for details
     */
    public int compareTo(Object anotherObj) throws ClassCastException {
        if(!(anotherObj instanceof UMarkerSetDTO))
            throw new ClassCastException("Object is of wrong class. UMarkerSetDTO object expected but not found.");
        return umsid - ((UMarkerSetDTO)anotherObj).getUmsid();
    }        
}
