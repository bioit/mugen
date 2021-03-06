/*
 * RemoveProjectResourceAction.java
 *
 * Created on January 20, 2006, 10:13 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.model;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.webapp.action.MugenAction;

/**
 *
 * @author lami
 */
public class RemoveModelResourceAction extends MugenAction {
    
    /**
     * Creates a new instance of RemoveProjectResourceAction 
     */
    public RemoveModelResourceAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "RemoveModelResourceAction";
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
            
            String resourceId = request.getParameter("resourceId");
            resourceManager.removeResource(Integer.parseInt(resourceId), caller);
            
           FormDataManager formDataManager = getFormDataManager(MugenFormDataManagerFactory.EXPMODEL, MugenFormDataManagerFactory.WEB_FORM, request); 
           String eid = formDataManager.getValue("eid");
                
            projectManager.log("user "+caller.getName()+" removed resource "+resourceId+" from model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ApplicationException)
                throw new ApplicationException(e.getMessage());                
        }
        
        return false;
    }     
}
