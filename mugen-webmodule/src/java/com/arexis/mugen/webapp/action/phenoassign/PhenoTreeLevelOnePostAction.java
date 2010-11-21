package com.arexis.mugen.webapp.action.phenoassign;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PhenoTreeLevelOnePostAction extends MugenAction {

    public PhenoTreeLevelOnePostAction() {
    }

    public String getName() {
        return "PhenoTreeLevelOnePostAction";
    }

    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            
            collectFormData(MugenFormDataManagerFactory.PHENOTREE, MugenFormDataManagerFactory.WEB_FORM, req);                                                                    
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Pheno Tree Post Action Failed.");
        }
    }      
}
