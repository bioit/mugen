/*
 * SaveUserAction.java
 *
 * Created on December 13, 2005, 12:42 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.admin;

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
public class SaveProjectAction extends MugenAction {
    
    /** Creates a new instance of SaveUserAction */
    public SaveProjectAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SaveProjectAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = req.getSession();
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");                                   
            
            if (isSubmit(req, "create")) {
                
                String name = req.getParameter("name");
                String comm = req.getParameter("comm");
                adminManager.createProject(name, comm, caller);
                
            } else if (isSubmit(req, "save")) {
                String pid = req.getParameter("pid");
                String name = req.getParameter("name");
                String comm = req.getParameter("comm");
                String status = req.getParameter("status");
                
                
                adminManager.updateProject(Integer.parseInt(pid), name, comm, status, caller);
                
                req.setAttribute("pid", pid);
            } else if(isSubmit(req, "remove")) {
                String pid = req.getParameter("pid");
                adminManager.removeProject(Integer.parseInt("pid"), caller);
            }

            return true;
        } catch (Exception e) {
            if(e instanceof ApplicationException)
                throw new ApplicationException("Failed to store project. ", e);
            else
                e.printStackTrace();
        }
        
        return false;
    }      
}
