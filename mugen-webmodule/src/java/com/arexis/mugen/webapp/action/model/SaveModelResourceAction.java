/*
 * SaveModelResourceAction.java
 *
 * Created on January 20, 2006, 8:36 AM
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
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.webapp.action.MugenAction;

/**
 *
 * @author lami
 */
public class SaveModelResourceAction extends MugenAction {
    
    /**
     * Creates a new instance of SaveModelResourceAction
     */
    public SaveModelResourceAction() {
    }

    
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "SaveModelResourceAction";
    }
    
    /**
     * Performs this action
     * @param request The http request object
     * @param context The servlet context
     * @return True of this action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            String resourceId = request.getParameter("resourceId");
            String name = request.getParameter("name");
            String comm = request.getParameter("comm");
            String url = request.getParameter("url");
            int catid = new Integer(request.getParameter("catid")).intValue();
            
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            resourceManager.updateResource(Integer.parseInt(resourceId), name, comm, url, catid, caller);
            
            FormDataManager formDataManager = getFormDataManager(MugenFormDataManagerFactory.EXPMODEL, MugenFormDataManagerFactory.WEB_FORM, request); 
            String eid = formDataManager.getValue("eid");
                
            projectManager.log("user "+caller.getName()+" updated resource "+resourceId+" on model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            
            request.setAttribute("id", request.getParameter("id"));
           
            return true;
        } catch (ApplicationException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to update resource information.",e);
        }
    }    
}
