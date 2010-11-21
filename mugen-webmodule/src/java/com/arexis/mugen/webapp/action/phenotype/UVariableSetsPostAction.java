/*
 * UVariableSetsPostAction.java
 *
 * Created on November 22, 2005, 1:35 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.phenotype;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author lami
 */
public class UVariableSetsPostAction extends MugenAction {
    
    /** Creates a new instance of UVariableSetsPostAction */
    public UVariableSetsPostAction() {
        super();
    }
    
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "UVariableSetsPostAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) {
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");                        
            
            if(isSubmit(req, "reset"))
                resetFormData(MugenFormDataManagerFactory.U_VARIABLE_SET_FILTER, req);
            else
                collectFormData(MugenFormDataManagerFactory.U_VARIABLE_SET_FILTER, MugenFormDataManagerFactory.WEB_FORM, req);        
            
            // Get the sid value, if it has not changed then it is set to default...
            // i.e. the same as before in caller
            String tmpSid = req.getParameter("sid");
            if(exists(tmpSid))                            
                caller.setSuid(Integer.parseInt(tmpSid));             
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }     
}
