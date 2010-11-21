/*
 * GetUVariableAction.java
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
import com.arexis.mugen.phenotype.phenotypemanager.TypeDTO;
import com.arexis.mugen.phenotype.phenotypemanager.UVariableDTO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;




/**
 * MugenAction class for handling the retrieval of a unified variable
 * @author lami
 */
public class GetUVariableAction extends MugenAction{
    
    /** Creates a new instance of GetUVariableAction */
    public GetUVariableAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetUVariableAction";
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
            
            int uvid = Integer.parseInt(req.getParameter("uvid"));

            UVariableDTO uvariable = phenotypeManager.getUVariable(uvid, caller);
            
            // Might seem as overkill but it makes the view cleaner to
            // do like this...
            TypeDTO d = new TypeDTO(uvariable.getType());            
            req.setAttribute("types", d);
            
            req.setAttribute("uvariabledto", uvariable);
            req.setAttribute("history", phenotypeManager.getUVariableHistory(uvid, caller));
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }   
}
