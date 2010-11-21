/*
 * SearchModelPreAction.java
 *
 * Created on January 18, 2006, 6:03 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.search;

import com.arexis.arxframe.Action;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.modelmanager.ModelManagerRemote;
import com.arexis.mugen.project.projectmanager.ProjectManagerRemote;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author heto
 */
public class SearchModelPreAction extends Action {
    
    public String getName() {
        return "SearchModelPreAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            //MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            ServiceLocator locator = ServiceLocator.getInstance();
            ProjectManagerRemote projectManager = (ProjectManagerRemote)locator.getManager(ServiceLocator.Services.PROJECTMANAGER);
            ModelManagerRemote modelManager = (ModelManagerRemote)locator.getManager(ServiceLocator.Services.MODELMANAGER);
            
            MugenCaller caller = modelManager.getSearchCaller();
            
            Collection varSets = modelManager.getVariableSets(caller);
            request.setAttribute("varsets", varSets);
            
            Collection apps = modelManager.getAllResearchApplications(caller);
            request.setAttribute("rapps", apps);
            
            Collection projects = projectManager.getProjectsByUser(caller);
            request.setAttribute("projects", projects);
            
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Failed to perform action", e);
        }
    }
}
