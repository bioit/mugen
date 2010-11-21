/*
 * GetMarkerSetPositionsAction.java
 *
 * Created on August 2, 2005, 12:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;

import com.arexis.arxframe.Navigator;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.genotype.genotypemanager.MarkerSetDTO;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;

/**
 * MugenAction class for getting the marker set positions
 * @author lami
 */
public class GetMarkerSetPositionsAction extends MugenAction {
    
    /**
     * Creates a new instance of GetMarkerSetPositionsAction
     */
    public GetMarkerSetPositionsAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "GetMarkerSetPositionsAction";
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
        try {
            HttpSession session = request.getSession();
            
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            Navigator nav = (Navigator)request.getSession().getAttribute("navigator");
            
            int suid = caller.getSuid();
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.MARKER_SET_POSITION_FILTER,
                    MugenFormDataManagerFactory.WEB_FORM,
                    request);
            
            Collection markersets = genotypeManager.getMarkerSets(caller.getSuid(), caller);
            Collection positions = new ArrayList();
            
            request.setAttribute("markersets", markersets);
            String tmpMsid = formDataManager.getValue("msid");
            
            if(!exists(tmpMsid)){
                Iterator i = markersets.iterator();
                if(i.hasNext()) {
                    MarkerSetDTO dto = (MarkerSetDTO)i.next();
                    msid = dto.getMsid();
                    positions = genotypeManager.getMarkerSetPositions(msid, caller, nav.getPageManager());
                }
            } else{
                msid = Integer.parseInt(tmpMsid);
                if(markersets.contains(new MarkerSetDTO(msid)))
                    positions = genotypeManager.getMarkerSetPositions(msid, caller, nav.getPageManager());
            }

            request.setAttribute("positions", positions);
            request.setAttribute("formdata", formDataManager);
            request.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller));
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get positions");
        }
    }
}
