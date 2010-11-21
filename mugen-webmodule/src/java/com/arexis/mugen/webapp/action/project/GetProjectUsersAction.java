/*
 * GetProjectUsersAction.java
 *
 * Created on July 29, 2005, 3:41 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.project;

import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.projectmanager.ProjectDTO;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;


/**
 * MugenAction class for retrieving info about projects and users
 * @author heto
 */
public class GetProjectUsersAction extends MugenAction {
    
    /** Creates a new instance of GetProjectUsersAction */
    public GetProjectUsersAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "GetProjectUsersAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            int pid = caller.getPid();
            
            String sort = null;
            if (request.getParameter("sort")!=null)
            {
                sort = request.getParameter("sort");
                caller.setAttribute("sort", sort);
            }
            
            Collection users = projectManager.getProjectUsers(pid, caller);
            request.setAttribute("users", users);
            
            ProjectDTO project = projectManager.getProject(caller.getPid(), caller);
            request.setAttribute("project", project);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
