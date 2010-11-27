package com.arexis.mugen.webapp.action.gene;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class SaveGeneAction extends MugenAction {
    
    public String getName() {
        return "SaveGeneAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller _caller = (MugenCaller)request.getSession().getAttribute("caller");
            
            int gaid = 0;
            
            if (request.getParameter("gaid")!=null)
            {
                gaid = new Integer(request.getParameter("gaid")).intValue();
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");
                String mgiid = request.getParameter("mgiid");
                String genesymbol = request.getParameter("genesymbol");
                String geneexpress = request.getParameter("geneexpress");
                String idgene = request.getParameter("idgene");
                String idensembl = request.getParameter("idensembl");
                String cid = request.getParameter("cid");
                int i_cid = new Integer(cid).intValue();
            
                modelManager.updateGene(gaid, name, comm, mgiid, genesymbol, geneexpress, idgene, idensembl, i_cid, _caller);
                
                projectManager.log("user "+_caller.getName()+" updated gene "+gaid, getName(), _caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            }
            else
            {
                String name = request.getParameter("name");
                String comm = request.getParameter("comm");
                String mgiid = request.getParameter("mgiid");
                String genesymbol = request.getParameter("genesymbol");
                String geneexpress = request.getParameter("geneexpress");
                String idgene = request.getParameter("idgene");
                String idensembl = request.getParameter("idensembl");
                String cid = request.getParameter("cid");
                int i_cid = new Integer(cid).intValue();
            
                gaid = modelManager.createGene(name, comm, mgiid, genesymbol, geneexpress, idgene, idensembl, i_cid, _caller);
                
                projectManager.log("user "+_caller.getName()+" created gene "+gaid, getName(), _caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
                
                //Workflow wf = (Workflow)request.getAttribute("workflow");
                //wf.setAttribute("gaid", new Integer(gaid).toString());
                
                //request.setAttribute("gaid", new Integer(gaid).toString());
            }
            
            request.getSession().setAttribute("viewgene", new Integer(gaid).toString());
            
            return true;
            
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("SaveGeneAction Failed to perform action", e);
        }
    }
}
