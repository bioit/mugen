/*
 * CreateFuncSigAction.java
 *
 * Created on December 16, 2005, 3:07 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.model;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lami
 */
public class CreateFuncSigAction extends MugenAction {
    
    /** Creates a new instance of CreateFuncSigAction */
    public CreateFuncSigAction() {
    }

    /**
     * Returns the name of this action
     * @return The action name
     */
    public String getName() {
        return "CreateFuncSigAction";
    }
    
    /**
     * Peroforms the action
     * @param req The http request object
     * @param context The servlet context
     * @return True of this action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");     
            
            req.setAttribute("types", modelManager.getFunctionalSignificanceTypes(caller));
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed in preaction for creation of functional significance.");
        }
    }      
}
