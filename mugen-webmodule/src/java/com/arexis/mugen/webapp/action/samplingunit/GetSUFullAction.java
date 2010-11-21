/*
 * GetSUFullAction.java
 *
 * Created on July 14, 2005, 4:43 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.ParamCollector;
import com.arexis.mugen.project.ParamDataObject;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;


/**
 * MugenAction class for the retrieval of sampling units
 * @author heto
 */
public class GetSUFullAction extends MugenAction {
    
    /** Creates a new instance of GetSUAction */
    public GetSUFullAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "GetSUFullAction";
    }
    
    /**
     * Performs this action
     * @param request The http request object
     * @param context The servlet context
     * @return True if this action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            Navigator nav = (Navigator)request.getSession().getAttribute("navigator");
            
            HttpSession se = request.getSession();
            
            int sid = caller.getSid();
            
            
            
            ParamDataObject pdo = (ParamDataObject)se.getAttribute("samplingunits.pdo");
            if (pdo==null)
            {
                ParamCollector pc = new ParamCollector(true);
                pc.putDefault("status", "E");
                pc.putDefault("sid", ""+sid);
                pdo = pc.fillDefaults(se, "getsufullaction", nav.getPageManager());
            }
            
                                  
            //ParamDataObject pdo = pc.collectParams(request, "getsufullaction", nav.getPageManager());
            
            Collection samplingunits = samplingUnitManager.getSamplingUnits(caller, nav.getPageManager(), pdo, sid);                        
            nav.getPageManager().setMax(samplingUnitManager.getNumberOfSamplingUnits(sid, caller, pdo)); 
            
            request.setAttribute("samplingunits", samplingunits);
            request.setAttribute("species", samplingUnitManager.getSpeciesForProject(caller.getPid(), caller));
            request.setAttribute("paramdataobject", pdo);
            request.setAttribute("currentSpecies", ""+sid);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve sampling units.");
        }
    }    
}
