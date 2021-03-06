/*
 * SaveSpeciesAction.java
 *
 * Created on January 16, 2006, 9:05 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.admin;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author heto
 */
public class SaveSpeciesAction extends MugenAction {
    
    public String getName() {
        return "SaveSpeciesAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            
            String name = request.getParameter("name");
            String comm = request.getParameter("comm");
            
            String tmp = request.getParameter("sid");
            if (tmp!=null)
            {
                // Update
                int sid = new Integer(tmp).intValue();
                adminManager.updateSpecies(sid, name, comm, caller);
            }
            else
            {
                // Create
                adminManager.createSpecies(name, comm, caller);
            }            
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Failed to perform action", e);
        }
    }
}
