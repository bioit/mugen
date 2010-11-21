package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class AddRepositoryAction extends MugenAction {
    
    public String getName() {
        return "AddRepositoryAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            Workflow wf = (Workflow)request.getAttribute("workflow");
            
            //if repository creation was triggered by av.gen.back. assignment from model view
            //we need the following if statement+the passed attribute.
            if (request.getParameter("eid")!=null){
                wf.setAttribute("eid", request.getParameter("eid"));
            }
            
            String reponame = request.getParameter("reponame");
            String repourl = request.getParameter("repourl");
            int rid = modelManager.addRepository(reponame, repourl, caller);
            
            projectManager.log("user "+caller.getName()+" created repository "+rid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("AddRepositoryAction Failed to perform action", e);
        }
    }
}
