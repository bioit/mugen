/*
 * UVariablesPostAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.phenotype;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;


/**
 * 
 * @author lami
 */
public class UVariablesPostAction extends MugenAction{
    
    /** Creates a new instance of UVariablePostAction */
    public UVariablesPostAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "UVariablesPostAction";
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
            
            String tmpSid = req.getParameter("sid");
            if(exists(tmpSid)) {                                   
                caller.setSid(Integer.parseInt(tmpSid));
            }             
            
            if(isSubmit(req, "reset"))
                resetFormData(MugenFormDataManagerFactory.U_VARIABLE, req);
            else
                collectFormData(MugenFormDataManagerFactory.U_VARIABLE, MugenFormDataManagerFactory.WEB_FORM, req);                                                                                       
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }   
}
