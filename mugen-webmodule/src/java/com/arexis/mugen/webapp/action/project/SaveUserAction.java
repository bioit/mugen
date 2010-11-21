package com.arexis.mugen.webapp.action.project;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SaveUserAction extends MugenAction {
    
    public SaveUserAction() {}
    
    public String getName() {
        return "SaveUserAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = req.getSession();
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");   
            Workflow wf = (Workflow)req.getAttribute("workflow");
            
            if (isSubmit(req, "create")) {
                String name = req.getParameter("name");
                String email = req.getParameter("email");
                String userLink = req.getParameter("userLink");
                //String role = req.getParameter("rid");
                //if(!exists(role))
                //    throw new ApplicationException("Cannot create a project user without a role");
                
                String groupPhone = req.getParameter("groupPhone");
                String groupAddress = req.getParameter("groupAddress");
                String groupLink = req.getParameter("groupLink");
                String groupName = req.getParameter("groupName");
                
                String pwd = req.getParameter("pwd");
                String usr = req.getParameter("usr");
                int id = projectManager.createUser(name, email, userLink, groupName, groupAddress, groupPhone, groupLink, usr, pwd, caller);
                wf.setAttribute("id", new Integer(id).toString());
            } else if (isSubmit(req, "save")) {
                String id = req.getParameter("id");
                String name = req.getParameter("name");
                String email = req.getParameter("email");
                String role = req.getParameter("rid");
                if(!exists(role))
                    throw new ApplicationException("Cannot store a project user without a role");                
                String userLink = req.getParameter("userLink");
                
                String groupPhone = req.getParameter("groupPhone");
                String groupAddress = req.getParameter("groupAddress");
                String groupLink = req.getParameter("groupLink");                              
                
                String groupName = req.getParameter("groupName"); 
                String pwd = req.getParameter("pwd");
                String usr = req.getParameter("usr");                  
                projectManager.updateUser(Integer.parseInt(id), Integer.parseInt(role), name, email, userLink, groupName, groupAddress, groupPhone, groupLink, caller, usr, pwd);
                
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
