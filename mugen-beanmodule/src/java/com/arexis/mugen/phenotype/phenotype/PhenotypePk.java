/*
 * PhenotypePk.java
 *
 * Created on June 16, 2005, 10:13 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.phenotype.phenotype;

import java.io.Serializable;

/**
 * Class for the compound key of a phenotype
 * @author lami
 */
public class PhenotypePk implements Serializable {
    private Integer vid, iid;
    
    /** Creates a new instance of PhenotypePk */
    public PhenotypePk() {
    }

        /**
     * Constructor for the Phenotype primary key class
     * @param vid variable id
     * @param iid individual id
     */
    public PhenotypePk(int vid, int iid) {
        this.vid = new Integer(vid);
        this.iid = new Integer(iid);
    }
    
    /**
     * Checks if all keys in the compound key match
     * @param pk Position primary key object
     * @return False if no match, true otherwise
     */
    public boolean equals(PhenotypePk pk) {
        if (pk.getVid().equals(vid) && pk.getIid().equals(iid))
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
        txt += vid +" "+ iid;
        return txt.hashCode();
    }

    /**
     * Returns the variable id
     * @return A variable id
     */
    public Integer getVid() {
        return vid;
    }

    /**
     * The indivual id
     * @return An indivual id
     */
    public Integer getIid() {
        return iid;
    }  
}
