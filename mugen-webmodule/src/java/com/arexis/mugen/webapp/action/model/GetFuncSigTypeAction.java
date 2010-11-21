/*
 * GetFuncSigTypesAction.java
 *
 * Created on December 21, 2005, 2:18 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.model;
import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.modelmanager.FunctionalSignificanceTypeDTO;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lami
 */
public class GetFuncSigTypeAction extends MugenAction {
    
    /** Creates a new instance of GetFuncSigTypesAction */
    public GetFuncSigTypeAction() {
    }

    /**
     * Returns the name of this action
     * @return The action name
     */
    public String getName() {
        return "GetFuncSigTypeAction";
    }
    
    /**
     * Peroforms the action
     * @param req The http request object
     * @param context The servlet context
     * @return True of this action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");
            
            Workflow wf = (Workflow)req.getAttribute("workflow");
            
            String fstid_str = req.getParameter("fstid");
            
            if (fstid_str==null || fstid_str.equals(""))
                fstid_str = wf.getAttribute("fstid");
            
            wf.setAttribute("fstid", fstid_str);
            
            int fstid = new Integer(fstid_str).intValue();            
            FunctionalSignificanceTypeDTO fst = modelManager.getFunctionalSignificanceType(fstid, caller);
            
            req.setAttribute("funcsigtype", fst);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve functional significance types.");
        }
    }     
}
