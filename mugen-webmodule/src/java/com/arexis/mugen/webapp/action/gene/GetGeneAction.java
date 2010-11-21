package com.arexis.mugen.webapp.action.gene;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.modelmanager.GeneDTO;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetGeneAction extends MugenAction {
    
    public String getName() {
        return "GetGeneAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = request.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");
            Workflow wf = (Workflow)request.getAttribute("workflow");
            
            int gaid = 0;
            
            if (request.getParameter("gaid")!=null && request.getParameter("gaid").compareTo("*")!=0){
                gaid = new Integer(request.getParameter("gaid")).intValue();
                se.setAttribute("viewgene", new Integer(gaid).toString());
                wf.setAttribute("gaid", new Integer(gaid).toString());  
            } else {
                String viewgene = (String)se.getAttribute("viewgene");
                gaid = new Integer(viewgene).intValue();
            }
            
            request.setAttribute("mpts", modelManager.getPhenoPathsByGene(gaid));
            
            GeneDTO gene = null;
            
            if(wf.getName().compareTo("EditGene")==0){
                gene = modelManager.getGene(gaid, caller);
            }else{
                gene = modelManager.getGene(gaid, caller, "superscript");
            }
            
            request.setAttribute("gene", gene);
            
            Collection models = modelManager.getModelsByGene(gaid, caller);
            request.setAttribute("models", models);
            
            Collection alleles = modelManager.getAllelesByGene(gaid, caller);
            request.setAttribute("alleles", alleles);
            
            Collection chromosomes = modelManager.getChromosomesForSpecies(caller.getSid(), caller);
            request.setAttribute("chromosomes", chromosomes);
            
            if(request.getParameter("workflow")!=null){
                String workflowCheck = request.getParameter("workflow");
                if(workflowCheck.compareTo("ViewGeneDirect")==0){
                    projectManager.log("user "+caller.getName()+" viewed gene "+gaid+" [directview]", getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
                }
            }
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("GetGeneAction Failed to perform action", e);
        }
    }
}
