/*
 * SaveGroupingAction.java
 *
 * Created on August 2, 2005, 12:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;
import java.util.Enumeration;

/**
 * MugenAction class for saving a grouping
 * @author lami
 */
public class SaveGroupingAction extends MugenAction {
    
    /**
     * Creates a new instance of SaveGroupingAction 
     */
    public SaveGroupingAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SaveGroupingAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performec
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException { 
        int gsid = 0; 
        try {
            HttpSession session = request.getSession();

            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            Navigator nav = (Navigator)request.getSession().getAttribute("navigator");
            
            caller.setSuid(Integer.parseInt(request.getParameter("suid")));
            
            debugParameters(request);
            
            
            if (isSubmit(request,"create")) {
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");              
                
                samplingUnitManager.createGrouping(name, comm, caller, caller.getSuid());
            }  
            else if(isSubmit(request, "remove")){
                gsid = Integer.parseInt(request.getParameter("gsid"));   
                samplingUnitManager.removeGrouping(gsid, caller); 
            }
            else if(isSubmit(request, "cancel")){
                gsid = Integer.parseInt(request.getParameter("gsid"));   
                samplingUnitManager.removeGrouping(gsid, caller); 
            }   
            else if(isSubmit(request, "save")){
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");    
                gsid = Integer.parseInt(request.getParameter("gsid"));   
                samplingUnitManager.updateGrouping(gsid, name, comm, caller);
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
