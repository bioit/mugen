/*
 * CreateUVariableMappingAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.phenotype;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitDTO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;
import com.arexis.mugen.webapp.action.*;



/**
 * MugenAction class for handling the retrieval pre-info for creation of a unified variable mapping
 * @author lami
 */
public class CreateUVariableMappingAction extends MugenAction{
    
    /** Creates a new instance of CreateUVariableMappingAction */
    public CreateUVariableMappingAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "CreateUVariableMappingAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException { 
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");
            
            int suid = caller.getSuid();                        
            String tmpSuid = req.getParameter("suid");
            String vid = req.getParameter("vid");
            Collection su = samplingUnitManager.getSamplingUnits(caller, nav.getPageManager(), null, caller.getSid());
            String uvid = req.getParameter("uvid");

            
            Collection vars = phenotypeManager.getVariables(suid, caller);

            req.setAttribute("samplingunits", su);
            req.setAttribute("variables", vars);
            req.setAttribute("uvid", uvid);
            req.setAttribute("vid", vid);
            req.setAttribute("suid", ""+suid);
            
            if(vid != null && uvid != null)
                req.setAttribute("mess", "Mapping created");       
            
            return true;
        } catch (Exception e) {
            if(e instanceof ApplicationException)
                throw new ApplicationException(e.getMessage());
            else
                e.printStackTrace();
        }
        return false;
    }   
}
