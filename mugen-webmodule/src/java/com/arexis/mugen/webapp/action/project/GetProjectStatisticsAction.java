/*
 * GetProjectStatisticsAction.java
 *
 * Created on July 26, 2005, 4:25 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.project;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.projectmanager.ProjectStatisticsDTO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;

/**
 * MugenAction to get the statistics for a project.
 * 
 * Uses pid (project id) from Navigator object in session
 * 
 * @author heto
 */
public class GetProjectStatisticsAction extends MugenAction {
    
    /** Creates a new instance of GetProjectStatisticsAction */
    public GetProjectStatisticsAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "GetProjectStatisticsAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if this action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            Navigator nav = (Navigator)request.getSession().getAttribute("navigator");
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            
            ProjectStatisticsDTO stats = projectManager.getStatistics(caller.getPid(), caller);
            
            request.setAttribute("stats", stats);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new ApplicationException("Failed to get project statistics", e);
        }
    }
}
