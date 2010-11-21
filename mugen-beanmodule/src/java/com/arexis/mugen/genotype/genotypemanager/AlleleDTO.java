/*
 * AlleleDTO.java
 *
 * Created on August 25, 2005, 8:08 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.genotype.genotypemanager;

import com.arexis.mugen.genotype.allele.AlleleRemote;
import com.arexis.mugen.genotype.marker.MarkerRemote;
import com.arexis.mugen.species.chromosome.ChromosomeRemote;
import java.io.Serializable;

/**
 * Data transfer object for allele data
 * @author lami
 */
public class AlleleDTO implements Serializable, Comparable {  
    private int aid, mid;
    private String name, user, comm, marker, samplingunit, species, chromosome;
    private java.sql.Date updated;
    
    /**
     * Creates a new instance of AlleleDTO
     * @param ar The allele bean
     */
    public AlleleDTO(AlleleRemote ar) {
        try{
            MarkerRemote mr = ar.getMarker();
            ChromosomeRemote cr = mr.getChromosome();
            
            aid = ar.getAid();
            marker = mr.getName();
            mid = mr.getMid();
            name = ar.getName();
            user = ar.getUser().getUsr();
            comm = ar.getComm();
            updated = ar.getUpdated();
            chromosome = cr.getName();
            species = cr.getSpecies().getName();
            samplingunit = mr.getSamplingUnit().getName();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Creates a new (not fully inited) instance of AlleleDTO for history info
     * @param name The name
     * @param comm The comment
     * @param user The username of the user that made the changes
     * @param updated The date for when the changes were made
     */
    public AlleleDTO(String name, String comm, String user, java.sql.Date updated) {
        this.name = name;
        this.comm = comm;
        this.user = user;
        this.updated = updated;
    }
    
    // Constructor used for making minimal objects used for comparison when only id is available
    /**
     * Creates a new instance of AlleleDTO
     * @param aid The id of the allele
     */
    public AlleleDTO(int aid) {
        this.aid = aid;
    }    
    
    /**
     * Returns the name of the samplingunit for the allele
     * @return The name of the sampling unit
     */
    public String getSamplingUnit() {
        if(samplingunit == null)
            return "";         
        return samplingunit;
    }
    
    /**
     * Returns the name of the chromosome for the allele
     * @return The name of the chromosome
     */
    public String getChromosome() {
        if(chromosome == null)
            return "";         
        return chromosome;
    }
    
    /**
     * Returns the name of the species for the allele
     * @return The name of the species
     */
    public String getSpecies() {
        if(species == null)
            return "";         
        return species;
    }

    /**
     * Returns the allele id
     * @return The allele id
     */
    public int getAid() {
        return aid;
    }

    /**
     * Returns the marker id
     * @return The marker id
     */
    public int getMid() {
        return mid;
    }
    
    /**
     * Returns the marker name
     * @return The marker name
     */
    public String getMarker() {
        return marker;
    }    

    /**
     * Returns the allele name
     * @return The allele name
     */
    public String getName() {
        if(name == null)
            return "";
        return name;
    }

    /**
     * Returns the username of the user which made the last changes to the allele
     * @return The username
     */
    public String getUser() {
        if(user == null)
            return "";        
        return user;
    }

    /**
     * Returns the comment for the allele
     * @return The comment for the allele
     */
    public String getComm() {
        if(comm == null)
            return "";        
        return comm;
    }

    /**
     * Returns the date for when the allele was last updated
     * @return The date for the last update
     */
    public String getUpdated() {
        if(updated == null)
            return "";        
        return updated.toString();
    }
    
    /**
     * Compares this object to another
     * @param anotherObj The object to compare to
     * @throws java.lang.ClassCastException If the object to compare with was not an AlleleDTO
     * @return An int stating wether or not the tow objects were identical. See the comparable interface for mor details
     */
    public int compareTo(Object anotherObj) throws ClassCastException {
        if(!(anotherObj instanceof AlleleDTO))
            throw new ClassCastException("Object is of wrong class. AlleleDTO object expected but not found.");
        return aid - ((AlleleDTO)anotherObj).getAid();
    }      
}
