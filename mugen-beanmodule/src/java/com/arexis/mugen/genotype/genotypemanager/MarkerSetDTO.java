/*
 * MarkerSetDTO.java
 *
 * Created on August 30, 2005, 12:07 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.genotype.genotypemanager;
import com.arexis.mugen.genotype.markerset.MarkerSetRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.species.species.SpeciesRemote;
import java.io.Serializable;

/**
 * Data transfer object for marker set information
 * @author lami
 */
public class MarkerSetDTO implements Serializable, Comparable {
    private String name, comm, user, samplingunit, species;
    private int msid;
    private java.sql.Date updated;
    
    
    /**
     * Creates a new instance of MarkerSetDTO
     * @param mr The marker set bean
     */
    public MarkerSetDTO(MarkerSetRemote mr) {
        try{
            msid = mr.getMsid();
            name = mr.getName();
            comm = mr.getComm();
            updated = mr.getTs();
            user = mr.getUser().getUsr();
            samplingunit = mr.getSamplingUnit().getName(); 
            species = mr.getSamplingUnit().getSpecies().getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Creates a new (not fully inited) instance of MarkerSetDTO used for history info
     * @param name The marker set name
     * @param comm The marker set comment
     * @param user The username of the user that made the changes
     * @param updated The date for when the changes were made
     */
    public MarkerSetDTO(String name, String comm, String user, java.sql.Date updated) {
        this.name = name;
        this.comm = comm;
        this.user = user;
        this.updated = updated;
    }
    
    // Constructor used for making minimal objects used for comparison when only id is available
    /**
     * Creates a new instance of MarkerSetDTO
     * @param msid The marker set id
     */
    public MarkerSetDTO(int msid) {
        this.msid = msid;
    }    
    
    /**
     * Returns the name of the species for the marker
     * @return The species name
     */
    public String getSpecies() {
        if(species == null)
            return "";
        return species;
    }

    /**
     * Returns the name of the marker
     * @return The name
     */
    public String getName() {
        if(name == null)
            return "";        
        return name;
    }

    /**
     * Returns the comment for the marker
     * @return The marker name
     */
    public String getComm() {
        if(comm == null)
            return "";        
        return comm;
    }

    /**
     * Returns the username of the user which made the last changes to the marker
     * @return The username of the user which made the last changes to the marker
     */
    public String getUser() {
        if(user == null)
            return "";        
        return user;
    }

    /**
     * Returns the name of the samplingunit for the marker
     * @return The samlingunit name
     */
    public String getSamplingunit() {
        if(samplingunit == null)
            return "";
        return samplingunit;
    }

    /**
     * Returns the marker set id
     * @return The marker set id
     */
    public int getMsid() {
        return msid;
    }

    /**
     * Returns the date for when the last changes were made to the marker
     * @return The date for when the last changes were made to the marker
     */
    public String getUpdated() {
        if(updated == null)
            return "";
        return updated.toString();
    }  
    
    /**
     * Compares the two objects
     * @param anotherObj The objects to compare with
     * @return True if equal
     */
    public boolean equals(Object anotherObj) {
        if(!(anotherObj instanceof MarkerSetDTO))
            throw new ClassCastException("Object is of wrong class. MarkerSetDTO object expected but not found.");
        return msid == ((MarkerSetDTO)anotherObj).getMsid();        
    }
    
    /**
     * Compares two objects
     * @param anotherObj The object to compare with
     * @throws java.lang.ClassCastException If the object to compare with is not of the same type
     * @return Wether or not then two objects are the same, see comparable interface for details
     */
    public int compareTo(Object anotherObj) throws ClassCastException {
        if(!(anotherObj instanceof MarkerSetDTO))
            throw new ClassCastException("Object is of wrong class. MarkerSetDTO object expected but not found.");
        return msid - ((MarkerSetDTO)anotherObj).getMsid();
    }    
}
