package com.arexis.mugen.webapp.action;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutExitAction extends MugenAction {
    
    /** Creates a new instance of LogoutAction */
    public LogoutExitAction() {
    }
    
    /**
     * Returns the name of the action
     * @return The name of the action
     */
    public String getName() {
        return "LogoutExitAction";
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
                projectManager.log("user "+caller.getName()+" closed browser", "CloseWindow", caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
                //clear session    
                request.getSession().invalidate();
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
