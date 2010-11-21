package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class RemoveUserCommentAction extends MugenAction {
    
    public String getName() {
        return "RemoveUserCommentAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            
            if (request.getParameter("commid")!=null)
            {            
                MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
                int commid = new Integer(request.getParameter("commid")).intValue();
                modelManager.deleteUserComment(commid, caller);
                
                projectManager.log("user "+caller.getName()+" removed user comment "+commid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            }
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("RemoveUserCommentAction failed To Perform Action", e);
        }
    }
}
