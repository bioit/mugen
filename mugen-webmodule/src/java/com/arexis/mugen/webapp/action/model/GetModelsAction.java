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

public class GetModelsAction extends MugenAction {
    
    private static Logger logger = Logger.getLogger(GetModelsAction.class);
    
    public GetModelsAction() {}

    public String getName() {
        return "GetModelsAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller _caller = (MugenCaller)se.getAttribute("caller");
            Navigator nav = (Navigator)se.getAttribute("navigator");
            PageManager pgm = nav.getPageManager();
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.EXPMODELS, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);
            
            FormDataManager formDataManagerNav = getFormDataManager(
                    MugenFormDataManagerFactory.NAV_PARAM, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);
            
            if(se.getAttribute("formDataManagerNav")!=null){
                formDataManagerNav = (FormDataManager) se.getAttribute("formDataManagerNav");
            }
            
            if (req.getParameter("_raid")!=null
                     || req.getParameter("_gaid")!=null
                     || req.getParameter("_fstid")!=null)
            {
                formDataManager.reset();
                logger.debug("reset form data manager");
            }   
                
            
            String tmp = req.getParameter("_raid");
            if (tmp!=null)
                formDataManager.put("raid", tmp);
            
            tmp = req.getParameter("_gaid");
            if (tmp!=null)
                formDataManager.put("gaid", tmp);
            
            tmp = req.getParameter("_fstid");
            if (tmp!=null)
                formDataManager.put("fstid", tmp);
            
            req.setAttribute("formdata", formDataManager);
            
            pgm.setMax(modelManager.getExperimentalModelsByForm(formDataManager, _caller));
            
            pgm.setDelta(new Integer(formDataManager.getValue("delta")).intValue());
            
            if (req.getParameter("last") == null && req.getParameter("first") == null && req.getParameter("prev") == null && req.getParameter("next") == null && req.getParameter("page") == null){
                pgm.setStart(new Integer(formDataManagerNav.getValue("start")).intValue());
                nav.setPagemanager(pgm);
                req.setAttribute("navigator", nav);
            } else {
                formDataManagerNav.put("start", new Integer(pgm.getStart()).toString());
                se.setAttribute("formDataManagerNav", formDataManagerNav);
            }
            
            Collection models = modelManager.getExperimentalModelsByPGM(pgm);
            
            req.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(_caller.getPid(), _caller));
            req.setAttribute("modelsdto", models);
            
            req.setAttribute("participants", modelManager.getParticipants(_caller));
            req.setAttribute("researchers", modelManager.getParticipantNames(_caller));
            req.setAttribute("mutations", modelManager.getMutationTypes(_caller.getPid(), _caller));
            req.setAttribute("mpterms", modelManager.endMPs());
            
            req.setAttribute("rapps", modelManager.getResearchApplications(_caller));
            req.setAttribute("funcsigtypes", modelManager.getFunctionalSignificanceTypes(_caller));
            req.setAttribute("genes", modelManager.getGenesByProject(_caller.getPid(), _caller, false));
            
            req.setAttribute("sortby", modelManager.getOrderByTypes());
            req.setAttribute("disseminationlevels", modelManager.getLevelsForModel());
            
            req.setAttribute("deltas", modelManager.getDeltas());
            
            req.setAttribute("samplingUnit", new Integer(_caller.getSuid()).toString());
            
            return true;
        } catch (ApplicationException e) {
            logger.error("Action failed", e);
            throw e;
        } catch (Exception e) {
            logger.error("GetModelsAction failed", e);
            throw new ApplicationException("Could not retrieve models.");
        }
    }     
}
