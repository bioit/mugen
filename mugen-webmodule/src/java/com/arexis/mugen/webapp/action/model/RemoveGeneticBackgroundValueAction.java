package com.arexis.mugen.webapp.action.model;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RemoveGeneticBackgroundValueAction extends MugenAction {
    
    public RemoveGeneticBackgroundValueAction() {}
    
    public String getName() {
        return "RemoveGeneticBackgroundValueAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = request.getSession();
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            
            int bid = new Integer(request.getParameter("bid")).intValue();
            String delgenback = modelManager.removeGenBackValue(bid, caller);
            
            projectManager.log("user "+caller.getName()+" removed genetic background value "+delgenback, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            
            ViewGeneticBackgroundValuesPreAction genbackaction = new ViewGeneticBackgroundValuesPreAction();
            genbackaction.performAction(request, context);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ApplicationException)
                throw new ApplicationException(e.getMessage());                
        }
        
        return false; 
    }    
}
