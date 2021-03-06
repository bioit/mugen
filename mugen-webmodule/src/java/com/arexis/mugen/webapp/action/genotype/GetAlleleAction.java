/*
 * GetAlleleAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;
import com.arexis.arxframe.Navigator;
import com.arexis.mugen.genotype.genotypemanager.AlleleDTO;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;




/**
 * MugenAction class for handling the retrieval an allele
 * @author lami
 */
public class GetAlleleAction extends MugenAction{
    
    /** Creates a new instance of GetAlleleAction */
    public GetAlleleAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetAlleleAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) {
        
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            int aid = Integer.parseInt(req.getParameter("aid"));
            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");           
            
            AlleleDTO allele = genotypeManager.getAllele(aid, caller);
            req.setAttribute("allele", allele);
            req.setAttribute("history", genotypeManager.getAlleleHistory(aid, caller));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }   
}
