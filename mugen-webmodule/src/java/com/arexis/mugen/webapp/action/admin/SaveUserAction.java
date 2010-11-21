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
public class SaveUserAction extends MugenAction {
    
    /** Creates a new instance of SaveUserAction */
    public SaveUserAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SaveUserAction";
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
                String email = req.getParameter("email");
                String userLink = req.getParameter("userLink");
                
                /*
                String role = req.getParameter("rid");
                if(!exists(role))
                    throw new ApplicationException("Cannot create a project user without a role");
                 */
                
                String groupPhone = req.getParameter("groupPhone");
                String groupAddress = req.getParameter("groupAddress");
                String groupLink = req.getParameter("groupLink");
                String groupName = req.getParameter("groupName");
                
                String pwd = req.getParameter("pwd");
                String usr = req.getParameter("usr");
                adminManager.createUser(name, email, userLink, groupName, groupAddress, groupPhone, groupLink, usr, pwd, caller);
            } else if (isSubmit(req, "save")) {
                String id = req.getParameter("id");
                String name = req.getParameter("name");
                String email = req.getParameter("email");
                /*
                String role = req.getParameter("rid");
                if(!exists(role))
                    throw new ApplicationException("Cannot store a project user without a role");                
                 **/
                String userLink = req.getParameter("userLink");
                
                String groupPhone = req.getParameter("groupPhone");
                String groupAddress = req.getParameter("groupAddress");
                String groupLink = req.getParameter("groupLink");                              
                
                String groupName = req.getParameter("groupName"); 
                String pwd = req.getParameter("pwd");
                String usr = req.getParameter("usr");                  
                adminManager.updateUser(Integer.parseInt(id), name, email, userLink, groupName, groupAddress, groupPhone, groupLink, caller, usr, pwd);
                
                req.setAttribute("id", id);
            } else if(isSubmit(req, "remove")) {
                String id = req.getParameter("id");
                projectManager.removeUser(Integer.parseInt("id"), caller);
            }

            return true;
        } catch (Exception e) {
            if(e instanceof ApplicationException)
                throw new ApplicationException(e.getMessage());
            else
                e.printStackTrace();
        }
        
        return false;
    }      
}
