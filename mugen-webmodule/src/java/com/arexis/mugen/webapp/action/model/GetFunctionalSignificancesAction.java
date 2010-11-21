/*
 * GetFunctionalSignificancesAction.java
 *
 * Created on December 21, 2005, 11:19 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author lami
 */
public class GetFunctionalSignificancesAction extends MugenAction {
    
    /** Creates a new instance of GetFunctionalSignificancesAction */
    public GetFunctionalSignificancesAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetFunctionalSignificancesAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        Collection phenotypes = null;
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");

            Collection funcsigs = new ArrayList();
                        
            String type = req.getParameter("_type");
            String message = "";
            if(exists(type)) {
                message = "<b>NOTE: Sampling unit filter may be incorrectly adjusted since listing originated from Functional Significance Types view which is <u>Sampling unit independent</u>.</b>";
                funcsigs = modelManager.getFunctionalSignificancesForType(Integer.parseInt(type), caller, nav.getPageManager());
                nav.getPageManager().setMax(modelManager.getNumberOfSignificancesForType(Integer.parseInt(type), caller));                
            }
            else {
                funcsigs = modelManager.getFunctionalSignificances(caller, nav.getPageManager());                
                nav.getPageManager().setMax(modelManager.getNumberOfFunctionalSignificances(caller.getSuid(), caller));
            }
            
            Collection samplingunits = samplingUnitManager.getSamplingUnits(caller.getPid(), caller);           
            
            req.setAttribute("message", message);
            req.setAttribute("funcsigs", funcsigs);
            req.setAttribute("samplingunits", samplingunits);            
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve functional significances.");
        }
    }       
}
