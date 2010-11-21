package com.arexis.mugen.webapp.action.model;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RemoveAvailableGeneticBackgroundAction extends MugenAction {
    
    public RemoveAvailableGeneticBackgroundAction() {}
    
    public String getName() {
        return "RemoveAvailableGeneticBackgroundAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = request.getSession();
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            
            int aid = new Integer(request.getParameter("aid")).intValue();
            modelManager.removeAvailableGeneticBackground(aid, caller);
            
            projectManager.log("user "+caller.getName()+" removed available genetic background "+aid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            
            ViewAvailableGeneticBackgroundsAction avgenbackaction = new ViewAvailableGeneticBackgroundsAction();
            avgenbackaction.performAction(request, context);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ApplicationException)
                throw new ApplicationException(e.getMessage());                
        }
        
        return false; 
    }    
}
