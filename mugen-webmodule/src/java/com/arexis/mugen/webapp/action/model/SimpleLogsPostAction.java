package com.arexis.mugen.webapp.action.model;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.form.FormDataManager;

public class SimpleLogsPostAction extends MugenAction {
    
    public SimpleLogsPostAction() {
    }

    /**
     * Returns the name of this action
     * @return The action name
     */
    public String getName() {
        return "SimpleLogsPostAction";
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
            
            if(isSubmit(req, "reset")) {                
                resetFormData(MugenFormDataManagerFactory.SIMPLELOGS, req);
            }
            else{
                collectFormData(MugenFormDataManagerFactory.SIMPLELOGS, MugenFormDataManagerFactory.WEB_FORM, req);
            }
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("SimpleLogsPostAction Failed.");
        }
    }      
}
