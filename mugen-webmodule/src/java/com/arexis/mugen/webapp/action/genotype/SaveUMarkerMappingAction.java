/*
 * SaveUMarkerMappingAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;
import com.arexis.arxframe.ActionException;
import com.arexis.arxframe.advanced.AlterWorkflowException;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;



/**
 * MugenAction class for handling the saving of the mapping for a unified marker
 * @author lami
 */
public class SaveUMarkerMappingAction extends MugenAction{
    
    /**
     * Creates a new instance of SaveUMarkerMappingAction 
     */
    public SaveUMarkerMappingAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "SaveUMarkerMappingAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ActionException {        
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            
            if(isSubmit(req, "reset"))
                resetFormData(MugenFormDataManagerFactory.U_MARKER_MAPPING, req);
            else
                collectFormData(MugenFormDataManagerFactory.U_MARKER_MAPPING, MugenFormDataManagerFactory.WEB_FORM, req);                      
            
            if(!isSubmit(req, "create")) {
                throw new AlterWorkflowException("RELOAD");
                /*
                ActionException e = new ActionException("RELOAD");
                e.setAlt("RELOAD");
                throw e;
                 */
            }           
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.U_MARKER_MAPPING, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);   
            
            System.out.println(formDataManager.toString());
            
            int umid = Integer.parseInt(formDataManager.getValue("umid"));
            int mid = Integer.parseInt(formDataManager.getValue("mid"));            
            
            genotypeManager.createUMarkerMapping(umid, mid, caller);                
            
            return true;
        } catch (ActionException e) {
            throw e;                    
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActionException("Failed to create mapping");
        }
    }   
}
