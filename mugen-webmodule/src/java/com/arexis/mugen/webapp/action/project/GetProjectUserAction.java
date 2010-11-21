package com.arexis.mugen.webapp.action.project;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.project.projectmanager.ProjectDTO;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetProjectUserAction extends MugenAction {
    
    public GetProjectUserAction() {}
    
    public String getName() {
        return "GetProjectUserAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            HttpSession session = request.getSession();
            String tmpId = request.getParameter("id");
            
            int id = 0;
            if(exists(tmpId)) {
                id = Integer.parseInt(tmpId);
                session.setAttribute("viewuser", new Integer(id).toString());
            } else {
                id = Integer.parseInt((String)session.getAttribute("viewuser"));
            }
            
            //Collection roles = projectManager.getRolesByProject(caller.getPid(), caller);
            //request.setAttribute("roles", roles);   
            
            if(exists(request.getParameter("workflow")) && request.getParameter("workflow").compareTo("AdminViewUser")==0){
                ProjectDTO project = projectManager.getProject(caller.getPid(), caller);
                request.setAttribute("project", project);
            }
            
            request.setAttribute("user", projectManager.getUser(id, caller));
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }    
}
