package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class AddUserCommentAction extends MugenAction {
    
    public String getName() {
        return "AddUserCommentAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            
            if (request.getParameter("eid")!=null){
                int eid = new Integer(request.getParameter("eid")).intValue();
                String usercomm = request.getParameter("usercomm");
                int commid = modelManager.createUserComment(eid, usercomm, caller);
                
                projectManager.log("user "+caller.getName()+" added comment "+commid+" to model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            }
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("AddUserCommentAction failed to perform action", e);
        }
    }
}
