/*
 * RemoveGenModAction.java
 *
 * Created on December 15, 2005, 4:13 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.model;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lami
 */
public class RemoveMutationTypeAction extends MugenAction {
    
    /** Creates a new instance of RemoveGenModAction */
    public RemoveMutationTypeAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "RemoveMutationTypeAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = request.getSession();
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");                        
            
            String strainalleleid = request.getParameter("strainalleleid");
            String mutationid = request.getParameter("mutationtypeid");

            String mutation = modelManager.removeMutationTypeFromStrainAllele(new Integer(mutationid).intValue(), new Integer(strainalleleid).intValue(), caller);
            String isTransgenic = modelManager.getIsStrainAlleleTransgenicFactor(new Integer(strainalleleid).intValue(), caller);
            
            session.setAttribute("strainalleleid", strainalleleid);
            //session.setAttribute("isatransgc", (String)request.getParameter("isatransgc"));
            //session.setAttribute("isatransgc", isTransgenic);
            request.setAttribute("isatransgc", isTransgenic);
            
            String eid = "";
            eid = (String)session.getAttribute("eid");
            
            projectManager.log("user "+caller.getName()+" removed mutation type "+mutationid+" from allele "+strainalleleid+" from model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            
            session.setAttribute("eid", eid);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ApplicationException)
                throw new ApplicationException(e.getMessage());                
        }
        
        return false;
    }        
}
