package com.arexis.mugen.webapp.action.model;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CreateModelAction extends MugenAction {
    
    public CreateModelAction() { }

    public String getName() {
        return "CreateModelAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");                                                              
                       
            req.setAttribute("users", projectManager.getRestrictedProjectUsers(caller.getPid(), caller));
            req.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller));              
            req.setAttribute("researchApps", modelManager.getResearchApplications(caller));
            req.setAttribute("userid", new Integer(caller.getId()).toString());
            req.setAttribute("desired_levels", modelManager.getLevelsForModel());
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve users.");
        }
    }      
}
