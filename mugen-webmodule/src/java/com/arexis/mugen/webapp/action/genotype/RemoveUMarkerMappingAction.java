/*
 * RemoveUMarkerMappingAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;
import com.arexis.arxframe.Navigator;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.genotype.genotypemanager.UMarkerDTO;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import com.arexis.mugen.webapp.action.*;



/**
 * MugenAction class for handling the deletion of the mapping for a unified marker
 * @author lami
 */
public class RemoveUMarkerMappingAction extends MugenAction{
    
    /**
     * Creates a new instance of RemoveUMarkerMappingAction 
     */
    public RemoveUMarkerMappingAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "RemoveUMarkerMappingAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) {
        
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            int umid = Integer.parseInt(req.getParameter("umid"));
            int mid = Integer.parseInt(req.getParameter("mid"));
            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");                     
            
            genotypeManager.deleteUMarkerMapping(umid, mid, caller);
            Collection umapping = genotypeManager.getUMarkerMapping(umid, caller);
            UMarkerDTO dto = genotypeManager.getUMarker(umid, caller);

            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.U_MARKER_MAPPING, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);             

            req.setAttribute("formdata", formDataManager);            
            req.setAttribute("umapping", umapping);            
            req.setAttribute("umarker", dto);                 
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }   
}
