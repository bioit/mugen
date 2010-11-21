/*
 * GetResourceAction.java
 *
 * Created on January 20, 2006, 8:30 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.resource.resourcemanager.ResourceDTO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author lami
 */
public class GetResourceAction extends MugenAction {
    
    /** Creates a new instance of GetResourceAction */
    public GetResourceAction() {
    }
    
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetResourceAction";
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
            String id = request.getParameter("id");
            
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            ResourceDTO dto = resourceManager.getResource(Integer.parseInt(resourceId), caller);
                                    
            request.setAttribute("resource", dto);
            request.setAttribute("id", id);
            request.setAttribute("categories", resourceManager.getResourceCategories(caller.getPid(), caller));
            
            return true;
        } catch (ApplicationException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get resource information.",e);
        }
    }
}
