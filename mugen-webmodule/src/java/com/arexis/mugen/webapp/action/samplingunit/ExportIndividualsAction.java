/*
 * ViewIndividualsAction.java
 *
 * Created on July 7, 2005, 10:43 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;

import com.arexis.arxframe.ActionException;
import com.arexis.arxframe.Navigator;
import com.arexis.arxframe.PageManager;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.samplingunit.samplingunitmanager.IndividualDTO;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import com.arexis.mugen.webapp.action.*;




/**
 * MugenAction class for retrieval of individuals
 * @author heto
 */
public class ExportIndividualsAction extends MugenAction {
    
    /** Creates a new instance of ViewIndividualsAction */
    public ExportIndividualsAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "ExportIndividualsAction";
    }
    
    /**
     * Performs the action
     * @return True if this action could be performed
     * @param req The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ActionException{
        try {
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.INDIVIDUALS_FILTER, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);                   
                                                                
            int num = samplingUnitManager.getNumberOfIndividuals(formDataManager, caller);
            
            // Create a new PageManager, do not save any data.
            PageManager p = new PageManager();
            p.setDelta(num);
            p.setMax(num);
            p.setFirst();
            
            
            Collection inds = samplingUnitManager.getIndividuals(caller.getSuid(), p, caller, formDataManager);                      
            
            
            String out = "IDENTITY;ALIAS;SEX;BIRTH_DATE;FATHER;MOTHER\n";
            
            
            Iterator it = inds.iterator();
            while (it.hasNext())
            {
                IndividualDTO ind = (IndividualDTO)it.next();
                out += ind.getIdentity()+";"+
                            ind.getAlias()+";"+
                            ind.getSex()+";"+
                            ind.getBirthDate()+";"+
                            ind.getFatherName()+";"+
                            ind.getMotherName()+"\n";
                        
            }    
            
            req.setAttribute("export", out);     
            
            return true;
        } catch (ApplicationException e) {
            resetFormData(MugenFormDataManagerFactory.INDIVIDUALS_FILTER, req);
            throw e;
        } catch (Exception e) {
            resetFormData(MugenFormDataManagerFactory.INDIVIDUALS_FILTER, req);
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve individuals.");
        }
    }   
}
