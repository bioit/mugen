/*
 * GetGeneOntologyAction.java
 *
 * Created on December 15, 2005, 4:43 PM
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
public class GetGeneOntologyAction extends MugenAction {
    
    /** Creates a new instance of GetGeneOntologyAction */
    public GetGeneOntologyAction() {
    }

    /**
     * Returns the name of this action
     * @return The action name
     */
    public String getName() {
        return "GetGeneOntologyAction";
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
            String tmpGoid = req.getParameter("goid");
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.EXPMODEL, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);                        
            
            if(exists(tmpGoid)) {
                if(exists(formDataManager.getValue("goid"))) {                   
                    if(!tmpGoid.equals(formDataManager.getValue("goid"))) {
                        resetFormData(MugenFormDataManagerFactory.EXPMODEL, req);
                    }              
                }
                
                formDataManager.put("goid", tmpGoid);
            }
            
            int goid = Integer.parseInt(formDataManager.getValue("goid"));
            collectFormData(MugenFormDataManagerFactory.EXPMODEL, MugenFormDataManagerFactory.WEB_FORM, req);                                                                                               
                 
            req.setAttribute("geneontologydto", modelManager.getGeneOntology(goid, caller));                         
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve gene ontology.");
        }
    }        
}
