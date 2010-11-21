/*
 * GetUVariableSetAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.phenotype;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;




/**
 * MugenAction class for handling the retrieval of a unified variable set
 * @author lami
 */
public class GetUVariableSetAction extends MugenAction{
    
    /** Creates a new instance of GetUVariableSetAction */
    public GetUVariableSetAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetUVariableSetAction";
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
            
            String tmpUvsid = req.getParameter("uvsid");
            if(tmpUvsid == null) {
                req.setAttribute("species", samplingUnitManager.getSpeciesForProject(caller.getPid(), caller));           
            }
            else {
                int uvsid = Integer.parseInt(tmpUvsid);
            
                req.setAttribute("uvariablesetdto", phenotypeManager.getUVariableSet(uvsid, caller));           
                req.setAttribute("history", phenotypeManager.getUVariableSetHistory(uvsid, caller));                           
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }   
}
