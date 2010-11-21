/*
 * SaveUAlleleAction.java
 *
 * Created on August 2, 2005, 12:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;

import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;

/**
 * MugenAction class for saving an unified allele
 * @author lami
 */
public class SaveUAlleleAction extends MugenAction {
    
    /**
     * Creates a new instance of SaveUAlleleAction
     */
    public SaveUAlleleAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SaveUAlleleAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        int uaid = 0;
        try {
            HttpSession session = request.getSession();
            
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            
            if (isSubmit(request, "save")) {
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");
                uaid = Integer.parseInt(request.getParameter("uaid"));
                genotypeManager.updateUAllele(uaid, comm, name, caller);
            } else if(isSubmit(request, "remove")) {
                uaid = Integer.parseInt(request.getParameter("uaid"));
                
                genotypeManager.removeUAllele(uaid, caller);
            } else if(isSubmit(request, "create")) {
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");
                
                int umid = Integer.parseInt(request.getParameter("umid"));
                
                genotypeManager.createUAllele(caller, name, comm, umid);
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to update unified allele uaid="+uaid);
        }
    }
}
