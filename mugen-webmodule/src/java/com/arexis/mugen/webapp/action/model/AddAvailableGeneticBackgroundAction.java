/*
 * AddAvailableGeneticBackgroundAction.java
 *
 * Created on July, 2006, 22:18 PM
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
 * @author zouberakis
 */
public class AddAvailableGeneticBackgroundAction extends MugenAction {
    
    public String getName() {
        return "AddAvailableGeneticBackgroundAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            Workflow wf = (Workflow)request.getAttribute("workflow");
            
            //if repository creation was triggered by av.gen.back. assignment from model view
            //we need the following if statement+the passed attribute.
            if (request.getParameter("eid")!=null){
                wf.setAttribute("eid", request.getParameter("eid"));
            }
            
            String avgenbackname = request.getParameter("avgenbackname");
            int aid = modelManager.addAvailableGeneticBackground(avgenbackname, caller);
            
            projectManager.log("user "+caller.getName()+" created available genetic background "+aid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("AddAvailableGeneticBackgroundAction Failed to perform action", e);
        }
    }
}
