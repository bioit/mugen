/*
 * GetUVariableSetMembershipAction.java
 *
 * Created on July 13, 2005, 1:30 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.phenotype;
import com.arexis.arxframe.ActionException;
import com.arexis.arxframe.Navigator;
import com.arexis.arxframe.PageManager;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.phenotype.phenotypemanager.UVariableSetDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;


/**
 * MugenAction class for handling of unified variable set membership
 * @author lami
 */
public class GetUVariableSetMembershipAction extends MugenAction {
    
    /** Creates a new instance of GetVariableSetMembershipAction */
    public GetUVariableSetMembershipAction() {
        super();
    }
    
    /**
     * Returns the name of the action
     * @return The name of the action
     */
    public String getName() {
        return "GetUVariableSetMembershipAction";
    }
    
    /**
     * Performs the action
     * @param req The http request
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ActionException {
        try {
            HttpSession session = req.getSession();
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator"); 
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");    
            
            Iterator i = null;
            
            int uvsid = 0;            
            PageManager pm = nav.getPageManager();        
            
            // Get the variable sets
            Collection vs = phenotypeManager.getAllUVariableSets(caller, caller.getSid());  
            
            req.setAttribute("uvariablesets", vs);
            
            if(vs.size() == 0)
                resetFormData(MugenFormDataManagerFactory.U_VARIABLE_SET_MEMBERSHIP_FILTER, req);              
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.U_VARIABLE_SET_MEMBERSHIP_FILTER, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);             

            // Get the selected option...
            String tmpUvsid = formDataManager.getValue("uvsid"); 
            // If it is the first time the page is displayed...choose the first 
            // variable set in the collection            
            if(!exists(tmpUvsid) || !vs.contains(new UVariableSetDTO(Integer.parseInt(tmpUvsid)))){
                i = vs.iterator();
                if(i.hasNext()) {
                    UVariableSetDTO vsdto = (UVariableSetDTO)i.next();                
                    uvsid = vsdto.getUvsid();
                }
            }
            else
                uvsid = Integer.parseInt(tmpUvsid);            
            
            
            Collection av = new ArrayList();
            Collection iv = new ArrayList();                  
                  
            
            av = phenotypeManager.getAvailableUVariables(uvsid, caller);
            iv = phenotypeManager.getUVariablesInSet(uvsid, caller);            

            req.setAttribute("availableUvars", av);
            req.setAttribute("includedUvars", iv);
            req.setAttribute("formdata", formDataManager);
            req.setAttribute("species", samplingUnitManager.getSpeciesForProject(caller.getPid(), caller)); 
            
            session.removeAttribute("pdo");
            
            return true;
        } catch (Exception e) {
            if(e instanceof ApplicationException)
                throw new ActionException(e.getMessage());
            else
                e.printStackTrace();
        }
        return false;
    } 
}
