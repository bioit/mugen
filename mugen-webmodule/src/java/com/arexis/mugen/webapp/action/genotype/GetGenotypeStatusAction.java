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
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;



/**
 * MugenAction class for handling the retrieval a genotype status
 * @author lami
 */
public class GetGenotypeStatusAction extends MugenAction{
    
    /**
     * Creates a new instance of GetGenotypeStatusAction 
     */
    public GetGenotypeStatusAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetGenotypeStatusAction";
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
            
            String tmpMsid = req.getParameter("msid");
            String tmpFid = req.getParameter("fid");
            
            if(tmpSuid != null) 
                caller.setSuid(Integer.parseInt(tmpSuid));
            
            if(req.getParameter("display") != null && tmpMsid != null) {
                int fid = Integer.parseInt(tmpFid);
                int msid = Integer.parseInt(tmpMsid);
                // Perform logic here...
            }
            
            req.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller));
            req.setAttribute("filters", phenotypeManager.getPhenotypeFilters(caller));
            req.setAttribute("markersets", genotypeManager.getMarkerSets(caller.getSuid(), caller));
            req.setAttribute("genotypestatus", status);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }   
}
