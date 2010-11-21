/*
 * GetAllelesAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;
import com.arexis.arxframe.Navigator;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.genotype.genotypemanager.MarkerSetDTO;
import com.arexis.mugen.samplingunit.samplingunitmanager.GroupDTO;
import com.arexis.mugen.samplingunit.samplingunitmanager.GroupingDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;



/**
 * Action class for handling the retrieval of genotype inheritance info
 * @author lami
 */
public class GenotypeInheritanceCheckAction extends MugenAction{
    
    /** Creates a new instance of GenotypeInheritanceAction */
    public GenotypeInheritanceCheckAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GenotypeInheritanceCheckAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {        
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");
            
            Collection includedMarkers = (Collection)req.getSession().getAttribute("tmp.genotypeinheritancecheckviewaction.markers");                       
            Collection includedGroups = (Collection)req.getSession().getAttribute("tmp.genotypeinheritancecheckviewaction.groups");                                   
            
            // Init lists if null
            if(includedGroups == null)
                includedGroups = new ArrayList();
            
            if(includedMarkers == null)
                includedMarkers = new ArrayList();            
            
            // Check if sampling unit id should be updated
            String tmpSuid = req.getParameter("suid");
            if(tmpSuid != null) {
                int suid = Integer.parseInt(tmpSuid);
                // If so....clear the selected markers and groups since they might not
                // be available in the new sampling unit!
                if(suid != caller.getSuid()) {
                    includedGroups = new ArrayList();
                    includedMarkers = new ArrayList(); 
                    req.getSession().setAttribute("tmp.genotypeinheritancecheckviewaction.markers", includedMarkers);
                    req.getSession().setAttribute("tmp.genotypeinheritancecheckviewaction.groups", includedGroups);
                    caller.setSuid(suid);
                }
            }

            // Fill up sampling units, markersets and init marker lists
            Collection samplingunits = samplingUnitManager.getSamplingUnits(caller.getPid(), caller);            
            Collection markersets = genotypeManager.getMarkerSets(caller.getSuid(), caller);
            Collection markers = new ArrayList();            
            Collection availableMarkers = new ArrayList();
            
            String tmpMsid = (String)req.getParameter("msid");
            int msid = 0;
            Iterator i = null;
            
            // If it is the first time we show the page...
            // Choose the first marker set
            if(tmpMsid == null || !markersets.contains(new MarkerSetDTO(Integer.parseInt(tmpMsid)))) {
                i = markersets.iterator();
                if(i.hasNext()) {
                    MarkerSetDTO msDto = (MarkerSetDTO)i.next();
                    msid = msDto.getMsid();
                }
            }
            // else, use the requested marker set id
            else{
                msid = Integer.parseInt(tmpMsid);
            }
            
            if(markersets.size() > 0) {
                availableMarkers = genotypeManager.getAllMarkers(caller); 
                // Add new markers to the set
                if(isSubmit(req, "addMarker")) {
                    String[] add = req.getParameterValues("availableMarkers");
                    if(add != null) {
                        includedMarkers = genotypeManager.getChoosenMarkers(availableMarkers, includedMarkers, add);
                        req.getSession().setAttribute("tmp.genotypeinheritancecheckviewaction.markers", includedMarkers);
                    }
                }
                
                // Remove markers from the set
                if(isSubmit(req, "removeMarker")) {
                    String[] remove = req.getParameterValues("includedMarkers");
                    if(remove != null) {
                        includedMarkers = genotypeManager.removeChoosenMarkers(availableMarkers, includedMarkers, remove);
                        req.getSession().setAttribute("tmp.genotypeinheritancecheckviewaction.markers", includedMarkers);
                    }
                }                                               
            }                      
            
            Collection groupings = samplingUnitManager.getGroupings(caller.getSuid(), caller);
            String tmpGsid = req.getParameter("gsid");
            int gsid = 0;
            
            // If it is the first time the page is displayed...choose the first 
            // grouping in the collection            
            if(tmpGsid == null){
                i = groupings.iterator();
                GroupingDTO grDto = (GroupingDTO)i.next();                
                gsid = grDto.getGsid();
            }
            // else, use the requested groupings id
            else{
                gsid = Integer.parseInt(tmpGsid);
                if(!groupings.contains(new GroupingDTO(gsid))) {
                    i = groupings.iterator();
                    if(i.hasNext()) {
                        GroupingDTO grDto = (GroupingDTO)i.next();                
                        gsid = grDto.getGsid();
                    }
                }
            }
            
            Collection availableGroups = samplingUnitManager.getGroups(gsid, caller, caller.getSuid());
            
            String tmpGid = (String)req.getParameter("gid");
            int gid = 0;
            
            // If it is the first time the page is displayed...choose the first 
            // group in the collection            
            if(tmpGid == null){
                i = availableGroups.iterator();
                if(i.hasNext()) {
                    GroupDTO grDto = (GroupDTO)i.next();                
                    gid = grDto.getGid();
                }
            }
            // else, use the requested group id
            else{                
                gid = Integer.parseInt(tmpGid);
                if(!availableGroups.contains(new GroupDTO(gid))) {
                    i = availableGroups.iterator();
                    if(i.hasNext()) {
                        GroupDTO grDto = (GroupDTO)i.next();                
                        gid = grDto.getGid();
                    }
                    else
                        gid = -1;
                }                
            }     
            
            if(availableGroups.size() > 0) {
                // Add new markers to the set
                if(isSubmit(req, "addGroup")) {
                    String[] add = req.getParameterValues("availableGroups");
                    if(add != null) {
                        includedGroups = genotypeManager.getChoosenGroups(availableGroups, includedGroups, add);
                        req.getSession().setAttribute("tmp.genotypeinheritancecheckviewaction.groups", includedGroups);
                    }
                }
                
                // Remove markers from the set
                if(isSubmit(req, "removeGroup")) {
                    String[] remove = req.getParameterValues("includedGroups");
                    if(remove != null) {
                        includedGroups = genotypeManager.removeChoosenGroups(availableGroups, includedGroups, remove);
                        req.getSession().setAttribute("tmp.genotypeinheritancecheckviewaction.groups", includedGroups);
                    }
                }                                               
            }             
    
            req.setAttribute("samplingunits", samplingunits);
            req.setAttribute("markersets", markersets);
            req.setAttribute("markers", markers);
            req.setAttribute("availableMarkers", availableMarkers);
            req.setAttribute("includedMarkers", includedMarkers);
            req.setAttribute("groupings", groupings);
            req.setAttribute("includedGroups", includedGroups);
            req.setAttribute("availableGroups", availableGroups);
            
            // Parameter 'memory'
            req.setAttribute("gsid", ""+gsid);
            req.setAttribute("msid", ""+msid);
            req.setAttribute("checkbox", req.getParameter("marker"));     

            req.setAttribute("noOfAvGroups", ""+availableGroups.size());
            req.setAttribute("noOfGroupings", ""+groupings.size());                       
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to retrive information for inheritance check.");
        }
    }     
}
