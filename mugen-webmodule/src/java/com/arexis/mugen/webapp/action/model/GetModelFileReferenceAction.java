/*
 * GetModelFileReferenceAction.java
 *
 * Created on December 19, 2005, 1:17 PM
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
public class GetModelFileReferenceAction extends MugenAction {
    
    /** Creates a new instance of GetModelFileReferenceAction */
    public GetModelFileReferenceAction() {
    }


    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "GetModelFileReferenceAction";
    }
    
    /**
     * Performs this action
     * @param request The http request object
     * @param context The servlet context
     * @return True if this action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) {
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");            
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.EXPMODEL, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req); 
            
            String tmpFileid = req.getParameter("fileid");            
            if(exists(tmpFileid))
                formDataManager.put("fileid", tmpFileid);
            
            req.setAttribute("filedto", resourceManager.getFile(Integer.parseInt(formDataManager.getValue("fileid")), caller));
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }       
}
