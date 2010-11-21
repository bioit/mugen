/*
 * FilterDTO.java
 *
 * Created on October 21, 2005, 1:40 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.phenotype.phenotypemanager;

import java.io.Serializable;

/**
 * Data transfer object for a GQL filter
 * @author lami
 */
public class FilterDTO implements Serializable {
    private String name, expression, comm;
    private int fid, pid, id, sid;
    private java.sql.Date ts;

    /**
     * Creates a new instance of FilterDTO
     * @param fid The filter id
     * @param pid The project id
     * @param sid The species id
     * @param id The id of the user that made the latest changes on the filter
     * @param name The name of the filter
     * @param comm The comment for the filter
     * @param expression The expression for the filter
     * @param ts The date for when the filter was last modified
     */
    public FilterDTO(int fid, int pid, int sid, int id, String name, String comm, 
            String expression, java.sql.Date ts) {
        
        this.fid = fid;
        this.sid = sid;
        this.pid = pid;
        this.id = id;
        this.name = name;
        this.comm = comm;
        this.expression = expression;
        this.ts = ts;
    }

    /**
     * Returns the name of the filter
     * @return The name of the filter
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the expression for the filter
     * @return The expression for the filter
     */
    public String getExpression() {
        return expression;
    }

    /**
     * Returns the comment for the filter
     * @return The comment for the filter
     */
    public String getComm() {
        return comm;
    }

    /**
     * Returns the filter id
     * @return The filter id
     */
    public int getFid() {
        return fid;
    }

    /**
     * Returns the project id
     * @return The project id
     */
    public int getPid() {
        return pid;
    }

    /**
     * Returns the id of the user that made the latest changes on the filter
     * @return The id of the user that made the latest changes on the filter
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the species id for the filter
     * @return The species id for the filter
     */
    public int getSid() {
        return sid;
    }

    /**
     * Returns the date for when the filter was last modified
     * @return The date for when the filter was last modified
     */
    public java.sql.Date getTs() {
        return ts;
    }
    
}
