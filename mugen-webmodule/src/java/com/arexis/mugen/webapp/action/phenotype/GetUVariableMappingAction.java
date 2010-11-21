/*
 * GetUVariableMappingAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.phenotype;

import com.arexis.arxframe.Navigator;
import com.arexis.arxframe.advanced.State;
import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;
import javax.servlet.http.HttpSession;




/**
 * MugenAction class for handling the retrieval of a unified variable mapping
 * @author lami
 */
public class GetUVariableMappingAction extends MugenAction{
    
    /** Creates a new instance of GetUVariableMappingAction */
    public GetUVariableMappingAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetUVariableMappingAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException { 
        try {
            HttpSession se = req.getSession();
            
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");
            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");
            String tmpUvid = req.getParameter("uvid");
            Workflow wf = (Workflow)req.getAttribute("workflow");
            
            if(tmpUvid == null)                 
                tmpUvid = (String)wf.getAttribute("uvarmapping.uvid");
            else
                wf.setAttribute("uvarmapping.uvid", tmpUvid);
                
            int uvid = Integer.parseInt(tmpUvid);
            
            req.setAttribute("uvarmappings", phenotypeManager.getUVariableMappings(uvid, caller));
           
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
