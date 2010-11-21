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
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.samplingunit.samplingunitmanager.GroupDTO;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import com.arexis.mugen.webapp.action.*;
import org.apache.log4j.Logger;




/**
 * MugenAction class for retrieval of individuals
 * @author heto
 */
public class GetIndividualsAction extends MugenAction {
    
    private static Logger logger = Logger.getLogger(GetIndividualsAction.class);
    
    /** Creates a new instance of ViewIndividualsAction */
    public GetIndividualsAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "GetIndividualsAction";
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
            
            logger.debug("suid="+caller.getSuid());
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.INDIVIDUALS_FILTER, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);                   
            

            if(exists(req.getParameter("gsid")) && !isSubmit(req, "reset")) {                
                formDataManager.put("gsid", ""+req.getParameter("gsid"));
                formDataManager.put("gid", ""+req.getParameter("gid"));
            }                        

            String tmpSuid = formDataManager.getValue("suid");
            String tmpGsid = formDataManager.getValue("gsid");
            String tmpGid = formDataManager.getValue("gid"); 
            
            // Special case...when pressed the advanced/simple style arrow
            // Cannot get params from formdatamanager here since no postaction
            // has been performed
            if(!exists(tmpSuid))
                tmpSuid = req.getParameter("suid");
            if(!exists(tmpGsid))
                tmpSuid = req.getParameter("gsid");
            if(!exists(tmpGid))
                tmpSuid = req.getParameter("gid");            
            
            
            Collection groupings = samplingUnitManager.getGroupings(caller.getSuid(), caller);
            int gid = -1;
            int gsid = -1;                
            Collection groups = new ArrayList();

            Iterator i = null;
            // If it is the first time the page is displayed...choose the first 
            // grouping in the collection            
            if(exists(tmpGsid) && !tmpGsid.equals("*")){
                gsid = Integer.parseInt(tmpGsid);
            }
            
            if(gsid > 0)
                groups = samplingUnitManager.getGroups(gsid, caller, caller.getSuid()); 
            
            // If it is the first time the page is displayed...choose the first 
            // group in the collection            
            if(!exists(tmpGsid)){
                i = groups.iterator();
                if(i.hasNext()) {
                    GroupDTO grDto = (GroupDTO)i.next();                
                    gid = grDto.getGid();
                }
            }
            // else, use the requested group id
            else if(!tmpGid.equals("*")){                
                gid = Integer.parseInt(tmpGid);              
            }               
                                                                
            
            nav.getPageManager().setMax(samplingUnitManager.getNumberOfIndividuals(formDataManager, caller));
            Collection inds = samplingUnitManager.getIndividuals(caller.getSuid(), nav.getPageManager(), caller, formDataManager);                      
            
            req.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller));
            req.setAttribute("individuals", inds);
            req.setAttribute("formdata", formDataManager);
            req.setAttribute("groupdto", groups);
            req.setAttribute("groupingsdto", groupings);  
            String query = req.getParameter("querytype");
            if(!exists(query))
                query = formDataManager.getValue("querytype");
            req.setAttribute("querytype", query);     
            
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
