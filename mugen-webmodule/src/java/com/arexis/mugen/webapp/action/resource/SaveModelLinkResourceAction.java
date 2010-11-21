package com.arexis.mugen.webapp.action.resource;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SaveModelLinkResourceAction extends MugenAction {
    
    public SaveModelLinkResourceAction() {
    }
    
    public String getName() {
        return "SaveLinkResourceAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = request.getSession();
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.EXPMODEL, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    request);             
            
            String eid = formDataManager.getValue("eid");
            
            if (isSubmit(request, "create")) {
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");
                String url = request.getParameter("url");
                int catid = new Integer(request.getParameter("catid")).intValue();
                
                int resid = modelManager.addLinkResource(Integer.parseInt(eid), name, comm, url, catid, caller);
                
                projectManager.log("user "+caller.getName()+" added link resource "+resid+" to model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
                
            } else if (isSubmit(request, "save")) {
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");
                String url = request.getParameter("url");                

                resourceManager.updateLink(Integer.parseInt(formDataManager.getValue("linkid")), name, url, comm, caller);
                modelManager.updateModelTimestamp(Integer.parseInt(eid), caller);
                
                projectManager.log("user "+caller.getName()+" updated link resource "+Integer.parseInt(formDataManager.getValue("linkid"))+" from model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            } 

            return true;
        } catch (Exception e) {
            if(e instanceof ApplicationException)
                throw new ApplicationException(e.getMessage());
            else
                e.printStackTrace();
        }
        
        return false;
    }      
}
