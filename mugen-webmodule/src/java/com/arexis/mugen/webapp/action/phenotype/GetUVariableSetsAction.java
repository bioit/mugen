/*
 * GetUVariableSetsAction.java
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
import com.arexis.mugen.webapp.action.*;


/**
 * MugenAction class for handling the retrieval of unified variable sets
 * @author lami
 */
public class GetUVariableSetsAction extends MugenAction{
    
    /** Creates a new instance of GetVariableSetsAction */
    public GetUVariableSetsAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetUVariableSetsAction";
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
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.U_VARIABLE_SET_FILTER, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);            
               
            nav.getPageManager().setMax(phenotypeManager.getNumberOfUVariableSets(caller, formDataManager));

            Collection variablesets = phenotypeManager.getUVariableSets(nav.getPageManager(), caller, formDataManager);                       
            
            req.setAttribute("species", samplingUnitManager.getSpeciesForProject(caller.getPid(), caller));
            req.setAttribute("formdata", formDataManager);       
            req.setAttribute("uvariablesetsdto", variablesets);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve unified variable sets.");
        }
    }   
}
