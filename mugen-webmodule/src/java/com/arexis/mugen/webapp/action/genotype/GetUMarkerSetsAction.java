/*
 * GetUMarkerSetsAction.java
 *
 * Created on August 2, 2005, 12:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;

import com.arexis.arxframe.Navigator;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;
import javax.servlet.http.HttpSession;

/**
 * MugenAction class for retrieving unified marker sets
 * @author lami
 */
public class GetUMarkerSetsAction extends MugenAction {
    
    /**
     * Creates a new instance of GetUMarkerSetsAction 
     */
    public GetUMarkerSetsAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "GetUMarkerSetsAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException { 
        try {             
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");   
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");
           
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.U_MARKER_SET_FILTER, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);    
            
            nav.getPageManager().setMax(genotypeManager.getNumberOfUMarkerSets(caller, formDataManager));
            Collection umarkersets = genotypeManager.getUMarkerSets(nav.getPageManager(), caller, formDataManager);         
            Collection species = samplingUnitManager.getSpeciesForProject(caller.getPid(), caller);
            
            req.setAttribute("umarkersets", umarkersets);
            req.setAttribute("species", species);       
            req.setAttribute("formdata", formDataManager);         
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve unified marker sets.");
        }
    }
}
