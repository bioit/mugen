/*
 * LogoutAction.java
 *
 * Created on July 14, 2005, 6:01 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * MugenAction class for logout operation
 * @author heto
 */
public class LogoutAction extends MugenAction {
    
    /** Creates a new instance of LogoutAction */
    public LogoutAction() {
    }
    
    /**
     * Returns the name of the action
     * @return The name of the action
     */
    public String getName() {
        return "LogoutAction";
    }
    
    /**
     * Performs the action
     * @param request The http request
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) {
        try {
           
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            String callerStr = caller.getId()+", "+caller.getName();
            
            if(caller!=null){
                projectManager.log("user "+caller.getName()+" logged out", "Logout", caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
                //clear session    
                request.getSession().invalidate();
                //get the new caller
                MugenCaller newcaller = projectManager.login("public", "notknown");
                //get a new navigator
                Navigator nav = new Navigator();
                //add the caller to the session
                request.getSession().setAttribute("caller", newcaller);
                //add the new navigator to the session
                request.getSession().setAttribute("navigator", nav);
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
