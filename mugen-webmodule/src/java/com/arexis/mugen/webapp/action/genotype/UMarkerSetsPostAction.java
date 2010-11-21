/*
 * UMarkerSetsPostAction.java
 *
 * Created on November 28, 2005, 11:26 AM
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
public class UMarkerSetsPostAction extends MugenAction {
    
    /** Creates a new instance of UMarkerSetsPostAction */
    public UMarkerSetsPostAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "UMarkerSetsPostAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ActionException { 
        try {          
            if(isSubmit(req, "reset"))
                resetFormData(MugenFormDataManagerFactory.U_MARKER_SET_FILTER, req);
            else
                collectFormData(MugenFormDataManagerFactory.U_MARKER_SET_FILTER, MugenFormDataManagerFactory.WEB_FORM, req);            
            
            
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");            
            String tmpSid = req.getParameter("v.sid");
            if(exists(tmpSid))
                caller.setSid(Integer.parseInt(tmpSid));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActionException("Error in post action for unified marker sets.");
        }
    }   
}
