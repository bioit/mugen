package com.arexis.mugen.webapp.action.model;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SaveStrainAction extends MugenAction
{
    
    public SaveStrainAction() {}

    public String getName() {
        return "SaveStrainAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");                                                              
            
            int id = new Integer((String)se.getAttribute("viewedstrainid")).intValue();
            
            String designation = req.getParameter("designation");
            String mgiid = req.getParameter("mgiid");
            String emmaid = req.getParameter("emmaid");
            
            modelManager.updateStrain(id, designation, mgiid, emmaid, caller);
            
            projectManager.log("user "+caller.getName()+" edited strain "+id, getName(), caller.getName(), req.getRemoteAddr(), req.getRemoteHost());
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not add strains and types.", e);
        }
    }     
}
