/*
 * SaveUVariableMappingAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.phenotype;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;





/**
 * MugenAction class for handling the storing of a unified variable mapping
 * @author lami
 */
public class SaveUVariableMappingAction extends MugenAction{
    
    /** Creates a new instance of SaveUVariableMappingAction */
    public SaveUVariableMappingAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "SaveUVariableMappingAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException { 
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");
            String tmpVid = req.getParameter("vid");
            String tmpUvid = req.getParameter("uvid");
                        
            String tmpSuid = req.getParameter("suid");
            if(tmpSuid != null)
                caller.setSuid(Integer.parseInt(tmpSuid));            
            

            
            if (isSubmit(req, "save")) { 
//                if(tmpVid == null || tmpUvid == null)
//                    return true;
                
                int vid = Integer.parseInt(tmpVid);
                int uvid = Integer.parseInt(tmpUvid);
                phenotypeManager.createUVariableMapping(uvid, vid, caller);
            }
            
            req.setAttribute("uvid", tmpUvid);
            req.setAttribute("vid", tmpVid);
            
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
