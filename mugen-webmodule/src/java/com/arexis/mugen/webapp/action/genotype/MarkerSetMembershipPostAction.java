/*
 * MarkerSetMembershipPostAction.java
 *
 * Created on November 25, 2005, 9:29 AM
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
public class MarkerSetMembershipPostAction extends MugenAction {
    
    /** Creates a new instance of MarkerSetMembershipPostAction */
    public MarkerSetMembershipPostAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "MarkerSetMembershipPostAction";
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
                resetFormData(MugenFormDataManagerFactory.MARKER_SET_MEMBERSHIP_FILTER, request);
            else
                collectFormData(MugenFormDataManagerFactory.MARKER_SET_MEMBERSHIP_FILTER, MugenFormDataManagerFactory.WEB_FORM, request);            
            
            int cid = 0;
            int msid = 0;
            
            String tmpSuid = request.getParameter("suid");
            if(tmpSuid != null)
                caller.setSuid(Integer.parseInt(tmpSuid));
                
            String tmpMsid = request.getParameter("msid");
            if(exists(tmpMsid)) {
                // Add new markers to the set
                msid = Integer.parseInt(tmpMsid);
                if(isSubmit(request, "add")) 
                {
                    String[] add = request.getParameterValues("available");
                    if(add != null) {
                        for(int j=0;j<add.length;j++)
                            genotypeManager.addMarkerInSet(msid, Integer.parseInt(add[j]), caller);
                    }
                }

                // Remove markers from the set
                if(isSubmit(request, "remove"))
                {
                    String[] remove = request.getParameterValues("included");
                    if(remove != null) {
                        for(int j=0;j<remove.length;j++)
                            genotypeManager.removeMarkerInSet(msid, Integer.parseInt(remove[j]), caller);
                    }
                }
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActionException("Error in post action for marker set membership.");
        }
    }    
}
