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

public class UpdatePhenoPathAction extends MugenAction {

    public UpdatePhenoPathAction() {
    }

    public String getName() {
        return "UpdatePhenoPathAction";
    }

    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            
            int eid = new Integer(req.getParameter("eid")).intValue();
            String mpold = req.getParameter("mpold");
            String mpnew = req.getParameter("mpnew");
            modelManager.replaceMP(eid, mpold, mpnew);
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Pheno Assignment Action Failed.");
        }
    }      
}
