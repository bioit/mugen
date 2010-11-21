package com.arexis.mugen.webapp.action.model;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class RemoveStrainAlleleAction extends MugenAction {
    
    public String getName() {
        return "RemoveStrainAlleleAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            
            String strainAlleleId = request.getParameter("strainalleleid");
            String eid = request.getParameter("eid");
            
            modelManager.removeStrainAllele(new Integer(eid).intValue(), new Integer(strainAlleleId).intValue(), caller);
            
            projectManager.log("user "+caller.getName()+" removed allele "+strainAlleleId+" from model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("RemoveStrainAlleleAction failed to perform action", e);
        }
    }
}
