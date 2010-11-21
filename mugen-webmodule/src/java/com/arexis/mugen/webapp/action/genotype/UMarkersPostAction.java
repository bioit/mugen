/*
 * UMarkersPostAction.java
 *
 * Created on November 28, 2005, 8:34 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;

import com.arexis.arxframe.ActionException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author lami
 */
public class UMarkersPostAction extends MugenAction {
    
    /** Creates a new instance of UMarkersPostAction */
    public UMarkersPostAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "UMarkersPostAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ActionException {        
        try {          
            if(isSubmit(req, "reset"))
                resetFormData(MugenFormDataManagerFactory.U_MARKER_FILTER, req);
            else
                collectFormData(MugenFormDataManagerFactory.U_MARKER_FILTER, MugenFormDataManagerFactory.WEB_FORM, req);            
            
            
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");            
            String tmpSid = req.getParameter("sid");
            if(exists(tmpSid))
                caller.setSid(Integer.parseInt(tmpSid));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActionException("Error in post action for unified markers.");
        }
    }       
}
