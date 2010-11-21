package com.arexis.mugen.webapp.action.gene;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class RemoveGeneAction extends MugenAction {
    
    public String getName() {
        return "RemoveGeneAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            
            if (request.getParameter("yes")!=null)
            {            
                MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
                Workflow wf = (Workflow)request.getAttribute("workflow");
                int gaid = new Integer(wf.getAttribute("gaid")).intValue();
                modelManager.removeGene(gaid, caller);
                
                projectManager.log("user "+caller.getName()+" removed gene "+gaid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
                
            }
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("RemoveGeneAction Failed To Perform Action", e);
        }
    }
}
