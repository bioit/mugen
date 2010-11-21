package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class AssignAvailabilityAction extends MugenAction {
    
    public String getName() {
        return "AssignAvailabilityAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            Workflow wf = (Workflow)request.getAttribute("workflow");
            
            int eid = new Integer(wf.getAttribute("eid")).intValue();
            int rid = new Integer(request.getParameter("repositories")).intValue();
            int aid = new Integer(request.getParameter("avgenbacks")).intValue();
            int stateid = new Integer(request.getParameter("state")).intValue();
            int typeid = new Integer(request.getParameter("type")).intValue();
                 
            modelManager.addAvailabilityToModel(eid, rid, aid, stateid, typeid);
            
            projectManager.log("user "+caller.getName()+" added availability to model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Availability information already exists. Please press return to enter different availability information.");
        }
    }
}
