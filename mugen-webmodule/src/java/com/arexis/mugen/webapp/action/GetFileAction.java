/*
 * GetFileAction.java
 *
 * Created on September 15, 2005, 6:09 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.exceptions.PermissionDeniedException;
import com.arexis.mugen.resource.file.FileRemote;
import com.arexis.mugen.resource.file.FileRemoteHome;
import com.arexis.mugen.servicelocator.ServiceLocator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author heto
 */
public class GetFileAction extends MugenAction {
    
    /** Creates a new instance of GetFileAction */
    public GetFileAction() {
    }
    
    public String getName() {
        return "GetFileAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            if (!caller.hasPrivilege("FILE_R"))
                throw new PermissionDeniedException("User "+caller.getName()+" is not allowed to view files. Privilege FILE_R is required.");
            
            
            Integer fileid = new Integer(workflow.getParameter("fileid"));
            FileRemoteHome fh = (FileRemoteHome)locator.getHome(ServiceLocator.Services.FILE);
            FileRemote file = fh.findByPrimaryKey(fileid);
            
            // Send the bean. This handles the file data array smarter.
            request.setAttribute("tmp.bean.file", file);
            
            return true;
        
        } 
        catch (ApplicationException e)
        {
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Unable to get file");
        }
    }
}
