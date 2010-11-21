/*
 * UploadFuncSigFileAction.java
 *
 * Created on December 18, 2005, 3:04 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.model;

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
public class UploadFuncSigFileAction extends MugenAction {
    
    /**
     * Creates a new instance of UploadFuncSigFileAction 
     */
    public UploadFuncSigFileAction() {
    }
    
    public String getName() {
        return "UploadFuncSigFileAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = request.getSession();            
            WebFileUpload webFile = new WebFileUpload(request, 100000000);
            FileDataObject fileData = webFile.getFile("file");                        
            String upload = webFile.getFormParameter("upload");
            
            if(exists(upload)) {                
                MugenCaller caller = (MugenCaller)session.getAttribute("caller");

                FormDataManager formDataManager = getFormDataManager(
                        MugenFormDataManagerFactory.EXPMODEL, 
                        MugenFormDataManagerFactory.WEB_FORM, 
                        request);             

                String fsid = formDataManager.getValue("fsid");

                modelManager.addFunctionalSignificanceFile(Integer.parseInt(fsid), fileData, caller);
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
