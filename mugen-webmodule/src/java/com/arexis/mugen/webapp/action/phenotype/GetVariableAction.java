/*
 * GetVariableAction.java
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
import com.arexis.mugen.phenotype.phenotypemanager.VariableDTO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;




/**
 * MugenAction class for handling the retrieval of a variable
 * @author lami
 */
public class GetVariableAction extends MugenAction{
    
    /** Creates a new instance of GetVariableAction */
    public GetVariableAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetVariableAction";
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
            
            int vid = Integer.parseInt(req.getParameter("vid"));

            VariableDTO variable = phenotypeManager.getVariable(vid, caller);
            
            // Might seem as overkill but it makes the view cleaner to
            // do like this...
            TypeDTO d = new TypeDTO(variable.getType());            
            req.setAttribute("types", d);
            
            req.setAttribute("variabledto", variable);
            req.setAttribute("history", phenotypeManager.getVariableHistory(vid, caller));
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }   
}
