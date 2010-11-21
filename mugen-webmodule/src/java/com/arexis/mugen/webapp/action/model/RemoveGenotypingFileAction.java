/*
 * RemoveGenotypingFileAction.java
 *
 * Created on December 15, 2005, 10:56 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.model;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author lami
 */
public class RemoveGenotypingFileAction extends MugenAction {
    
    /** Creates a new instance of RemoveGenotypingFileAction */
    public RemoveGenotypingFileAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "RemoveGenotypingFileAction";
    }
    
    /**
     * Performs this action
     * @param request The http request object
     * @param context The servlet context
     * @return True if this action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");  
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.EXPMODEL, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    request);             

            String eid = formDataManager.getValue("eid");
  
            modelManager.removeGenotypingFile(Integer.parseInt(eid), caller);    
            GetModelAction action = new GetModelAction();
            action.performAction(request, context);            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }    
}
