/*
 * CreateGroupingAction.java
 *
 * Created on August 2, 2005, 3:05 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;

/**
 *
 * @author lami
 */

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;

/**
 * MugenAction class for creation of groupings
 * @author lami
 */
public class CreateGroupingAction extends MugenAction {
    
    /** Creates a new instance of CreateGroupingAction */
    public CreateGroupingAction() {
    }
    
    /**
     * Returns the name of the action
     * @return The name of the action
     */
    public String getName() {
        return "CreateGroupingAction";
    }
    
    /**
     * Performs the action
     * @param request The http request
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True, if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            // Get the caller
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");            

            Navigator nav = (Navigator)request.getSession().getAttribute("navigator"); 
                
            // Get the sampling units

            // Set the data transfer object containing the sampling units
            request.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller));  
                
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Create grouping failed.");
        }
    } 
}
