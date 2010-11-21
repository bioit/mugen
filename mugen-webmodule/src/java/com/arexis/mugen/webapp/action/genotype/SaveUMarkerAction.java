/*
 * SaveUMarkerAction.java
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
 * MugenAction class for saving an unified marker
 * @author lami
 */
public class SaveUMarkerAction extends MugenAction {
    
    /**
     * Creates a new instance of SaveUMarkerAction
     */
    public SaveUMarkerAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SaveUMarkerAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ActionException {
        int umid = 0;
        try {
            HttpSession session = request.getSession();
             MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            String tmpSid = request.getParameter("sid");
            if(tmpSid != null)
                caller.setSid(Integer.parseInt(tmpSid));
            
            if(isSubmit(request, "reset"))
                resetFormData(MugenFormDataManagerFactory.U_MARKER, request);
            else
                collectFormData(MugenFormDataManagerFactory.U_MARKER, MugenFormDataManagerFactory.WEB_FORM, request);                                   
            
            if (isSubmit(request, "save")) {
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");
                String position = request.getParameter("position");
                String alias = request.getParameter("alias");
                String chromosome = request.getParameter("chromosome");
                umid = Integer.parseInt(request.getParameter("umid"));
                genotypeManager.updateUMarker(umid, name, comm, Double.parseDouble(position), alias, Integer.parseInt(chromosome), caller);
            } else if(isSubmit(request, "remove")) {
                umid = Integer.parseInt(request.getParameter("umid"));
                
                genotypeManager.removeUMarker(umid, caller);
            } else if(isSubmit(request, "create")) {
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");
                String position = request.getParameter("position");
                String alias = request.getParameter("alias");
                String chromosome = request.getParameter("chromosome");
                int sid = Integer.parseInt(request.getParameter("sid"));
                
                double pos = 0;
                if(position.length() > 0)
                    pos = Double.parseDouble(position);                
                genotypeManager.createUMarker(name, comm, pos, alias, Integer.parseInt(chromosome), caller, sid);
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
            throw new ActionException("Failed to update unified marker");
        }
    }
}
