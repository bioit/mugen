package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.modelmanager.GeneDTO;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetGenBackValue extends MugenAction {
    
    public String getName() {
        return "GetGenBackValue";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = request.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");
            Workflow wf = (Workflow)request.getAttribute("workflow");
            
            int bid = new Integer(request.getParameter("bid")).intValue();
            
            request.setAttribute("genback", modelManager.getGeneBackValueName(bid,caller));
            request.setAttribute("models", modelManager.getExperimentalModelsByGenBackValue(bid, caller));
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("GetGenBackValue Failed to perform action", e);
        }
    }
}
