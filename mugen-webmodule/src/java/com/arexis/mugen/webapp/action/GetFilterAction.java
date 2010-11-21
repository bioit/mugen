/*
 * GetFilterAction.java
 *
 * Created on November 17, 2005, 8:29 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.export.GQLFilterDTO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author lami
 */
public class GetFilterAction extends MugenAction {
    
    /** Creates a new instance of GetFilterAction */
    public GetFilterAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetFilterAction";
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
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");
                        
            int fid = Integer.parseInt(req.getParameter("fid"));
            GQLFilterDTO filter = exportManager.getFilter(fid, caller);
            req.setAttribute("filterdto", filter);
            req.setAttribute("sid", ""+caller.getSid());
            req.setAttribute("species", samplingUnitManager.getSpeciesForProject(caller.getPid(), caller));
            req.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller.getSid(), caller));
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }  
}
