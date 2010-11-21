/*
 * CreateFuncSigTypeAction.java
 *
 * Created on February 22, 2006, 10:13 AM
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


/**
 *
 * @author heto
 */
public class CreateFuncSigTypeAction extends MugenAction {
    
    public String getName() {
        return "CreateFuncSigTypeAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            String name = request.getParameter("name");
            String comm = request.getParameter("comm");
            
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            
            modelManager.createFuncSigType(name, comm, caller);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Failed to perform action", e);
        }
    }
}
