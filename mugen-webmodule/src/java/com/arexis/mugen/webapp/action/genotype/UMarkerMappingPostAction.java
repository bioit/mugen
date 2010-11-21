/*
 * UMarkerMappingPostAction.java
 *
 * Created on November 28, 2005, 9:03 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;

import com.arexis.arxframe.ActionException;
import com.arexis.arxframe.advanced.AlterWorkflowException;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author lami
 */
public class UMarkerMappingPostAction extends MugenAction {
    
    /** Creates a new instance of UMarkerMappingPostAction */
    public UMarkerMappingPostAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "UMarkerMappingPostAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ActionException {        
        try {
            if(isSubmit(req, "reset"))
                resetFormData(MugenFormDataManagerFactory.U_MARKER_MAPPING, req);
            else
                collectFormData(MugenFormDataManagerFactory.U_MARKER_MAPPING, MugenFormDataManagerFactory.WEB_FORM, req);                      
                          
            if(isSubmit(req, "create")) {
                throw new AlterWorkflowException("CREATE");
                
                /*
                ActionException e = new ActionException("CREATE");
                e.setAlt("CREATE");
                throw e;
                 */
            }            
            return true;
        } catch (ActionException e) {
            throw e;             
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActionException("Failed to get unified marker mapping information.");
        }
    }    
}
