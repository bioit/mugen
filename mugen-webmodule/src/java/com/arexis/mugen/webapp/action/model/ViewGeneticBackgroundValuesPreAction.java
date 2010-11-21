package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ViewGeneticBackgroundValuesPreAction extends MugenAction {
    
    public String getName() {
        return "ViewGeneticBackgroundValuesPreAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            Collection genbackValues = modelManager.getGeneticBackgroundsByProject(caller.getPid(), caller);
            request.setAttribute("genBacks", genbackValues);
            
            Workflow wf = (Workflow)request.getAttribute("workflow");
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Failed to perform action", e);
        }
    }
}
