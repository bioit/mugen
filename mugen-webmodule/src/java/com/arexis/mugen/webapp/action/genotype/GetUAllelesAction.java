/*
 * GetUAllelesAction.java
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
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;


/**
 * MugenAction class for handling the retrieval of unified alleles
 * @author lami
 */
public class GetUAllelesAction extends MugenAction{
    
    /** Creates a new instance of GetUAllelesAction */
    public GetUAllelesAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetUAllelesAction";
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
            String tmpMid = req.getParameter("umid");
            HttpSession session = req.getSession();
            int umid = 0;
            if(tmpMid == null)
                tmpMid = (String)session.getAttribute("umid");
            if(tmpMid != null)
                umid = Integer.parseInt(tmpMid);
            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");         
            
            Collection ualleles = genotypeManager.getUAllelesForUMarker(umid, caller);
            Iterator i = ualleles.iterator();
            String umarker = "";
            if(i.hasNext()){
                UAlleleDTO dto = (UAlleleDTO)i.next();
                umarker = dto.getUmarker();
            }
  
            session.setAttribute("umarker", umarker);
            req.setAttribute("ualleles", ualleles);
            session.setAttribute("umarkerid", tmpMid);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }   
}
