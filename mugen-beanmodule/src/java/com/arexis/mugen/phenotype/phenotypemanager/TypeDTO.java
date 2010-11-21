/*
 * TypeDTO.java
 *
 * Created on August 22, 2005, 10:23 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.phenotype.phenotypemanager;

import java.io.Serializable;

/**
 * Convenience class for enabling the use of custom tag for Variable type drop-down menu.
 * @author lami
 */
public class TypeDTO implements Serializable {  
    private String type;
    
    /**
     * Creates a new instance of TypeDTO
     * @param type The type
     */
    public TypeDTO(String type) {
        this.type = type;
    }
    
    
    /**
     * Returns the type
     * @return The type (N or E)
     */
    public String getType(){
        return type;
    }
}
