/*
 * CreateGenotypeAction.java
 *
 * Created on August 2, 2005, 12:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;

import com.arexis.arxframe.Navigator;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.genotype.genotypemanager.LevelDTO;
import com.arexis.mugen.genotype.genotypemanager.MarkerDTO;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;


/**
 * MugenAction class for saving a genotype
 * @author lami
 */
public class CreateGenotypeAction extends MugenAction {
    
    /**
     * Creates a new instance of CreateGenotypeAction 
     */
    public CreateGenotypeAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "CreateGenotypeAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException { 
        try {
            HttpSession se = request.getSession();            
            MugenCaller caller = (MugenCaller)se.getAttribute("caller"); 
            Navigator nav = (Navigator)se.getAttribute("navigator");
            int cid = -1;
            int suid = caller.getSuid(); 
            int mid = -1;               
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.GENOTYPE, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    request);
                
            
            String tmpMid = formDataManager.getValue("mid");
            
            // Fill up chromosomes
            Collection chromosomes = genotypeManager.getChromosomes(caller.getSid(), caller);             
            

            Collection markers = genotypeManager.getAllMarkers(caller);
            
            // Fill up identities
            Collection individuals = samplingUnitManager.getIndividuals(suid, caller);
                        
            // Fill up allele 1 and 2
            // Get desired values if they are set, else pick the first one...
            if(exists(tmpMid))
                mid = Integer.parseInt(tmpMid);
            else if(markers.size() > 0){
                Iterator i = markers.iterator();
                MarkerDTO mdto = (MarkerDTO)i.next();

                mid = mdto.getMid();
            }
            
            Collection alleles = genotypeManager.getAllelesForMarker(mid, caller);

            // Fill up level
            Collection levels = new ArrayList();
            for(int i=0;i<10;i++)
                levels.add(new LevelDTO(i));            

            request.setAttribute("chromosomes", chromosomes);
            request.setAttribute("markers", markers);
            request.setAttribute("individuals", individuals);
            request.setAttribute("alleles", alleles);
            request.setAttribute("levels", levels);
            request.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller));
            request.setAttribute("formdata", formDataManager);
                    
            return true;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to get info regarding genotype relations");
        }
    }
}
