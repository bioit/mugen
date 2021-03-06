/*
 * SaveGeneOntologyAction.java
 *
 * Created on December 15, 2005, 5:14 PM
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
public class SaveGeneOntologyAction extends MugenAction {
    
    /** Creates a new instance of SaveGeneOntologyAction */
    public SaveGeneOntologyAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SaveGeneOntologyAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = request.getSession();
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");                        
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.EXPMODEL, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    request);             
            
            String goid = formDataManager.getValue("goid");
            String gmid = formDataManager.getValue("gmid");


            String eid = formDataManager.getValue("eid");
            String name = request.getParameter("name");
            String comm = request.getParameter("comm");             
            String linkid = request.getParameter("linkid"); 
            if (isSubmit(request, "create")) {   
                modelManager.addGeneOntology(Integer.parseInt(gmid), linkid, name, comm, caller);
            } else if (isSubmit(request, "save")) {            
                modelManager.updateGeneOntology(Integer.parseInt(goid), linkid, name, comm, caller);
            } 

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ApplicationException)
                throw new ApplicationException(e.getMessage());                
        }
        
        return false;
    }       
}
