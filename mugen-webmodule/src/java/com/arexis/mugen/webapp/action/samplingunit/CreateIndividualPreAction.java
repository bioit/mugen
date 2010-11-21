/*
 * GetIndividualAction.java
 *
 * Created on July 13, 2005, 8:41 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;
import java.util.Collection;
import javax.servlet.http.HttpSession;


/**
 * MugenAction class for retrieval of an individual
 * @author heto
 */
public class CreateIndividualPreAction extends MugenAction {
    
    /** Creates a new instance of GetIndividualAction */
    public CreateIndividualPreAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "CreateIndividualPreAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If something went wrong during retrieval of the individual
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        boolean result = false;
        try {
            
            HttpSession session = req.getSession();
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            
            Collection males = samplingUnitManager.getMales(caller.getSuid());
            Collection females = samplingUnitManager.getFemales(caller.getSuid());            
            
            req.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller));
            req.setAttribute("males", males);
            req.setAttribute("females", females);
    
            result = true;
        } catch (ApplicationException e) {
            throw new ApplicationException("Failed to get sampling units: "+e.getMessage());
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }
}
