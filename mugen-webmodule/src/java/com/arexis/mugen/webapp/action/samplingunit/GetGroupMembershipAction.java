/*
 * GetGroupMembershipAction.java
 *
 * Created on July 13, 2005, 1:30 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;
import com.arexis.arxframe.Navigator;
import com.arexis.arxframe.PageManager;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.samplingunit.samplingunitmanager.GroupDTO;
import com.arexis.mugen.samplingunit.samplingunitmanager.GroupingDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;


/**
 * MugenAction class for handling of group membership
 * @author lami
 */
public class GetGroupMembershipAction extends MugenAction {
    
    /** Creates a new instance of GetGroupMembershipAction */
    public GetGroupMembershipAction() {
        
    }
    
    /**
     * Returns the name of the action
     * @return The name of the action
     */
    public String getName() {
        return "GetGroupMembershipAction";
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
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.GROUP_MEMBERSHIP_FILTER,
                    MugenFormDataManagerFactory.WEB_FORM,
                    req);   
            Iterator i = null;
            
            int suid = 0;
            int gid = 0;
            int gsid = 0;
            
            int pid = caller.getPid();
            PageManager pm = nav.getPageManager();                  
            // Get the selected options...
            String tmpSuid = formDataManager.getValue("suid");
                                
            Collection su = samplingUnitManager.getSamplingUnits(pid, caller);            
            Collection groupings = samplingUnitManager.getGroupings(caller.getSuid(), caller);
            
            if(groupings.size() == 0)
                resetFormData(MugenFormDataManagerFactory.GROUP_MEMBERSHIP_FILTER, req);
            
            String tmpGsid = formDataManager.getValue("gsid");           
            
            // If it is the first time the page is displayed...choose the first 
            // grouping in the collection            
            if(!exists(tmpGsid)){
                i = groupings.iterator();
                if(i.hasNext()) {
                    GroupingDTO grDto = (GroupingDTO)i.next();                
                    gsid = grDto.getGsid();                
                }
            }
            // else, use the requested groupings id
            else{
                gsid = Integer.parseInt(tmpGsid);
            }           
                         
            Collection groups = new ArrayList();
            if(gsid > 0)
                groups = samplingUnitManager.getGroups(gsid, caller, caller.getSuid()); 
            
            if(groups.size() == 0)
                resetFormData(MugenFormDataManagerFactory.GROUP_MEMBERSHIP_FILTER, req);
                         
            String tmpGid = formDataManager.getValue("gid"); 
            
            // If it is the first time the page is displayed...choose the first 
            // group in the collection            
            if(!exists(tmpGid)){
                i = groups.iterator();
                if(i.hasNext()) {
                    GroupDTO grDto = (GroupDTO)i.next();                
                    gid = grDto.getGid();
                }
            }
            // else, use the requested group id
            else{                
                gid = Integer.parseInt(tmpGid);            
            }                                                  
                
            
            Collection availableInds = new ArrayList();  
            Collection includedInds = new ArrayList();                         
            
            if(gid > 0) {
                Collection[] tmp = samplingUnitManager.getGroupMembership(gid, formDataManager, caller);
                availableInds = tmp[1];
                includedInds = tmp[0];
            }
            
            if(groups.size() == 0)
                includedInds = new ArrayList();            
            
            // Put the collections in the memory
            req.setAttribute("groupdto", groups);
            req.setAttribute("samplingunits", su);
            req.setAttribute("groupingsdto", groupings);            
            req.setAttribute("formdata", formDataManager);                        
            req.setAttribute("availableInds", availableInds);
            req.setAttribute("includedInds", includedInds);                          
            
            return true;
        } catch (ApplicationException e) {
            throw new ApplicationException("Could not get group membership information: "+e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get group membership information.");
        }
    } 
}
