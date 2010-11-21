/*
 * CreateFamilyGroupingAction.java
 *
 * Created on July 14, 2005, 4:43 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;


/**
 * MugenAction class for the creation of a grouping and groups based on family relations
 * @author heto
 */
public class CreateFamilyGroupingAction extends MugenAction {
    
    /**
     * Creates a new instance of CreateFamilyGroupingAction 
     */
    public CreateFamilyGroupingAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "CreateFamilyGroupingAction";
    }
    
    /**
     * Performs this action
     * @param request The http request object
     * @param context The servlet context
     * @return True if this action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");        
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.FAMILY_GROUPING, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req); 
            
            req.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller));  
            req.setAttribute("females", samplingUnitManager.getFemales(caller.getSuid()));  
            req.setAttribute("males", samplingUnitManager.getMales(caller.getSuid()));             
            req.setAttribute("formdata", formDataManager);
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not create grouping.");
        }
    }    
}
