package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class EditGeneticBackgroundValuePreAction extends MugenAction {
    
    public String getName() {
        return "EditGeneticBackgroundValuePreAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            
            if (request.getParameter("bid")!=null)
            {
                int bid = new Integer(request.getParameter("bid")).intValue();
                String backname = modelManager.getGeneBackValueName(bid,caller);
                request.setAttribute("backname", backname);
                request.setAttribute("bid", new Integer(bid).toString());
                
                Workflow wf = (Workflow)request.getAttribute("workflow");
                wf.setAttribute("bid", new Integer(bid).toString());
            }
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("EditGeneticBackgroundValuePreAction Failed to perform action", e);
        }
    }
}
