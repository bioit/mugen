/*
 * GroupingsPostAction.java
 *
 * Created on November 29, 2005, 10:09 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;

import com.arexis.arxframe.ActionException;
import com.arexis.arxframe.Navigator;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author lami
 */
public class GroupingsPostAction extends MugenAction {
    
    /** Creates a new instance of GroupingsPostAction */
    public GroupingsPostAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GroupingsPostAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ActionException{
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");                                    
            String tmpSuid = req.getParameter("suid");
            if(tmpSuid != null)
                caller.setSuid(Integer.parseInt(tmpSuid));
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActionException("An error occured in groupings post action.");
        }
    }      
}
