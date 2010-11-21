/*
 * GetPhenotypesAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.phenotype;

import com.arexis.arxframe.Navigator;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;


/**
 * MugenAction class for handling the retrieval of phenotypes
 * @author lami
 */
public class GetPhenotypesAction extends MugenAction{
    
    /** Creates a new instance of GetPhenotypesAction */
    public GetPhenotypesAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetPhenotypesAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        Collection phenotypes = null;
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.PHENOTYPE_FILTER, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);   
            
            // In case we were linked to here from model view
            String identity = req.getParameter("_identity");
            if(exists(identity)) 
                formDataManager.put("identity", identity);

            Collection samplingunits = samplingUnitManager.getSamplingUnits(caller.getPid(), caller);
            nav.getPageManager().setMax(phenotypeManager.getNumberOfPhenotypes(formDataManager, caller));
            phenotypes = phenotypeManager.getPhenotypes(caller.getSuid(), nav.getPageManager(), caller, formDataManager);

            req.setAttribute("phenotypesdto", phenotypes);
            req.setAttribute("samplingunits", samplingunits);
            req.setAttribute("formdata", formDataManager);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve phenotypes.");
        }
    }   
}
