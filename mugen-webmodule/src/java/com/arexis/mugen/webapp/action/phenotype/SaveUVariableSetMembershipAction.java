/*
 * SaveVariableSetMembershipAction.java
 *
 * Created on July 13, 2005, 1:30 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.phenotype;
import com.arexis.arxframe.ActionException;
import com.arexis.arxframe.Navigator;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;


/**
 * MugenAction class for handling of Uvariable set membership
 * @author lami
 */
public class SaveUVariableSetMembershipAction extends MugenAction {
    
    /** Creates a new instance of SaveUVariableSetMembershipAction */
    public SaveUVariableSetMembershipAction() {
        
    }
    
    /**
     * Returns the name of the action
     * @return The name of the action
     */
    public String getName() {
        return "SaveUVariableSetMembershipAction";
    }
    
    /**
     * Performs the action
     * @param req The http request
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ActionException {
        try {
            HttpSession session = req.getSession();
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator"); 
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");    
            
            Iterator i = null;
            
            int uvsid = 0;            
            int sid = caller.getSid();
            if(isSubmit(req, "reset"))
                resetFormData(MugenFormDataManagerFactory.U_VARIABLE_SET_MEMBERSHIP_FILTER, req);
            else
                collectFormData(MugenFormDataManagerFactory.U_VARIABLE_SET_MEMBERSHIP_FILTER, MugenFormDataManagerFactory.WEB_FORM, req);
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.U_VARIABLE_SET_MEMBERSHIP_FILTER, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);                
            
            // Get the suid value, if it has not changed then it is set to default...
            // i.e. the same as before in caller
            String tmpSid = formDataManager.getValue("sid");
            if(exists(tmpSid))                            
                caller.setSuid(Integer.parseInt(tmpSid));     
            
            String tmpUvsid = formDataManager.getValue("uvsid");
            if(exists(tmpUvsid))
                uvsid = Integer.parseInt(tmpUvsid);
              
            
            // Add new variables to the set
            if(isSubmit(req, "add") && uvsid > 0){
                String[] add = req.getParameterValues("available");
                phenotypeManager.addUVarsInSet(uvsid, add, caller);
            }

            // Remove variables from the set
            if(isSubmit(req, "remove") && uvsid > 0){
                String[] remove = req.getParameterValues("included");
                phenotypeManager.removeUVarsInSet(uvsid, remove, caller);
            }                                   
            
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
