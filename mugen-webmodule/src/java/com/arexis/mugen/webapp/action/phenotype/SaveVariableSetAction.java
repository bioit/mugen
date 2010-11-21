/*
 * SaveVariableSetAction.java
 *
 * Created on August 2, 2005, 12:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.phenotype;

import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;

/**
 * MugenAction class for saving a variable set
 * @author lami
 */
public class SaveVariableSetAction extends MugenAction {
    
    /**
     * Creates a new instance of SaveVariableSetAction 
     */
    public SaveVariableSetAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SaveVariableSetAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException { 
        int vsid = 0;
        try {
            HttpSession session = request.getSession();
             
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            
            String name = request.getParameter("name");                         
            String comm = request.getParameter("comm");  
            String tmpSuid = request.getParameter("suid");
            if(tmpSuid != null)
                caller.setSuid(Integer.parseInt(tmpSuid));
                
            if (isSubmit(request, "save")) {            
                vsid = Integer.parseInt(request.getParameter("vsid"));                                                                                                  
                phenotypeManager.updateVariableSet(vsid, name, comm, caller);
            }  
            else if(isSubmit(request, "remove")) {
                vsid = Integer.parseInt(request.getParameter("vsid"));  
                phenotypeManager.removeVariableSet(vsid, caller);                
            }

            else if(isSubmit(request, "new")) {         
                phenotypeManager.createVariableSet(name, comm, caller);              
            }

            return true;
        } catch (Exception e) {
            if(e instanceof ApplicationException)
                throw new ApplicationException(e.getMessage());
            else
                e.printStackTrace();
        }
        return false;
    }
}
