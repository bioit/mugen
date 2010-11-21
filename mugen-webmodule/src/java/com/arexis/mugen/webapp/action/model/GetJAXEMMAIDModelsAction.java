package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.Navigator;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class GetJAXEMMAIDModelsAction extends MugenAction {
    
    private static Logger logger = Logger.getLogger(GetModelsAction.class);
    
    public GetJAXEMMAIDModelsAction() {
    }

    /**
     * Returns the name of this action
     * @return The action name
     */
    public String getName() {
        return "GetJAXEMMAIDModelsAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");            
            Navigator nav = (Navigator)se.getAttribute("navigator");
            
            req.setAttribute("modelsdto", modelManager.getExperimentalModelsWithJAXEMMAID(caller.getSuid(), caller));
            
            return true;
        } catch (ApplicationException e) {
            logger.error("Action failed", e);
            throw e;
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("GetJAXEMMAIDModelsAction Action failed", e);
            throw new ApplicationException("Could not retrieve models.");
        }
    }     
}
