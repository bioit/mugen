package com.arexis.mugen.webapp.action.gene;

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

public class GetGenesAction extends MugenAction {
    
    public String getName() {
        return "GetGenesAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = request.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");
            Navigator nav = (Navigator)se.getAttribute("navigator");
            PageManager pgm = nav.getPageManager();
            
            FormDataManager formDataManagerNavGenes = getFormDataManager(
                    MugenFormDataManagerFactory.NAV_PARAM_GENES, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    request);
            
            if(se.getAttribute("formDataManagerNavGenes")!=null){
                formDataManagerNavGenes = (FormDataManager) se.getAttribute("formDataManagerNavGenes");
            }
            
            pgm.setMax(modelManager.getGenesByProject(caller.getPid(), caller, "quick"));
            
            if (request.getParameter("last") == null && request.getParameter("first") == null && request.getParameter("prev") == null && request.getParameter("next") == null && request.getParameter("page")==null){
                pgm.setStart(new Integer(formDataManagerNavGenes.getValue("start")).intValue());
                nav.setPagemanager(pgm);
                request.setAttribute("navigator", nav);
            } else {
                formDataManagerNavGenes.put("start", new Integer(pgm.getStart()).toString());
                se.setAttribute("formDataManagerNavGenes", formDataManagerNavGenes);
            }
            
            Collection genes = modelManager.getGenesByProjectForNavTag(pgm, caller);
            
            request.setAttribute("genes", genes);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("GetGenesAction failed to perform", e);
        }
    }
}
