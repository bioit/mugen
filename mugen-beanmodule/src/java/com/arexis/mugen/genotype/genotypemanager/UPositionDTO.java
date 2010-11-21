/*
 * UPositionDTO.java
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
 * Data transfer object for positions in unified marker set information
 * @author lami
 */
public class UPositionDTO extends UMarkerDTO implements Serializable{
    private String umname, cname;
    private int umid, umsid;
    private double defpos, pos, overpos;
    
    /**
     * Creates a new instance of UPositionDTO
     * @param umid The unified marker id
     * @param umsid The unified marker set id
     * @param umname The unified marker name
     * @param cname The unified chromosome name
     * @param pos The position
     * @param defpos The default position
     * @param overpos The over position
     */
    public UPositionDTO(String umname, String cname, int umid, int umsid, double pos, 
            double defpos, double overpos) {
        this.umname = umname;
        this.cname = cname;
        this.umid = umid;
        this.umsid = umsid;
        this.pos = pos;
        this.defpos = defpos;
        this.overpos = overpos;
    } 

    /**
     * Returns the unified marker name
     * @return The unified marker name
     */
    public String getUmname() {
        if(umname == null)
            return "";
        return umname;
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
     * Returns the unified marker id
     * @return The unified marker id
     */
    public int getUmid() {
        return umid;
    }
    
    /**
     * Returns the unified marker set id
     * @return The unified marker set id
     */
    public int getUmsid() {
        return umsid;
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
