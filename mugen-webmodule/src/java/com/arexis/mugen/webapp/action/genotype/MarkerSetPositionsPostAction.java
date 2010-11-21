/*
 * MarkerSetPositionsPostAction.java
 *
 * Created on November 25, 2005, 1:35 PM
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
public class MarkerSetPositionsPostAction extends MugenAction {
    
    /** Creates a new instance of MarkerSetPositionsPostAction */
    public MarkerSetPositionsPostAction() {
    }
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "MarkerSetPositionsPostAction";
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
                resetFormData(MugenFormDataManagerFactory.MARKER_SET_POSITION_FILTER, request);
            else
                collectFormData(MugenFormDataManagerFactory.MARKER_SET_POSITION_FILTER, MugenFormDataManagerFactory.WEB_FORM, request);            
            
            String tmpSuid = request.getParameter("suid");
            if(tmpSuid != null)
                caller.setSuid(Integer.parseInt(tmpSuid));
                
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActionException("Error in post action for marker set positions.");
        }
    }       
}
