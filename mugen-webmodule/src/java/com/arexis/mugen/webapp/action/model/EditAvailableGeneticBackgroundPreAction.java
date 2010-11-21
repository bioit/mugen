/*
 * EditAvailableGeneticBackgroundPreAction.java
 *
 * Created on July 20, 2006, 22:18 PM
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
import com.arexis.mugen.model.modelmanager.AvailableGeneticBackgroundDTO;


/**
 *
 * @author zouberakis
 */
public class EditAvailableGeneticBackgroundPreAction extends MugenAction {
    
    public String getName() {
        return "EditAvailableGeneticBackgroundPreAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            int aid = new Integer(request.getParameter("aid")).intValue();
            AvailableGeneticBackgroundDTO avgenback = modelManager.returnAvailableGeneticBackgroundById(aid);
            request.setAttribute("avgenback", avgenback);
            Workflow wf = (Workflow)request.getAttribute("workflow");
            wf.setAttribute("aid", new Integer(aid).toString());
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("EditAvailableGeneticBackgroundPreAction failed to perform action", e);
        }
    }
}
