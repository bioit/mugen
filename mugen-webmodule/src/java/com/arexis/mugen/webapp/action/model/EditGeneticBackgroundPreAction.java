package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class EditGeneticBackgroundPreAction extends MugenAction {
    
    public EditGeneticBackgroundPreAction() {}

    public String getName() {
        return "EditGeneticBackgroundPreAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            Workflow wf = (Workflow)req.getAttribute("workflow");
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");
            Collection genbackValues = modelManager.getGeneticBackgroundsByProject(caller.getPid(), caller);
            
            int eid = 0;
            
            if(req.getParameter("eid")!=null){
                eid = new Integer(req.getParameter("eid")).intValue();
            }
            else {
                eid = new Integer(wf.getAttribute("eid")).intValue();
            }

            req.setAttribute("genBacks", genbackValues);
            req.setAttribute("modeldto", modelManager.getExperimentalModel(eid, caller));        
            req.setAttribute("genbackdto", modelManager.getGeneticBackgroundDTO(eid,caller));
            req.setAttribute("backcrosseCollection", modelManager.getBackcrossesCollection());
            
            //wf.setAttribute("eid",req.getParameter("eid"));
            wf.setAttribute("eid", new Integer(eid).toString());
            req.setAttribute("eid", new Integer(eid).toString());
                
            return true;
        } catch (ApplicationException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("EditGeneticBackgroundPreAction Failed to perform action.");
        }
    }     
}
