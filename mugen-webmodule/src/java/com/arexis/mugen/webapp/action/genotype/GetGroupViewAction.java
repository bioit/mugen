/*
 * GetPhenotypeAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.genotype.genotypemanager.GenotypeSpecialViewDTO;
import com.arexis.mugen.samplingunit.samplingunitmanager.GroupDTO;
import com.arexis.mugen.samplingunit.samplingunitmanager.GroupingDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;



/**
 * MugenAction class for handling the retrieval of group special view
 * @author lami
 */
public class GetGroupViewAction extends MugenAction{
    
    /**
     * Creates a new instance of GetGroupViewAction 
     */
    public GetGroupViewAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetGroupViewAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) {        
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            String tmpSuid = req.getParameter("suid");
            Collection status = new ArrayList();
            
            String tmpMid1 = req.getParameter("mid1");
            String tmpMid2 = req.getParameter("mid2");
            String tmpMid3 = req.getParameter("mid3");
            String tmpGsid = req.getParameter("gsid");
            String tmpGid = req.getParameter("gid");
            
            
            if(tmpSuid != null) 
                caller.setSuid(Integer.parseInt(tmpSuid));
            
            GenotypeSpecialViewDTO dto = new GenotypeSpecialViewDTO();
            Collection pedigree = new ArrayList();     
            
            Collection groupings = samplingUnitManager.getGroupings(caller.getSuid(), caller);
            int gsid = 0;
            int gid = 0;
            Iterator i = null;
            // If it is the first time the page is displayed...choose the first 
            // grouping in the collection            
            if(tmpGsid == null){
                i = groupings.iterator();
                if(i.hasNext()) {
                    GroupingDTO grDto = (GroupingDTO)i.next();                
                    gsid = grDto.getGsid();
                }
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
            
            Collection groups = new ArrayList();
            if(gsid > 0)
                groups = samplingUnitManager.getGroups(gsid, caller, caller.getSuid());            

            // If it is the first time the page is displayed...choose the first 
            // group in the collection            
            if(tmpGid == null){
                i = groups.iterator();
                if(i.hasNext()) {
                    GroupDTO grDto = (GroupDTO)i.next();                
                    gid = grDto.getGid();
                }
            }
            // else, use the requested group id
            else{                
                gid = Integer.parseInt(tmpGid);
                if(!groups.contains(new GroupDTO(gid))) {
                    i = groups.iterator();
                    if(i.hasNext()) {
                        GroupDTO grDto = (GroupDTO)i.next();                
                        gid = grDto.getGid();
                    }
                    else
                        gid = -1;
                }                
            }              

            if(req.getParameter("display") != null && tmpGid != null && tmpGid.length() > 0) {
                int mid1 = 0;
                int mid2 = 0;
                int mid3 = 0;
                
                if(tmpMid1 != null && tmpMid1.length() > 0)
                    mid1 = Integer.parseInt(tmpMid1);

                if(tmpMid2 != null && tmpMid2.length() > 0)
                    mid2 = Integer.parseInt(tmpMid2);                

                if(tmpMid3 != null && tmpMid3.length() > 0)
                    mid3 = Integer.parseInt(tmpMid3);                
                
                if(tmpGid != null && tmpGid.length() > 0)
                    gid = Integer.parseInt(tmpGid);   
                                
                pedigree = genotypeManager.getGroupView(gid, mid1, mid2, mid3);
                if(pedigree.size() > 0)
                    req.getSession().setAttribute("tmp.getspecialviewaction.pedigreegraph", genotypeManager.getGraphForPedigree(pedigree, false));
                i = pedigree.iterator();
                if(pedigree.size() > 0)
                    dto = (GenotypeSpecialViewDTO)i.next();
            }                             

            req.setAttribute("gsid", ""+gsid);
            req.setAttribute("gid", ""+gid);            
            req.setAttribute("pedigree", pedigree);             
            req.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller));
            req.setAttribute("groupings", groupings);
            req.setAttribute("groups", groups);
            req.setAttribute("markers", genotypeManager.getMarkers(caller));
            
            req.setAttribute("header1", dto.getMname1());
            req.setAttribute("header2", dto.getMname2());
            req.setAttribute("header3", dto.getMname3());

            req.setAttribute("mid1", tmpMid1);
            req.setAttribute("mid2", tmpMid2);
            req.setAttribute("mid3", tmpMid3);             

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }     
}
