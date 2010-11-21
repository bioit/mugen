package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetFuncSigTypesAction extends MugenAction {
    
    public GetFuncSigTypesAction() {}

    public String getName() {
        return "GetFuncSigTypesAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");            
            Navigator nav = (Navigator)se.getAttribute("navigator");                                                     
            Collection funcsigTypes = modelManager.getFunctionalSignificanceTypes(caller);
            
            req.setAttribute("funcsigtypes", funcsigTypes);    
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve functional significance types.");
        }
    }     
}
