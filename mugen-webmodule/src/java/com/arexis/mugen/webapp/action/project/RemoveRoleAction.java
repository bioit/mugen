/*
 * RemoveRoleAction.java
 *
 * Created on July 28, 2005, 2:54 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.project;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;

/**
 * An action class to remove a role from a project
 * @author heto
 */
public class RemoveRoleAction extends MugenAction {
    
    /** Creates a new instance of RemoveRoleAction */
    public RemoveRoleAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "RemoveRoleAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = request.getSession();
            //String rid = (String)session.getAttribute("rid");
            
            Workflow w = (Workflow)request.getAttribute("workflow");
            String rid = w.getAttribute("rid");
                        
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            
            projectManager.removeRole(new Integer(rid).intValue(), caller);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not remove role.");
        }
    }
}
