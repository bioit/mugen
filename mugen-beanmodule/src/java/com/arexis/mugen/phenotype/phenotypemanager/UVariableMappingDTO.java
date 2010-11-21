/*
 * UVariableMappingDTO.java
 *
 * Created on July 27, 2005, 1:42 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.phenotype.phenotypemanager;

import com.arexis.mugen.phenotype.uvariable.UVariableRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.species.species.SpeciesRemote;
import java.io.Serializable;

/**
 * Datatransfer object for information regarding Unified Variable mappings.
 * @author lami
 */
public class UVariableMappingDTO implements Serializable {        
    private String suname, varname;
    private int suid, vid, uvid;

    /**
     * Creates a new instance of UVariableMappingDTO
     * @param suname The sampling unit name
     * @param varname The variable name
     * @param suid The sampling unit id
     * @param vid The variable id
     * @param uvid The unified variable id
     */
    public UVariableMappingDTO(String suname, String varname, int suid, int vid, int uvid) {
        this.suname = suname;
        this.varname = varname;
        this.suid = suid;
        this.vid = vid;
        this.uvid = uvid;
    }   

    /**
     * Returns the sampling unit name
     * @return The sampling unit name
     */
    public String getSuname() {
        return suname;
    }

    /**
     * Returns the variable name
     * @return The variable name
     */
    public String getVarname() {
        return varname;
    }

    /**
     * Returns the sampling unit id
     * @return The sampling unit id
     */
    public int getSuid() {
        return suid;
    }

    /**
     * Returns the variable id
     * @return The variable id
     */
    public int getVid() {
        return vid;
    }

    /**
     * Returns the unified variable id
     * @return The unified variable id
     */
    public int getUvid() {
        return uvid;
    }
}
