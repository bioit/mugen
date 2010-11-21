/*
 * GetGroupAction.java
 *
 * Created on August 5, 2005, 1:48 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.samplingunit.samplingunitmanager.GroupDTO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import com.arexis.mugen.webapp.action.*;
import javax.servlet.http.HttpSession;

/**
 * MugenAction class for retrieval of a group
 * @author lami
 */
public class GetGroupAction extends MugenAction{
    
    /** Creates a new instance of GetGroupAction */
    public GetGroupAction() {
    }
    
    /**
     * Returns the name of the action
     * @return The name of the action
     */
    public String getName() {
        return "GetGroupAction";
    }        
    
    /**
     * Performs the action
     * @param req The http request
     * @param context The servlet context
     * @return True, if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) {        
        try {        
            // Get the grouping id and get a group
            // data transfer object filled with the
            // requested group
            String id = req.getParameter("gid");
            HttpSession session = req.getSession();
            
            if (id==null || id.equals(""))
            {
                id = (String)session.getAttribute("GetGroupAction.gid");
            }    
            else
            {
                session.setAttribute("GetGroupAction.gid",id);
            }    
            
            int gsid = Integer.parseInt(id);                
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            GroupDTO grdto = samplingUnitManager.getGroup(gsid, caller);
            Collection grhistory = samplingUnitManager.getGroupHistory(grdto.getGid(), caller);
            // Put the data transfer object in the session
            // variable...
            req.setAttribute("groupdto", grdto);
            req.setAttribute("grouphistory", grhistory);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
   
