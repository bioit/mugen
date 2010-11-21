package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.Navigator;
import com.arexis.arxframe.PageManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.form.FormDataManager;

public class ModelsPostAction extends MugenAction {
    
    public ModelsPostAction() {}

    public String getName() {
        return "ModelsPostAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");
            String suid = req.getParameter("suid");
            
            if(exists(suid))
                caller.setSuid(Integer.parseInt(suid));
            
            if(isSubmit(req, "reset")) {                
                resetFormData(MugenFormDataManagerFactory.EXPMODELS, req);
            }
            else{
                collectFormData(MugenFormDataManagerFactory.EXPMODELS, MugenFormDataManagerFactory.WEB_FORM, req);
                //once all data is collected get the FDM again
                FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.EXPMODELS, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);
                
                if(isSubmit(req, "byID"))
                    formDataManager.put("ordertype", "MMMDb ID");
                
                if(isSubmit(req, "byNAME"))
                    formDataManager.put("ordertype", "LINE NAME");
                
                if(isSubmit(req, "byDATE"))
                    formDataManager.put("ordertype", "DATE");
            }
                    
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed in models post action.");
        }
    }      
}
