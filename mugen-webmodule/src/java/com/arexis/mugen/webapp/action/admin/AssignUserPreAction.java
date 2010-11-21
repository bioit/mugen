/*
 * AssignUserPreAction.java
 *
 * Created on January 11, 2006, 9:24 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.admin;
import com.arexis.arxframe.ActionException;
import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.project.projectmanager.ProjectDTO;
import com.arexis.mugen.project.projectmanager.UserDTO;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 *
 * @author heto
 */
public class AssignUserPreAction extends MugenAction {
    
    /** Creates a new instance of AssignUserPreAction */
    public AssignUserPreAction() {
    }
    
    public String getName() {
        return "AssignUserPreAction";
    }
    
    /**
     * 
     * @param request 
     * @param context 
     * @throws com.arexis.arxframe.ActionException 
     * @return 
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ActionException {
        try {
            HttpSession session = request.getSession();
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            
            String pidString = request.getParameter("pid");
            int pid = 0;
            if (pidString==null)
                pid = caller.getPid();
            else
                pid = new Integer(pidString).intValue();
            
            Workflow w = (Workflow)request.getAttribute("workflow");
            w.setAttribute("pid", Integer.toString(pid));
            
            Collection roles = projectManager.getRolesByProject(pid, caller);
            request.setAttribute("roles", roles);
            
            // Get all non member users
            Collection users = projectManager.getNonProjectUsers(pid, caller);
            request.setAttribute("users", users);
            
            int id = 0;
            String tmp  = null;
            if (request.getParameter("id")!=null)
                tmp = request.getParameter("id");
            if (w.getAttribute("id")!=null)
                tmp = w.getAttribute("id");
            if (tmp!=null)
            {
                id = new Integer(tmp).intValue();
                UserDTO user = projectManager.getUser(id, caller);
                request.setAttribute("user", user);
            }
            
            ProjectDTO project = projectManager.getProject(caller.getPid(), caller);
            request.setAttribute("project", project);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Failed to assign user", e);
        }
    }
}
