package com.arexis.mugen.webapp.action.project;

import com.arexis.arxframe.io.FileDataObject;
import com.arexis.arxframe.io.WebFileUpload;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UploadProjectFileResourceAction extends MugenAction {
    
    public UploadProjectFileResourceAction() {}
    
    public String getName() {
        return "UploadProjectFileResourceAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            System.out.println("UploadProjectFileResourceAction#performAction start");
            HttpSession session = request.getSession();            
            WebFileUpload webFile = new WebFileUpload(request, 100000000);
            
            FileDataObject fileData = webFile.getFile("file"); 
            String upload = webFile.getFormParameter("upload");
            String comm = webFile.getFormParameter("comm");            
            String name = webFile.getFormParameter("name");
                                    
            String pidId = webFile.getFormParameter("id");            
            String catId = webFile.getFormParameter("catId");
            
            if(exists(upload)) {                
                MugenCaller caller = (MugenCaller)session.getAttribute("caller");        
                projectManager.addResource("file", Integer.parseInt(catId), Integer.parseInt(pidId), name, comm, fileData, caller, null);
            }
            
            System.out.println("UploadProjectFileResourceAction#performAction end");
            return true;
        } catch (ApplicationException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to upload file",e);
        }
        //return false;
    }     
}
