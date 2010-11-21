/*
 * SaveFamilyGroupingAction.java
 *
 * Created on November 29, 2005, 10:18 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;

import com.arexis.arxframe.ActionException;
import com.arexis.arxframe.advanced.AlterWorkflowException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author lami
 */
public class SaveFamilyGroupingAction extends MugenAction {
    
    /** Creates a new instance of SaveFamilyGroupingAction */
    public SaveFamilyGroupingAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SaveFamilyGroupingAction";
    }
    
    /**
     * Performs this action
     * @param request The http request object
     * @param context The servlet context
     * @return True if this action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ActionException {
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            String created = "";
            
            String tmpSuid = req.getParameter("suid");
            if(tmpSuid != null)
                caller.setSuid(Integer.parseInt(tmpSuid));
            
            if(isSubmit(req, "reset"))
                resetFormData(MugenFormDataManagerFactory.FAMILY_GROUPING, req);
            else
                collectFormData(MugenFormDataManagerFactory.FAMILY_GROUPING, MugenFormDataManagerFactory.WEB_FORM, req);                                               

            if(isSubmit(req, "create")) {
                String name = req.getParameter("name");
                String comm = req.getParameter("comm");            
                String father = req.getParameter("father");
                String mother = req.getParameter("mother");
                if(mother != null && father != null) {
                    int fid = Integer.parseInt(father);
                    int mid = Integer.parseInt(mother); 
                    samplingUnitManager.createFamilyGrouping(fid, mid, name, comm, caller);                    
                } 
                else {
                    throw new AlterWorkflowException("RELOAD");
                    /*
                    ActionException e = new ActionException("RELOAD");
                    e.setAlt("RELOAD");
                    throw e;                    
                     */
                }
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
            throw new ActionException("Failed to save family grouping");
        }      
    }
}
