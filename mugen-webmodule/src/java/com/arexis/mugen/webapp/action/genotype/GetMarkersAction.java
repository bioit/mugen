/*
 * GetMarkersAction.java
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
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.genotype.genotypemanager.ChromosomeDTO;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;


/**
 * MugenAction class for handling the retrieval of markers
 * @author lami
 */
public class GetMarkersAction extends MugenAction{
    
    /** Creates a new instance of GetMarkersAction */
    public GetMarkersAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetMarkersAction";
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
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");
            
            HttpSession se = req.getSession();
            
            int suid = caller.getSuid();
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.MARKER_FILTER, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);   

            nav.getPageManager().setMax(genotypeManager.getNumberOfMarkers(caller, formDataManager));
            Collection markers = genotypeManager.getMarkers(nav.getPageManager(), caller, formDataManager);
            Collection samplingunits = samplingUnitManager.getSamplingUnits(caller.getPid(), caller);             

            Collection chr = genotypeManager.getChromosomes(caller.getSid(), caller);
            
            //Wild card chromo insert
            chr.add(new ChromosomeDTO());
            
            req.setAttribute("markers", markers);
            req.setAttribute("samplingunits", samplingunits);
            req.setAttribute("chromosomes", chr);
            req.setAttribute("formdata", formDataManager);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve markers.");
        }
    }   
}
