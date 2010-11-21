/*
 * AssignGeneAction.java
 *
 * Created on January 31, 2006, 9:49 AM
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
public class AssignGeneAction extends MugenAction {
    
    public String getName() {
        return "AssignGeneAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            Workflow wf = (Workflow)request.getAttribute("workflow");
            
            if (request.getParameter("gene")!=null){
                int gaid = new Integer(request.getParameter("gene")).intValue();
                int eid = new Integer(wf.getAttribute("eid")).intValue();
            
                modelManager.addGeneToModel(gaid, eid, caller);
                
                projectManager.log("user "+caller.getName()+" added gene "+gaid+" to model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            }
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Failed to perform action", e);
        }
    }
}
