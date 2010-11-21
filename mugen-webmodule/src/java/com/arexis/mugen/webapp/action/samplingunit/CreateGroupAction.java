/*
 * CreateGroupAction.java
 *
 * Created on August 3, 2005, 3:20 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;

import com.arexis.arxframe.Navigator;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import com.arexis.mugen.webapp.action.*;

/**
 * MugenAction class for creation of a group
 *
 * @author lami
 */
public class CreateGroupAction extends MugenAction{
    
    /** Creates a new instance of CreateGroupAction */
    public CreateGroupAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The action name
     */
    public String getName() {
        return "CreateGroupAction";
    }
    
    /**
     * Peroforms the action
     * @param req The http request object
     * @param context The servlet context
     * @return True of this action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) {
        Collection grpCollection = null;
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");
            int suid = caller.getSuid();
            resetFormData(MugenFormDataManagerFactory.GROUP, req);
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.GROUP,
                    MugenFormDataManagerFactory.WEB_FORM,
                    req);           
            
            grpCollection = samplingUnitManager.getGroupings(suid, nav.getPageManager(), caller);
            
            
            req.setAttribute("mugen.collection.groupingdto", grpCollection);
            req.setAttribute("samplingunits", samplingUnitManager.getSamplingUnits(caller.getPid(), caller));
            req.setAttribute("formdata", formDataManager);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
