/*
 * CreatePhenotypeAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.phenotype;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;



/**
 * MugenAction class for handling the retrieval of information neccessary for
 * creation of a phenotype
 * @author lami
 */
public class CreatePhenotypeAction extends MugenAction{
    
    /** Creates a new instance of CreatePhenotypeAction */
    public CreatePhenotypeAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "CreatePhenotypeAction";
    }
    
    /**
     * Performs the action
     * @return True if the action could be performed
     * @param req The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException 
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {        
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.PHENOTYPE, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);              
            
            req.setAttribute("individualsdto", samplingUnitManager.getExperimentalObjects(caller.getSuid(), caller));
            req.setAttribute("variablesdto", phenotypeManager.getVariables(caller.getSuid(), caller));
            req.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller));
            req.setAttribute("formdata", formDataManager);
            
            return true;
        } 
        catch (ApplicationException ae)
        {
            throw ae;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new ApplicationException("Failed to create phenotype", e);
        }
    }   
}
