/*
 * GetGroupsAction.java
 *
 * Created on August 3, 2005, 3:18 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;
import com.arexis.arxframe.Navigator;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.samplingunit.samplingunitmanager.GroupingDTO;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;
import java.util.Iterator;

/**
 * MugenAction class for handling retrieval of groups
 * @author lami
 */
public class GetGroupsAction extends MugenAction{
    
    /** Creates a new instance of GetGroupsAction */
    public GetGroupsAction() {
        super();
    }

    /**
     * Returns the name of this action
     * @return The action name
     */
    public String getName() {
        return "GetGroupsAction";
    }
    
    /**
     * Peroforms the action
     * @param req The http request object
     * @param context The servlet context
     * @return True of this action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException{
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");           
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.GROUPING_FILTER, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);  
            
            if(exists(req.getParameter("gsid"))) {                
                formDataManager.put("gsid", ""+req.getParameter("gsid"));
            }                  
            
            int suid = caller.getSuid();
            int gsid = 0;            
                    
            Collection samplingunits = samplingUnitManager.getSamplingUnits(caller.getPid(), caller);          
            
            Collection grpCollection = samplingUnitManager.getGroupings(suid, caller);
            Collection groups = new ArrayList();
            
            req.setAttribute("mugen.collection.groupingdto", grpCollection);  
            
            String tmpGsid = formDataManager.getValue("gsid");
            
            if(exists(tmpGsid) || grpCollection.size() > 0){
                if(exists(tmpGsid))
                    gsid = Integer.parseInt(tmpGsid);
                else {
                    Iterator i = grpCollection.iterator();
                    GroupingDTO dto = (GroupingDTO)i.next();
                    gsid = dto.getGsid();
                    
                    // Need to store grouping id if selected but not
                    // actively chosen by the user...otherwise, gsid will
                    // be empty in the quick links to individuals view/edit
                    formDataManager.put("gsid", ""+gsid);
                }
            
                groups = samplingUnitManager.getGroups(gsid, nav.getPageManager(), caller, suid);
                nav.getPageManager().setMax(samplingUnitManager.getNumberOfGroups(gsid, caller));                  
            }                            

            
            req.setAttribute("mugen.collection.groupdto", groups);
            req.setAttribute("samplingunits", samplingunits);
            req.setAttribute("formdata", formDataManager);
            
            return true;
        } catch (ApplicationException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve groups.");
        }
    }     
}
