/*
 * CreateGeneticBackgroundAction.java
 *
 * Created on July 18, 2006, 12:10 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author zouberakis
 */
public class CreateGeneticBackgroundAction extends MugenAction {
    
    public String getName() {
        return "CreateGeneticBackgroundAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            Workflow wf = (Workflow)request.getAttribute("workflow");
            
            if (request.getParameter("dna_origin")!=null){
                int eid = new Integer(wf.getAttribute("eid")).intValue();
                int dna_origin = new Integer(request.getParameter("dna_origin")).intValue();
                int targeted_back = new Integer(request.getParameter("targeted_back")).intValue();
                int host_back = new Integer(request.getParameter("host_back")).intValue();
                int backcrossing_strain = new Integer(request.getParameter("backcrossing_strain")).intValue();
                String backcrosses = request.getParameter("backcrosses");
                
                modelManager.setGeneticBackgroundForModel(eid, dna_origin, targeted_back, host_back, backcrossing_strain, backcrosses);
                
                projectManager.log("user "+caller.getName()+" added genetic background for model "+eid, getName(), caller.getName(), request.getRemoteAddr(), request.getRemoteHost());
            }
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("CreateGeneticBackgroundAction Failed to perform action", e);
        }
    }
}
