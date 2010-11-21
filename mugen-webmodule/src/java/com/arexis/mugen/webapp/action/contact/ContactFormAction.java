package com.arexis.mugen.webapp.action.contact;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.arxframe.mailer.ContactFormMailer;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ContactFormAction extends MugenAction {
    
    public String getName() {
        return "ContactFormAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            String name = request.getParameter("name");
            String mail = request.getParameter("mail");
            String reason = request.getParameter("reason");
            String message = request.getParameter("message");
            
            ContactFormMailer cfm = new ContactFormMailer();
            cfm.sendEmail(mail, "mmmdb@mugen-noe.org", reason, message, name);
            
            return true;
        } catch (Exception e) {
            throw new ApplicationException("Failed to perform action", e);
        }
    }
}
