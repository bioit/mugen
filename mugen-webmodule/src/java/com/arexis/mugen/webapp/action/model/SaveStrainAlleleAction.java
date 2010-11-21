/*
 * GetStrainTypesAction.java
 *
 * Created on July 10, 2006, 3:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.arexis.mugen.webapp.action.model;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author heto
 */
public class SaveStrainAlleleAction extends MugenAction
{
    
    /** Creates a new instance of GetStrainTypesAction */
    public SaveStrainAlleleAction()
    {
    }

    /**
     * Returns the name of this action
     * @return The action name
     */
    public String getName() {
        return "SaveStrainAlleleAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");                                                              
            
            int strainalleleid = new Integer((String)se.getAttribute("strainalleleid")).intValue();
            
            String symbol = req.getParameter("symbol");
            String name = req.getParameter("name");
            String attributes = req.getParameter("attributes");
            String gene = req.getParameter("gene");
            int geneId = 0;
            
            int eid = new Integer((String)se.getAttribute("eid")).intValue();
            
            //int isTrans = new Integer((String)se.getAttribute("isatransgc")).intValue();
            //int isTrans = new Integer((String)req.getAttribute("isatransgc")).intValue();
            
            if (gene!=null && gene!=""){
                geneId = new Integer(gene).intValue();
                /*if(isTrans == 1 && modelManager.getGeneAssignmentForTransgenicModel(eid,geneId,caller)==0){
                    modelManager.addGeneToModel(geneId, eid, caller);
                }*/
            }
            String mgiid = req.getParameter("mgiid");
            
            modelManager.updateStrainAllele(eid, strainalleleid, symbol, name, attributes, geneId, mgiid, caller);
            
            //System.out.println("geneId equals --> "+geneId);
            //System.out.println("gene equals --> "+gene);
            
            if(gene==""){
                modelManager.clearGeneFromStrainAllele(strainalleleid);
            }
            
            projectManager.log("user "+caller.getName()+" edited allele "+strainalleleid+" for model "+eid, getName(), caller.getName(), req.getRemoteAddr(), req.getRemoteHost());
            
            //modelManager.removeStrainAllelesFromGene(eid, caller);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not save strains allele", e);
        }
    }
}
