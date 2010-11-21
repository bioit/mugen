/*
 * RequestLogAction.java
 *
 * Created on July 11, 2005, 10:51 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.arxframe;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;


/**
 * Action for retrieving the log
 * @author heto
 */
public class RequestLogAction extends Action {
    
    private static Logger logger = Logger.getLogger(RequestLogAction.class);
    
    /** Creates a new instance of RequestLogAction */
    public RequestLogAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName()
    {
        return "RequestLogAction";
    }
    
    /**
     * Performes the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context)
    {
        logger.debug("Request QS="+req.getQueryString());
        return true;
    }
    
}
