/*
 * GetVariablesAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.phenotype;

import com.arexis.arxframe.Navigator;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;


/**
 * MugenAction class for handling the retrieval of variables
 * @author lami
 */
public class GetVariablesAction extends MugenAction{
    
    /** Creates a new instance of GetVariablesAction */
    public GetVariablesAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetVariablesAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");
            HttpSession se = req.getSession();
            
            if(isSubmit(req, "reset"))
                resetFormData(MugenFormDataManagerFactory.VARIABLE_FILTER, req);
            else
                collectFormData(MugenFormDataManagerFactory.VARIABLE_FILTER, MugenFormDataManagerFactory.WEB_FORM, req);                                           
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.VARIABLE_FILTER, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);                  
            

            nav.getPageManager().setMax(phenotypeManager.getNumberOfVariables(formDataManager, caller));
            Collection variables = phenotypeManager.getVariables(caller.getSuid(), nav.getPageManager(), caller, formDataManager);
            Collection samplingunits = samplingUnitManager.getSamplingUnits(caller.getPid(), caller);            
            
            // Get the variable sets
            Collection vs = phenotypeManager.getAllVariableSets(caller.getSuid(), caller);  
            
            req.setAttribute("variablesets", vs);            
            
            req.setAttribute("variablesdto", variables);
            req.setAttribute("samplingunits", samplingunits);
            req.setAttribute("formdata", formDataManager);          
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve variables.",e);
        }
    }   
}
