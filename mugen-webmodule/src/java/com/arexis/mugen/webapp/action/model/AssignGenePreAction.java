package com.arexis.mugen.webapp.action.model;
import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class AssignGenePreAction extends MugenAction {
    
    public String getName() {
        return "AssignGenePreAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            Workflow wf = (Workflow)request.getAttribute("workflow");
            
            //String eid = request.getParameter("eid");
            String eid = "";
            
            if (request.getParameter("eid")!= null){
                eid = request.getParameter("eid");
            } else {
                eid = wf.getAttribute("eid");
            }
            
            Collection genes = null;
            //Collection genes = modelManager.getUnassignedGenes(Integer.parseInt(eid), caller.getPid(), caller);
            
            request.setAttribute("chromosomes", modelManager.getChromosomesForSpecies(caller.getSid(), caller));
            
            //int strainid = new Integer((String)request.getParameter("strainid")).intValue();
            
            //int isTransgenic = new Integer((String)request.getParameter("transgc")).intValue();
            
            //if(isTransgenic == 0){
                genes = modelManager.getUnassignedGenes(Integer.parseInt(eid), caller.getPid(), caller);
            /*}else{
                genes = modelManager.getUnassignedGenesForTransgenic(Integer.parseInt(eid), strainid, caller.getPid(), caller);
            }*/
            
            request.setAttribute("genes", genes);
            
            wf.setAttribute("eid", eid);
            request.setAttribute("eid", eid);
            //request.setAttribute("strainid", Integer.toString(strainid));
            //request.setAttribute("transgc", Integer.toString(isTransgenic));

            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Failed to perform action", e);
        }
    }
}
