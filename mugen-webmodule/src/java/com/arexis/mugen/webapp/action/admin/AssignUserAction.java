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
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 *
 * @author heto
 */
public class AssignUserAction extends MugenAction {
    
    /** Creates a new instance of AssignUserPreAction */
    public AssignUserAction() {
    }
    
    public String getName() {
        return "AssignUserAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ActionException {
        try {
            HttpSession session = request.getSession();
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            
            Workflow w = (Workflow)request.getAttribute("workflow");
            String pid = w.getAttribute("pid");
            
            String rid = request.getParameter("role");
            
            String id = null;
            if (request.getParameter("user")!=null)
                id = request.getParameter("user");
            if (w.getAttribute("id")!=null)
                id = w.getAttribute("id");
            
            projectManager.assignUserToProject(new Integer(id).intValue(), 
                    new Integer(rid).intValue(),
                    new Integer(pid).intValue(), caller);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Failed to assign user", e);
        }
    }
}
