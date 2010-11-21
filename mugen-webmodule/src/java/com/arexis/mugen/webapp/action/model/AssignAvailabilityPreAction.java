package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class AssignAvailabilityPreAction extends MugenAction {
    
    public String getName() {
        return "AssignAvailabilityPreAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            int pid = caller.getPid();
            
            Collection repositories = modelManager.getRepositoriesByProject(pid);
            request.setAttribute("repositories", repositories);
            
            Collection avgenbacks = modelManager.getAvailableGeneticBackgroundsByProject(pid);
            request.setAttribute("avgenbacks", avgenbacks);
            
            request.setAttribute("states", modelManager.getStrainStates(caller));
            request.setAttribute("types", modelManager.getStrainTypes(caller));
            
            Workflow wf = (Workflow)request.getAttribute("workflow");
            
            String eid = "";
            
            if (request.getParameter("eid") == null){
                eid = wf.getAttribute("eid");
            }
            else{
                eid = request.getParameter("eid");
            }
            
            
            
            wf.setAttribute("eid", eid);
            request.setAttribute("eid", eid);

            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("AssignAvailabilityPreAction failed to perform action", e);
        }
    }
}
