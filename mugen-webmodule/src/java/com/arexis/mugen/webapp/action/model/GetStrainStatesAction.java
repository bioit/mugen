package com.arexis.mugen.webapp.action.model;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetStrainStatesAction extends MugenAction
{

    public GetStrainStatesAction() {}

    public String getName() {
        return "GetStrainStatesAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");                                                              
            
            req.setAttribute("strainstates", modelManager.getStrainStates(caller));
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve strain states.", e);
        }
    }     
}
