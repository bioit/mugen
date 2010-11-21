/*
 * SaveUMarkerSetPositionAction.java
 *
 * Created on August 2, 2005, 12:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;

import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;

/**
 * MugenAction class for saving a unified marker set position
 * @author lami
 */
public class SaveUMarkerSetPositionAction extends MugenAction {
    
    /**
     * Creates a new instance of SaveUMarkerSetPositionAction
     */
    public SaveUMarkerSetPositionAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SaveUMarkerSetPositionAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        int umsid = 0;
        int umid = 0;
        try {
            HttpSession session = request.getSession();
            
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            
            umsid = Integer.parseInt(request.getParameter("umsid"));
            umid = Integer.parseInt(request.getParameter("umid"));
            double position = Double.parseDouble(request.getParameter("pos"));
            
            genotypeManager.updateUMarkerSetPosition(umsid, umid, position, caller);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to update unified marker set position for umsid="+umsid+", umid="+umid);
        }
    }
}
