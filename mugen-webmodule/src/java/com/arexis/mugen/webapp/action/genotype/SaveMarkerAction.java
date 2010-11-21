/*
 * SaveMarkerAction.java
 *
 * Created on August 2, 2005, 12:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;

import com.arexis.arxframe.ActionException;
import com.arexis.arxframe.advanced.AlterWorkflowException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;

/**
 * MugenAction class for saving a marker
 * @author lami
 */
public class SaveMarkerAction extends MugenAction {
    
    /**
     * Creates a new instance of SaveMarkerAction
     */
    public SaveMarkerAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SaveMarkerAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ActionException {
        int mid = 0;
        try {
            HttpSession session = request.getSession();
            
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            String tmpSuid = request.getParameter("suid");              
            if(exists(tmpSuid))
                caller.setSuid(Integer.parseInt(tmpSuid));                                     
          
            if(isSubmit(request, "reset"))
                resetFormData(MugenFormDataManagerFactory.MARKER, request);
            else
                collectFormData(MugenFormDataManagerFactory.MARKER, MugenFormDataManagerFactory.WEB_FORM, request);                        
            
            if (isSubmit(request, "save")) {
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");
                String p1 = request.getParameter("p1");
                String p2 = request.getParameter("p2");
                String position = request.getParameter("position");
                String alias = request.getParameter("alias");
                String chromosome = request.getParameter("chromosome");
                mid = Integer.parseInt(request.getParameter("mid"));
                genotypeManager.updateMarker(mid, name, comm, p1, p2, Double.parseDouble(position), alias, Integer.parseInt(chromosome), caller);
            } else if(isSubmit(request, "remove")) {
                mid = Integer.parseInt(request.getParameter("mid"));
                
                genotypeManager.removeMarker(mid, caller);
            } else if(isSubmit(request, "create")) {
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");
                String p1 = request.getParameter("p1");
                String p2 = request.getParameter("p2");
                String position = request.getParameter("position");
                double pos = 0;
                if(exists(position))
                    pos = Double.parseDouble(position);
                String alias = request.getParameter("alias");
                String chromosome = request.getParameter("cname");
                int cid = 0;
                if(exists(chromosome))
                    cid = Integer.parseInt(chromosome);
                genotypeManager.createMarker(name, comm, p1, p2, pos, alias, cid, caller.getSuid(), caller);
            } else {
                throw new AlterWorkflowException("RELOAD");
                /*
                ActionException e = new ActionException("RELOAD");
                e.setAlt("RELOAD");
                throw e;
                 */
            }
            return true;
        } catch (ActionException e) {
            throw e;                    
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActionException("Failed to update marker");
        }
    }
}
