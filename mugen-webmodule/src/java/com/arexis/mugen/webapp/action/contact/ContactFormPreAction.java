package com.arexis.mugen.webapp.action.contact;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.arxframe.mailer.ContactFormMailer;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ContactFormPreAction extends MugenAction {
    
    public String getName() {
        return "ContactFormPreAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            request.setAttribute("reasons",modelManager.getContactReason());
            return true;
        } catch (Exception e) {
            throw new ApplicationException("Failed to perform action", e);
        }
    }
}
