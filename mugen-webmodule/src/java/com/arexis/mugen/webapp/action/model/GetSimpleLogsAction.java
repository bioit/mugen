package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.Navigator;
import com.arexis.arxframe.PageManager;
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

public class GetSimpleLogsAction extends MugenAction {
    
    private static Logger logger = Logger.getLogger(GetModelsAction.class);
    
    public GetSimpleLogsAction() {}

    public String getName() {
        return "GetSimpleLogsAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");            
            Navigator nav = (Navigator)se.getAttribute("navigator");
            PageManager pgm = nav.getPageManager();
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.SIMPLELOGS, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);
            
            FormDataManager formDataManagerNavLogs = getFormDataManager(
                    MugenFormDataManagerFactory.NAV_PARAM_LOGS, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);
            
            if(se.getAttribute("formDataManagerNavLogs")!=null){
                formDataManagerNavLogs = (FormDataManager) se.getAttribute("formDataManagerNavLogs");
            }
            
            pgm.setMax(modelManager.getMaxSimpleLogs(formDataManager, caller));
            
            if (req.getParameter("last") == null && req.getParameter("first") == null && req.getParameter("prev") == null && req.getParameter("next") == null && req.getParameter("page")==null){
                pgm.setStart(new Integer(formDataManagerNavLogs.getValue("start")).intValue());
                nav.setPagemanager(pgm);
                req.setAttribute("navigator", nav);
            } else {
                formDataManagerNavLogs.put("start", new Integer(pgm.getStart()).toString());
                se.setAttribute("formDataManagerNavLogs", formDataManagerNavLogs);
            }
            
            Collection simplelogs = modelManager.getSimpleLogsByFDM(formDataManager, caller, pgm);
            
            req.setAttribute("logsdto", simplelogs);
            req.setAttribute("mgnactions", modelManager.getDistinctSimpleLogActions());
            req.setAttribute("mgnusers", modelManager.getDistinctSimpleLogUsers());
            req.setAttribute("formdata", formDataManager);
            
            return true;
        } catch (ApplicationException e) {
            logger.error("Action failed", e);
            throw e;
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("Action failed", e);
            throw new ApplicationException("GetSimpleLogsAction Failed.");
        }
    }     
}
