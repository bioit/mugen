/*
 * SaveFuncSigTypeAction.java
 *
 * Created on February 22, 2006, 2:18 PM
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
public class SaveFuncSigTypeAction extends MugenAction {
    
    public String getName() {
        return "SaveFuncSigTypeAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            Workflow wf = (Workflow)request.getAttribute("workflow");
            
            int fstid = new Integer(wf.getAttribute("fstid")).intValue();
            String name = request.getParameter("name");
            String comment = request.getParameter("comm");
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            
            modelManager.updateFuncSigType(fstid, name, comment, caller);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Failed to perform action", e);
        }
    }
}
