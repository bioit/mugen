/*
 * IndividualDTO.java
 *
 * Created on July 5, 2005, 4:45 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.samplingunit.samplingunitmanager;

import com.arexis.mugen.samplingunit.individual.IndividualRemote;
import java.io.Serializable;

/**
 * Data transfer object for info about individuals
 * @author heto
 */
public class IndividualDTO implements Serializable, Comparable {
    
    private int iid;
    private String identity;
    private String alias;
    private String sex;
    private String birthDate;    
    private ParentDTO father;
    private ParentDTO mother;
    private String user;
    private String updated;
    private String status;
    private String comm;
    private String fatherName;
    private String motherName;
       
    public IndividualDTO()
    {
    }
         
    /**
     * Creates a new instance of IndividualDO
     * @param iid The individual id
     * @param identity The identity of the individual
     * @param alias The alias of the individual
     * @param sex The sex of the individual
     * @param birthDate The birthdate of the individual
     * @param father The father of the individual
     * @param mother The mother of the individual
     * @param user The user who performed the last changes in the individual
     * @param updated The date for when the individual was last updated
     * @param status The status of the individual
     * @param comm The comment for the individual
     */
    public IndividualDTO(int iid, String identity, String alias,
            String sex, String birthDate, ParentDTO father, ParentDTO mother, 
            String user, String updated, String status, String comm) 
    {
        this.iid = iid;
        this.identity = identity;
        this.alias = alias;
        this.sex = sex;
        this.birthDate = birthDate;
        this.father = father;
        this.mother = mother;
        this.user = user;
        this.updated = updated;
        this.status = status;
        this.comm = comm;
        if(father != null)
            this.fatherName = father.getName();
        else
            fatherName = "";
        if(mother != null)
            this.motherName = mother.getName();
        else
            motherName = "";
    }
    
    /**
     * Creates a new instance of individual dto
     * @param ind The individual bean
     */
    public IndividualDTO(IndividualRemote ind) {
        try {
            this.iid = ind.getIid();
            this.identity = ind.getIdentity();
            this.alias = ind.getAlias();
            this.sex = ind.getSex();
            this.birthDate = ind.getBirthDate().toString();
            this.fatherName = ind.getFather().getIdentity();
            this.motherName = ind.getMother().getIdentity();
            this.user = ind.getUser().getUsr();
            this.updated = ind.getTs().toString();
            this.status = ind.getStatus();
            this.comm = ind.getComm();      
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if this individual is equal to the object o
     * @param o The object to check with
     * @return True if equal, false otherwise
     */
    public boolean equals(Object o) {
        try {
            IndividualDTO i = (IndividualDTO)o;
            if (i.getIid() == iid)
                return true;
        } catch (Exception e) {
        }
        return false;
    }
    
    /**
     * Compares this individual dto with another object
     * @param o The object to compare with
     * @return Wether or not the two objects have the same id
     */
    public int compareTo(Object o) {
        IndividualDTO i = (IndividualDTO)o;
        return (iid - i.getIid());
    }   
    
    /**
     * Returns the date for when this individual was updated
     * @return The date for when this individual was updated
     */
    public String getUpdated(){
        return updated;
    }

    /**
     * Returns the individual id
     * @return The individual id
     */
    public int getIid() {
        return iid;
    }

    /**
     * Returns the identity of the individual
     * @return The id of the individual
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * Returns the alias of the individual
     * @return The alias for the individual
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Returns the sex for this individual
     * @return The sex of the individual
     */
    public String getSex() {
        return sex;
    }

    /**
     * Returns the birthdate of the individual
     * @return The birthdate of the individual
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Returns the id for the father for this individual
     * @return The fathers id
     */
    public ParentDTO getFather() {
        return father;
    }

    /**
     * Returns the id for the mother of the individual
     * @return The id of the individuals mother
     */
    public ParentDTO getMother() {
        return mother;
    }
    
    /**
     * Returns the name (identity) of the mother
     * @return The name (identity) of the mother
     */
    public String getMotherName() {
        if(motherName == null)
            motherName = "";
        return motherName;
    }
    
    /**
     * Returns the name (identity) of the father
     * @return The name (identity) of the father
     */
    public String getFatherName() {
        if(fatherName == null)
            fatherName = "";        
        return fatherName;
    }

    /**
     * Returns the username for the user who last updated the individual
     * @return The username for the user who last updated the individual
     */
    public String getUser() {
        return user;
    }
    
    /**
     * Sets the comment for the individual
     * @param comm The comment for the individual
     */
    public void setComm(String comm){
        this.comm = comm;
    }
    
    /**
     * Returns the comment for the individual
     * @return The comment for the individual
     */
    public String getComm(){
        return comm;
    }

    /**
     * Sets the id for the individual�
     * @param iid The id of the individual
     */
    public void setIid(int iid) {
        this.iid = iid;
    }

    /**
     * Sets the identity of the individual
     * @param identity The identity of the individual
     */
    public void setIdentity(String identity) {
        this.identity = identity;
    }

    /**
     * sets the alias for the individual
     * @param alias The alias for the individual
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Sets the sex of the individual
     * @param sex The sex of the individual (F=Female, M=Male, U=Unknown)
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * Sets the birthdate of the individual
     * @param birthDate The birhtdate of the individual
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Sets the father of the individual
     * @param father The id of the father
     */
    public void setFather(ParentDTO father) {
        this.father = father;
    }

    /**
     * Sets the mother of the individual
     * @param mother The id of the mother
     */
    public void setMother(ParentDTO mother) {
        this.mother = mother;
    }

    /**
     * Sets the username of the one who last updated the individual
     * @param user The name of the user who last updated the individual
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Sets the date for when the individual was last updated
     * @param updated The date for when the individual was last updated
     */
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    /**
     * Returns the status of the individual (Enabled(E)/Disabled(D))
     * @return The status of the individual
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the individual (Enabled(E)/Disabled(D))
     * @param status The status of the individual
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
}
