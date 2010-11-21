/*
 * GetGroupMembershipAction.java
 *
 * Created on July 13, 2005, 1:30 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;
import com.arexis.arxframe.Navigator;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;


/**
 * MugenAction class for handling of group membership
 * @author lami
 */
public class SetGroupMembershipAction extends MugenAction {
    
    /** Creates a new instance of GetGroupMembershipAction */
    public SetGroupMembershipAction() {
        
    }
    
    /**
     * Returns the name of the action
     * @return The name of the action
     */
    public String getName() {
        return "SetGroupMembershipAction";
    }
    
    /**
     * Performs the action
     * @param req The http request
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = req.getSession();
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator"); 
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");    
            
            if(isSubmit(req, "reset"))
                resetFormData(MugenFormDataManagerFactory.GROUP_MEMBERSHIP_FILTER, req);
            else
                collectFormData(MugenFormDataManagerFactory.GROUP_MEMBERSHIP_FILTER, MugenFormDataManagerFactory.WEB_FORM, req);            
            
            String tmpSuid = req.getParameter("suid");
            if(exists(tmpSuid))
                caller.setSuid(Integer.parseInt(tmpSuid));
            
            String tmpGid = req.getParameter("gid");

            if(exists(tmpGid)) {
                // Add new individuals to the group
                if(isSubmit(req, "add")) {
                    String[] add = req.getParameterValues("available");
                    for(int j=0;j<add.length;j++)
                        samplingUnitManager.addIndInGroup(Integer.parseInt(tmpGid), Integer.parseInt(add[j]), caller);
                }

                // Remove individuals from the group
                if(isSubmit(req, "remove")){
                    String[] remove = req.getParameterValues("included");
                    for(int j=0;j<remove.length;j++)
                        samplingUnitManager.removeIndInGroup(Integer.parseInt(tmpGid), Integer.parseInt(remove[j]), caller);
                }  
            }
            
            return true;
        } catch (ApplicationException e) {
            throw new ApplicationException("Could not get group membership information: "+e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get group membership information.");
        }
    } 
}
