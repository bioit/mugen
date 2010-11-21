/*
 * EditRepositoryAction.java
 *
 * Created on July 20, 2006, 22:18 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.model.modelmanager.RepositoriesDTO;


/**
 *
 * @author zouberakis
 */
public class EditRepositoryAction extends MugenAction {
    
    public String getName() {
        return "EditRepositoryAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            Workflow wf = (Workflow)request.getAttribute("workflow");
            int rid = new Integer(wf.getAttribute("rid")).intValue();
            String reponame = request.getParameter("reponame");
            String repourl = request.getParameter("repourl");
            modelManager.updateRepositoryName(rid, reponame ,repourl);
            
            projectManager.log("user "+caller.getName()+" updated repository "+rid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("EditRepositoryAction failed to perform action", e);
        }
    }
}
