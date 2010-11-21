package com.arexis.mugen.webapp.action.model;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ViewAvailableGeneticBackgroundsAction extends MugenAction {
    
    public String getName() {
        return "ViewAvailableGeneticBackgroundsAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = request.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");
            int pid = caller.getPid();
            Collection avgenbacks = modelManager.getAvailableGeneticBackgroundsByProject(pid);
            request.setAttribute("avgenbacks", avgenbacks);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("ViewAvailableGeneticBackgroundsAction failed to perform action", e);
        }
    }
}
