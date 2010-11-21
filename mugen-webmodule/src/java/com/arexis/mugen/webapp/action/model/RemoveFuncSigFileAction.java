/*
 * RemoveFuncSigFileAction.java
 *
 * Created on December 18, 2005, 3:17 PM
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
public class RemoveFuncSigFileAction extends MugenAction {
    
    /** Creates a new instance of RemoveFuncSigFileAction */
    public RemoveFuncSigFileAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "RemoveFuncSigFileAction";
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

            String fsid = formDataManager.getValue("fsid");
  
            modelManager.removeFunctionalSignificanceFile(Integer.parseInt(fsid), caller);    
            GetFuncSigAction action = new GetFuncSigAction();
            action.performAction(request, context);            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }     
}
