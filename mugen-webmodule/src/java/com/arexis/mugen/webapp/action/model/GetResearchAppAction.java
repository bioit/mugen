/*
 * GetResearchAppAction.java
 *
 * Created on February 23, 2006, 9:52 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.modelmanager.ResearchAppDTO;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author heto
 */
public class GetResearchAppAction extends MugenAction {
    
    public String getName() {
        return "GetResearchAppAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            Workflow wf = (Workflow)request.getAttribute("workflow");
            
            int raid = 0;
            String tmp = request.getParameter("raid");
            if (tmp==null || tmp.equals(""))
            {
                tmp = wf.getAttribute("raid");    
            }
            else
            {
                raid = new Integer(tmp).intValue();
                wf.setAttribute("raid", tmp);
            }
            
            ResearchAppDTO rapp = modelManager.getResearchApplication(raid, caller);
            request.setAttribute("rapp", rapp);
            request.setAttribute("projectId", new Integer(caller.getPid()).toString());
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Failed to perform action", e);
        }
    }
}
