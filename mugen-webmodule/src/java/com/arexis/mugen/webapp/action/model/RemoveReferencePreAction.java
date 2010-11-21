package com.arexis.mugen.webapp.action.model;
import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RemoveReferencePreAction extends MugenAction {
    
    public RemoveReferencePreAction() {}
    
    public String getName() {
        return "RemoveReferencePreAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = request.getSession();
            Workflow wf = (Workflow)request.getAttribute("workflow");            
            wf.setAttribute("refid", request.getParameter("refid"));
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ApplicationException)
                throw new ApplicationException(e.getMessage());                
        }
        
        return false; 
    }
}
