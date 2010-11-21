/*
 * GetSimpleModelAction.java
 *
 * Created on December 20, 2005, 1:25 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.model;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lami
 */
public class GetSimpleModelAction extends MugenAction {
    
    /** Creates a new instance of GetSimpleModelAction */
    public GetSimpleModelAction() {
    }

    /**
     * Returns the name of this action
     * @return The action name
     */
    public String getName() {
        return "GetSimpleModelAction";
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
            
            String tmpEid = req.getParameter("eid");
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.EXPMODEL, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);  
                    
            
            if(exists(tmpEid)) {
                collectFormData(MugenFormDataManagerFactory.EXPMODEL, MugenFormDataManagerFactory.WEB_FORM, req);                                                                                   
                if(exists(formDataManager.getValue("eid"))) {                   
                    if(!tmpEid.equals(formDataManager.getValue("eid"))) {
                        resetFormData(MugenFormDataManagerFactory.EXPMODEL, req);
                    }              
                }
                
                formDataManager.put("eid", tmpEid);                                              
            }
            
            int eid = Integer.parseInt(formDataManager.getValue("eid")); 
            
            req.setAttribute("modeldto", modelManager.getExperimentalModel(eid, caller, "superscript"));     

            // These are used in the model details section
            //req.setAttribute("users", projectManager.getProjectUsers(caller.getPid(), caller));    
            req.setAttribute("users", projectManager.getRestrictedProjectUsers(caller.getPid(), caller));    
            req.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller));              
            req.setAttribute("researchApps", modelManager.getResearchApplications(caller)); 
            req.setAttribute("levels", modelManager.getLevelsForModel());
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve model.", e);
        }
    }     
}
