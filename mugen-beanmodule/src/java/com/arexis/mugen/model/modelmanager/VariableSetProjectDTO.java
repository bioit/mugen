/*
 * VariableSetProjectDTO.java
 *
 * Created on January 19, 2006, 11:13 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.phenotype.phenotypemanager.VariableSetDTO;
import com.arexis.mugen.phenotype.variableset.VariableSetRemote;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.projectmanager.ProjectDTO;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author heto
 */
public class VariableSetProjectDTO extends VariableSetDTO implements Serializable, Comparable {
   
    private ProjectDTO[] prjs;
    
    
    /** Creates a new instance of VariableSetProjectDTO */
    public VariableSetProjectDTO(VariableSetRemote vs, Collection projects) 
    {
        super(vs);   
        prjs = new ProjectDTO[projects.size()];
        int i = 0;
        Iterator ip = projects.iterator();
        while (ip.hasNext())
        {
            ProjectRemote prj = (ProjectRemote)ip.next();
            prjs[i] = new ProjectDTO(prj);
            i++;
        }
    }
    
    /**
     * Get an array of ProjectDTO for this VariableSet.
     * @return 
     */
    public ProjectDTO[] getProjects()
    {
        return prjs;
    }
}
