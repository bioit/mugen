/*
 * CreateUMarkerMappingAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;
import com.arexis.arxframe.Navigator;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.genotype.genotypemanager.ChromosomeDTO;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;
import com.arexis.mugen.webapp.action.*;



/**
 * MugenAction class for handling the creation of the mapping for a unified marker
 * @author lami
 */
public class CreateUMarkerMappingAction extends MugenAction{
    
    /**
     * Creates a new instance of CreateUMarkerMappingAction 
     */
    public CreateUMarkerMappingAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "CreateUMarkerMappingAction";
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
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.U_MARKER_MAPPING, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);    
            
//            String tmpCid = formDataManager.getValue("cid");
//            String mid = formDataManager.getValue("mid");
            
//            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");                
            
            Collection chromosomes = genotypeManager.getChromosomes(caller.getSid(), caller);
//            int cid = 0;
//            
//            // No chromosome is selected...get the first one
//            if(!exists(tmpCid)){
//                Iterator i = chromosomes.iterator();
//                ChromosomeDTO c = (ChromosomeDTO)i.next();
//                cid = c.getCid();
//            }
//            else
//                cid = Integer.parseInt(tmpCid);    
            
            Collection markers = genotypeManager.getAllMarkers(caller);
            
            req.setAttribute("chromosomes", chromosomes);            
            req.setAttribute("markers", markers);                 
            req.setAttribute("formdata", formDataManager);
//            req.setAttribute("umid", req.getParameter("umid")); 
//            req.setAttribute("cid", ""+cid);
//            req.setAttribute("mid", mid);            
            
//            if (isSubmit(req, "save")) {
//                int umid = Integer.parseInt(req.getParameter("umid"));
//            
//                genotypeManager.createUMarkerMapping(umid, Integer.parseInt(mid), caller);                
//                return true;
//            }
            

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }   
}
