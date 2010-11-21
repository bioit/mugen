/*
 * ProjectPropertiesAction.java
 *
 * Created on July 13, 2005, 2:23 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.project;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.projectmanager.ProjectDTO;
import com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitDTO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;

/**
 * MugenAction for handling the project properties
 * @author heto
 */
public class ProjectPropertiesAction extends MugenAction {
    
    /** Creates a new instance of ProjectPropertiesAction */
    public ProjectPropertiesAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "ProjectPropertiesAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException{
        try {
            HttpSession session = request.getSession();
            Navigator nav = (Navigator)session.getAttribute("navigator");
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");            
            
            System.out.println("SetProject");
            /** Set the project */
            String pid = request.getParameter("project");
            caller.setPid(new Integer(pid).intValue());
            //caller.updatePrivileges();
            ProjectDTO prj = projectManager.getProject(new Integer(pid).intValue(), caller);
            session.setAttribute("project.projectdto", prj);

            System.out.println("SetSamplingUnit");
            /** Set sampling unit */
            SamplingUnitDTO su = samplingUnitManager.getDefaultSamplingUnit(caller);
            caller.setSuidName(su.getName());
            caller.setSid(su.getSid());
            caller.setSuid(su.getSuid());
            
            System.out.println("SetRows");
            /** Set the number of rows to display */
            String tmpRows = request.getParameter("rows");
            int rows = 60;
            try{
                rows = Integer.parseInt(tmpRows);
            } catch (NumberFormatException nfe) {
                throw new ApplicationException("Number of rows is not an integer.");
            }
            //nav.getPageManager().setDelta(new Integer(rows).intValue());
            nav.getPageManager().setDelta(rows);
            
            
            return true;
        } 
        catch (ApplicationException ae)
        {
            ae.printStackTrace();
            throw ae;
        }
        catch (Exception e) {
            throw new ApplicationException("Failed to get project properties" +e.getMessage(),e);
        }
    }
}
