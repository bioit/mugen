package com.arexis.mugen.webapp.action.ontology.cell;

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

public class LoadCellAction extends MugenAction {
    
    public LoadCellAction() {}
    
    public String getName() {
        return "LoadCellAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            
            modelManager.loadCELL();
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }      
}
