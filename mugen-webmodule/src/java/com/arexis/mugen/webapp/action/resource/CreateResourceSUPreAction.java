/*
 * CreateProjectLinkResourceAction.java
 *
 * Created on January 20, 2006, 10:43 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.resource;

import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lami
 */
public class CreateResourceSUPreAction extends MugenAction {
    
    /** Creates a new instance of CreateProjectLinkResourceAction */
    public CreateResourceSUPreAction() {
    }
    
    public String getName() {
        return "CreateResourceAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = request.getSession();            
            
            String suid = request.getParameter("suid");
            workflow.setAttribute("suid", suid);
            
            System.out.println("suid="+suid);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }      
}
