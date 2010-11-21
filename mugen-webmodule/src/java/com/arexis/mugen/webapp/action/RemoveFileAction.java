/*
 * RemoveFileAction.java
 *
 * Created on October 17, 2005, 9:33 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author heto
 */
public class RemoveFileAction extends MugenAction {
    
    /** Creates a new instance of RemoveFileAction */
    public RemoveFileAction() {
    }
    
    public String getName() {
        return "RemoveFileAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            
            ConfirmAction c = new ConfirmAction();
            c.performAction(request, context);
            
            String tmp = (String)request.getSession().getAttribute("tmp.preconfirmremoveaction.fileid");
            System.out.println("fileid="+tmp);
            int fileId = new Integer(tmp).intValue();

            resourceManager.removeFile(fileId, (MugenCaller)caller);
            
            
            request.getSession().removeAttribute("tmp.preconfirmremoveaction.fileid");
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
