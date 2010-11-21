/*
 * GetUMarkerSetMembershipAction.java
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
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;
import java.util.ArrayList;

/**
 * MugenAction class for retrieving info regarding unified marker set memberships
 * @author lami
 */
public class GetUMarkerSetMembershipAction extends MugenAction {
    
    /**
     * Creates a new instance of GetUMarkerSetMembershipAction 
     */
    public GetUMarkerSetMembershipAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "GetUMarkerSetMembershipAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException { 
        try {
            HttpSession session = request.getSession();
             
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");   
            Navigator nav = (Navigator)request.getSession().getAttribute("navigator");
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.U_MARKER_SET_MEMBERSHIP_FILTER, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    request);              
            System.out.println(formDataManager.toString());
            int suid = caller.getSuid();
            int sid = caller.getSid();       
            
            Collection umarkersets = genotypeManager.getUMarkerSets(caller, sid);

            String tmpUmsid = formDataManager.getValue("umsid");
            int umsid = 0;
            
            // If it is the first time we show the page...
            // Choose the first unified marker set
            if(!exists(tmpUmsid)) {
                Iterator i = umarkersets.iterator(); 
                if(i.hasNext()) {                    
                    UMarkerSetDTO dto = (UMarkerSetDTO)i.next();
                    umsid = dto.getUmsid();
                }                    
            }
            // else, use the requested unified  marker set id
            else{
                umsid = Integer.parseInt(tmpUmsid);
            }                         
            
            Collection available = genotypeManager.getUMarkers(caller);
            Collection included = genotypeManager.getUMarkerSetPositions(umsid, caller, nav.getPageManager());                                    
            
            if(umarkersets.size() == 0) {
                available = new ArrayList();
                included = new ArrayList();
            }
            
            available.removeAll(included);
            request.setAttribute("umarkersets", umarkersets);                      
            request.setAttribute("available", available);
            request.setAttribute("included", included);         
            request.setAttribute("species", samplingUnitManager.getSpeciesForProject(caller.getPid(), caller));
            request.setAttribute("formdata", formDataManager);
                                                                       
            return true;
        } catch (ApplicationException e) {
            throw new ApplicationException("Could not get unified marker set membership information: "+e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get unified marker set membership information.");
        }
    }  
}

