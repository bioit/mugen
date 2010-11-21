package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RemoveReferenceActionSimple extends MugenAction {
    
    public RemoveReferenceActionSimple() {}
    
    public String getName() {
        return "RemoveReferenceActionSimple";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = request.getSession();
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.EXPMODEL, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    request); 
            
            String refid = request.getParameter("refid");
            String eid = formDataManager.getValue("eid");

            modelManager.removeReference(Integer.parseInt(eid), Integer.parseInt(refid), caller);
            
            projectManager.log("user "+caller.getName()+" removed reference "+refid+" from model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ApplicationException)
                throw new ApplicationException(e.getMessage());                
        }
        
        return false; 
    }
}
