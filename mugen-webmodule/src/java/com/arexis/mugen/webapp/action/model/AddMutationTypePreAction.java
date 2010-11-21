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
public class AddMutationTypePreAction extends MugenAction
{
    
    /** Creates a new instance of GetStrainTypesAction */
    public AddMutationTypePreAction()
    {
    }

    /**
     * Returns the name of this action
     * @return The action name
     */
    public String getName() {
        return "AddMutationTypePreAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");                                                              
            
            int id = new Integer(req.getParameter("strainalleleid")).intValue();
            workflow.setAttribute("strainalleleid", req.getParameter("strainalleleid"));
           
            //req.setAttribute("types", modelManager.getMutationTypesFromStrainAllele(id, caller));
            //--------------DUDE_TEST_STRAIN_ALLELE
            //req.setAttribute("types", modelManager.getMutationTypes(caller.getPid(), caller));
            req.setAttribute("types", modelManager.getUnassignedMutationTypes(id, caller));
            
            String eid = "";
            eid = (String)se.getAttribute("eid");
            se.setAttribute("eid", eid);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("AddMutationTypePreAction failed to perform action.", e);
        }
    }     
}
