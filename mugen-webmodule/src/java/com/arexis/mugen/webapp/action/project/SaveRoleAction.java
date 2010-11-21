/*
 * SaveRoleAction.java
 *
 * Created on July 19, 2005, 2:06 PM
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
 * MugenAction class for saving a role
 * @author heto
 */
public class SaveRoleAction extends MugenAction {
    
    /** Creates a new instance of SaveRoleAction */
    public SaveRoleAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "SaveRoleAction";
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
            
            String ass[] = request.getParameterValues("ass");
            String other[] = request.getParameterValues("other");
            
            String rid = (String)request.getParameter("rid");
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            
            
            if (isSubmit(request, "add"))
            {
                projectManager.addPrivilegesToRole(other, new Integer(rid).intValue(), caller);
            }
            else if (isSubmit(request, "remove"))
            {
                projectManager.removePrivilegesFromRole(ass, new Integer(rid).intValue(), caller);
            }
            else if (isSubmit(request, "submit"))
            {
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");

                projectManager.updateRole(Integer.parseInt(rid), name, comm, caller);
            }
            else if(isSubmit(request, "create"))
            {
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");
                
                int pid = caller.getPid();
                
                Workflow w = (Workflow)request.getAttribute("workflow");
                
                if (w.getAttribute("pid")!=null)
                    pid = new Integer((String)w.getAttribute("pid")).intValue();
                
                System.out.println("AttrPid="+w.getAttribute("pid"));
                System.out.println("ParamPid="+request.getParameter("pid"));

                int roleID = projectManager.createRole(name, comm, pid, caller);                
            }
                    
            return true;
        } 
        catch (ApplicationException ae)
        {
            throw ae;
        }
        catch (Exception e) 
        {            
            throw new ApplicationException("Failed to Save role ", e);
        }
    }
}
