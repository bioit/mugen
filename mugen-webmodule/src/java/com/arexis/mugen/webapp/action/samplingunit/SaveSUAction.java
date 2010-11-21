/*
 * SaveSUAction.java
 *
 * Created on August 2, 2005, 12:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;

/**
 * MugenAction class for saving a sampling unit
 * @author lami
 */
public class SaveSUAction extends MugenAction {
    
    /**
     * Creates a new instance of SaveSUAction
     */
    public SaveSUAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SaveSUAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        int suid = 0;
        try {
            HttpSession session = request.getSession();
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            Workflow wf = (Workflow)request.getAttribute("workflow");
            int pid = 0;
            if (wf.getAttribute("pid")!=null)
                pid = new Integer(wf.getAttribute("pid")).intValue();
            
            if (isSubmit(request, "create")) {
                String name = ""+request.getParameter("name");
                String comm = ""+request.getParameter("comm");
                String status = ""+request.getParameter("status");
                String species = ""+request.getParameter("species");
                
                // Create a new sampling unit
                samplingUnitManager.createSamplingUnit(name, comm, status, Integer.parseInt(species), pid, caller);
            } else if (isSubmit(request, "save")) {
                String name = ""+request.getParameter("name");
                String comm = ""+request.getParameter("comm");
                String status = ""+request.getParameter("status");
                suid = Integer.parseInt(request.getParameter("suid"));
                request.setAttribute("suid", request.getParameter("suid"));
                samplingUnitManager.updateSamplingUnit(suid, name, comm, status, caller);
            } else if(isSubmit(request, "remove")) {
                suid = Integer.parseInt(request.getParameter("suid"));
                samplingUnitManager.removeSamplingUnit(suid, caller);
            }
            
            return true;
        } 
        catch (ApplicationException ae)
        {
            throw ae;
        }
        catch (Exception e) {
            throw new ApplicationException("Failed to save sampling unit", e);
        }
    }
}
