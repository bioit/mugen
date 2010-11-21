package com.arexis.mugen.webapp.action.model;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.modelmanager.StrainDTO;
import com.arexis.mugen.webapp.action.MugenAction;
import java.lang.Character;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class GetModelAction extends MugenAction {
    
    private static Logger logger = Logger.getLogger(GetModelAction.class);
    
    public GetModelAction() {}

    public String getName() {
        return "GetModelAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            HttpSession se = req.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");                                                              
            
            String tmpEid = req.getParameter("eid");
            
            if(exists(tmpEid) && new Character(tmpEid.charAt(0)).compareTo(new Character('M'))==0){
                tmpEid = tmpEid.substring(1);
            }
            logger.debug("eid="+tmpEid);
            
            FormDataManager formDataManager = getFormDataManager(
                    MugenFormDataManagerFactory.EXPMODEL, 
                    MugenFormDataManagerFactory.WEB_FORM, 
                    req);  
                    
            
            if(exists(tmpEid)) {
                collectFormData(MugenFormDataManagerFactory.EXPMODEL, MugenFormDataManagerFactory.WEB_FORM, req);                                                                                   
                if(exists(formDataManager.getValue("eid"))) {                   
                    if(!tmpEid.equals(formDataManager.getValue("eid"))) {
                        resetFormData(MugenFormDataManagerFactory.EXPMODEL, req);
                    }              
                }
                
                formDataManager.put("eid", tmpEid);                                              
            }
            
            int eid = Integer.parseInt(formDataManager.getValue("eid")); 
            Collection genmods = modelManager.getGeneticModifications(eid, caller);
            if(genmods.size() == 0)
                genmods = new ArrayList();
            
            req.setAttribute("modeldto", modelManager.getExperimentalModel(eid, caller));    
            req.setAttribute("genmods", genmods);    

            req.setAttribute("researchApps", modelManager.getResearchApplications(caller)); 
            req.setAttribute("funcSigs", modelManager.getFunctionalSignificances(eid, caller)); 
            req.setAttribute("genesAffected", modelManager.getGenesByModel(eid, caller)); 
            req.setAttribute("references", modelManager.getReferences(eid, caller));
            
            req.setAttribute("geneticBackground", modelManager.getGeneticBackground(eid,caller));
            StrainDTO strain = modelManager.getStrainFromModel(eid, caller);
            
            System.out.println("GetModelAction#performAction: strainid="+strain.getStrainId());
            
            req.setAttribute("straindto", strain);
            req.setAttribute("types", modelManager.getStrainTypesForStrain(strain.getStrainId(),caller));
            req.setAttribute("states", modelManager.getStrainStatesForStrain(strain.getStrainId(),caller));
            req.setAttribute("availability", modelManager.getAvailabilityForModel(eid));
                
            req.setAttribute("strainalleles", modelManager.getStrainAllelesFromStrain(strain.getStrainId(), caller));
            req.setAttribute("resourceTree", modelManager.getResourceTreeCollection(eid, caller));
            //req.setAttribute("phenoPaths", modelManager.getPhenosFromModel(eid));
            req.setAttribute("curruser", caller.getName());
            
            req.setAttribute("usercomms", modelManager.getUserCommentsByModel(eid));
            
            req.setAttribute("mpz", modelManager.getPhenoPaths(eid));
            
            if(req.getParameter("workflow")!=null){
                String workflowCheck = req.getParameter("workflow");
                if(workflowCheck.compareTo("ViewModelDirect")==0){
                    req.setAttribute("DirectViewLevel", modelManager.getExperimentalModel(eid, caller).getLevel());
                    projectManager.log("user "+caller.getName()+" viewed model "+eid+" [directview]", getName(), caller.getName(), req.getRemoteAddr(), req.getRemoteHost());
                }
            }
            
            return true;
        } catch (ApplicationException e) {
            logger.error("Failed to get model",e);
            throw e;
        } catch (Exception e) {
            logger.error("Failed to get model. Unknown error", e);
            throw new ApplicationException("Could not retrieve model.");
        }
    }     
}
