/*
 * UploadFileResourceAction.java
 *
 * Created on December 20, 2005, 12:27 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.resource;

import com.arexis.arxframe.io.FileDataObject;
import com.arexis.arxframe.io.WebFileUpload;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lami
 */
public class UploadModelFileResourceAction extends MugenAction {
    
    /** Creates a new instance of UploadFileResourceAction */
    public UploadModelFileResourceAction() {
    }
    
    public String getName() {
        return "UploadFileResourceAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = request.getSession();            
            WebFileUpload webFile = new WebFileUpload(request, 100000000);
            FileDataObject fileData = webFile.getFile("file");                        
            String upload = webFile.getFormParameter("upload");
            String name = webFile.getFormParameter("name");
            String comm = webFile.getFormParameter("comm");
            int catid = new Integer(webFile.getFormParameter("catid")).intValue();
            
            if(exists(upload)) {                
                MugenCaller caller = (MugenCaller)session.getAttribute("caller");

                FormDataManager formDataManager = getFormDataManager(
                        MugenFormDataManagerFactory.EXPMODEL, 
                        MugenFormDataManagerFactory.WEB_FORM, 
                        request);             

                String eid = formDataManager.getValue("eid");
                int resid = modelManager.addFileResource(Integer.parseInt(eid), fileData.getFileName(), comm, fileData, catid, caller);
                
                projectManager.log("user "+caller.getName()+" added file resource "+resid+" to model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
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
