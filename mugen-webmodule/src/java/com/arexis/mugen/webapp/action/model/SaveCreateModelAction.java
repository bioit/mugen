package com.arexis.mugen.webapp.action.model;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class SaveCreateModelAction extends MugenAction {
    private static Logger logger = Logger.getLogger(SaveCreateModelAction.class);
    
    public SaveCreateModelAction() {}
    
    public String getName() {
        return "SaveCreateModelAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = request.getSession();
            MugenCaller caller = (MugenCaller)session.getAttribute("caller"); 
            
            String suid = request.getParameter("suid");
            if (suid!=null)
                caller.setSuid(Integer.parseInt(suid));
            
            String alias = request.getParameter("alias");
            String availability = request.getParameter("availability");
            String raid = request.getParameter("raid");
            String geneticBackground = request.getParameter("geneticBackground");
            String researchAppsText = request.getParameter("researchAppsText");
            String contactId = request.getParameter("contactId");
            String comm = request.getParameter("comm");
            String desired_level = request.getParameter("desired_level");
            
            logger.debug("SamplingUnitId: "+caller.getSuid());

            int eid = modelManager.createModel(caller.getSuid(), alias, geneticBackground, availability, Integer.parseInt(raid), researchAppsText, Integer.parseInt(contactId), caller, comm, desired_level);
            
            projectManager.log("user "+caller.getName()+" created model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            
            return true;
            
        } catch (ApplicationException ae) {
             throw ae;     
        } catch (Exception e) {    
            throw new ApplicationException("SaveCreateModelAction Failed",e);
        }
    }     
}
