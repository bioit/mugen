/*
 * SaveIndividalAction.java
 *
 * Created on July 13, 2005, 1:30 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;

import com.arexis.arxframe.ActionException;
import com.arexis.arxframe.advanced.AlterWorkflowException;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;
import org.apache.log4j.Logger;


/**
 * MugenAction class for saving an individual
 * @author heto
 */
public class SaveIndividualAction extends MugenAction {
    
    private static Logger logger = Logger.getLogger(SaveIndividualAction.class);
    
    /** Creates a new instance of SaveIndividalAction */
    public SaveIndividualAction() {
        
    }
    
    /**
     * Returns the name of the action
     * @return The name of the action
     */
    public String getName() {
        return "SaveIndividualAction";
    }
    
    /**
     * Performs the action
     * @param req The http request
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ActionException {
        try {
            HttpSession session = req.getSession();
             
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            
            debugParameters(req);

            String identity = req.getParameter("identity");

            String alias = req.getParameter("alias");
            String sex = req.getParameter("sex").toUpperCase();
            String father = (String)req.getParameter("father");
            String mother = (String)req.getParameter("mother");              
            String birthDate = req.getParameter("birthdate");            
            String status = req.getParameter("status");
            String comm = req.getParameter("comm");
            String suid = req.getParameter("suid");
            if(exists(suid))
                caller.setSuid(new Integer(suid).intValue());
            
            logger.debug("Running SaveIndividualAction");
            
            int fid = 0;
            int mid = 0;
            if(father != null && father.length() > 0)
                fid = Integer.parseInt(father);
            if(mother != null && mother.length() > 0)
                mid = Integer.parseInt(mother);                              
            
            if (isSubmit(req, "create")) {  
                if(identity == null || identity.trim().length() == 0)
                    throw new ApplicationException("Identity cannot be left blank");
                samplingUnitManager.createNewIndividual(caller.getSuid(), caller, identity, alias, sex, fid, mid, birthDate, comm);
            }
            else if(isSubmit(req, "update")) {
                if(identity == null || identity.trim().length() == 0)
                    throw new ApplicationException("Identity cannot be left blank");                
                int iid = Integer.parseInt(req.getParameter("iid"));
                samplingUnitManager.updateIndividual(iid, identity, alias, sex, status, fid, mid, birthDate, comm, caller);      
            }            
            else if (isSubmit(req, "remove")) {  
                samplingUnitManager.removeIndividual(Integer.parseInt(req.getParameter("iid")), caller);
            } else {
                throw new AlterWorkflowException("RELOAD");
                /*
                ActionException e = new ActionException("RELOAD");
                e.setAlt("RELOAD");
                throw e;
                 */
            }
            
            logger.debug("SaveIndividualAction ended");
            return true;
        } catch (ActionException e) {
            throw e;                    
        } catch (Exception e) {
            logger.error("Failed to save individual", e);
            throw new ActionException("Failed to save individual");
        }
    }
}
