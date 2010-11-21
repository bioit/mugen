package com.arexis.mugen.webapp.action.phenotype;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.modelmanager.GeneDTO;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetPhenotypeOntologyAction extends MugenAction {
    
    public String getName() {
        return "GetPhenotypeOntologyAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = request.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");
            Workflow wf = (Workflow)request.getAttribute("workflow");
            
            int potid = 0;
            
            if (request.getParameter("potid")!=null){
                potid = new Integer(request.getParameter("potid")).intValue();
                se.setAttribute("potid", new Integer(potid).toString());  
            } else {
                potid = new Integer((String)se.getAttribute("potid")).intValue();
            }
            
            request.setAttribute("pot", modelManager.getPhenoOntology(potid, caller));
            request.setAttribute("alts", modelManager.getPhenoAltIds(potid, caller));
            request.setAttribute("syns", modelManager.getPhenoSynonyms(potid, caller));
            request.setAttribute("isas", modelManager.getPhenoIsAs(potid, caller));
            request.setAttribute("mpz", modelManager.getPhenoEndNodePaths(potid));
            request.setAttribute("models", modelManager.getExperimentalModelsByMP(potid, caller));
            request.setAttribute("genz", modelManager.getGenesByMP(new Integer(potid).toString()));            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("GetPhenotypeOntologyAction Failed to perform action", e);
        }
    }
}
