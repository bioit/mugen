/*
 * AdminRemoveRoleAction.java
 *
 * Created on January 11, 2006, 8:44 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.admin;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.project.projectmanager.RoleDTO;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author heto
 */
public class AdminRemoveRoleAction  extends MugenAction {
    
    /** Creates a new instance of AdminRemoveRoleAction */
    public AdminRemoveRoleAction() {
    }
    
     /**
     * Returns the name of the action
     * @return The name of the action
     */
    public String getName() {
        return "AdminRemoveRoleAction";
    }
    
    /**
     * Performs the action
     * @param request The http request
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True, if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            // Get the caller
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            
            if (request.getParameter("rid")!=null)
            {
                Workflow w = (Workflow)request.getAttribute("workflow");
                w.setAttribute("rid", request.getParameter("rid"));
            }
            
            return true;
        } catch (Exception e) {
            throw new ApplicationException("Failed to fetch parameters.");
        }
    }
}
