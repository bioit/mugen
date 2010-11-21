package com.arexis.mugen.webapp.action.model;

import com.arexis.arxframe.ArxLoginForward;
import com.arexis.arxframe.IWorkFlowManager;
import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.arexis.arxframe.advanced.AdvancedWorkflowManager;

public class AssignPhenoAction extends MugenAction {

    public AssignPhenoAction() {
    }

    public String getName() {
        return "AssignPhenoAction";
    }

    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");
            HttpSession se = req.getSession();
            Workflow wf = (Workflow)req.getAttribute("workflow");
            
            collectFormData(MugenFormDataManagerFactory.PHENOTREE, MugenFormDataManagerFactory.WEB_FORM, req);
            
            FormDataManager formDataManagerEID = getFormDataManager(MugenFormDataManagerFactory.EXPMODEL, MugenFormDataManagerFactory.WEB_FORM, req);
            
            int eid = Integer.parseInt(formDataManagerEID.getValue("eid")); 
            if(isSubmit(req, "ass")) {
                
                projectManager.log("user "+caller.getName()+" assigned mp term to model "+eid, getName(), caller.getName(), req.getRemoteAddr(), req.getRemoteHost());
                
                String StringLevelOne = req.getParameter("levelOne");
                if(StringLevelOne.compareTo("")!=0){
                    int IntLevelOne = new Integer(StringLevelOne).intValue();
                    
                    String StringLevelTwo = req.getParameter("levelTwo");
                    //if the levelTwo parameter does not exist or if it is zero
                    if(!exists(StringLevelTwo) || StringLevelTwo.compareTo("")==0){
                        //proceed with pheno assignment
                        modelManager.addPhenoToModel(eid, IntLevelOne, 0,0,0,0,0,0,0,0,caller);
                    }
                    //otherwise...
                    else{
                        //get the integer from levelTwo parameter
                        int IntLevelTwo = new Integer(StringLevelTwo).intValue();
                        
                        String StringLevelThree = req.getParameter("levelThree");
                        if(!exists(StringLevelThree) || StringLevelThree.compareTo("")==0){
                            modelManager.addPhenoToModel(eid, IntLevelOne, IntLevelTwo,0,0,0,0,0,0,0,caller);
                        }
                        else{
                            //get the integer from levelThree parameter
                            int IntLevelThree = new Integer(StringLevelThree).intValue();
                            
                            String StringLevelFour = req.getParameter("levelFour");
                            if(!exists(StringLevelFour) || StringLevelFour.compareTo("")==0){
                                modelManager.addPhenoToModel(eid, IntLevelOne, IntLevelTwo, IntLevelThree,0,0,0,0,0,0,caller);
                            }
                            else{
                                int IntLevelFour = new Integer(StringLevelFour).intValue();
                                
                                String StringLevelFive = req.getParameter("levelFive");
                                if(!exists(StringLevelFive) || StringLevelFive.compareTo("")==0){
                                    modelManager.addPhenoToModel(eid, IntLevelOne, IntLevelTwo, IntLevelThree, IntLevelFour,0,0,0,0,0,caller);
                                }
                                else{
                                    int IntLevelFive = new Integer(StringLevelFive).intValue();
                                    
                                    String StringLevelSix = req.getParameter("levelSix");
                                    if(!exists(StringLevelSix) || StringLevelSix.compareTo("")==0){
                                        modelManager.addPhenoToModel(eid, IntLevelOne, IntLevelTwo, IntLevelThree, IntLevelFour, IntLevelFive,0,0,0,0,caller);
                                    }
                                    else{
                                        int IntLevelSix = new Integer(StringLevelSix).intValue();
                                        
                                        String StringLevelSeven = req.getParameter("levelSeven");
                                        if(!exists(StringLevelSeven) || StringLevelSeven.compareTo("")==0){
                                            modelManager.addPhenoToModel(eid, IntLevelOne, IntLevelTwo, IntLevelThree, IntLevelFour, IntLevelFive, IntLevelSix,0,0,0,caller);
                                        }
                                        else{
                                            int IntLevelSeven = new Integer(StringLevelSeven).intValue();
                                            
                                            String StringLevelEight = req.getParameter("levelEight");
                                            if(!exists(StringLevelEight) || StringLevelEight.compareTo("")==0){
                                                modelManager.addPhenoToModel(eid, IntLevelOne, IntLevelTwo, IntLevelThree, IntLevelFour, IntLevelFive, IntLevelSix, IntLevelSeven,0,0,caller);
                                            }
                                            else{
                                                int IntLevelEight = new Integer(StringLevelEight).intValue();
                                                
                                                String StringLevelNine = req.getParameter("levelNine");
                                                if(!exists(StringLevelNine) || StringLevelNine.compareTo("")==0){
                                                    modelManager.addPhenoToModel(eid, IntLevelOne, IntLevelTwo, IntLevelThree, IntLevelFour, IntLevelFive, IntLevelSix, IntLevelSeven, IntLevelEight,0,caller);
                                                }
                                                else{
                                                    int IntLevelNine = new Integer(StringLevelNine).intValue();
                                                    
                                                    modelManager.addPhenoToModel(eid, IntLevelOne, IntLevelTwo, IntLevelThree, IntLevelFour, IntLevelFive, IntLevelSix, IntLevelSeven, IntLevelEight, IntLevelNine,caller);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else if(isSubmit(req, "whodat")){
                //setWorkflow(new Workflow("ViewModel"));
                //ArxLoginForward f = new ArxLoginForward("Client is forwarded to view model page...");
                //f.setUrl("Controller?workflow=ViewModel&eid="+formDataManagerEID.getValue("eid"));
                //do nothing...well almost.
                resetFormData(MugenFormDataManagerFactory.PHENOTREE, req);
            }
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Pheno Assignment Action Failed.");
        }
    }      
}
