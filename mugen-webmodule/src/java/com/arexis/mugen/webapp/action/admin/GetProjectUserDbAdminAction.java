/*
 * GetProjectUserDbAdminAction.java
 *
 * Created on December 20, 2005, 4:35 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.admin;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author heto
 */
public class GetProjectUserDbAdminAction extends MugenAction {
    
    /** Creates a new instance of GetProjectUserAction */
    public GetProjectUserDbAdminAction() {
    }
    
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "GetProjectUserDbAdminAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            String tmpId = request.getParameter("id");
            
            if(exists(tmpId)) {
                int id = Integer.parseInt(tmpId);
                request.setAttribute("user", projectManager.getUser(id, caller));
            }
            
            //Collection roles = projectManager.getRolesByProject(caller.getPid(), caller);
            //request.setAttribute("roles", roles);            
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }    
}