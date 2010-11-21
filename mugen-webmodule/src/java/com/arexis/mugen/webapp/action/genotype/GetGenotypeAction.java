/*
 * GetGenotypeAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;
import com.arexis.arxframe.Navigator;
import com.arexis.mugen.genotype.genotypemanager.LevelDTO;
import com.arexis.mugen.MugenCaller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import com.arexis.mugen.webapp.action.*;


/**
 * MugenAction class for handling the retrieval a genotype
 * @author lami
 */
public class GetGenotypeAction extends MugenAction{
    
    /** Creates a new instance of GetGenotypeAction */
    public GetGenotypeAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetGenotypeAction";
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
            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");
            
            int mid = Integer.parseInt(req.getParameter("mid"));
            int iid = Integer.parseInt(req.getParameter("iid"));
                        
            Collection levels = new ArrayList();
            for(int i=0;i<10;i++)
                levels.add(new LevelDTO(i));
            
            req.setAttribute("genotypedto", genotypeManager.getGenotype(mid, iid, caller));            
            req.setAttribute("alleles", genotypeManager.getAllelesForMarker(mid, caller));
            req.setAttribute("levels", levels);
            req.setAttribute("history", genotypeManager.getGenotypeHistory(iid,  mid, caller));
                
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }   
}
