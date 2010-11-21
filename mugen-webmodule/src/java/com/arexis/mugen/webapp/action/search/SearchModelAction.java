/*
 * SearchByGeneAction.java
 *
 * Created on January 18, 2006, 11:18 AM
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
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.util.Collection;
import java.util.TreeSet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author heto
 */
public class SearchModelAction extends Action {
    
    public String getName() {
        return "SearchByGeneAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            ServiceLocator locator = ServiceLocator.getInstance();
            //ProjectManagerRemote projectManager = (ProjectManagerRemote)locator.getManager(ServiceLocator.Services.PROJECTMANAGER);
            ModelManagerRemote modelManager = (ModelManagerRemote)locator.getManager(ServiceLocator.Services.MODELMANAGER);
            
            
            String geneName = request.getParameter("gene");
            String vsName = request.getParameter("vsname");
            String raName = request.getParameter("raname");
            String project = request.getParameter("project");
            
            // Fix wildcards
            if (geneName!=null)
            {
                geneName = geneName.replace('*', '%');
                geneName = geneName.replace('?', '_');     
                geneName = "%"+geneName+"%";
            }
            
            MugenCaller searchCaller = modelManager.getSearchCaller();
            
            Collection models_gene = modelManager.searchByGene(geneName, searchCaller);
            Collection models_vs = modelManager.searchByVariableSet(vsName, searchCaller);
            Collection models_ra = modelManager.searchByResearchApplication(raName, searchCaller);
            Collection models_prj  = modelManager.searchByProject(project, searchCaller);
            
            Collection models = new TreeSet();
            models.addAll(models_gene);
            models.addAll(models_vs);
            models.addAll(models_ra);
            models.addAll(models_prj);
            
            
            System.out.println("Number="+models.size());
            request.setAttribute("models", models);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Failed to perform action", e);
        }
    }
}
