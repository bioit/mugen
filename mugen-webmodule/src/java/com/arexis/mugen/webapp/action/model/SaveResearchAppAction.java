/*
 * SaveResearchAppAction.java
 *
 * Created on February 23, 2006, 10:05 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author heto
 */
public class SaveResearchAppAction extends MugenAction {
    
    public String getName() {
        return "SaveResearchAppAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            Workflow wf = (Workflow)request.getAttribute("workflow");
            
            String name = request.getParameter("name");
            String comm = request.getParameter("comm");
            
            if (wf.getAttribute("raid")==null)
            {
                // Create
                int raid = modelManager.createResearchApplication(name, comm, caller);
                
                projectManager.log("user "+caller.getName()+" created research application "+raid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
                
                wf.setAttribute("raid", new Integer(raid).toString());
            }
            else
            {
                // Update
                int raid = new Integer(wf.getAttribute("raid")).intValue();
                modelManager.updateResearchApplication(raid, name, comm, caller);
                
                projectManager.log("user "+caller.getName()+" updated research application "+raid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            }
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Failed to perform action", e);
        }
    }
}
