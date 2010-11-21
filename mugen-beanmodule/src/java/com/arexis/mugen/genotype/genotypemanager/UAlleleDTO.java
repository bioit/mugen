/*
 * UAlleleDTO.java
 *
 * Created on August 25, 2005, 8:08 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.genotype.genotypemanager;
import com.arexis.mugen.genotype.uallele.UAlleleRemote;
import com.arexis.mugen.genotype.umarker.UMarkerRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.species.chromosome.ChromosomeRemote;
import com.arexis.mugen.species.species.SpeciesRemote;
import java.io.Serializable;

/**
 * Data transfer object for unified allele data
 * @author lami
 */
public class UAlleleDTO implements Serializable {  
    private int uaid, umid;
    private String name, user, comm, umarker, species, chromosome;
    private java.sql.Date updated;
    
    /**
     * Creates a new instance of UAlleleDTO
     * @param ar The unified allele bean
     */
    public UAlleleDTO(UAlleleRemote ar) {
        try{
            UserRemote ur = ar.getUser(); //userHome.findByPrimaryKey(new Integer(ar.getId()));
            UMarkerRemote mr = ar.getUMarker(); //umrh.findByPrimaryKey(new Integer(ar.getUmid()));
            SpeciesRemote sr = mr.getSpecies(); //speciesHome.findByPrimaryKey(new Integer(mr.getSid()));
            ChromosomeRemote cr = mr.getChromosome(); //chromosomeHome.findByPrimaryKey(new Integer(mr.getCid()));
            
            uaid = ar.getUaid();
            umarker = mr.getName();
            umid = mr.getUmid();
            name = ar.getName();
            user = ur.getUsr();
            comm = ar.getComm();
            updated = ar.getTs();
            chromosome = cr.getName();
            species = sr.getName();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Creates a new (not fully inited) instance of UAlleleDTO for history info
     * @param name The name
     * @param comm The comment
     * @param user The username of the user that made the changes
     * @param updated The date for when the changes were made
     */
    public UAlleleDTO(String name, String comm, String user, java.sql.Date updated) {
        this.name = name;
        this.comm = comm;
        this.user = user;
        this.updated = updated;
    }    
    
    /**
     * Returns the name of the chromosome for the unified allele
     * @return The name of the chromosome
     */
    public String getChromosome() {
        if(chromosome == null)
            return "";         
        return chromosome;
    }
    
    /**
     * Returns the name of the species for the unified allele
     * @return The name of the species
     */
    public String getSpecies() {
        if(species == null)
            return "";         
        return species;
    }

    /**
     * Returns the unified allele id
     * @return The unified allele id
     */
    public int getUaid() {
        return uaid;
    }

    /**
     * Returns the unified marker id
     * @return The unified marker id
     */
    public int getUmid() {
        return umid;
    }
    
    /**
     * Returns the unified marker name
     * @return The unified marker name
     */
    public String getUmarker() {
        return umarker;
    }    

    /**
     * Returns the unified allele name
     * @return The unified allele name
     */
    public String getName() {
        if(name == null)
            return "";
        return name;
    }

    /**
     * Returns the username of the user which made the last changes to the unified allele
     * @return The username
     */
    public String getUser() {
        if(user == null)
            return "";        
        return user;
    }

    /**
     * Returns the comment for the unified allele
     * @return The comment for the unified allele
     */
    public String getComm() {
        if(comm == null)
            return "";        
        return comm;
    }

    /**
     * Returns the date for when the unified allele was last updated
     * @return The date for the last update
     */
    public String getUpdated() {
        if(updated == null)
            return "";        
        return updated.toString();
    }
}
