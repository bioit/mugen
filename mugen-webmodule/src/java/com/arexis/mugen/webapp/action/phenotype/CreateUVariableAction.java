/*
 * CreateUVariableAction.java
 *
 * Created on July 14, 2005, 4:43 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.phenotype;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.phenotype.phenotypemanager.TypeDTO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;



/**
 * MugenAction class for the retrieval type
 * @author lami
 */
public class CreateUVariableAction extends MugenAction {
    
    /** Creates a new instance of CreateUVariableAction */
    public CreateUVariableAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "CreateUVariableAction";
    }
    
    /**
     * Performs this action
     * @param request The http request object
     * @param context The servlet context
     * @return True if this action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            TypeDTO d = new TypeDTO("E");            
            request.setAttribute("types", d);        
            
            request.setAttribute("species", samplingUnitManager.getSpeciesForProject(caller.getPid(), caller));
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
