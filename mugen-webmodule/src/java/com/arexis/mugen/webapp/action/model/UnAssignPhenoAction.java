package com.arexis.mugen.webapp.action.model;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class UnAssignPhenoAction extends MugenAction {
    
    public String getName() {
        return "UnAssignPhenoAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            
            int eid = new Integer(request.getParameter("eid")).intValue();
            int mp01 = new Integer(request.getParameter("mp01")).intValue();
            int mp02 = new Integer(request.getParameter("mp02")).intValue();
            int mp03 = new Integer(request.getParameter("mp03")).intValue();
            int mp04 = new Integer(request.getParameter("mp04")).intValue();
            int mp05 = new Integer(request.getParameter("mp05")).intValue();
            int mp06 = new Integer(request.getParameter("mp06")).intValue();
            int mp07 = new Integer(request.getParameter("mp07")).intValue();
            int mp08 = new Integer(request.getParameter("mp08")).intValue();
            int mp09 = new Integer(request.getParameter("mp09")).intValue();
            
            modelManager.removePhenoFromModel(eid, mp01, mp02, mp03, mp04, mp05, mp06, mp07, mp08, mp09, caller);
            
            projectManager.log("user "+caller.getName()+" removed mp term from model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Failed to remove ontology", e);
        }
    }
}
