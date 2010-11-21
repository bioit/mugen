/*
 * ProjectUnAssignSpeciesAction.java
 *
 * Created on January 25, 2006, 2:25 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.project;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lami
 */
public class ProjectUnAssignSpeciesAction extends MugenAction {
    
    /** Creates a new instance of ProjectUnAssignSpeciesAction */
    public ProjectUnAssignSpeciesAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "ProjectUnAssignSpeciesAction";
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
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");                        

            String pid = request.getParameter("pid");
            String speciesId = request.getParameter("sid");
            projectManager.unAssignSpeciesFromProject(Integer.parseInt(pid), Integer.parseInt(speciesId), caller);
            request.setAttribute("pid", pid); 

            GetProjectInfoAction project = new GetProjectInfoAction();
            project.performAction(request, context);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ApplicationException)
                throw new ApplicationException(e.getMessage());                
        }
        
        return false;
    }     
}
