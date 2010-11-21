/*
 * SaveLinkReferenceAction.java
 *
 * Created on December 19, 2005, 11:11 AM
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
public class SaveLinkReferenceAction extends MugenAction {
    
    /** Creates a new instance of SaveLinkReferenceAction */
    public SaveLinkReferenceAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SaveLinkReferenceAction";
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
            
            String eid = formDataManager.getValue("eid");
            
            if (isSubmit(request, "create")) {
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");
                String url = request.getParameter("url");
                
                int refid = modelManager.addLinkReference(Integer.parseInt(eid), name, comm, url, caller);
                
                projectManager.log("user "+caller.getName()+" added reference link "+refid+" to model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
                
            } else if (isSubmit(request, "save")) {
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");
                String url = request.getParameter("url");                

                resourceManager.updateLink(Integer.parseInt(formDataManager.getValue("linkid")), name, url, comm, caller);
                modelManager.updateModelTimestamp(Integer.parseInt(eid), caller);
                
                projectManager.log("user "+caller.getName()+" updated reference link "+Integer.parseInt(formDataManager.getValue("linkid"))+" from model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            } 

            return true;
        } catch (Exception e) {
            if(e instanceof ApplicationException)
                throw new ApplicationException(e.getMessage());
            else
                e.printStackTrace();
        }
        
        return false;
    }       
}
