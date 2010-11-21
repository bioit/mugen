package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class CreateGeneticBackgroundPreAction extends MugenAction {
    
    public String getName() {
        return "CreateGeneticBackgroundPreAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            Workflow wf = (Workflow)request.getAttribute("workflow");
            Collection genbackValues = modelManager.getGeneticBackgroundsByProject(caller.getPid(), caller);
            request.setAttribute("genBacks", genbackValues);
            request.setAttribute("backcrosseCollection", modelManager.getBackcrossesCollection());
            
            //if gen. back. returns from gen. back. value creation there will be no parameter...
            String eid = "";
            if(request.getParameter("eid")!=null){
                eid = request.getParameter("eid");
            }
            else {
                //...but a workflow attribute instead.
                eid = wf.getAttribute("eid");
            }
            
            wf.setAttribute("eid", eid);
            request.setAttribute("eid", eid);

            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("CreateGeneticBackgroundPreAction Failed to perform action", e);
        }
    }
}
