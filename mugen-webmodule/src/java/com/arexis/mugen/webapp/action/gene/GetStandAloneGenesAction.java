package com.arexis.mugen.webapp.action.gene;
import com.arexis.arxframe.Navigator;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetStandAloneGenesAction extends MugenAction {
    
    public String getName() {
        return "GetStandAloneGenesAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = request.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");
            Collection genes = modelManager.getStandAloneGenes(caller.getPid(), caller);
            request.setAttribute("genes", genes);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("GetStandAloneGenesAction Failed To Perform Áction", e);
        }
    }
}
