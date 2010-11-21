/*
 * LoginAction.java
 *
 * Created on July 11, 2005, 4:04 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


/**
 * MugenAction class for setting language
 * @author heto
 */
public class LanguageAction extends MugenAction {
    
    /** Creates a new instance of LoginAction */
    public LanguageAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName()
    {
        return "LanguageAction";
    }
    
    /**
     * Performs this action
     * @param req The http request object
     * @param context The servlet context
     * @return True if this action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context)
    {
        System.out.println("LanguageAction performed");
        
        return true;
    }
    
}
