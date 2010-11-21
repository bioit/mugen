/*
 * GetMarkerAction.java
 *
 * Created on August 2, 2005, 12:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;

/**
 * MugenAction class for retrieving a marker
 * @author lami
 */
public class GetMarkerAction extends MugenAction {
    
    /**
     * Creates a new instance of GetMarkerAction
     */
    public GetMarkerAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "GetMarkerAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        int mid = 0;
        try {
            HttpSession session = request.getSession();
            
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            String tmpMid = request.getParameter("mid");
            String tmpSuid = request.getParameter("suid");
            Collection sus = samplingUnitManager.getSamplingUnits(caller.getPid(), caller);
            String mess = "";
            
            // Used when action is used for viewing a certain marker
            if(tmpMid != null) {
                mid = Integer.parseInt(tmpMid);
                request.setAttribute("marker", genotypeManager.getMarker(mid, caller));
                request.setAttribute("history", genotypeManager.getMarkerHistory(mid, caller));
            }
            
            if(tmpSuid != null)
                caller.setSuid(Integer.parseInt(tmpSuid));
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.MARKER, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    request);            
            
            request.setAttribute("samplingunits", sus);
            request.setAttribute("chromosomes", genotypeManager.getChromosomes(caller.getSid(), caller));
            request.setAttribute("formdata", formDataManager);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get marker mid="+mid);
        }
    }
}
