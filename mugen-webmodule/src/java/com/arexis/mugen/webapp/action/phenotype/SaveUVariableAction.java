/*
 * SaveUVariableAction.java
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
 * MugenAction class for saving an unified variable
 * @author lami
 */
public class SaveUVariableAction extends MugenAction {
    
    /**
     * Creates a new instance of SaveVariableAction 
     */
    public SaveUVariableAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SaveUVariableAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException { 
        int uvid = 0;
        int sid = 0;
        try {
            HttpSession session = request.getSession();
             
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            
            String name = request.getParameter("name");
            String unit = request.getParameter("unit");                           
            String comm = request.getParameter("comm");  
            String type = request.getParameter("type");             
            
                
            if (isSubmit(request, "save")) {            
                uvid = Integer.parseInt(request.getParameter("uvid"));
                sid = Integer.parseInt(request.getParameter("sid"));
                phenotypeManager.updateUVariable(uvid, sid, name, unit, type, comm, caller);
            }  
            else if(isSubmit(request, "remove")) {
                uvid = Integer.parseInt(request.getParameter("uvid"));  
                phenotypeManager.removeUVariable(uvid, caller);                
            }

            else if(isSubmit(request, "new")) {         
                sid = Integer.parseInt(request.getParameter("sid"));
                phenotypeManager.createUVariable(name, unit, type, comm, caller, sid);              
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
