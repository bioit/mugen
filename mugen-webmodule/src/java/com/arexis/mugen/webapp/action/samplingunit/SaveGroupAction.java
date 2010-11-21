/*
 * SaveGroupAction.java
 *
 * Created on August 2, 2005, 12:37 PM
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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;

/**
 * MugenAction class for saving a group
 * @author lami
 */
public class SaveGroupAction extends MugenAction {
    
    /**
     * Creates a new instance of SaveGroupAction
     */
    public SaveGroupAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SaveGroupAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ActionException {
        int gid = 0;
        try {
            HttpSession session = request.getSession();
            String tmpSuid = request.getParameter("suid");
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");            
            
            if(exists(tmpSuid)) {                                   
                caller.setSuid(Integer.parseInt(tmpSuid));
            }             
            
            if(isSubmit(request, "reset"))
                resetFormData(MugenFormDataManagerFactory.GROUP, request);
            else
                collectFormData(MugenFormDataManagerFactory.GROUP, MugenFormDataManagerFactory.WEB_FORM, request);                                                           
            
            
            if (isSubmit(request, "save") && exists(request.getParameter("gid"))){
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");
                gid = Integer.parseInt(request.getParameter("gid"));
                
                samplingUnitManager.updateGroup(gid, name, comm, caller);
            } else if(isSubmit(request, "remove") && exists(request.getParameter("gid"))){
                gid = Integer.parseInt(request.getParameter("gid"));

                samplingUnitManager.removeGroup(gid, caller);
            } else if(isSubmit(request, "cancel") && exists(request.getParameter("gid"))){
                gid = Integer.parseInt(request.getParameter("gid"));

                samplingUnitManager.removeGroup(gid, caller);
            } else if(isSubmit(request, "new") && exists(request.getParameter("gsid"))){
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");
                int gsid = Integer.parseInt(request.getParameter("gsid"));
                
                samplingUnitManager.createGroup(name, comm, caller, gsid);
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
            throw new ActionException("Failed to save group");
        }  
    }
}
