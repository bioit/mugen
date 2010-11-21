/*
 * CreateResourceCategoryAction.java
 *
 * Created on January 18, 2006, 4:05 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.admin;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author lami
 */
public class CreateResourceCategoryAction extends MugenAction {
    
    /** Creates a new instance of CreateResourceCategoryAction */
    public CreateResourceCategoryAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "CreateResourceCategoryAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            
            request.setAttribute("pid", request.getParameter("pid"));
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }    
}
