/*
 * GenotypeDTO.java
 *
 * Created on August 24, 2005, 3:13 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.genotype.genotypemanager;
import com.arexis.mugen.genotype.genotype.GenotypeRemote;
import com.arexis.mugen.genotype.marker.MarkerRemote;
import java.io.Serializable;

/**
 * Data transfer object for information regarding a genotype
 * @author lami
 */
public class GenotypeDTO implements Serializable, Comparable {  
    private int suid, mid, iid, aid1, aid2, level;
    private String user, comm, reference, markerName, 
            identity, allele1, allele2, chromosome, speciesname, 
            suname, raw1, raw2;
    private java.sql.Date updated;
    
    /**
     * Creates a new instance of GenotypeDTO
     * @param gr The genotype bean
     */
    public GenotypeDTO(GenotypeRemote gr) {
        try{
            suid = gr.getSamplingUnit().getSuid();
            
            MarkerRemote m = gr.getMarker();
            mid = m.getMid();
            markerName = m.getName();
            
            iid = gr.getIndividual().getIid();
            aid1 = gr.getAid1().getAid();
            aid2 = gr.getAid2().getAid();
            level = gr.getLevel();
            comm = gr.getComm();
            reference = gr.getReference();
            
            identity = gr.getIndividual().getIdentity();
            allele1 = gr.getAid1().getName();
            allele2 = gr.getAid2().getName();
            updated = gr.getUpdated();
            chromosome = gr.getMarker().getChromosome().getName();
            user = gr.getUser().getUsr();
            speciesname = gr.getSamplingUnit().getSpecies().getName();
            suname = gr.getSamplingUnit().getName();
            raw1 = gr.getRaw1();
            raw2 = gr.getRaw2();
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
  
    /**
     * Creates a new (not fully inited) instance of GenotypeDTO for history information
     * @param aid1 The allele 1 id
     * @param aid2 The allele 2 id
     * @param raw1 The raw 1
     * @param raw2 The raw 2
     * @param level The level
     * @param comm The comment
     * @param reference The reference
     * @param user The username of the user that made the changes
     * @param updated The date for when the changes were made
     */
    public GenotypeDTO(int aid1, int aid2, String raw1, String raw2, int level, 
            String comm, String reference, String user, java.sql.Date updated) {
        this.aid1 = aid1;
        this.aid2 = aid2;
        this.raw1 = raw1;
        this.raw2 = raw2;
        this.level = level;
        this.comm = comm;
        this.reference = reference;
        this.user = user;
        this.updated = updated;
    }
    
    /**
     * Creates a new instance of GenotypeDTO
     * @param suid The sampling unit id
     * @param mid The marker id
     * @param iid The individual id
     * @param aid1 The id of the first allele
     * @param aid2 The id of the second allele
     * @param level The level for the genotype
     * @param comm The comment for the genotype
     * @param reference The reference for the genotype
     * @param markerName The name of the marker
     * @param identity The identity of the individual
     * @param allele1 The name of the first allele
     * @param allele2 The name of the second allele
     * @param updated The date for when the last update was made on the genotype
     * @param chromosome The chromosome for the genotype
     * @param user The name of the user that made the last changes on the genotype
     * @param speciesname The name of the species for the genotype
     * @param suname The sampling unit name
     * @param raw1 The raw 1 parameter for the genotype
     * @param raw2 The raw 2 parameter for the genotype
     */
    public GenotypeDTO(int suid, int mid, int iid, int aid1, int aid2, int level, 
            String comm, String reference, String markerName, String identity, 
            String allele1, String allele2, java.sql.Date updated, String chromosome,
            String user, String speciesname, String suname, String raw1, String raw2) {
        try{
            this.suid = suid;
            this.mid = mid;
            this.iid = iid;
            this.aid1 = aid1;
            this.aid2 = aid2;
            this.level = level;
            this.comm = comm;
            this.reference = reference;
            this.markerName = markerName;
            this.identity = identity;
            this.allele1 = allele1;
            this.allele2 = allele2;
            this.updated = updated;
            this.chromosome = chromosome;
            this.user = user;
            this.speciesname = speciesname;
            this.suname = suname;
            this.raw1 = raw1;
            this.raw2 = raw2;
        }catch(Exception e){
            e.printStackTrace();
        }        
    }    
    
    /**
     * Creates a new instance of GenotypeDTO
     * @param mid The marker id
     * @param iid The individual id
     */
    public GenotypeDTO(int mid, int iid) {
        this.mid = mid;
        this.iid = iid;
    }
    
    /**
     * Returns raw1
     * @return Raw1
     */
    public String getRaw1() {
        if(raw1 == null)
            return "";
        return raw1;
    }
    
    /**
     * Returns raw2
     * @return Raw2
     */
    public String getRaw2() {
        if(raw2 == null)
            return "";
        return raw2;
    }    
    
    /**
     * Returns the name of the sampling unit
     * @return The name of the sampling unit
     */
    public String getSuname() {
        if(suname == null)
            return "";
        return suname;
    }
    
    /**
     * Returns the species name
     * @return The species name
     */
    public String getSpecies() {
        if(speciesname == null)
            return "";
        return speciesname;
    }
    
    /**
     * Returns the chromosome name
     * @return The chromosome name
     */
    public String getChromosome() {
        if(chromosome == null)
            return "";
        return chromosome;
    }
    
    /**
     * Returns the date for when the genotype was last updated
     * @return The date for the last updated
     */
    public String getUpdated() {
        if(updated == null)
            return "";
        return updated.toString();
    }

    /**
     * Returns the sampling unit id
     * @return The sampling unit id
     */
    public int getSuid() {
        return suid;
    }

    /**
     * Returns the marker id
     * @return The marker id
     */
    public int getMid() {
        return mid;
    }

    /**
     * Returns the individual id
     * @return The individual id
     */
    public int getIid() {
        return iid;
    }

    /**
     * Returns the allele id for allele 1
     * @return The allele id for allele 1
     */
    public int getAid1() {
        return aid1;
    }

    /**
     * Returns the allele id for allele 2
     * @return The allele id for allele 2
     */
    public int getAid2() {
        return aid2;
    }

    /**
     * Returns the level for the genotype
     * @return The level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Returns the username for the user which made the last changes on the genotype
     * @return The username
     */
    public String getUser() {
        if(user == null)
            return "";
        return user;
    }

    /**
     * Returns the comment for the genotype
     * @return The comment for the genotype
     */
    public String getComm() {
        if(comm == null)
            return "";        
        return comm;
    }

    /**
     * Returns the reference for the genotype
     * @return The reference for the genotype
     */
    public String getReference() {
        if(reference == null || reference.equals("null"))
            return "";        
        return reference;
    }

    /**
     * Returns the marker name for the genotype
     * @return The marker name
     */
    public String getMarkerName() {
        if(markerName == null)
            return "";        
        return markerName;
    }

    /**
     * Returns the identity of the individual for the genotype
     * @return The individual identity
     */
    public String getIdentity() {
        if(identity == null)
            return "";        
        return identity;
    }

    /**
     * Returns the name of allele 1
     * @return The name of allele 1
     */
    public String getAllele1() {
        if(allele1 == null)
            return "";        
        return allele1;
    }

    /**
     * Returns the name of allele 2
     * @return The name of allele 2
     */
    public String getAllele2() {
        if(allele2 == null)
            return "";        
        return allele2;
    }
    
    /**
     * Compares this object to another object
     * @param anotherObj The object to compare with
     * @throws java.lang.ClassCastException If the object to compare with is of another type than GenotypeDTO
     * @return Wether or not the objects are identical, see comparable interface for details
     */
    public int compareTo(Object anotherObj) throws ClassCastException {
        if(!(anotherObj instanceof GenotypeDTO))
            throw new ClassCastException("Object is of wrong class. GenotypeDTO object expected but not found.");
        int midResult = mid - ((GenotypeDTO)anotherObj).getMid();
        int iidResult = iid - ((GenotypeDTO)anotherObj).getIid();
        return midResult - iidResult; 
    }         
}
