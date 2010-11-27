package com.arexis.mugen.webapp.action.gene;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class SaveGeneModelAction extends MugenAction {
    
    public String getName() {
        return "SaveGeneModelAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {            
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            
            // Get variables
            String name = request.getParameter("name");
            String comm = request.getParameter("comm");
            String mgiid = request.getParameter("mgiid");
            String genesymbol = request.getParameter("genesymbol");
            String geneexpress = request.getParameter("geneexpress");
            String idgene = request.getParameter("idgene");
            String idensembl = request.getParameter("idensembl");
            String cid = request.getParameter("cid");
            int i_cid = new Integer(cid).intValue();
            
            Workflow wf = (Workflow)request.getAttribute("workflow");
            int eid = new Integer(wf.getAttribute("eid")).intValue();
            
            // Create and assign the new gene to a model.
            //int gaid = modelManager.createGeneModel(name, comm, mgiid, genesymbol, geneexpress, idgene, idensembl, eid, caller);
            int gaid = modelManager.createGene(name, comm, mgiid, genesymbol, geneexpress, idgene, idensembl, i_cid, caller);
            
            projectManager.log("user "+caller.getName()+" created gene "+gaid+" from model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Failed to perform action", e);
        }
    }
}
