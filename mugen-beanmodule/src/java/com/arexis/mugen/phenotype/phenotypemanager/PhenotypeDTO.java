/*
 * PhenotypeDTO.java
 *
 * Created on July 27, 2005, 1:42 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.phenotype.phenotypemanager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.phenotype.phenotype.PhenotypeRemote;
import com.arexis.mugen.phenotype.variable.VariableRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.samplingunit.expobj.ExpObj;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.species.species.SpeciesRemote;
import java.io.Serializable;

/**
 * Datatransfer object for information regarding Phenotypes.
 * @author lami
 */
public class PhenotypeDTO implements Serializable {    
    private int vid, eid, suid, userId;
    private String value, reference, comm, user, identity, variable,
            speciesname, type, suname, accNr;
    private java.sql.Date date, updated;
    
    /**
     * Creates a new instance of PhenotypeDTO
     * @param pr The phenotype bean
     */
    public PhenotypeDTO(PhenotypeRemote pr) {
        try{
            ExpObj expObject = pr.getExperimentalObject(null);
            VariableRemote vr = pr.getVariable();
            SamplingUnitRemote sur = pr.getSamplingUnit();
            SpeciesRemote sr = sur.getSpecies();
            UserRemote user = pr.getUser();
            
            this.vid = vr.getVid();
            this.variable = vr.getName();
            
            this.eid = expObject.getEid();
            
            this.identity = expObject.getIdentity();
            
            this.value = pr.getValue();
            this.reference = pr.getReference();
            this.comm = pr.getComm();
            this.date = pr.getDate();            
            this.updated = pr.getTs();                        
            this.user = user.getUsr();           
            this.userId = user.getId();
            
            this.type = vr.getType();
            this.speciesname = sr.getName();
            this.suname = sur.getName();
        }catch(Exception e){
            e.printStackTrace();
        }
    }    
    
    /**
     * Creates a new instance of PhenotypeDTO
     * @param caller The caller
     * @param pr The phenotype bean
     */
    public PhenotypeDTO(PhenotypeRemote pr, MugenCaller caller) {
        try{
            ExpObj expObject = pr.getExperimentalObject(caller);
            VariableRemote vr = pr.getVariable();
            SamplingUnitRemote sur = pr.getSamplingUnit();
            SpeciesRemote sr = sur.getSpecies();
            UserRemote user = pr.getUser();
            
            this.vid = vr.getVid();
            this.variable = vr.getName();
            
            this.eid = expObject.getEid();
            
            if(expObject.getType() == 0) {
                this.identity = expObject.getIdentity();
                this.accNr = "";
            }
            else {
                this.identity = "";
                this.accNr = expObject.getIdentity();                
            }
            
            this.value = pr.getValue();
            this.reference = pr.getReference();
            this.comm = pr.getComm();
            this.date = pr.getDate();            
            this.updated = pr.getTs();                        
            this.user = user.getUsr(); 
            this.userId = user.getId();
            
            this.type = vr.getType();
            this.speciesname = sr.getName();
            this.suname = sur.getName();
        }catch(Exception e){
            e.printStackTrace();
        }
    }       
    
    /**
     * Creates a new (partially inited) instance of PhenotypeDTO used for tracking history.
     * @param value The value
     * @param date The date
     * @param comm The comment
     * @param reference The reference
     * @param user The username of the user which made the last update
     * @param updated The date for the last update
     */
    public PhenotypeDTO(String value, java.sql.Date date, String comm, String reference, UserRemote user, java.sql.Date updated) {   
        try
        {
            this.value = value;
        this.date = date;
        this.comm = comm;
        this.reference = reference;
        this.user = user.getUsr();
        this.userId = user.getId();
        this.updated = updated;    
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }        
        
    /**
     * Returns the name of the variable for the phenotype
     * @return The name of the variable
     */
    public String getVariable() {
        if(variable == null)
            return "";
        return variable;
    }
    
    /**
     * Returns the variable id
     * @return The variable id
     */
    public int getVid() {
        return vid;
    }

    /**
     * Returns the individual id
     * @return The individual id
     */
    public int getIid() {
        return eid;
    }
    
    /**
     * Returns the experimental object id
     * @return The experimental object id (i.e. the individual or model id)
     */
    public int getEid() {
        return eid;
    }    
    
    /**
     * Returns the accession number for the model associated to the phenotype
     * @return The accession number for the model
     */
    public String getAccNr() {
        return accNr;
    }

    /**
     * Returns the sampling unit id
     * @return The sampling unit id
     */
    public int getSuid() {
        return suid;
    }

    /**
     * Returns the phenotype value
     * @return The pheotype value
     */
    public String getValue() {
        if(value == null)
            return "";
        
        return value;
    }

    /**
     * Returns the reference for the phenotype
     * @return The reference for the phenotype
     */
    public String getReference() {
        if(reference == null)
            return "";
        
        return reference;
    }

    /**
     * Returns the comment for the phenotype
     * @return The comment for the phenotype
     */
    public String getComm() {
        if(comm == null)
            return "";
        
        return comm;
    }

    /**
     * Returns the date for the phenotype
     * @return The date for the phenotype
     */
    public String getDate() {
        if(date == null)
            return "";
        
        return date.toString();
    }

    /**
     * Returns the username of the user who made the last changes to the pheotype
     * @return The username
     */
    public String getUser() {
        if(user == null)
            return "";
        
        return user;
    }

    /**
     * Returns the date for when the last changes were made
     * @return The date for when the last changes were made
     */
    public String getUpdated() {
        if(updated == null)
            return "";
        
        return updated.toString();
    }

    /**
     * Returns the identity of the individual showing the phenotype
     * @return The identity of the individual
     */
    public String getIdentity() {
        if(identity == null)
            return "";
        
        return identity;
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
     * Returns the name of the species for the phenotype
     * @return The species name
     */
    public String getSpeciesname() {
        if(speciesname == null)
            return "";        
        
        return speciesname;
    }

    /**
     * Returns the type for the phenotype variable
     * @return The type for the phenotype variable
     */
    public String getType() {
        if(type == null)
            return "";
        
        return type;
    }

    public int getUserId() {
        return userId;
    }
}
