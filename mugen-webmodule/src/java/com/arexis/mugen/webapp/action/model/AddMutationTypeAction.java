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
public class AddMutationTypeAction extends MugenAction
{
    
    /** Creates a new instance of GetStrainTypesAction */
    public AddMutationTypeAction()
    {
    }

    /**
     * Returns the name of this action
     * @return The action name
     */
    public String getName() {
        return "AddMutationTypeAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");                                                              
            
            int id = new Integer(workflow.getAttribute("strainalleleid")).intValue();
            String type = req.getParameter("type");
            
            modelManager.addMutationTypeToStrainAllele(new Integer(type).intValue(),id,caller);
            //se.setAttribute("isatransgc", modelManager.getIsStrainAlleleTransgenicFactor(id,caller));
            req.setAttribute("isatransgc", modelManager.getIsStrainAlleleTransgenicFactor(id,caller));
            
            String eid = "";
            eid = (String)se.getAttribute("eid");
            
            projectManager.log("user "+caller.getName()+" added mutation type "+type+" on allele "+id+" from model "+eid, getName(), caller.getName(), req.getRemoteAddr(), req.getRemoteHost());
            
            se.setAttribute("eid", eid);
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve model.", e);
        }
    }     
}
