/*
 * DeleteUVariableMappingAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.phenotype;

import com.arexis.arxframe.ActionException;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;




/**
 * MugenAction class for handling the deletion of a unified variable mapping
 * @author lami
 */
public class DeleteUVariableMappingAction extends MugenAction{
    
    /** Creates a new instance of DeleteUVariableMappingAction */
    public DeleteUVariableMappingAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "DeleteUVariableMappingAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ActionException { 
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");            
            
            int uvid = Integer.parseInt(req.getParameter("uvid"));
            int vid = Integer.parseInt(req.getParameter("vid"));

            phenotypeManager.removeUVariableMapping(uvid, vid, caller);
           
            return true;
        } catch (Exception e) {
            if(e instanceof ApplicationException)
                throw new ActionException(e.getMessage());
            else
                e.printStackTrace();
        }
        return false;
    }   
}
