package com.arexis.mugen.webapp.action.phenopath;

import com.arexis.arxframe.ArxLoginForward;
import com.arexis.arxframe.IWorkFlowManager;
import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.arexis.arxframe.advanced.AdvancedWorkflowManager;

public class AssignPhenoPathAction extends MugenAction {

    public AssignPhenoPathAction() {
    }

    public String getName() {
        return "AssignPhenoPathAction";
    }

    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            HttpSession se = req.getSession();
            Workflow wf = (Workflow)req.getAttribute("workflow");
            
            collectFormData(MugenFormDataManagerFactory.MP_LO, MugenFormDataManagerFactory.WEB_FORM, req);
            
            FormDataManager formDataManagerEID = getFormDataManager(MugenFormDataManagerFactory.EXPMODEL, MugenFormDataManagerFactory.WEB_FORM, req);
            
            int eid = Integer.parseInt(formDataManagerEID.getValue("eid")); 
            if(isSubmit(req, "ass")) {
                
                projectManager.log("user "+caller.getName()+" assigned mp path to model "+eid, getName(), caller.getName(), req.getRemoteAddr(), req.getRemoteHost());
                
                String StringLevelOne = req.getParameter("LO");
                if(StringLevelOne.compareTo("")!=0){
                    int IntLevelOne = new Integer(StringLevelOne).intValue();
                    
                    //String StringLevelTwo = req.getParameter("levelTwo");
                }
            }
            else if(isSubmit(req, "whodat")){
                resetFormData(MugenFormDataManagerFactory.MP_LO, req);
            }
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Pheno Assignment Action Failed.");
        }
    }      
}
