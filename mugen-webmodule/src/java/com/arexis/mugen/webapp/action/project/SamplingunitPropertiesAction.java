/*
 * SamplingunitPropertiesAction.java
 *
 * Created on July 14, 2005, 4:11 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.project;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitDTO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;




/**
 * MugenAction class for handling the sampling unit properties
 * @author heto
 */
public class SamplingunitPropertiesAction extends MugenAction {
    
    /** Creates a new instance of SamplingunitPropertiesAction */
    public SamplingunitPropertiesAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "SamplingunitPropertiesAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) {
        try {
            HttpSession session = request.getSession();
            Navigator nav = (Navigator)session.getAttribute("navigator");
            MugenCaller c = (MugenCaller)session.getAttribute("caller");

            /** Set the selected Sampling unit id in navigator */
            String su = request.getParameter("suid");

            SamplingUnitDTO home = samplingUnitManager.getSamplingUnit(c, Integer.parseInt(su));
            c.setSuidName(home.getName());
            c.setSid(home.getSid());
            c.setSuid(Integer.parseInt(su));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
