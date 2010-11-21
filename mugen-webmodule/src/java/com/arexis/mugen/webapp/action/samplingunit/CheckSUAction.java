/*
 * CreateSampleAction.java
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

import com.arexis.arxframe.ActionException;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;

/**
 * MugenAction class for checking the values for a sampling unit
 */
public class CheckSUAction extends MugenAction {
    
    /** Creates a new instance of CheckSUAction */
    public CheckSUAction() {
    }
    
    /**
     * Returns the name of the action
     * @return The name of the action
     */
    public String getName() {
        return "CheckSUAction";
    }
    
    /**
     * Performs the action
     * @param request The http request
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True, if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ActionException {
        try {
            // Get the caller
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");    
            int suid = Integer.parseInt(request.getParameter("suid"));
            int numInds = samplingUnitManager.getNumberOfIndividuals(suid);
            Collection checked = samplingUnitManager.checkSamplingUnit(suid, caller);
            request.setAttribute("sucheckdto", checked);
            request.setAttribute("indsOK", ""+(numInds-checked.size()));
            request.setAttribute("indsERR", ""+checked.size());
            return true;
      
        } catch (Exception e) {
            throw new ApplicationException("Checking sampling unit failed.");
        }
    } 
}
