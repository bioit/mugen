/*
 * GroupsPostAction.java
 *
 * Created on November 29, 2005, 11:14 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;

import com.arexis.arxframe.ActionException;
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
public class GroupsPostAction extends MugenAction {
    
    /** Creates a new instance of GroupsPostAction */
    public GroupsPostAction() {
    }

    /**
     * Returns the name of this action
     * @return The action name
     */
    public String getName() {
        return "GroupsPostAction";
    }
    
    /**
     * Peroforms the action
     * @param req The http request object
     * @param context The servlet context
     * @return True of this action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ActionException{

        int gsid = 0;
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator"); 
            
            String tmpSuid = req.getParameter("suid");
            if(exists(tmpSuid)) {                                   
                caller.setSuid(Integer.parseInt(tmpSuid));
            }             
            
            if(isSubmit(req, "reset"))
                resetFormData(MugenFormDataManagerFactory.GROUPING_FILTER, req);
            else
                collectFormData(MugenFormDataManagerFactory.GROUPING_FILTER, MugenFormDataManagerFactory.WEB_FORM, req);                                                           
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActionException("Could not retrieve groups.");
        }
    }     
}
