package com.arexis.mugen.webapp.action.phenopath;

import com.arexis.mugen.webapp.action.model.GetModelAction;
import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.modelmanager.PhenoOntologyDTO;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.form.FormDataManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class AssignPhenoPathPreAction extends MugenAction {
    private static Logger logger = Logger.getLogger(GetModelAction.class);
    
    public String getName() {
        return "AssignPhenoPathPreAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            Workflow wf = (Workflow)request.getAttribute("workflow");
            
            String tmpEid = request.getParameter("eid");
            
            logger.debug("pheno path assignment for model with eid="+tmpEid);
            
            FormDataManager formDataManagerEID = getFormDataManager(MugenFormDataManagerFactory.EXPMODEL, MugenFormDataManagerFactory.WEB_FORM, request);  
                    
            
            if(exists(tmpEid)) {
                collectFormData(MugenFormDataManagerFactory.EXPMODEL, MugenFormDataManagerFactory.WEB_FORM, request);                                                                                   
                if(exists(formDataManagerEID.getValue("eid"))) {                   
                    if(!tmpEid.equals(formDataManagerEID.getValue("eid"))) {
                        resetFormData(MugenFormDataManagerFactory.EXPMODEL, request);
                    }              
                }
                
                formDataManagerEID.put("eid", tmpEid);                                              
            }
            
            
            int eid = Integer.parseInt(formDataManagerEID.getValue("eid")); 
            
            FormDataManager formDataManager = getFormDataManager(MugenFormDataManagerFactory.MP_LO, MugenFormDataManagerFactory.WEB_FORM, request);
            
            request.setAttribute("formdata", formDataManager);
            request.setAttribute("formdataEID", formDataManagerEID);
            request.setAttribute("phenoPaths", modelManager.getPhenoPaths(eid));
            
            //whatever happens the first level of mp terms must be uploaded
            request.setAttribute("roots", modelManager.phenoCollectorLevelOne());
            
            Collection rootpaths = new ArrayList();
            
            //if user has selected some value from the first level list
            if(formDataManager.getValue("LO")!=""){
                rootpaths = modelManager.getPhenoPathsByRoot(formDataManager.getValue("LO"));
            }
            
            request.setAttribute("rootpaths", rootpaths);

            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Failed to perform action", e);
        }
    }
}
