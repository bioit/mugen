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
 * MugenAction class for handling of variable set membership
 * @author lami
 */
public class SaveVariableSetMembershipAction extends MugenAction {
    
    /** Creates a new instance of SaveVariableSetMembershipAction */
    public SaveVariableSetMembershipAction() {
        
    }
    
    /**
     * Returns the name of the action
     * @return The name of the action
     */
    public String getName() {
        return "SaveVariableSetMembershipAction";
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
            
            int vsid = 0;
            vsid = new Integer(workflow.getAttribute("vsid")).intValue();
            
            /*
            Iterator i = null;
            
            int sid = caller.getSid();      
            
            if(isSubmit(req, "reset"))
                resetFormData(MugenFormDataManagerFactory.VARIABLE_SET_MEMBERSHIP_FILTER, req);
            else
                collectFormData(MugenFormDataManagerFactory.VARIABLE_SET_MEMBERSHIP_FILTER, MugenFormDataManagerFactory.WEB_FORM, req);                                                                                       
            
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.VARIABLE_SET_MEMBERSHIP_FILTER, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);                
            
            // Get the suid value, if it has not changed then it is set to default...
            // i.e. the same as before in caller
            String tmpSuid = formDataManager.getValue("suid");
            if(exists(tmpSuid))                            
                caller.setSuid(Integer.parseInt(tmpSuid));     
            
            String tmpVsid = formDataManager.getValue("vsid");
            if(exists(tmpVsid))
                vsid = Integer.parseInt(tmpVsid);    
            */
            
            // Add new variables to the set
            if(isSubmit(req, "add") && vsid > 0){
                String[] add = req.getParameterValues("available");
                phenotypeManager.addVarsInSet(vsid, add, caller);
            }

            // Remove variables from the set
            if(isSubmit(req, "remove") && vsid > 0){
                String[] remove = req.getParameterValues("included");
                phenotypeManager.removeVarsInSet(vsid, remove, caller);
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
