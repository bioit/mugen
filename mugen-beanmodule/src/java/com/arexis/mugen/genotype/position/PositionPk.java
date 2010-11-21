/*
 * PositionPk.java
 *
 * Created on June 15, 2005, 6:18 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.genotype.position;

import java.io.Serializable;

/**
 * Compound primary key class for the Position Bean
 * @author lami
 */
public class PositionPk implements Serializable {
    private Integer msid, mid;
    /** Creates a new instance of PositionPk */
    public PositionPk() {
    }
 
    /**
     * Constructor for the Position primary key class
     * @param msid The marker set id
     * @param mid The marker id
     */
    public PositionPk(int msid, int mid) {
        this.msid = new Integer(msid);
        this.mid = new Integer(mid);
    }
    
    /**
     * Checks if all keys in the compound key match
     * @param pk Position primary key object
     * @return False if no match, true otherwise
     */
    public boolean equals(PositionPk pk) {
        if (pk.getMid().equals(mid) && pk.getMsid().equals(msid))
            return true;
        else
            return false;
    }
    
    /**
     * Builds a hash code for the keys in the compound key
     * @return The hash code
     */
    public int hashCode() {
        String txt = new String();
        txt += msid +" "+ mid;
        return txt.hashCode();
    }

    /**
     * Returns the Markerset id
     * @return A Markerset
     */
    public Integer getMsid() {
        return msid;
    }

    /**
     * The Marker id
     * @return A Marker id
     */
    public Integer getMid() {
        return mid;
    }      
}
