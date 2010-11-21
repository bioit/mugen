/*
 * RemoveSpeciesPreAction.java
 *
 * Created on January 17, 2006, 8:39 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.admin;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author heto
 */
public class RemoveSpeciesPreAction extends MugenAction {
    
    public String getName() {
        return "RemoveSpeciesPreAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            Workflow wf = (Workflow)request.getAttribute("workflow");
            String sid = request.getParameter("sid");
            wf.setAttribute("sid", sid);
            
            request.setAttribute("description", "Are you sure you want to delete this species?");
            
            return true;
        } catch (Exception e) {
            throw new ApplicationException("Failed to perform action", e);
        }
    }
}
