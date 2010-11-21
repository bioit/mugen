package com.arexis.mugen.webapp.action.project;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetProjectUserAdminAction extends MugenAction {
    
    public GetProjectUserAdminAction() {}
    
    public String getName() {
        return "GetProjectUserAdminAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            HttpSession session = request.getSession();
            String tmpId = request.getParameter("id");
            
            int id = 0;
            
            if(exists(tmpId)) {
                id = Integer.parseInt(tmpId);
                request.setAttribute("user", projectManager.getUser(id, caller));
                session.setAttribute("viewuser", projectManager.getUser(id, caller));
            }
            
            Collection roles = projectManager.getRolesByProject(caller.getPid(), caller);
            request.setAttribute("roles", roles);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }    
}