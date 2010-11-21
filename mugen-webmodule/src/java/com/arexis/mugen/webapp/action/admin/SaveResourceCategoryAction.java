/*
 * SaveResourceCategoryAction.java
 *
 * Created on January 18, 2006, 4:07 PM
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
public class SaveResourceCategoryAction extends MugenAction {
    
    /** Creates a new instance of SaveResourceCategoryAction */
    public SaveResourceCategoryAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SaveResourceCategoryAction";
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
            
            if (isSubmit(req,"create")) {
                String pid = req.getParameter("pid");
                String name = req.getParameter("name");
                String comm = req.getParameter("comm");
                 
                resourceManager.createResourceCategory(Integer.parseInt(pid), name, comm, caller);
                
            } else if (isSubmit(req, "save")) {
                String pid = req.getParameter("pid");
                String catId = req.getParameter("catId");
                String name = req.getParameter("name");
                String comm = req.getParameter("comm");
                resourceManager.updateResourceCategory(Integer.parseInt(catId), name, comm, caller);
                req.setAttribute("pid", pid);
            } else if(isSubmit(req, "remove")) {
                String pid = req.getParameter("pid");
                String catId = req.getParameter("catId");
                resourceManager.removeResourceCategory(Integer.parseInt(catId), caller);
                req.setAttribute("pid", pid);                
            }

            return true;
        } catch (Exception e) {
            if(e instanceof ApplicationException)
                throw new ApplicationException("Failed to store resource category. ", e);
            else
                e.printStackTrace();
        }
        
        return false;
    }        
}
