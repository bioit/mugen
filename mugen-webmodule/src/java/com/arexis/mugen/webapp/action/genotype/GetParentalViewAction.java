/*
 * GetPhenotypeAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;

import com.arexis.mugen.genotype.genotypemanager.GenotypeSpecialViewDTO;
import com.arexis.mugen.MugenCaller;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;



/**
 * MugenAction class for handling the retrieval of parental special view
 * @author lami
 */
public class GetParentalViewAction extends MugenAction{
    
    /**
     * Creates a new instance of GetParentalViewAction 
     */
    public GetParentalViewAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetParentalViewAction";
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
                
                String order = req.getParameter("order");
                boolean depthFirst = false;
                if(order.equals("depth"))
                    depthFirst = true;
                
                req.setAttribute("order", order);
                pedigree = genotypeManager.getParentalView(iid, mid1, mid2, mid3, depthFirst);
                if(pedigree.size() > 0)
                    req.getSession().setAttribute("tmp.getspecialviewaction.pedigreegraph", genotypeManager.getGraphForPedigree(pedigree, depthFirst));
                Iterator i = pedigree.iterator();
                if(pedigree.size() > 0)
                    dto = (GenotypeSpecialViewDTO)i.next();
            }
            
            req.setAttribute("pedigree", pedigree);
            req.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller));
            req.setAttribute("inds", samplingUnitManager.getIndividuals(caller.getSuid(), caller));
            req.setAttribute("markers", genotypeManager.getMarkers(caller));
            
            req.setAttribute("header1", dto.getMname1());
            req.setAttribute("header2", dto.getMname2());
            req.setAttribute("header3", dto.getMname3());
            
            req.setAttribute("iid", tmpIid);            
            req.setAttribute("mid1", tmpMid1);
            req.setAttribute("mid2", tmpMid2);
            req.setAttribute("mid3", tmpMid3);            
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }   
}
