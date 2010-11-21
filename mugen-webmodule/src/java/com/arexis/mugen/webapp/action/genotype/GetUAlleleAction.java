/*
 * GetUAlleleAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;
import com.arexis.arxframe.Navigator;
import com.arexis.mugen.genotype.genotypemanager.UAlleleDTO;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;




/**
 * MugenAction class for handling the retrieval an unified allele
 * @author lami
 */
public class GetUAlleleAction extends MugenAction{
    
    /** Creates a new instance of GetUAlleleAction */
    public GetUAlleleAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetUAlleleAction";
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
            int uaid = Integer.parseInt(req.getParameter("uaid"));
            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");          
            
            UAlleleDTO uallele = genotypeManager.getUAllele(uaid, caller);
            req.setAttribute("uallele", uallele);
            req.setAttribute("history", genotypeManager.getUAlleleHistory(uaid, caller));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }   
}
