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

public class RemovePhenoPathAction extends MugenAction {

    public RemovePhenoPathAction() {
    }

    public String getName() {
        return "RemovePhenoPathAction";
    }

    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            int eid = new Integer(req.getParameter("eid")).intValue();
            int ppid = new Integer(req.getParameter("ppid")).intValue();
            //modelManager.addPhenoPathToModel(eid, ppid, caller);
            modelManager.removePhenoPathFromModel(eid, ppid, caller);
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Pheno Assignment Action Failed.");
        }
    }      
}
