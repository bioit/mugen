/*
 * UMarkerSetPositionPostAction.java
 *
 * Created on November 28, 2005, 1:25 PM
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author lami
 */
public class UMarkerSetPositionPostAction extends MugenAction {
    
    /** Creates a new instance of UMarkerSetPositionPostAction */
    public UMarkerSetPositionPostAction() {
    }
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "UMarkerSetPositionPostAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ActionException {
        try {
            HttpSession session = request.getSession();
            
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            if(isSubmit(request, "reset"))
                resetFormData(MugenFormDataManagerFactory.U_MARKER_SET_POSITION_FILTER, request);
            else
                collectFormData(MugenFormDataManagerFactory.U_MARKER_SET_POSITION_FILTER, MugenFormDataManagerFactory.WEB_FORM, request);            
            
            String tmpSid = request.getParameter("sid");
            if(tmpSid != null)
                caller.setSid(Integer.parseInt(tmpSid));
                
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActionException("Error in post action for unified marker set positions.");
        }
    }   
}
