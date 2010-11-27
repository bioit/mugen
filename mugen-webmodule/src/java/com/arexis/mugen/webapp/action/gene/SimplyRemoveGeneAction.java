package com.arexis.mugen.webapp.action.gene;
import com.arexis.arxframe.IWorkFlowManager;
import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SimplyRemoveGeneAction extends MugenAction {
    
    public String getName() {
        return "SimplyRemoveGeneAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = request.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");
            Workflow w = (Workflow)request.getAttribute("workflow");
            
            int gaid = new Integer(request.getParameter("gaid")).intValue();
            
            modelManager.removeGene(gaid, caller);
            
            projectManager.log("user "+caller.getName()+" removed gene "+gaid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            
            if(w.getName().compareTo("RemoveGeneSimple")==0){
                IWorkFlowManager mgr = (IWorkFlowManager)se.getAttribute("WorkflowManager");
                mgr.removeMaliciousWorkflows("ViewGene");
                se.setAttribute("WorkflowManager", mgr);
            }
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("SimplyRemoveGeneAction Failed To Perform Action", e);
        }
    }
}
