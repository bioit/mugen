/*
 * GetGenModAction.java
 *
 * Created on December 15, 2005, 1:01 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.model;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lami
 */
public class GetGenModAction extends MugenAction {
    
    /** Creates a new instance of GetGenModAction */
    public GetGenModAction() {
    }

    /**
     * Returns the name of this action
     * @return The action name
     */
    public String getName() {
        return "GetGenModAction";
    }
    
    /**
     * Peroforms the action
     * @param req The http request object
     * @param context The servlet context
     * @return True of this action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");                                                              
            String tmpGmid = req.getParameter("gmid");

            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.EXPMODEL, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);                        
            
            if(exists(tmpGmid)) {
                if(exists(formDataManager.getValue("gmid"))) {                   
                    if(!tmpGmid.equals(formDataManager.getValue("gmid"))) {
                        resetFormData(MugenFormDataManagerFactory.EXPMODEL, req);
                    }              
                }
                
                formDataManager.put("gmid", tmpGmid);
            }
            
            int gmid = Integer.parseInt(formDataManager.getValue("gmid"));
            
            req.setAttribute("genmoddto", modelManager.getGeneticModification(gmid, caller)); 
            req.setAttribute("files", resourceManager.getGeneticModificationFiles(gmid, caller));
            req.setAttribute("geneontologies", modelManager.getGeneOntologies(gmid, caller));  
                                                
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve genetic modification.");
        }
    }     
}
