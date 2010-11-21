/*
 * GetIndividualsFullAction.java
 *
 * Created on July 7, 2005, 10:43 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.MugenCaller;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;



/**
 * MugenAction class for retrieval of individuals
 * @author heto
 */
public class GetIndividualsFullAction extends MugenAction {
    
    /** Creates a new instance of GetIndividualsFullAction */
    public GetIndividualsFullAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "GetIndividualsFullAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if this action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) {
        Collection indCollection = null;
        try {
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            int suid = caller.getSuid();
            
            
            if (nav.getPageManager().isLast()) {
                nav.getPageManager().setMax(samplingUnitManager.getNumberOfIndividuals(suid));
            }
            
            indCollection = samplingUnitManager.getIndividuals(suid, caller);
            
            req.setAttribute("mugen.collection.individualdo", indCollection);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
