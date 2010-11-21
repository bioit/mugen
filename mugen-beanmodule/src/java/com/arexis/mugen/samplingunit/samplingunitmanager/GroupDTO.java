/*
 * GroupDTO.java
 *
 * Created on July 27, 2005, 1:42 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.samplingunit.samplingunitmanager;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.samplingunit.group.GroupRemote;
import com.arexis.mugen.samplingunit.grouping.GroupingRemote;
import java.io.Serializable;

/**
 * Datatransfer object for information regarding Groups.
 * @author lami
 */
public class GroupDTO implements Serializable, Comparable {    
    private int gid;
    private int suid;
    private String name;
    private String comm;
    private String user;
    private String gsid;
    private int individuals;
    private java.sql.Date date; 
    
    public GroupDTO()
    {
    }
    
    /**
     * Creates a new instance of GroupingDTO
     * @param grgr The grouping bean
     * @param gr The interface for the group EJB
     * @param caller The caller object for the current working session
     */
    public GroupDTO(GroupRemote gr, GroupingRemote grgr, MugenCaller caller) {
        try{
            this.gid = gr.getGid(); 
            this.name = gr.getName();
            this.comm = gr.getComm();
            this.user = caller.getUsr();
            this.gsid = grgr.getName();            
            this.date = gr.getDate();
            this.suid = caller.getSuid();
            this.individuals = gr.getNumberOfIndividuals();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    // Constructor used for making minimal objects used for comparison when only id is available
    /**
     * Creates a new instanc of group dto
     * @param gid The group id
     */
    public GroupDTO(int gid) {
        this.gid = gid;
    }    
    
    /**
     * Creates a new (limited) instance of GroupDTO used for history information
     * @param name The name
     * @param comm The comment
     * @param user The username
     * @param date The date
     */
    public GroupDTO(String name, String comm, String user, java.sql.Date date) {
        this.name = name;
        this.comm = comm;
        this.date = date;
        this.user = user;
    }

    /**
     * Returns the group id
     * @return The group id
     */
    public int getGid() {
        return gid;
    }
    
    /**
     * Returns the sampling unit id we are working on
     * @return The sampling unit id
     */
    public int getSuid() {
        return suid;
    }    
    
    /**
     * Returns the number of individuals belonging to the group
     * @return The number of individuals in the group
     */
    public int getIndividuals(){
        return individuals;
    }

    /**
     * Returns the name of the group
     * @return The name of the group
     */
    public String getName() {
        if(name == null)
            return "";
        return name;
    }
    
    /**
     * Returns the comment for the group
     * @return The comment for the group
     */
    public String getComm() {
        if(comm == null)
            return "";        
        return comm;
    }

    /**
     * Returns the user who last modified the group
     * @return The username for the user who last modified the group
     */
    public String getUser() {
        if(user == null)
            return "";        
        return user;
    }

    /**
     * Returns the groupings name for which this group belongs to
     * @return The grouping name
     */
    public String getGsid() {
        return gsid;
    }

    /**
     * Returns the timestamp for when the group was last modified
     * @return The timestamp for when the group was last modified
     */
    public String getDate() {
        if(date == null)
            return "";        
        return date.toString();
    }
    
    public String getUpdated() {
        if(date == null)
            return "";        
        return date.toString();
    }
    
    /**
     * Compares this group dto to another object
     * @param anotherObj The object to compare with
     * @throws java.lang.ClassCastException If the object to compare with is not of correct type
     * @return Wether or not the objects are equal
     */
    public int compareTo(Object anotherObj) throws ClassCastException {
        if(!(anotherObj instanceof GroupDTO))
            throw new ClassCastException("Object is of wrong class. GroupDTO object expected but not found.");
        return gid - ((GroupDTO)anotherObj).getGid();
    }    
}
