/*
 * GetSpeciesDetailsAction.java
 *
 * Created on January 16, 2006, 8:16 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.admin;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.adminmanager.SpeciesDTO;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Gets info about a species.
 *
 * @author heto
 */
public class GetSpeciesDetailsAction extends MugenAction {
    
    /** Creates a new instance of GetSpeciesDetailsAction */
    public GetSpeciesDetailsAction() {
    }
    
    public String getName() {
        return "GetSpeciesDetailsAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            HttpSession session = request.getSession();
            
            String tmp = request.getParameter("sid");
            if (tmp==null)
                tmp = (String)session.getAttribute("GetSpeciesDetailsAction.sid");
            else
                session.setAttribute("GetSpeciesDetailsAction.sid", tmp);
            
            int sid = new Integer(tmp).intValue();
            
            SpeciesDTO species = adminManager.getSpecies(sid, caller);
            request.setAttribute("species", species);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Failed to get Species", e);
        }
    }
}
