package com.arexis.mugen.webapp.action.search;

import com.arexis.arxframe.Action;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.modelmanager.ModelManagerRemote;
import com.arexis.mugen.servicelocator.ServiceLocator;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import java.util.TreeSet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenFormDataManagerFactory;
import javax.servlet.http.HttpSession;

public class SearchKeywordFastAction extends MugenAction {
    
    public String getName() {
        return "SearchKeywordFastAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = request.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");
            ServiceLocator locator = ServiceLocator.getInstance();
            ModelManagerRemote modelManager = (ModelManagerRemote)locator.getManager(ServiceLocator.Services.MODELMANAGER);
            
            collectFormData(MugenFormDataManagerFactory.FAST_SEARCH, MugenFormDataManagerFactory.WEB_FORM, request);
            
            FormDataManager formDataManager = getFormDataManager(MugenFormDataManagerFactory.FAST_SEARCH, MugenFormDataManagerFactory.WEB_FORM, request);
            
            String tmp = formDataManager.getValue("fast_search_key");
            
            String [] filter = request.getParameterValues("filter");
            
            if(filter != null || exists(request.getParameter("ctrl"))){
                se.setAttribute("quicksearchfilter", filter);
            } else if(!exists(request.getParameter("ctrl"))){
                filter = (String [])se.getAttribute("quicksearchfilter");
            }
            
            Collection results = modelManager.searchByKeywordFast(tmp, filter, caller);
            request.setAttribute("results", results);
            request.setAttribute("hits", new Integer(results.size()).toString());
            
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("SearchKeywordFastAction Failed to perform action", e);
        }
    }
}
