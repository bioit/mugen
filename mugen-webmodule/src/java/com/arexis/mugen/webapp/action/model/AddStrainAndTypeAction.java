/*
 * GetStrainTypesAction.java
 *
 * Created on July 10, 2006, 3:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.arexis.mugen.webapp.action.model;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author heto
 */
public class AddStrainAndTypeAction extends MugenAction
{
    
    /** Creates a new instance of GetStrainTypesAction */
    public AddStrainAndTypeAction()
    {
    }

    /**
     * Returns the name of this action
     * @return The action name
     */
    public String getName() {
        return "AddStrainAndTypeAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");                                                              
            
            int id = new Integer(workflow.getAttribute("strainid")).intValue();
            
            String stateString = req.getParameter("availablestates").trim();
            int state = 0;
            if (stateString!="" && stateString!=null) 
                state = new Integer(stateString).intValue();
            
            
            String typeString = req.getParameter("availabletypes").trim();
            System.out.println("AddStrainAndTypeAction#performAction: state="+stateString+", type="+typeString);
            int type = 0;
            if (typeString!="" && typeString!=null)
                type = new Integer(typeString).intValue();
            
            modelManager.addStrainAndTypeToStrain(id,type,state,caller);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not add strains and types.", e);
        }
    }     
}
