/*
 * UMarkerSetMembershipPostAction.java
 *
 * Created on November 28, 2005, 12:40 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;

import com.arexis.arxframe.ActionException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lami
 */
public class UMarkerSetMembershipPostAction extends MugenAction {
    
    /** Creates a new instance of UMarkerSetMembershipPostAction */
    public UMarkerSetMembershipPostAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "UMarkerSetMembershipPostAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ActionException {
        try {
            HttpSession session = request.getSession();
            
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            if(isSubmit(request, "reset"))
                resetFormData(MugenFormDataManagerFactory.U_MARKER_SET_MEMBERSHIP_FILTER, request);
            else
                collectFormData(MugenFormDataManagerFactory.U_MARKER_SET_MEMBERSHIP_FILTER, MugenFormDataManagerFactory.WEB_FORM, request);                        
            
            String tmpSid = request.getParameter("sid");
            if(tmpSid != null)
                caller.setSid(Integer.parseInt(tmpSid));
                
            String tmpUmsid = request.getParameter("umsid");
            if(exists(tmpUmsid)) {
                int umsid = Integer.parseInt(tmpUmsid);
                // Add new markers to the set ?
                if(isSubmit(request, "add")) {
                    String[] add = request.getParameterValues("available");
                    if(add != null) {
                        for(int j=0;j<add.length;j++)
                            genotypeManager.addUMarkerInSet(umsid, Integer.parseInt(add[j]), caller);
                    }
                }

                // Remove markers from the set ?
                if(isSubmit(request, "remove")) {
                    String[] remove = request.getParameterValues("included");
                    if(remove != null) {
                        for(int j=0;j<remove.length;j++)
                            genotypeManager.removeUMarkerInSet(umsid, Integer.parseInt(remove[j]), caller);
                    }
                }       
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActionException("Error in post action for unified marker set membership.");
        }
    }       
}
