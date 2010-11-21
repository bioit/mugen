/*
 * PositionDTO.java
 *
 * Created on August 30, 2005, 12:07 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.genotype.genotypemanager;
import java.io.Serializable;

/**
 * Data transfer object for positions in marker set information
 * @author lami
 */
public class PositionDTO extends MarkerDTO implements Serializable{
    private String mname, cname;
    private int mid, msid;
    private double defpos, pos, overpos;
    
    /**
     * Creates a new instance of PositionDTO
     * @param msid The marker set id
     * @param mname The marker name
     * @param cname The chromosome name
     * @param mid The marker id
     * @param pos The position
     * @param defpos The default position
     * @param overpos The over position
     */
    public PositionDTO(String mname, String cname, int mid, int msid, double pos, 
            double defpos, double overpos) {
        this.mname = mname;
        this.cname = cname;
        this.mid = mid;
        this.msid = msid;
        this.pos = pos;
        this.defpos = defpos;
        this.overpos = overpos;
    } 

    /**
     * Returns the marker name
     * @return The marker name
     */
    public String getMname() {
        if(mname == null)
            return "";
        return mname;
    }

    /**
     * Return the chromosome name
     * @return The chromosome name
     */
    public String getCname() {
        if(cname == null)
            return "";        
        return cname;
    }

    /**
     * Returns the marker id
     * @return The marker id
     */
    public int getMid() {
        return mid;
    }
    
    /**
     * Returns the marker set id
     * @return The marker set id
     */
    public int getMsid() {
        return msid;
    }    

    /**
     * Returns the default position
     * @return The default position
     */
    public double getDefpos() {
        return defpos;
    }

    /**
     * Returns the position
     * @return The position
     */
    public double getPos() {
        return pos;
    }

    /**
     * Returns the overposition
     * @return The overposition
     */
    public double getOverpos() {
        return overpos;
    }
}
