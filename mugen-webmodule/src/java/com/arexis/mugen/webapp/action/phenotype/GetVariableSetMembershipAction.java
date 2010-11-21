/*
 * GetVariableSetMembershipAction.java
 *
 * Created on July 13, 2005, 1:30 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.phenotype;
import com.arexis.arxframe.Navigator;
import com.arexis.arxframe.PageManager;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.phenotype.phenotypemanager.VariableSetDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;


/**
 * MugenAction class for handling of variable set membership
 * @author lami
 */
public class GetVariableSetMembershipAction extends MugenAction {
    
    /** Creates a new instance of GetVariableSetMembershipAction */
    public GetVariableSetMembershipAction() {
        
    }
    
    /**
     * Returns the name of the action
     * @return The name of the action
     */
    public String getName() {
        return "GetVariableSetMembershipAction";
    }
    
    /**
     * Performs the action
     * @param req The http request
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = req.getSession();
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator"); 
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");  
            
            /*
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.VARIABLE_SET_MEMBERSHIP_FILTER, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);
             */
            
            String tmp = req.getParameter("vsid");
            if (tmp!=null)
                workflow.setAttribute("vsid", tmp);
            else
                tmp = workflow.getAttribute("vsid");
            int vsid = new Integer(tmp).intValue();
            
            VariableSetDTO vs = phenotypeManager.getVariableSet(vsid, caller);
            req.setAttribute("variableset", vs);
            
            Collection av = new ArrayList();
            Collection iv = new ArrayList();      
            
            av = phenotypeManager.getAvailableVariables(vsid, caller, caller.getSuid());
            iv = phenotypeManager.getVariablesInSet(vsid, caller);
            
            req.setAttribute("availableVars", av);
            req.setAttribute("includedVars", iv);
            
            
           
            
            /*
            
            
            Iterator i = null;            
            
            int vsid = 0;            

            PageManager pm = nav.getPageManager();         
            
            // Get the variable sets
            Collection vs = phenotypeManager.getAllVariableSets(caller.getSuid(), caller);  
            
            req.setAttribute("variablesets", vs);
            
            if(vs.size() == 0)
                resetFormData(MugenFormDataManagerFactory.VARIABLE_SET_MEMBERSHIP_FILTER, req);              
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.VARIABLE_SET_MEMBERSHIP_FILTER, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);        
         
            // Get the selected option...
            String tmpVsid = formDataManager.getValue("vsid"); 
            // If it is the first time the page is displayed...choose the first 
            // variable set in the collection            
            if(!exists(tmpVsid)){
                i = vs.iterator();
                if(i.hasNext()) {
                    VariableSetDTO vsdto = (VariableSetDTO)i.next();                
                    vsid = vsdto.getVsid();
                }
            }
            else
                vsid = Integer.parseInt(tmpVsid);                               
            
            
            Collection av = new ArrayList();
            Collection iv = new ArrayList();                    
            
            if(vsid > 0) {
                av = phenotypeManager.getAvailableVariables(vsid, caller, caller.getSuid());
                iv = phenotypeManager.getVariablesInSet(vsid, caller);                        
            }                        

            req.setAttribute("availableVars", av);
            req.setAttribute("includedVars", iv);
            req.setAttribute("formdata", formDataManager);
            req.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller)); 
            
             */
            
            return true;
        } 
        catch (ApplicationException ae)
        {
            throw ae;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to Get membership information");
        }
    }     
}
