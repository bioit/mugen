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
public class CreateStrainAlleleAction extends MugenAction
{
    
    /** Creates a new instance of GetStrainTypesAction */
    public CreateStrainAlleleAction()
    {
    }

    /**
     * Returns the name of this action
     * @return The action name
     */
    public String getName() {
        return "CreateStrainAlleleAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");                                                              
            
            int id = new Integer(workflow.getAttribute("strainid")).intValue();
            
            String symbol = req.getParameter("symbol");
            String name = req.getParameter("name");
            String type = req.getParameter("type");
            
            int strainAlleleID = modelManager.createStrainAllele(id,symbol,name,caller);
            
            modelManager.addMutationTypeToStrainAllele(new Integer(type).intValue(),strainAlleleID,caller);
            
            projectManager.log("user "+caller.getName()+" created allele "+strainAlleleID+" for strain "+id, getName(), caller.getName(), req.getRemoteAddr(), req.getRemoteHost());
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not create strains allele", e);
        }
    }     
}
