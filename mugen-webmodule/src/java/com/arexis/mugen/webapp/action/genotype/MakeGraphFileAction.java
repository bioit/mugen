/*
 * MakeGraphFileAction.java
 *
 * Created on October 28, 2005, 11:26 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.genotype.genotypemanager.GenotypeSpecialViewDTO;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;

/**
 *
 * @author lami
 */
public class MakeGraphFileAction extends MugenAction {
    
    /** Creates a new instance of MakeGraphFileAction */
    public MakeGraphFileAction() {
        super();
    }
    
    public String getName() {
        return "MakeGraphFileAction";
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
            String tmpSuid = req.getParameter("suid");
            Collection status = new ArrayList();
            
            String format = req.getParameter("format");
            
            String tmpMid1 = req.getParameter("mid1");
            String tmpMid2 = req.getParameter("mid2");
            String tmpMid3 = req.getParameter("mid3");
            String tmpIid = req.getParameter("iid");
            
            
            if(tmpSuid != null) 
                caller.setSuid(Integer.parseInt(tmpSuid));
            
            GenotypeSpecialViewDTO dto = new GenotypeSpecialViewDTO();
            Collection pedigree = new ArrayList();
            
            if(req.getParameter("display") != null) {
                int mid1 = 0;
                int mid2 = 0;
                int mid3 = 0;
                int iid = 0;
                if(tmpMid1 != null && tmpMid1.length() > 0)
                    mid1 = Integer.parseInt(tmpMid1);

                if(tmpMid2 != null && tmpMid2.length() > 0)
                    mid2 = Integer.parseInt(tmpMid2);                

                if(tmpMid3 != null && tmpMid3.length() > 0)
                    mid3 = Integer.parseInt(tmpMid3);                
                
                if(tmpIid != null && tmpIid.length() > 0)
                    iid = Integer.parseInt(tmpIid);                
                                
                pedigree = genotypeManager.getParentalView(iid, mid1, mid2, mid3, false);
            }
            
            req.setAttribute("pedigree", pedigree);          
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }      
    
}
