/*
 * GetUMarkerSetPositionsAction.java
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
import com.arexis.mugen.genotype.genotypemanager.UMarkerSetDTO;
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
 * MugenAction class for getting the unified marker set positions
 * @author lami
 */
public class GetUMarkerSetPositionsAction extends MugenAction {
    
    /**
     * Creates a new instance of GetUMarkerSetPositionsAction
     */
    public GetUMarkerSetPositionsAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "GetUMarkerSetPositionsAction";
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
        
        try {
            HttpSession session = request.getSession();
            
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            Navigator nav = (Navigator)request.getSession().getAttribute("navigator");
            int sid = caller.getSid();
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.U_MARKER_SET_POSITION_FILTER,
                    MugenFormDataManagerFactory.WEB_FORM,
                    request);            
            
            Collection umarkersets = genotypeManager.getUMarkerSets(caller, sid);
            Collection upositions = new ArrayList();
            
            request.setAttribute("umarkersets", umarkersets);
            
            String tmpUmsid = request.getParameter("umsid");
            
            if(tmpUmsid == null){
                Iterator i = umarkersets.iterator();
                if(i.hasNext()) {
                    UMarkerSetDTO dto = (UMarkerSetDTO)i.next();
                    umsid = dto.getUmsid();
                    upositions = genotypeManager.getUMarkerSetPositions(umsid, caller, nav.getPageManager());
                }
            } else {
                umsid = Integer.parseInt(tmpUmsid);
                if(umarkersets.contains(new UMarkerSetDTO(umsid)))
                    upositions = genotypeManager.getUMarkerSetPositions(umsid, caller, nav.getPageManager());
            }
            request.setAttribute("formdata", formDataManager);
            request.setAttribute("upositions", upositions);
            request.setAttribute("species", samplingUnitManager.getSpeciesForProject(caller.getPid(), caller));
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get positions");
        }
    }
}
