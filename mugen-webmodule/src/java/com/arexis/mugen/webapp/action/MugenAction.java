/*
 * AbstractAction.java
 *
 * Created on July 7, 2005, 10:41 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action;

import com.arexis.arxframe.Action;
import com.arexis.arxframe.ActionException;
import com.arexis.arxframe.Navigator;
import com.arexis.form.FormDataManager;
import com.arexis.arxframe.WebFormDataManager;
import com.arexis.form.AbstractFormDataManagerFactory;
import com.arexis.form.FormDataException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.adminmanager.AdminManagerRemote;
import com.arexis.mugen.export.ExportManagerRemote;
import com.arexis.mugen.genotype.genotypemanager.GenotypeManagerRemote;
import com.arexis.mugen.model.modelmanager.ModelManagerRemote;
import com.arexis.mugen.phenotype.phenotypemanager.PhenotypeManagerRemote;
import com.arexis.mugen.project.projectmanager.ProjectManagerRemote;
import com.arexis.mugen.resource.resourcemanager.ResourceManagerRemote;
import com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitManagerRemote;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Abstract class for an action
 * @author heto
 */
public abstract class MugenAction extends Action {
    protected ServiceLocator locator;
    
    protected static SamplingUnitManagerRemote samplingUnitManager;
    protected static PhenotypeManagerRemote phenotypeManager;
    protected static GenotypeManagerRemote genotypeManager;
    protected static ProjectManagerRemote projectManager;
    protected static ExportManagerRemote exportManager;
    protected static ResourceManagerRemote resourceManager;
    protected static ModelManagerRemote modelManager;
    protected static AdminManagerRemote adminManager;
    
    /** Creates a new instance of AbstractAction */
    public MugenAction() {
        locator = ServiceLocator.getInstance();
        
        //SamplingUnitManagerRemoteHome s = (SamplingUnitManagerRemoteHome)locator.getHome(ServiceLocator.Services.SAMPLINGUNITMANAGER);
        //samplingUnitManager = s.create();
        
        if (samplingUnitManager==null)
            samplingUnitManager =
                    (SamplingUnitManagerRemote)locator.getManager(ServiceLocator.Services.SAMPLINGUNITMANAGER);
        if (phenotypeManager==null)
            phenotypeManager =
                    (PhenotypeManagerRemote)locator.getManager(ServiceLocator.Services.PHENOTYPEMANAGER);
        if (genotypeManager==null)
            genotypeManager =
                    (GenotypeManagerRemote)locator.getManager(ServiceLocator.Services.GENOTYPEMANAGER);
        if (projectManager==null)
            projectManager =
                    (ProjectManagerRemote)locator.getManager(ServiceLocator.Services.PROJECTMANAGER);
        if (exportManager==null)
            exportManager =
                    (ExportManagerRemote)locator.getManager(ServiceLocator.Services.EXPORTMANAGER);
        if (resourceManager==null)
            resourceManager =
                    (ResourceManagerRemote)locator.getManager(ServiceLocator.Services.RESOURCEMANAGER);        
        if (modelManager==null)
            modelManager =
                    (ModelManagerRemote)locator.getManager(ServiceLocator.Services.MODELMANAGER);        
        if (adminManager==null)
            adminManager =
                    (AdminManagerRemote)locator.getManager(ServiceLocator.Services.ADMINMANAGER);
    }
    
    public FormDataManager getFormDataManager(int name, int type, HttpServletRequest req) throws ActionException {
        // Get the form data manager from the http session...
        WebFormDataManager formDataManager = (WebFormDataManager)req.getSession().getAttribute(AbstractFormDataManagerFactory.getInstanceName(name));        
        // If not created yet...use the factory to build a new one
        // with correct default values
        if(formDataManager == null) {
            try {
                MugenFormDataManagerFactory formFactory = new MugenFormDataManagerFactory((MugenCaller)req.getSession().getAttribute("caller"));
                formDataManager = (WebFormDataManager)formFactory.createInstance(name, type);
            } catch(FormDataException e) {
                throw new ActionException(e.getMessage());
            }
        }
        
        return formDataManager;
    } 
    
    
    /**
     * Collects the data in a form using the FormDataManager
     * @param name 
     * @param req 
     * @throws com.arexis.arxframe.ActionException 
     */
    public void collectFormData(int name, int type, HttpServletRequest req) throws ActionException {
        HttpSession se = req.getSession();            
        Navigator nav = (Navigator)se.getAttribute("navigator");        
        WebFormDataManager formDataManager = (WebFormDataManager)getFormDataManager(name, type, req);
        formDataManager.collectParams((Object)req);
        se.setAttribute(MugenFormDataManagerFactory.getInstanceName(name), formDataManager);
    }
    
    /**
     * Resets the FormDataManager and thus the form also...
     */
    public void resetFormData(int name, HttpServletRequest req) throws ActionException {
        FormDataManager formDataManager = getFormDataManager(name, MugenFormDataManagerFactory.WEB_FORM, req);
        formDataManager.reset();
    }
    
    /**
     * Convenience method for string validation (null or emtpy returns false, true otherwise)
     * @param value 
     * @return 
     */
    public boolean exists(String value) {
        if(value != null && value.length() > 0)
            return true;
        else
            return false;
    }
    
    public boolean isSubmit(HttpServletRequest req, String name)
    {
        if (req.getParameter(name)!=null)
            return true;
        else if (req.getParameter(name+".x")!=null)
            return true;
        return false;
    }
    
    public void debugParameters(HttpServletRequest req)
    {
        String out = "Debug Parameters\n";
        Enumeration num = req.getParameterNames();
        while (num.hasMoreElements())
        {
            String e = (String)num.nextElement();
            out += e+"="+req.getParameter(e)+"\n";
        }
        System.out.println(out);
    }
    
}
