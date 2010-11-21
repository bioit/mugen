/*
 * GetModelsForIMSRAction.java
 *
 * Created on November 6, 2006, 8:50 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.model;

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

/**
 *
 * @author zouberakis
 */
public class GetUniqueModelsForIMSRAction extends MugenAction {
    
    /** Creates a new instance of GetModelsForIMSRAction */
    public GetUniqueModelsForIMSRAction() {
    }

    /**
     * Returns the name of this action
     * @return The action name
     */
    public String getName() {
        return "GetUniqueModelsForIMSRAction";
    }
    
    /**
     * Peroforms the action
     * @param req The http request object
     * @param context The servlet context
     * @return True of this action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");            
            
            Collection models = modelManager.getUniqueExperimentalModelsForIMSR(caller.getSuid(), caller);
            
            Collection models2 = modelManager.getExperimentalModelsToIMSRTable(models, caller.getSuid());
            
            req.setAttribute("modelsdto", models2);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve models.");
        }
    }     
}
