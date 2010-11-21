package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.lang.Integer;

public class CreateGeneticBackgroundValueAction extends MugenAction {
    
    public String getName() {
        return "CreateGeneticBackgroundValueAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            Workflow wf = (Workflow)request.getAttribute("workflow");
            
            //if gen. back. value creation was triggered by gen. back. info assignment from model view
            //we need the following if statement+the passed attribute.
            if (request.getParameter("eid")!=null){
                wf.setAttribute("eid", request.getParameter("eid"));
            }
            
            if (request.getParameter("bid")!=null)
            {
                String backname = request.getParameter("backname");
                int bid = new Integer(request.getParameter("bid")).intValue();
                modelManager.updateGeneBackValue(bid,backname,caller);
                projectManager.log("user "+caller.getName()+" updated genetic background value "+bid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            }
            else if (wf.getAttribute("bid")!=null)
            {
                
                String backname = request.getParameter("backname");
                int bid = Integer.parseInt((String)wf.getAttribute("bid"));
                modelManager.updateGeneBackValue(bid,backname,caller);
                projectManager.log("user "+caller.getName()+" updated genetic background value "+bid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            }
            else{
                String backname = request.getParameter("backname");
                int bid = modelManager.createGeneBackValue(backname, caller);
                projectManager.log("user "+caller.getName()+" created genetic background value "+bid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
                wf.setAttribute("bid", new Integer(bid).toString());
                request.setAttribute("bid", new Integer(bid).toString());
            }
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("CreateGeneticBackgroundValueAction Failed to perform action", e);
        }
    }
}
