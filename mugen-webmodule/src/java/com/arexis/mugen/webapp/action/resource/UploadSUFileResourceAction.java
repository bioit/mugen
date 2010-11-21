/*
 * UploadProteinFileResourceAction.java
 *
 * Created on February 1, 2006, 11:28 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.resource;

import com.arexis.arxframe.io.FileDataObject;
import com.arexis.arxframe.io.WebFileUpload;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lami
 */
public class UploadSUFileResourceAction extends MugenAction {
    
    /** Creates a new instance of UploadSUFileResourceAction */
    public UploadSUFileResourceAction() {
    }
    
    public String getName() {
        return "UploadSUFileResourceAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = request.getSession();            
            WebFileUpload webFile = new WebFileUpload(request, 100000000);
            
            FileDataObject fileData = webFile.getFile("file"); 
            String upload = webFile.getFormParameter("upload");
            String comm = webFile.getFormParameter("comm");            
            String name = webFile.getFormParameter("name");
                                    
            String proteinId = webFile.getFormParameter("id");            
            String catId = webFile.getFormParameter("catid");
            
            int suid = new Integer(workflow.getAttribute("suid")).intValue();
            
            caller = (MugenCaller)session.getAttribute("caller");
            
            if(exists(upload)) {                
                MugenCaller caller = (MugenCaller)session.getAttribute("caller");        
                samplingUnitManager.addResource("file", Integer.parseInt(catId), caller.getPid(), name, comm, fileData, caller, null, suid);
            }
            
            
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }     
}
