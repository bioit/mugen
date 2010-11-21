/*
 * GenotypePk.java
 *
 * Created on June 15, 2005, 5:07 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.genotype.genotype;

import java.io.Serializable;
/**
 * Compound primary key class for the Genotype Bean
 * @author lami
 */
public class GenotypePk implements Serializable {
    private Integer mid, iid;
    /** Creates a new instance of GenotypePk */
    public GenotypePk() {
    }

     /**
     * Constructor for the Genotype primary key class
     * @param mid Marker id
     * @param iid Individual id
     */
    public GenotypePk(int mid, int iid) {
        this.mid = new Integer(mid);
        this.iid = new Integer(iid);
    }    

        /**
     * Checks if all keys in the compound key match
     * @param pk Genotype primary key object
     * @return False if no match, true otherwise
     */
    public boolean equals(GenotypePk pk) {
        if (pk.getMid().equals(mid) && pk.getIid().equals(iid))
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
        txt += mid +" "+ iid;
        return txt.hashCode();
    }

    /**
     * Returns the Individual id
     * @return A Individual id
     */
    public Integer getIid() {
        return iid;
    }

    /**
     * The Marker id
     * @return A Marker id
     */
    public Integer getMid() {
        return mid;
    }  
}
