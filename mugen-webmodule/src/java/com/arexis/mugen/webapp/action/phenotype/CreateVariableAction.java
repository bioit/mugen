/*
 * CreateVariableAction.java
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
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;



/**
 * MugenAction class for the retrieval of sampling units and type
 * @author lami
 */
public class CreateVariableAction extends MugenAction {
    
    /** Creates a new instance of CreateVariableAction */
    public CreateVariableAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "CreateVariableAction";
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
            
            Collection samplingunits = samplingUnitManager.getSamplingUnits(caller.getPid(), caller);
            
            //if (samplingunits==null)
            //    throw new Exception("Samplingunits obj null");
            
            request.setAttribute("samplingunits", samplingunits);
            
            // Might seem as overkill but it makes the view cleaner to
            // do like this...
            TypeDTO d = new TypeDTO("E");            
            request.setAttribute("types", d);        
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
