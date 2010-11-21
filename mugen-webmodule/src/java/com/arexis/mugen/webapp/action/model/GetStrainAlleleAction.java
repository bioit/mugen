package com.arexis.mugen.webapp.action.model;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.modelmanager.StrainAlleleDTO;
import com.arexis.mugen.webapp.action.MugenAction;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetStrainAlleleAction extends MugenAction
{
    
    public GetStrainAlleleAction() {}

    public String getName() {
        return "GetStrainAlleleAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");   
            
            String strainAlleleId = "";
            
            if (req.getParameter("strainalleleid")!=null){
                strainAlleleId = req.getParameter("strainalleleid");
            }else{
                strainAlleleId = (String)se.getAttribute("strainalleleid");
            }
            
            se.setAttribute("strainalleleid", strainAlleleId);
            
            //System.out.println("GetStrainAllele#performAction: strainalleleid="+strainAlleleId);
            
            String isTransgenic = "";
            if(req.getParameter("isatransgc")!=null){
                isTransgenic = req.getParameter("isatransgc");
            }
            
            if (req.getAttribute("isatransgc")!=null){
                isTransgenic = (String)req.getAttribute("isatransgc");
            }
            
            Collection genes = null;
            
            //if (new Integer(isTransgenic).intValue()==0){
                genes = modelManager.getGenesByProject(caller.getPid(), caller, true); 
            /*}else{
                genes = modelManager.getGenesForTransgenicMice(caller.getPid(), caller);
            }*/
            
            StrainAlleleDTO strainAllele = modelManager.getStrainAllele(new Integer(strainAlleleId).intValue(),caller);
            Collection mutationTypes = modelManager.getMutationTypesFromStrainAllele(strainAllele.getId(),caller);
            Collection mutationTypeAttributes = modelManager.getMutationTypeAttributes();
            
            req.setAttribute("strainallele", strainAllele);
            req.setAttribute("mutationtypes", mutationTypes);
            req.setAttribute("genes", genes);
            req.setAttribute("attributes", mutationTypeAttributes);
            
            String eid = "";
            if(req.getParameter("eid")!=null){
                eid = req.getParameter("eid");
            }else{
                eid = (String)se.getAttribute("eid");
            }
            
            se.setAttribute("eid", eid);
            se.setAttribute("isatransgc", isTransgenic);
            req.setAttribute("isatransgc", isTransgenic);
            return true;
        
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not create strains allele", e);
        }
    }     
}
