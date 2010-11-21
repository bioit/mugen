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
import com.arexis.arxframe.Navigator;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.samplingunit.samplingunitmanager.IndividualDTO;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;
import javax.servlet.http.HttpSession;


/**
 * MugenAction class for retrieval of an individual
 * @author heto
 */
public class GetIndividualAction extends MugenAction {
    
    /** Creates a new instance of GetIndividualAction */
    public GetIndividualAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "GetIndividualAction";
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
            String s_iid = req.getParameter("iid");
            int iid;
            HttpSession session = req.getSession();
            
            if (s_iid==null || s_iid.equals(""))
            {
                s_iid = (String)session.getAttribute("getIndividualAction.iid");
            }
            else
            {
                session.setAttribute("getIndividualAction.iid", s_iid);
            }
            
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            
            if(s_iid !=null){
                iid = new Integer(s_iid).intValue();
                IndividualDTO ind = samplingUnitManager.getIndividual(iid, caller);
                if (ind.getFather()!=null) {
                    String father = ""+ind.getFather().getIid();
                    req.setAttribute("father", father);
                }
                if (ind.getMother()!=null) {
                    String mother = ""+ind.getMother().getIid();
                    req.setAttribute("mother", mother);
                }
                req.setAttribute("ind", ind);
                req.setAttribute("history", samplingUnitManager.getIndHistory(iid, caller));
            }
            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");
            
            Collection males = samplingUnitManager.getMales(caller.getSuid());
            Collection females = samplingUnitManager.getFemales(caller.getSuid());            
            
            req.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller));
            req.setAttribute("males", males);
            req.setAttribute("females", females);
            
            result = true;
        } catch (ApplicationException e) {
            throw new ApplicationException("Failed to get individuals: "+e.getMessage());
        } catch (Exception e) {
            result = false;
            
            e.printStackTrace();
        }
        return result;
    }
}
