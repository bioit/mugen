/*
 * GetUsersAction.java
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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;
import java.util.Collection;


/**
 * MugenAction class for retrieving info about projects and users
 * @author heto
 */
public class GetProjectInfoAction extends MugenAction {
    
    /**
     * Creates a new instance of GetProjectInfoAction 
     */
    public GetProjectInfoAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "GetProjectInfoAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            int pid = caller.getPid();
            if (pid == 0)
                throw new ApplicationException("Project id is missing");
            
            ProjectDTO prj = projectManager.getProject(pid, caller);
            Collection users = projectManager.getProjectUsers(prj.getPid(), caller);
            Collection species = adminManager.getSpeciesForProject(pid, caller);
            Collection categoriesAndResources = projectManager.getCategoriesAndResources(pid, caller);
            request.setAttribute("project", prj);
            request.setAttribute("pid", ""+prj.getPid());
            request.setAttribute("users", users);
            request.setAttribute("species", species);
            request.setAttribute("categoriesAndResources", categoriesAndResources);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
