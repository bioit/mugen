/*
 * SavePhenotypeAction.java
 *
 * Created on August 2, 2005, 12:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.phenotype;
import com.arexis.arxframe.ActionException;
import com.arexis.arxframe.advanced.AlterWorkflowException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;

/**
 * MugenAction class for saving a phenotype
 * @author lami
 */
public class SavePhenotypeAction extends MugenAction {
    
    /**
     * Creates a new instance of SavePhenotypeAction 
     */
    public SavePhenotypeAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SavePhenotypeAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ActionException { 
        int iid = 0; 
        int vid = 0;
        try {
            HttpSession session = request.getSession();           
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            String tmpSuid = request.getParameter("suid");
            if(exists(tmpSuid))
                caller.setSuid(Integer.parseInt(tmpSuid)); 
            
            if(isSubmit(request, "reset"))
                resetFormData(MugenFormDataManagerFactory.PHENOTYPE, request);
            else
                collectFormData(MugenFormDataManagerFactory.PHENOTYPE, MugenFormDataManagerFactory.WEB_FORM, request);                                    
            
            String value = request.getParameter("value");
            String date = request.getParameter("date");               
            String reference = request.getParameter("reference");
            String comm = request.getParameter("comm");  
            String tmpIid = request.getParameter("iid");
            String tmpVid = request.getParameter("vid");
            
            if(exists(tmpIid))
                iid = Integer.parseInt(tmpIid); 
            if(exists(tmpVid))
                vid = Integer.parseInt(tmpVid);   
                
            if(iid > 0 && vid > 0) {
                if (isSubmit(request, "save")) {                                                                                                                              
                    phenotypeManager.updatePhenotype(iid, vid, value, reference, comm, date, caller);
                }  
                else if(isSubmit(request, "remove")) {                                              
                    phenotypeManager.removePhenotype(iid, vid, caller);                
                }

                else if(isSubmit(request, "new")) {
                    phenotypeManager.createPhenotype(caller, vid, iid, comm, reference, date, value, caller.getSuid());              
                } 
                else {
                    throw new AlterWorkflowException("RELOAD");
                    /*
                    ActionException e = new ActionException("RELOAD");
                    e.setAlt("RELOAD");
                    throw e;
                     */
                }
            }
            else {
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
            throw new ActionException("Failed to save phenotype");
        }
    }
}
