/*
 * GetAllelesAction.java
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
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;


/**
 * MugenAction class for handling the retrieval of alleles
 * @author lami
 */
public class GetAllelesAction extends MugenAction{
    
    /** Creates a new instance of GetAllelesAction */
    public GetAllelesAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetAllelesAction";
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
            String tmpMid = req.getParameter("mid");
            HttpSession session = req.getSession();
            int mid = 0;
            if(tmpMid == null)
                tmpMid = (String)session.getAttribute("mid");
            if(tmpMid != null)
                mid = Integer.parseInt(tmpMid);
            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");     
            
            Collection alleles = genotypeManager.getAllelesForMarker(mid, caller);
            Iterator i = alleles.iterator();
            String marker = "";
            if(i.hasNext()){
                AlleleDTO dto = (AlleleDTO)i.next();
                marker = dto.getMarker();
            }
  
            session.setAttribute("marker", marker);
            req.setAttribute("alleles", alleles);
            session.setAttribute("markerid", tmpMid);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }   
}
