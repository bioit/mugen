/*
 * GetMarkerSetMembershipAction.java
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
import com.arexis.mugen.genotype.genotypemanager.ChromosomeDTO;
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
 * MugenAction class for retrieving info regarding marker set memberships
 * @author lami
 */
public class GetMarkerSetMembershipAction extends MugenAction {
    
    /**
     * Creates a new instance of GetMarkerSetMembershipAction
     */
    public GetMarkerSetMembershipAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "GetMarkerSetMembershipAction";
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
            
            int cid = 0;
            int msid = 0;
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.MARKER_SET_MEMBERSHIP_FILTER, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    request);                
            
            String tmpSuid = formDataManager.getValue("suid");
            if(tmpSuid != null)
                caller.setSuid(Integer.parseInt(tmpSuid));
            
            Collection chromosomes = genotypeManager.getChromosomes(caller.getSid(), caller);
            Collection markersets = genotypeManager.getMarkerSets(caller.getSuid(), caller);
            
            Collection available = new ArrayList();
            Collection included = new ArrayList();
            
            request.setAttribute("chromosomes", chromosomes);
            request.setAttribute("markersets", markersets);
            request.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller));
            
            // Get the selected options
            String tmpCid = formDataManager.getValue("cid");
            String tmpMsid = formDataManager.getValue("msid");

            // If it is the first time we show the page...
            // Choose the first chromosome
            if(!exists(tmpCid)) {
                Iterator i = chromosomes.iterator();
                if(i.hasNext()) {
                    ChromosomeDTO chrDto = (ChromosomeDTO)i.next();
                    cid = chrDto.getCid();
                }
            }
            // else, use the requested chromosome id
            else{
                if(!tmpCid.equals("*"))
                    cid = Integer.parseInt(tmpCid);
            }

            // If it is the first time we show the page...
            // Choose the first marker set
            if(!exists(tmpMsid) || !markersets.contains(new MarkerSetDTO(Integer.parseInt(tmpMsid)))) {
                Iterator i = markersets.iterator();
                if(i.hasNext()) {
                    MarkerSetDTO msDto = (MarkerSetDTO)i.next();
                    msid = msDto.getMsid();
                }
            }
            // else, use the requested marker set id
            else{
                msid = Integer.parseInt(tmpMsid);
            }
            
                
            if(cid == 0 || !exists(tmpCid))
                available = genotypeManager.getAllMarkers(caller);
            else
                available = genotypeManager.getAllMarkersForChromosome(caller, cid);
            included = genotypeManager.getMarkerSetPositions(msid, caller, nav.getPageManager());
            
            available.removeAll(included);
            // Fetch the markers
            request.setAttribute("available", available);
            request.setAttribute("included", included);
            request.setAttribute("cid", ""+cid);
            request.setAttribute("formdata", formDataManager);            
            
            return true;
        } catch (ApplicationException e) {
            throw new ApplicationException("Could not get marker set information: "+e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get marker set membership information.");
        }
    }
}

