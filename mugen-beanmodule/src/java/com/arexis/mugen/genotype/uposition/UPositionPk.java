/*
 * UPositionPk.java
 *
 * Created on June 15, 2005, 9:57 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.genotype.uposition;

import java.io.Serializable;

/**
 * Class for a compound primary key for the unified position bean
 * @author lami
 */
public class UPositionPk implements Serializable {
    private Integer umsid;
    private Integer umid;
    
    /** Creates a new instance of UPositionPk */
    public UPositionPk() {
    }
    
    /**
     * Constructor for the UPosition primary key class
     * @param umsid UMarkerset id
     * @param umid UMarker id
     */
    public UPositionPk(int umsid, int umid) {
        this.umsid = new Integer(umsid);
        this.umid = new Integer(umid);
    }
    
    /**
     * Checks if all keys in the compound key match
     * @param pk UPosition primary key object
     * @return False if no match, true otherwise
     */
    public boolean equals(UPositionPk pk) {
        if (pk.getUmid().intValue() == umid.intValue() && pk.getUmsid().intValue() == umsid.intValue())
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
        txt += umsid +" "+ umid;
        return txt.hashCode();
    }

    /**
     * Returns the UMarkerset id
     * @return A UMarkerset
     */
    public Integer getUmsid() {
        return umsid;
    }

    /**
     * The UMarker id
     * @return A UMarker id
     */
    public Integer getUmid() {
        return umid;
    }    
}
