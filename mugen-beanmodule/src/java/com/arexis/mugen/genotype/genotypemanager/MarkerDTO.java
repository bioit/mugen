/*
 * MarkerDTO.java
 *
 * Created on August 30, 2005, 12:07 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.genotype.genotypemanager;

import com.arexis.mugen.genotype.marker.MarkerRemote;
import com.arexis.mugen.species.chromosome.ChromosomeRemote;
import java.io.Serializable;

/**
 * Data transfer object for marker information
 * @author lami
 */
public class MarkerDTO implements Serializable, Comparable {
    private String name, comm, alias, p1, p2, user, chromosome, samplingunit, species;
    private int mid, cid;
    private double position;
    private java.sql.Date updated;
    
    
    /**
     * Creates a new instance of MarkerDTO
     * @param mr The marker bean
     */
    public MarkerDTO(MarkerRemote mr) {
        try{
            mid = mr.getMid();
            name = mr.getName();
            comm = mr.getComm();
            alias = mr.getAlias();
            position = mr.getPosition();
            updated = mr.getTs();
            user = mr.getUser().getUsr();
            p1 = mr.getP1();
            p2 = mr.getP2();
            chromosome = mr.getChromosome().getName();
            samplingunit = mr.getSamplingUnit().getName(); 
            ChromosomeRemote cr = mr.getChromosome();
            species = cr.getSpecies().getName();
            cid = cr.getCid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new (not fully inited) instance of MarkerDTO used for history info
     * @param name The marker name
     * @param alias The alias
     * @param p1 Primer one
     * @param p2 Primer two
     * @param position The position
     * @param chromosome The chromomsome
     * @param comm The comment
     * @param user The username of the user who made the changes
     * @param updated The date for when the changes were made
     */
    public MarkerDTO(String name, String alias, String p1, String p2, double position,
            String chromosome, String comm, String user, java.sql.Date updated) {
        this.name = name;
        this.alias = alias;
        this.p1 = p1;
        this.p2 = p2;
        this.position = position;
        this.chromosome = chromosome;
        this.comm = comm;
        this.user = user;
        this.updated = updated;
    }
    
    // Constructor used for making minimal objects used for comparison when only id is available
    /**
     * Creates a new instance of MarkerDTO
     * @param mid The marker id
     */
    public MarkerDTO(int mid) {
        this.mid = mid;
    }    
    
    /**
     * Creates a new instance of MarkerDTO
     */
    public MarkerDTO() {
        
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
     * Returns the chromosome id for the marker
     * @return The chromosome id
     */
    public int getCid() {
        return cid;
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
     * Returns the alias for the marker
     * @return The alias
     */
    public String getAlias() {
        if(alias == null)
            return "";        
        return alias;
    }

    /**
     * Returns the p1 for the marker
     * @return The p1
     */
    public String getP1() {
        if(p1 == null)
            return "";        
        return p1;
    }

    /**
     * Returns the p2 for the marker
     * @return The p2
     */
    public String getP2() {
        if(p2 == null)
            return "";        
        return p2;
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
     * Returns the name of the chromosome for the marker
     * @return The name of the chromsome for the marker
     */
    public String getChromosome() {
        if(chromosome == null)
            return "";        
        return chromosome;
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
     * Returns the marker id
     * @return The marker id
     */
    public int getMid() {
        return mid;
    }

    /**
     * Returns the position for the marker
     * @return The position
     */
    public double getPosition() {        
        return position;
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
     * Compares this object to another object
     * @param anotherObj The object to compare with
     * @throws java.lang.ClassCastException If the object to compare with is of another type than MarkerDTO
     * @return Wether or not the objects are the same, see comparable interface for details
     */
    public int compareTo(Object anotherObj) throws ClassCastException {
        if(!(anotherObj instanceof MarkerDTO))
            throw new ClassCastException("Object is of wrong class. MarkerDTO object expected but not found.");
        return mid - ((MarkerDTO)anotherObj).getMid();
    }    
    
    /**
     * Compares to objects
     * @param anotherObj The object to compare to
     * @return True if the two objects are equal
     */
    public boolean equals(Object anotherObj) {
        return mid == ((MarkerDTO)anotherObj).getMid(); 
    }
}
