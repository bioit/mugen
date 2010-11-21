/*
 * MutationTypeDTO.java
 *
 * Created on July 10, 2006, 9:32 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.model.strain.mutationtype.MutationTypeRemote;
import java.io.Serializable;

/**
 *
 * @author heto
 */
public class MutationTypeDTO implements Serializable
{
    private int mutantid;
    private String abbreviation;
    private String name;
    
    /** Creates a new instance of MutationTypeDTO */
    public MutationTypeDTO(MutationTypeRemote mut)
    {
        try
        {
            mutantid = mut.getMutantid();
            abbreviation = mut.getAbbreviation();
            name = mut.getName();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @deprecated use getId() instead
     */
    public int getMutantid()
    {
        return mutantid;
    }
    
    public int getId()
    {
        return mutantid;
    }

    public String getAbbreviation()
    {
        return abbreviation;
    }

    public String getName()
    {
        return name;
    }
    
}
