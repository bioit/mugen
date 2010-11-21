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
public class CreateResourceAction extends MugenAction {
    
    /** Creates a new instance of CreateProjectLinkResourceAction */
    public CreateResourceAction() {
    }
    
    public String getName() {
        return "CreateResourceAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = request.getSession();            
            
            String url = request.getParameter("url");
            String comm = request.getParameter("comm");            
            String name = request.getParameter("name");
                                    
            String pidId = request.getParameter("id");            
            String catId = request.getParameter("catId");
            
            int suid = new Integer(request.getParameter("suid")).intValue();
            
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");        
            samplingUnitManager.addResource("weblink", Integer.parseInt(catId), Integer.parseInt(pidId), name, comm, null, caller, url, suid);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }      
}
