package com.arexis.mugen.webapp.action.model;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetStrainAction extends MugenAction
{
    
    public GetStrainAction() {}

    public String getName() {
        return "GetStrainAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");                                                              
            
            String strainid = req.getParameter("strainid");
            if (strainid==null || strainid=="")
                strainid = (String)se.getAttribute("viewedstrainid");
            else
                se.setAttribute("viewedstrainid", strainid);
            
            int id = Integer.valueOf(strainid).intValue();
            
            req.setAttribute("straindto", modelManager.getStrain(id, caller, "superscript"));
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve model.", e);
        }
    }     
}
