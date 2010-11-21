package com.arexis.mugen.webapp.action.admin;

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

public class GetListDissUpdateAction extends MugenAction {
    
    private static Logger logger = Logger.getLogger(GetListDissUpdateAction.class);
    
    public GetListDissUpdateAction() {}

    public String getName() {
        return "GetListDissUpdateAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");            
            Navigator nav = (Navigator)se.getAttribute("navigator");
            PageManager pgm = nav.getPageManager();
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.EXPMODELS, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);
            
            FormDataManager formDataManagerNavDiss = getFormDataManager(
                    MugenFormDataManagerFactory.NAV_PARAM_DISS, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);
            
            if(se.getAttribute("formDataManagerNavDiss")!=null){
                formDataManagerNavDiss = (FormDataManager) se.getAttribute("formDataManagerNavDiss");
            }
            
            logger.debug("PerformAction");
            
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
            
            pgm.setMax(modelManager.getExperimentalModelsByFormForDissUpdateNoDelta(formDataManager, caller));
            
            if (req.getParameter("last") == null && req.getParameter("first") == null && req.getParameter("prev") == null && req.getParameter("next") == null && req.getParameter("page")==null){
                pgm.setStart(new Integer(formDataManagerNavDiss.getValue("start")).intValue());
                nav.setPagemanager(pgm);
                req.setAttribute("navigator", nav);
            } else {
                formDataManagerNavDiss.put("start", new Integer(pgm.getStart()).toString());
                se.setAttribute("formDataManagerNavDiss", formDataManagerNavDiss);
            }
            
            Collection models = modelManager.getExperimentalModelsByFormForDissUpdate(pgm);
            
            req.setAttribute("allMice", new Integer(nav.getPageManager().getMax()).toString());
            
            req.setAttribute("someMice", new Integer(nav.getPageManager().getViewed()));
            
            req.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller));              
            req.setAttribute("modelsdto", models);
            
            req.setAttribute("participants", modelManager.getParticipants(caller));
            req.setAttribute("participantnames", modelManager.getParticipantNames(caller));
            req.setAttribute("mutations", modelManager.getMutationTypes(caller.getPid(), caller));
            
            req.setAttribute("rapps", modelManager.getResearchApplications(caller));
            req.setAttribute("funcsigtypes", modelManager.getFunctionalSignificanceTypes(caller));
            req.setAttribute("genes", modelManager.getGenesByProject(caller.getPid(), caller, false));
            
            req.setAttribute("sortby", modelManager.getOrderByTypes2());
            req.setAttribute("disseminationlevels", modelManager.getLevelsForModel());
            
            req.setAttribute("samplingUnit", new Integer(caller.getSuid()).toString());
            
            return true;
        } catch (ApplicationException e) {
            logger.error("GetListDissUpdateAction failed", e);
            throw e;
        } catch (Exception e) {
            logger.error("GetListDissUpdateAction failed", e);
            throw new ApplicationException("Could not retrieve models.");
        }
    }     
}
