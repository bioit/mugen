package com.arexis.mugen.webapp.action.model;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.form.FormDataManager;

public class StrainsPostAction extends MugenAction {
    
    public StrainsPostAction() {
    }

    /**
     * Returns the name of this action
     * @return The action name
     */
    public String getName() {
        return "StrainsPostAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True of this action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");            
            //String suid = req.getParameter("suid");
            /*if(exists(suid))
                caller.setSuid(Integer.parseInt(suid));
            */
            if(isSubmit(req, "reset")) {                
                resetFormData(MugenFormDataManagerFactory.STRAINS, req);
            }
            else{
                collectFormData(MugenFormDataManagerFactory.STRAINS, MugenFormDataManagerFactory.WEB_FORM, req);
                //once all data is collected get the FDM again
                FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.STRAINS, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);
                
                if(isSubmit(req, "byEMMA"))
                    formDataManager.put("otherid", "EMMA");
                
                if(isSubmit(req, "byMGI"))
                    formDataManager.put("otherid", "MGI");
            }
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("StrainsPostAction Failed.");
        }
    }      
}
