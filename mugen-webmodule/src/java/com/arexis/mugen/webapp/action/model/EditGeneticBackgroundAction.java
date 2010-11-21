package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class EditGeneticBackgroundAction extends MugenAction {
    
    public String getName() {
        return "EditGeneticBackgroundAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            Workflow wf = (Workflow)request.getAttribute("workflow");
            
            //if (request.getParameter("dna_origin")!=null){
                int eid = new Integer(wf.getAttribute("eid")).intValue();
                int dna_origin = new Integer(request.getParameter("dna_origin")).intValue();
                int targeted_back = new Integer(request.getParameter("targeted_back")).intValue();
                int host_back = new Integer(request.getParameter("host_back")).intValue();
                int backcrossing_strain = new Integer(request.getParameter("backcrossing_strain")).intValue();
                String backcrosses = request.getParameter("backcrosses");
                
                modelManager.updateGeneticBackgroundForModel(eid, dna_origin, targeted_back, host_back, backcrossing_strain, backcrosses, caller);
                
                projectManager.log("user "+caller.getName()+" edited genetic background for model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            //}
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("EditGeneticBackgroundAction Failed to perform action", e);
        }
    }
}
