package com.arexis.mugen.webapp.action.gene;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class PreConfirmRemoveAction extends MugenAction {
    
    public PreConfirmRemoveAction() {}
    
    public String getName() {
        return "PreConfirmRemoveAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            // Do action code here:
            request.setAttribute("description", new String("Are you sure you want to remove this gene?"));
            
            Workflow wf = (Workflow)request.getAttribute("workflow");
            wf.setAttribute("gaid", request.getParameter("gaid"));
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
