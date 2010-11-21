/*
 * UnAssignGeneAction.java
 *
 * Created on January 31, 2006, 10:08 AM
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
 * @author heto
 */
public class UnAssignGeneAction extends MugenAction {
    
    public String getName() {
        return "UnAssignGeneAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            
            int gaid = new Integer(request.getParameter("gaid")).intValue();
            int eid = new Integer(request.getParameter("eid")).intValue();
            
            modelManager.removeGeneFromModel(gaid, eid, caller);
            
            projectManager.log("user "+caller.getName()+" removed gene "+gaid+" from model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            /*if(request.getParameter("istrans")!=null && new Integer(request.getParameter("istrans")).intValue()==1){
                modelManager.removeGeneFromStrainAlleles(gaid, eid, caller);
            }*/
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Failed to perform action", e);
        }
    }
}
