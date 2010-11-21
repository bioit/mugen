/*
 * SaveModelAction.java
 *
 * Created on December 14, 2005, 4:57 PM
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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lami
 */
public class SaveModelAction extends MugenAction {
    //just testing subversion...
    /** Creates a new instance of SaveModelAction */
    public SaveModelAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SaveModelAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = request.getSession();
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");                        
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.EXPMODEL, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    request); 
            
            String eid = formDataManager.getValue("eid");
            
            System.out.println("SaveModelAction: eid="+eid);
            System.out.println("SaveModelAction: create="+request.getParameter("create"));
            
            
            System.out.println("SaveModelAction: save");
            String alias = request.getParameter("alias");
            String availability = request.getParameter("availability");
            String raid = request.getParameter("raid");
            String geneticBackground = request.getParameter("geneticBackground");
            String researchAppsText = request.getParameter("researchAppsText");
            String contactId = request.getParameter("contactId");               
            String comm = request.getParameter("comm");
            String level = request.getParameter("level");
            String desired_level = request.getParameter("desired_level");

            modelManager.updateModel(caller.getSuid(), Integer.parseInt(eid), alias, geneticBackground, availability, Integer.parseInt(raid), researchAppsText, Integer.parseInt(contactId), caller, comm, level, desired_level);
            
            projectManager.log("user "+caller.getName()+" edited model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());

            return true;
        }
        catch (ApplicationException ae)
        {
             throw ae;     
        } 
        catch (Exception e) 
        {    
            throw new ApplicationException("SaveModel failed",e);
        }
    }     
}
