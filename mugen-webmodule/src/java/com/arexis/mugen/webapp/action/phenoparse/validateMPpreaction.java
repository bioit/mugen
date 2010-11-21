package com.arexis.mugen.webapp.action.phenoparse;

import com.arexis.arxframe.io.FileDataObject;
import com.arexis.arxframe.io.WebFileUpload;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class validateMPpreaction extends MugenAction {
    
    public validateMPpreaction() {}
    
    public String getName() {
        return "validateMPpreaction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            
            request.setAttribute("total", new Integer(modelManager.numreassignMP()).toString());
            //Collection altmps = modelManager.reassignMP();
            //request.setAttribute("altmps", altmps);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }      
}
