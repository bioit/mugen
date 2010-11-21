/*
 * UMarkerMappingDTO.java
 *
 * Created on August 30, 2005, 12:07 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.genotype.genotypemanager;
import com.arexis.mugen.genotype.umarker.UMarkerRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.species.chromosome.ChromosomeRemote;
import com.arexis.mugen.species.species.SpeciesRemote;
import java.io.Serializable;

/**
 * Data transfer object for unified marker set mapping information
 * @author lami
 */
public class UMarkerMappingDTO implements Serializable{
    private int umid, mid;
    private String samplingunit, marker;
    
    /**
     * Creates a new instance of UMarkerMappingDTO
     * @param marker The name of the marker
     * @param samplingunit The sampling unit name
     * @param umid The unified marker id
     * @param mid The marker id
     */
    public UMarkerMappingDTO(String samplingunit, String marker, int umid, int mid) {
        this.mid = mid;
        this.umid = umid;
        this.samplingunit = samplingunit;
        this.marker = marker;
    } 

    /**
     * Returns the unified marker id
     * @return The unified marker id
     */
    public int getUmid() {
        return umid;
    }

    /**
     * Returns the marker id
     * @return The marker id
     */
    public int getMid() {
        return mid;
    }
    
    /**
     * Returns the name of the marker
     * @return The name of the marker
     */
    public String getMarker() {
        if(marker == null)
            return "";
        return marker;
    }    

    /**
     * Returns the name of the sampling unit
     * @return The sampling unit
     */
    public String getSamplingunit() {
        if(samplingunit == null)
            return "";
        return samplingunit;
    }
}
