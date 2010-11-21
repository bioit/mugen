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
import com.arexis.mugen.phenotype.variableset.VariableSetRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.species.species.SpeciesRemote;
import java.io.Serializable;

/**
 * Datatransfer object for information regarding Variable sets.
 * @author lami
 */
public class VariableSetDTO implements Serializable, Comparable {    
    private int vsid, suid, numVars;
    private String name, comm, user, speciesname;
    private java.sql.Date updated;
    
    /**
     * Create a new instance of VariableSetDTO
     * @param vr The Variable Set bean
     */
    public VariableSetDTO(VariableSetRemote vr) {
        try{
            vsid = vr.getVsid();
            suid = vr.getSuid();
            name = vr.getName();
            comm = vr.getComm();       
            numVars = vr.getNumberOfVariables();
            updated = vr.getUpdated();
            user = vr.getUser().getUsr();
            speciesname = vr.getSamplingUnit().getSpecies().getName();
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
    
    /**
     * Creates a new (not fully inited) instance of VariableSetDTO used for history information
     * @param name The name
     * @param comm The comment
     * @param user The username of the user that made the last update
     * @param updated The date for the last update
     */
    public VariableSetDTO(String name, String comm, String user, java.sql.Date updated) {
        this.name = name;
        this.comm = comm;
        this.user = user;
        this.updated = updated;
    }
    
    /**
     * Creates a new instance of VariableSetDTO
     * @param vsid The variable set id
     */
    public VariableSetDTO(int vsid) {
        this.vsid = vsid;
    }
    
    /**
     * Returns the species name
     * @return The species name
     */    
    public String getSpecies(){
        if(speciesname == null)
            return "";
        return speciesname;
    }
    
    /**
     * Returns the number of variables in the set
     * @return The number of variables in the set
     */
    public int getNumVars() {
        return numVars;
    }    

    /**
     * Returns the variable set id
     * @return The variable set id
     */
    public int getVsid() {
        return vsid;
    }

    /**
     * Returns the sampling unit id
     * @return The sampling unit id
     */
    public int getSuid() {
        return suid;
    }

    /**
     * Returns the name of the variable set
     * @return The name of the variable set
     */
    public String getName() {
        if(name == null)
            return "";
        return name;
    }

    /**
     * Returns the comment for the variable set
     * @return The comment
     */
    public String getComm() {
        if(comm == null)
            return "";        
        return comm;
    }

    /**
     * Returns the username of the user that performed the last changes to the variable set
     * @return The username
     */
    public String getUser() {
        if(user == null)
            return "";        
        return user;
    }

    /**
     * Returns the date for when the variable set was last changed
     * @return The date for when the variable set was last changed
     */
    public String getUpdated() {
        if(updated == null)
            return "";        
        return updated.toString();
    }
    
    /**
     * Compares this object to another
     * @param anotherObj The object to compare to
     * @throws java.lang.ClassCastException If the object to compare to was not of this type
     * @return Wether or not the two objects are equal
     */
    public int compareTo(Object anotherObj) throws ClassCastException {
        if(!(anotherObj instanceof VariableSetDTO))
            throw new ClassCastException("Object is of wrong class. VariableSetDTO object expected but not found.");
        return vsid - ((VariableSetDTO)anotherObj).getVsid();
    }     
    
    public boolean equals(Object otherObject)
    {
        if (vsid == ((VariableSetDTO)otherObject).getVsid())
            return true;
        else
            return false;
    }
}
