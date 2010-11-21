/*
 * GroupingDTO.java
 *
 * Created on July 27, 2005, 1:42 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.samplingunit.samplingunitmanager;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.samplingunit.grouping.GroupingRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import java.io.Serializable;

/**
 * Datatransfer object for information regarding Groupings.
 * @author lami
 */
public class GroupingDTO implements Serializable, Comparable {    
    private int gsid;
    private String name;
    private String comm;
    private String user;
    private String suid;
    private int groups;
    private java.sql.Date updated;    
    
    public GroupingDTO()
    {}
    
    /**
     * Creates a new instance of GroupingDTO
     * @param gr The interface for the grouping EJB
     * @param caller The caller object for the current working session
     * @param groups The number of groups belonging to the grouping
     */
    public GroupingDTO(GroupingRemote gr, MugenCaller caller, int groups) {
        try{
            this.gsid = gr.getGsid(); 
            this.name = gr.getName();
            this.comm = gr.getComm();
            this.user = caller.getUsr();
            SamplingUnitRemote sr = gr.getSamplingUnit();
            
            this.suid = sr.getName();
            this.updated = gr.getTs();
            this.groups = groups;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * Creates a new instance of GroupingDTO
     * @param gsid The groupings id
     * @param name The groupings name
     * @param comm The groupings comment
     * @param suid The groupings sampling unit
     * @param updated The date for when the last changes were made
     * @param caller The caller object
     * @param groups The number of groups in the grouping
     */
    public GroupingDTO(int gsid, String name, String comm, String suid, java.sql.Date updated, MugenCaller caller, int groups) {
        try{
            this.gsid = gsid; 
            this.name = name;
            this.comm = comm;
            this.user = caller.getUsr();
            this.suid = suid;
            this.updated = updated;
            this.groups = groups;
        }catch(Exception e){
            e.printStackTrace();
        }
    }       
    
    // Constructor used for making minimal objects used for comparison when only id is available
    /**
     * Creates a new instance of grouping dto
     * @param gsid The grouping id
     */
    public GroupingDTO(int gsid) {
        this.gsid = gsid;
    }

    /**
     * Returns the grouping id.
     * @return The grouping id.
     */
    public int getGsid() {
        return gsid;
    }

    /**
     * Returns the name of the grouping.
     * @return The grouping name.
     */
    public String getName() {
        if(name == null)
            return "";        
        return name;
    }

    /**
     * Returns the comment for this grouping.
     * @return The comment for the grouping.
     */
    public String getComm() {
        if(comm == null)
            return "";        
        return comm;
    }

    /**
     * Returns the user who last operated on this grouping.
     *
     * @return The username of the user who last edited this grouping.
     */
    public String getUser() {
        if(user == null)
            return "";
        return user;
    }

    /**
     * The sampling unit name for this grouping.
     * @return The sampling unit name for the grouping.
     */
    public String getSuid() {
        return suid;
    }

    /**
     * Returns the date for when this grouping was last edited.
     * @return The date for when this grouping was last edited.
     */
    public String getUpdated() {
        if(updated == null)
            return "";
        return updated.toString();
    }

    /**
     * Returns the number of groups belonging to this grouping.
     * @return The number of groups belonging to this grouping.
     */
    public int getGroups() {
        return groups;
    }
    
    /**
     * Compares this grouping with another object
     * @param anotherObj The object to compare with
     * @throws java.lang.ClassCastException If the object to compare with is of wrong type
     * @return Wether or not the two objects are equal
     */
    public int compareTo(Object anotherObj) throws ClassCastException {
        if(!(anotherObj instanceof GroupingDTO))
            throw new ClassCastException("Object is of wrong class. GroupingDTO object expected but not found.");
        GroupingDTO gdto = (GroupingDTO)anotherObj;

        return gsid - gdto.getGsid();
    }
}
