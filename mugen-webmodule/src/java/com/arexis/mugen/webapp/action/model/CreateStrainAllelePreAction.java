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
public class CreateStrainAllelePreAction extends MugenAction
{
    
    /** Creates a new instance of GetStrainTypesAction */
    public CreateStrainAllelePreAction()
    {
    }

    /**
     * Returns the name of this action
     * @return The action name
     */
    public String getName() {
        return "CreateStrainAllelePreAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");   
            
            String id = req.getParameter("strainid");
            workflow.setAttribute("strainid", id);
            
            //-------------DUDE_TEST_STRAIN_ALLELE
            //collect all mutation types to assign only one at first for the strain allele's creation
            req.setAttribute("types", modelManager.getMutationTypes(caller.getPid(), caller));
            
            return true;
        
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not create strains allele", e);
        }
    }     
}
