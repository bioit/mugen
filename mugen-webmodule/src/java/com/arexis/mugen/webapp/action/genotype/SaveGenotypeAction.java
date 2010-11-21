/*
 * SaveGenotypeAction.java
 *
 * Created on August 2, 2005, 12:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;

import com.arexis.arxframe.ActionException;
import com.arexis.arxframe.Navigator;
import com.arexis.arxframe.advanced.AlterWorkflowException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;

/**
 * MugenAction class for saving a genotype
 * @author lami
 */
public class SaveGenotypeAction extends MugenAction {
    
    /**
     * Creates a new instance of SaveGenotypeAction
     */
    public SaveGenotypeAction() {
    }
    
    /**
     * Returns the name of this action
     * @return The name of the action
     */
    public String getName() {
        return "SaveGenotypeAction";
    }
    
    /**
     * Performs the action
     * @param request The http request object
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ActionException {
        int mid = 0;
        int iid = 0;
        try {
            HttpSession session = request.getSession();
            Navigator nav = (Navigator)request.getSession().getAttribute("navigator");
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");
            
            String raw1 = request.getParameter("raw1");
            String raw2 = request.getParameter("raw2");
            String reference = request.getParameter("reference");
            String comm = request.getParameter("comm");
            String aid1 = request.getParameter("aid1");
            String aid2 = request.getParameter("aid2");
            String level = request.getParameter("level");
            String tmpMid = request.getParameter("mid");
            String tmpIid = request.getParameter("iid");
            String tmpCid = request.getParameter("cid");
                                  
                  
            String tmpSuid = request.getParameter("suid");              
            if(exists(tmpSuid))
                caller.setSuid(Integer.parseInt(tmpSuid));                                     
          
            if(isSubmit(request, "reset"))
                resetFormData(MugenFormDataManagerFactory.GENOTYPE, request);
            else
                collectFormData(MugenFormDataManagerFactory.GENOTYPE, MugenFormDataManagerFactory.WEB_FORM, request);            
            
            if (isSubmit(request, "save")) {
                iid = Integer.parseInt(request.getParameter("iid"));
                mid = Integer.parseInt(request.getParameter("mid"));
                
                genotypeManager.updateGenotype(iid, mid, raw1, raw2, reference, comm, Integer.parseInt(aid1), Integer.parseInt(aid2), Integer.parseInt(level), caller);
            } else if(isSubmit(request, "remove")) {
                iid = Integer.parseInt(request.getParameter("iid"));
                mid = Integer.parseInt(request.getParameter("mid"));
                genotypeManager.removeGenotype(mid, iid, caller, Integer.parseInt(level));
            }
            
            else if(isSubmit(request, "new")) {
                iid = Integer.parseInt(request.getParameter("iid"));
                mid = Integer.parseInt(request.getParameter("mid"));
                
                genotypeManager.createGenotype(iid, mid, comm, raw1, raw2, reference, Integer.parseInt(aid1), Integer.parseInt(aid2), Integer.parseInt(level), caller.getSuid(), caller);
            }
            else {
                throw new AlterWorkflowException("RELOAD");
                /*
                ActionException e = new ActionException("RELOAD");
                e.setAlt("RELOAD");
                throw e;
                 */
            }
            return true;
        } catch (ActionException e) {
            throw e;                    
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActionException("Failed to update genotype");
        }
    }
}
