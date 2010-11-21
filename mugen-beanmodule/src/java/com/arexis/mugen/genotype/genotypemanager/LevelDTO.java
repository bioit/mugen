/*
 * LevelDTO.java
 *
 * Created on August 25, 2005, 8:27 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.genotype.genotypemanager;

import java.io.Serializable;

/**
 * Data transfer object for allele levels. Used for generation of combobox by custom tag.
 * @author lami
 */
public class LevelDTO implements Serializable{
    private String level;
    
    /**
     * Creates a new instance of LevelDTO
     * @param level The level
     */
    public LevelDTO(int level) {
        this.level = ""+level;
    }
    
    /**
     * Creates a new instance of LevelDTO
     * @param level The level
     */
    public LevelDTO(String level) {
        this.level = level;
    }
    
    
    /**
     * Returns the level
     * @return The level
     */
    public String getLevel() {
        return level;
    }
}
