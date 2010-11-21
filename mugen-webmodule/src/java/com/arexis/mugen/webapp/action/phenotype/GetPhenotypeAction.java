/*
 * GetPhenotypeAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.phenotype;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;
import com.arexis.mugen.webapp.action.model.GetModelAction;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;



/**
 * MugenAction class for handling the retrieval a phenotype
 * @author lami
 */
public class GetPhenotypeAction extends MugenAction{
    
    private static Logger logger = Logger.getLogger(GetModelAction.class);
    
    /** Creates a new instance of GetPhenotypeAction */
    public GetPhenotypeAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetPhenotypeAction";
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
            HttpSession session = req.getSession();
            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");
  
            int vid = 0;
            int iid = 0;
            
            if (req.getParameter("vid")!=null)
                vid = Integer.parseInt(req.getParameter("vid"));
//            else if (workflow.getAttribute("vid")!=null)
//                vid = Integer.parseInt(workflow.getAttribute("vid"));
            else if (session.getAttribute("vid")!=null)
                vid = Integer.parseInt((String)session.getAttribute("vid"));
            
            if (req.getParameter("iid")!=null)
                iid = Integer.parseInt(req.getParameter("iid"));
//            else if (workflow.getParameter("iid")!=null)
//                iid = Integer.parseInt(workflow.getParameter("iid"));
            else if (session.getAttribute("iid")!=null)
                vid = Integer.parseInt((String)session.getAttribute("iid"));
            
            if (vid==0 && iid==0)
                throw new ApplicationException("Parameter vid and iid is missing");
            
//            workflow.setAttribute("iid", new Integer(iid).toString());
//            workflow.setAttribute("vid", new Integer(vid).toString());
            
            session.setAttribute("iid", new Integer(iid).toString());
            session.setAttribute("vid", new Integer(vid).toString());
            
            logger.debug("iid="+iid);
            logger.debug("vid="+vid);
            
            req.setAttribute("phenotypedto", phenotypeManager.getPhenotype(iid, vid, caller));
            req.setAttribute("history", phenotypeManager.getPhenotypeHistory(vid, iid, caller));
            return true;
        } catch (Exception e) {
            logger.error("Failed to get phenotype", e);
            throw new ApplicationException("Phenotype could not be loaded", e);
        }
    }   
}
