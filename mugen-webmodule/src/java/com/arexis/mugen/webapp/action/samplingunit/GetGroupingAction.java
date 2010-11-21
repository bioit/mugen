/*
 * GetGroupingAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.samplingunit.samplingunitmanager.GroupingDTO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import com.arexis.mugen.webapp.action.*;
import javax.servlet.http.HttpSession;


/**
 * MugenAction class for retrival of a grouping
 * @author lami
 */
public class GetGroupingAction extends MugenAction{
    
    /** Creates a new instance of GetGroupingsAction */
    public GetGroupingAction() {
        super();
    }
    
    /**
     * Returns the name of the action
     * @return The name of the action
     */
    public String getName() {
        return "GetGroupingAction";
    }
    
    /**
     * Performs the action
     * @return True, if the action could be performed
     * @param req The http request
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If something went wrong during the retrieval of groupings
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException{
        String id = "";
        try {
            // Get the grouping id and fetch a data transfer
            // object containing the requested grouping
            id = req.getParameter("gsid");
            
            HttpSession session = req.getSession();
            
            if (id==null || id.equals(""))
            {
                id = (String)session.getAttribute("GetGroupingAction.gsid");
            }
            else
            {
                session.setAttribute("GetGroupingAction.gsid", id);
            }
            int gsid = Integer.parseInt(id);

            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            GroupingDTO grdto = samplingUnitManager.getGrouping(gsid, caller);
            
            Collection history = samplingUnitManager.getGroupingHistory(gsid, caller);
            
            // Put the data transfer object in the session variable
            req.setAttribute("groupingdto", grdto);
            req.setAttribute("history", history);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get grouping with gsid="+id);
        }
    }
}
