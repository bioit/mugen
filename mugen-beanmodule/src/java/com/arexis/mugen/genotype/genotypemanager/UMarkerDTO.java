/*
 * UMarkerDTO.java
 *
 * Created on August 30, 2005, 12:07 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.genotype.genotypemanager;
import com.arexis.mugen.genotype.umarker.UMarkerRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.species.chromosome.ChromosomeRemote;
import com.arexis.mugen.species.species.SpeciesRemote;
import java.io.Serializable;

/**
 * Data transfer object for unified marker set information
 * @author lami
 */
public class UMarkerDTO implements Serializable{
    private int umid, cid;
    private String name, alias, comm, user, species, chromosome;
    private double position;
    private java.sql.Date updated;
    
    /**
     * Creates a new instance of UMarkerDTO
     * @param umr The remote interface to the Umarker bean
     */
    public UMarkerDTO(UMarkerRemote umr) {
        try{
            SpeciesRemote sr = umr.getSpecies();
            ChromosomeRemote cr = umr.getChromosome();
            cid = cr.getCid();
            UserRemote ur = umr.getUser();
            
            umid = umr.getUmid();
            name = umr.getName();
            comm = umr.getComm();
            alias = umr.getAlias();
            position = umr.getPosition();
            updated = umr.getTs();
            chromosome = cr.getName();
            user = ur.getUsr();
            species = sr.getName();
        } catch(Exception e) {
            e.printStackTrace();
        }
    } 
    
    /**
     * Creates a new (not fully inited) instance of UMarkerDTO used for history info
     * @param name The unified marker name
     * @param alias The alias
     * @param position The position
     * @param chromosome The chromomsome
     * @param comm The comment
     * @param user The username of the user who made the changes
     * @param updated The date for when the changes were made
     */
    public UMarkerDTO(String name, String alias, double position,
            String chromosome, String comm, String user, java.sql.Date updated) {
        this.name = name;
        this.alias = alias;
        this.position = position;
        this.chromosome = chromosome;
        this.comm = comm;
        this.user = user;
        this.updated = updated;
    }    
    
    /**
     * Creates a new instance of UMarkerDTO
     */
    public UMarkerDTO() {}

    /**
     * Returns the id of the unified marker
     * @return The unified marker id
     */
    public int getUmid() {
        return umid;
    }

    /**
     * Returns the name of the unified marker
     * @return The unified marker name
     */
    public String getName() {
        if(name == null)
            return "";
        return name;
    }
    
    /**
     * Returns the name of the chromosome
     * @return The chromosome name
     */    
    public String getChromosome() {
        if(chromosome == null)
            return "";
        return chromosome;
    }    
    
    /**
     * Returns the chromosome id
     * @return The chromosome id
     */
    public int getCid() {
        return cid;
    }

    /**
     * Returns the alias of the unified marker
     * @return The alias of the unified marker
     */
    public String getAlias() {
        if(alias == null)
            return "";        
        return alias;
    }

    /**
     * Returns the comment for the unified marker
     * @return The comment for the unified marker
     */
    public String getComm() {
        if(comm == null)
            return "";        
        return comm;
    }

    /**
     * Returns the name of the user which made the latest changes to the unified marker
     * @return The username of the user which made the last changes
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
    public String getSpecies() {
        if(species == null)
            return "";        
        return species;
    }

    /**
     * Returns the position for the unified marker
     * @return The position for the unified marker
     */
    public double getPosition() {
        return position;
    }

    /**
     * Returns the date for when the unified marker was last modified
     * @return The date for the last modification on the unified marker
     */
    public String getUpdated() {
        if(updated == null)
            return "";        
        return updated.toString();
    }
    
    /**
     * Compares this object with another for equality
     * @param anotherObj The object to compare with
     * @return True if equal, false otherwise
     */
    public boolean equals(Object anotherObj) {
        return umid == ((UMarkerDTO)anotherObj).getUmid(); 
    }    
}
