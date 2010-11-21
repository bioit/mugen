/*
 * ParentDTO.java
 *
 * Created on July 5, 2005, 4:53 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.samplingunit.samplingunitmanager;

import com.arexis.mugen.samplingunit.individual.IndividualRemote;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Data transfer object for information about a parent
 * @author heto
 */
public class ParentDTO implements Serializable {
        
    private String name;
    private int iid;
    
    public ParentDTO()
    {}
    
    /**
     * Creates a new instance of ParentDTO
     * @param name The name of the parent
     * @param iid The id of the parent
     */
    public ParentDTO(String name, int iid) 
    {
        this.name = name;
        this.iid = iid;
    }
    
    /**
     * Creates a new instance of ParentDTO
     * @param parent The remote interface of the parent
     */
    public ParentDTO(IndividualRemote parent)
    {
        try
        {
            if(parent != null) {
                this.name = parent.getIdentity();
                this.iid = parent.getIid();
            }
            else {
                this.name = "";
                this.iid = -1;                
            }
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Returns the name of the parent
     * @return The name of the parent
     */
    public String getName() {
        if(name.equals("-"))
            name = "";
        return name;
    }

    /**
     * Returns the id of the parent
     * @return The id of the parent
     */
    public int getIid() {
        return iid;
    }
}
