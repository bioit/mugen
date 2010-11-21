package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class CreateGeneFromModelAction extends MugenAction {
    
    public String getName() {
        return "CreateGeneFromModelAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            
            // Set chromosomes for UI
            Collection chromosomes = modelManager.getChromosomesForSpecies(caller.getSid(), caller);
            request.setAttribute("chromosomes", chromosomes);
            request.setAttribute("speciesname", modelManager.getSpecies(caller.getSid(), caller).getName());
            
            String eid = request.getParameter("eid");
            request.setAttribute("eid", eid);
            Workflow wf = (Workflow)request.getAttribute("workflow");
            wf.setAttribute("eid", eid);
            return true;
       
        } catch (Exception e) {
            throw new ApplicationException("Failed to perform action", e);
        }
    }
}
