/*
 * GetMarkerSetPositionAction.java
 *
 * Created on August 2, 2005, 12:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.genotype.genotypemanager.PositionDTO;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;

/**
 * MugenAction class for getting a marker set position
 * @author lami
 */
public class GetMarkerSetPositionAction extends MugenAction {
    
    /**
     * Creates a new instance of GetMarkerSetPositionAction
     */
    public GetMarkerSetPositionAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "GetMarkerSetPositionAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        int msid = 0;
        int mid = 0;
        try {
            HttpSession session = request.getSession();
            
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            Navigator nav = (Navigator)request.getSession().getAttribute("navigator");
            
            msid = Integer.parseInt(request.getParameter("msid"));
            mid = Integer.parseInt(request.getParameter("mid"));
            PositionDTO dto = genotypeManager.getMarkerSetPosition(msid, mid, caller);
            request.setAttribute("position", dto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get position for markerset:"+msid+", marker:"+mid);
        }
    }
}
