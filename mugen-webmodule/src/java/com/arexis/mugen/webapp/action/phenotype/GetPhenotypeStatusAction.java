/*
 * GetPhenotypeAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.phenotype;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;



/**
 * MugenAction class for handling the retrieval a phenotype status
 * @author lami
 */
public class GetPhenotypeStatusAction extends MugenAction{
    
    /**
     * Creates a new instance of GetPhenotypeStatusAction 
     */
    public GetPhenotypeStatusAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetPhenotypeStatusAction";
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
            String tmpSuid = req.getParameter("suid");
            Collection status = new ArrayList();
            
            String tmpVsid = req.getParameter("vsid");
            String tmpFid = req.getParameter("fid");
            
            if(tmpSuid != null) 
                caller.setSuid(Integer.parseInt(tmpSuid));
            
            if(req.getParameter("display") != null && tmpVsid != null) {
                int fid = Integer.parseInt(tmpFid);
                int vsid = Integer.parseInt(tmpVsid);
                // Perform logic here...
            }
            
            req.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller));
            req.setAttribute("filters", phenotypeManager.getPhenotypeFilters(caller));
            req.setAttribute("variablesets", phenotypeManager.getAllVariableSets(caller.getSuid(), caller));
            req.setAttribute("phenotypestatus", status);
            return true;
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get phenotype status information.", e);
        }
    }   
}
