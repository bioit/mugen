package com.arexis.mugen.webapp.action.questionnaire;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class AskPostAction extends MugenAction {
    private static Logger logger = Logger.getLogger(AskPostAction.class);
    
    public AskPostAction() {}
    
    public String getName() {
        return "AskPostAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = request.getSession();
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            
            int qsum = 33;
            
            String [] qs = new String[qsum];
            String name, inst, mail;
            
            for(int i = 1; i <= qsum; i++){
                if(exists(request.getParameter("q"+i))){
                    qs[i-1] = request.getParameter("q"+i);
                } else {
                    qs[i-1] = "";
                }
            }
            
            if(exists(request.getParameter("name"))){
                name = request.getParameter("name");
            } else {
                name = "???";
            }
            
            if(exists(request.getParameter("inst"))){
                inst = request.getParameter("inst");
            } else {
                inst = "???";
            }
            
            if(exists(request.getParameter("mail"))){
                mail = request.getParameter("mail");
            } else {
                mail = "???";
            }
            
            int quid = modelManager.createQuestionnaireEntry(name, inst, mail, qs);
            
            projectManager.log("visitor "+name+" completed questionnaire entry with id "+quid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            
            return true;
            
        } catch (ApplicationException ae) {
             throw ae;     
        } catch (Exception e) {    
            throw new ApplicationException("AskPostAction Failed",e);
        }
    }     
}
