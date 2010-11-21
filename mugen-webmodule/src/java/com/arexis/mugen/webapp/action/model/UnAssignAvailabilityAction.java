/*
 * UnAssignAvailabilityAction.java
 *
 * Created on July 20, 2006, 10:08 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.model;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author zouberakis
 */
public class UnAssignAvailabilityAction extends MugenAction {
    
    public String getName() {
        return "UnAssignAvailabilityAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            
            int eid = new Integer(request.getParameter("eid")).intValue();
            int rid = new Integer(request.getParameter("rid")).intValue();
            int aid = new Integer(request.getParameter("aid")).intValue();
            int stateid = new Integer(request.getParameter("stateid")).intValue();
            int typeid = new Integer(request.getParameter("typeid")).intValue();
            
            modelManager.removeAvailabilityFromModel(eid, rid, aid, stateid, typeid, caller);
            
            projectManager.log("user "+caller.getName()+" removed availability from model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("UnAssignAvailabilityAction failed to perform action", e);
        }
    }
}
