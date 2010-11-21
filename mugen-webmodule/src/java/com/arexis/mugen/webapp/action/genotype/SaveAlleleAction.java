/*
 * SaveAlleleAction.java
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
 * MugenAction class for saving an allele
 * @author lami
 */
public class SaveAlleleAction extends MugenAction {
    
    /**
     * Creates a new instance of SaveAlleleAction
     */
    public SaveAlleleAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SaveAlleleAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        int aid = 0;
        try {
            HttpSession session = request.getSession();
            
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            
            if (isSubmit(request, "save")) {
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");
                aid = Integer.parseInt(request.getParameter("aid"));
                genotypeManager.updateAllele(aid, comm, name, caller);
            } else if(isSubmit(request, "remove")) {
                aid = Integer.parseInt(request.getParameter("aid"));
                
                genotypeManager.removeAllele(aid, caller);
            } else if(isSubmit(request, "create")) {
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");
                
                int mid = Integer.parseInt(request.getParameter("mid"));
                
                genotypeManager.createAllele(caller, name, comm, mid);
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to update allele aid="+aid);
        }
    }
}
