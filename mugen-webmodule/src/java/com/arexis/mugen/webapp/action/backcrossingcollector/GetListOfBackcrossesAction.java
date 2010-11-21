package com.arexis.mugen.webapp.action.backcrossingcollector;

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

public class GetListOfBackcrossesAction extends MugenAction {
    
    private static Logger logger = Logger.getLogger(GetListOfBackcrossesAction.class);

    public GetListOfBackcrossesAction() {
    }

    public String getName() {
        return "GetListOfBackcrossesAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");            
            Navigator nav = (Navigator)se.getAttribute("navigator");
            
            logger.debug("Perform BackcrossingListFileAction");
            
            Collection models = modelManager.getExperimentalModelsForBackcrossingListGeneration(caller);
            
            req.setAttribute("modelsdto", models);
            req.setAttribute("disseminationlevels", modelManager.getLevelsForModel());
            
            return true;
        } catch (ApplicationException e) {
            logger.error("GetListOfBackcrossesAction failed", e);
            throw e;
        } catch (Exception e) {
            logger.error("GetListOfBackcrossesAction failed", e);
            throw new ApplicationException("Could not retrieve list of models for backcrossing info.");
        }
    }     
}
