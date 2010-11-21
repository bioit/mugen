package com.arexis.mugen.webapp.action.model;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.modelmanager.StrainAlleleDTO;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetStrainAlleleViewAction extends MugenAction
{
    
    public GetStrainAlleleViewAction() {}

    public String getName() {
        return "GetStrainAlleleViewAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");   
            
            String allid = "";
            
            if(exists(req.getParameter("allid"))){
                allid = req.getParameter("allid");
                se.setAttribute("allid", allid);
            } else {
                allid = (String) se.getAttribute("allid");
            }
            
            StrainAlleleDTO allele = modelManager.getStrainAlleleView(new Integer(allid).intValue(),caller);
            Collection models = modelManager.getExperimentalModelsByAllele(allele.getId(), caller);
            Collection mpts = modelManager.getPhenoPathsByAllele(allele.getId());
            
            req.setAttribute("allele", allele);
            req.setAttribute("models", models);
            req.setAttribute("mpts", mpts);
            
            if(req.getParameter("workflow")!=null){
                String workflowCheck = req.getParameter("workflow");
                if(workflowCheck.compareTo("ViewStrainAlleleDirect")==0){
                    projectManager.log("user "+caller.getName()+" viewed allele "+allid+" [directview]", getName(), caller.getName(), req.getRemoteAddr(), req.getRemoteHost());
                }
            }
            
            return true;
        
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not create strains allele", e);
        }
    }     
}
