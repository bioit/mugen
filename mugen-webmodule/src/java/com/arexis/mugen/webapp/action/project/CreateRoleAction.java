/*
 * CreateRoleAction.java
 *
 * Created on July 27, 2005, 2:51 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.project;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.projectmanager.RoleDTO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;

/**
 * MugenAction class for creation of a role
 * @author heto
 */
public class CreateRoleAction extends MugenAction {
    
    /** Creates a new instance of CreateRoleAction */
    public CreateRoleAction() {
    }
    
    /**
     * Returns the name of the action
     * @return The name of the action
     */
    public String getName() {
        return "CreateRoleAction";
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
            
            RoleDTO r = new RoleDTO(-1, "New Role", "");
            
            request.setAttribute("role", r);
            
            if (request.getParameter("pid")!=null)
            {
                Workflow w = (Workflow)request.getAttribute("workflow");
                w.setAttribute("pid", request.getParameter("pid"));
            }
            
            return true;
        } catch (Exception e) {
            throw new ApplicationException("Create role failed.");
        }
        //return false;
    }
}
