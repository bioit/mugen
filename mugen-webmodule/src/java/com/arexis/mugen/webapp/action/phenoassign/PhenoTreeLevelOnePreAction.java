package com.arexis.mugen.webapp.action.phenoassign;

import com.arexis.arxframe.io.FileDataObject;
import com.arexis.arxframe.io.WebFileUpload;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.MugenFormDataManagerFactory;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.arexis.mugen.model.modelmanager.PhenoOntologyDTO;

public class PhenoTreeLevelOnePreAction extends MugenAction {
    
    public String getName() {
        return "PhenoTreeLevelOnePreAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            HttpSession session = request.getSession();
            
            FormDataManager formDataManager = getFormDataManager(MugenFormDataManagerFactory.PHENOTREE, MugenFormDataManagerFactory.WEB_FORM, request);
            System.out.println("levelOne during Pre is -> "+formDataManager.getValue("levelOne"));
            request.setAttribute("formdata", formDataManager);
            
            Collection mptermsleveltwo = new ArrayList();
            Collection mptermslevelthree = new ArrayList();
            Collection mptermslevelfour = new ArrayList();
            Collection mptermslevelfive = new ArrayList();
            Collection mptermslevelsix = new ArrayList();
            Collection mptermslevelseven = new ArrayList();
            Collection mptermsleveleight = new ArrayList();
            Collection mptermslevelnine = new ArrayList();
            
            Collection bulk = new ArrayList();
            
            int is_level_two_collection_empty = 0;
            int is_level_three_collection_empty = 0;
            int is_level_four_collection_empty = 0;
            int is_level_five_collection_empty = 0;
            int is_level_six_collection_empty = 0;
            int is_level_seven_collection_empty = 0;
            int is_level_eight_collection_empty = 0;
            int is_level_nine_collection_empty = 0;
            
            //whatever happens the first level of mp terms must be uploaded
            request.setAttribute("mptermslevelone", modelManager.phenoCollectorLevelOne());
            
            
            //if user has selected some value from the first level list
            if(formDataManager.getValue("levelOne")!=""){
                //get the collection of all the corresponding second level terms
                mptermsleveltwo = modelManager.phenoCollectorLowerLevel(new Integer(formDataManager.getValue("levelOne")).intValue());
                
                if (mptermsleveltwo.size()==0){
                    is_level_two_collection_empty = 1;
                    request.setAttribute("is_level_two_collection_empty", new Integer(is_level_two_collection_empty).toString());
                }else{
                    is_level_two_collection_empty = 0;
                    request.setAttribute("is_level_two_collection_empty", new Integer(is_level_two_collection_empty).toString());
                }
                
                request.setAttribute("mptermsleveltwo", mptermsleveltwo);
                //if the user has selected a second level value
                if(formDataManager.getValue("levelTwo")!=""){
                    //check that the levelTwo value is part of the right collection
                    Iterator i = mptermsleveltwo.iterator();
                    
                    while(i.hasNext()){
                        int tmp_level_two_c = new Integer(formDataManager.getValue("levelTwo")).intValue();
                        int tmp_level_two_a = ((PhenoOntologyDTO)i.next()).getPhenoId();
                        
                        //if the levelTwo is not in the collection everything must be emptied...
                        if(tmp_level_two_c!=tmp_level_two_a){
                            //write code to empty here
                            /*mptermslevelthree = bulk;
                            mptermslevelfour = bulk;
                            mptermslevelfive = bulk;
                            mptermslevelsix = bulk;
                            mptermslevelseven = bulk;
                            mptermsleveleight = bulk;
                            mptermslevelnine = bulk;*/
                        }
                        else{
                            mptermslevelthree = modelManager.phenoCollectorLowerLevel(new Integer(formDataManager.getValue("levelTwo")).intValue());
                            
                            if (mptermslevelthree.size()==0){
                                is_level_three_collection_empty = 1;
                                request.setAttribute("is_level_three_collection_empty", new Integer(is_level_three_collection_empty).toString());
                            }else{
                                is_level_three_collection_empty = 0;
                                request.setAttribute("is_level_three_collection_empty", new Integer(is_level_three_collection_empty).toString());
                            }
                            
                            request.setAttribute("mptermslevelthree", mptermslevelthree);
                            //perform other controls and proceed
                            if(formDataManager.getValue("levelThree")!=""){
                                Iterator j = mptermslevelthree.iterator();
                                while(j.hasNext()){
                                    int tmp_level_three_c = new Integer (formDataManager.getValue("levelThree")).intValue();
                                    int tmp_level_three_a = ((PhenoOntologyDTO)j.next()).getPhenoId();
                                    
                                    if(tmp_level_three_c!=tmp_level_three_a){
                                        mptermslevelfour = bulk;
                                        mptermslevelfive = bulk;
                                        mptermslevelsix = bulk;
                                        mptermslevelseven = bulk;
                                        mptermsleveleight = bulk;
                                        mptermslevelnine = bulk;
                                    }
                                    else{
                                        mptermslevelfour = modelManager.phenoCollectorLowerLevel(new Integer(formDataManager.getValue("levelThree")).intValue());
                                        
                                        if (mptermslevelfour.size()==0){
                                            is_level_four_collection_empty = 1;
                                            request.setAttribute("is_level_four_collection_empty", new Integer(is_level_four_collection_empty).toString());
                                        }else{
                                            is_level_four_collection_empty = 0;
                                            request.setAttribute("is_level_four_collection_empty", new Integer(is_level_four_collection_empty).toString());
                                        }
                                        
                                        request.setAttribute("mptermslevelfour", mptermslevelfour);
                                        
                                        if(formDataManager.getValue("levelFour")!=""){
                                            Iterator k = mptermslevelfour.iterator();
                                            
                                            while(k.hasNext()){
                                                int tmp_level_four_c = new Integer (formDataManager.getValue("levelFour")).intValue();
                                                int tmp_level_four_a = ((PhenoOntologyDTO)k.next()).getPhenoId();
                                                
                                                if(tmp_level_four_c!=tmp_level_four_a){
                                                    mptermslevelfive = bulk;
                                                    mptermslevelsix = bulk;
                                                    mptermslevelseven = bulk;
                                                    mptermsleveleight = bulk;
                                                    mptermslevelnine = bulk;
                                                }
                                                else{
                                                    mptermslevelfive = modelManager.phenoCollectorLowerLevel(new Integer(formDataManager.getValue("levelFour")).intValue());
                                                    
                                                    if (mptermslevelfive.size()==0){
                                                        is_level_five_collection_empty = 1;
                                                        request.setAttribute("is_level_five_collection_empty", new Integer(is_level_five_collection_empty).toString());
                                                    }else{
                                                        is_level_five_collection_empty = 0;
                                                        request.setAttribute("is_level_five_collection_empty", new Integer(is_level_five_collection_empty).toString());
                                                    }
                                                    
                                                    request.setAttribute("mptermslevelfive", mptermslevelfive);
                                                    
                                                    if(formDataManager.getValue("levelFive")!=""){
                                                        Iterator l = mptermslevelfive.iterator();
                                                        
                                                        while(l.hasNext()){
                                                            int tmp_level_five_c = new Integer (formDataManager.getValue("levelFive")).intValue();
                                                            int tmp_level_five_a = ((PhenoOntologyDTO)l.next()).getPhenoId();
                                                            
                                                            if(tmp_level_five_c!=tmp_level_five_a){
                                                                mptermslevelsix = bulk;
                                                                mptermslevelseven = bulk;
                                                                mptermsleveleight = bulk;
                                                                mptermslevelnine = bulk;
                                                            }
                                                            else{
                                                                mptermslevelsix = modelManager.phenoCollectorLowerLevel(new Integer(formDataManager.getValue("levelFive")).intValue());
                                                                
                                                                if (mptermslevelsix.size()==0){
                                                                    is_level_six_collection_empty = 1;
                                                                    request.setAttribute("is_level_six_collection_empty", new Integer(is_level_six_collection_empty).toString());
                                                                }else{
                                                                    is_level_six_collection_empty = 0;
                                                                    request.setAttribute("is_level_six_collection_empty", new Integer(is_level_six_collection_empty).toString());
                                                                }
                                                                
                                                                request.setAttribute("mptermslevelsix", mptermslevelsix);
                                                                
                                                                if(formDataManager.getValue("levelSix")!=""){
                                                                    Iterator m = mptermslevelsix.iterator();
                                                                    
                                                                    while(m.hasNext()){
                                                                        int tmp_level_six_c = new Integer (formDataManager.getValue("levelSix")).intValue();
                                                                        int tmp_level_six_a = ((PhenoOntologyDTO)m.next()).getPhenoId();
                                                                        
                                                                        if(tmp_level_six_c!=tmp_level_six_a){
                                                                            mptermslevelseven = bulk;
                                                                            mptermsleveleight = bulk;
                                                                            mptermslevelnine = bulk;
                                                                        }
                                                                        else{
                                                                            mptermslevelseven = modelManager.phenoCollectorLowerLevel(new Integer(formDataManager.getValue("levelSix")).intValue());
                                                                            
                                                                            if (mptermslevelseven.size()==0){
                                                                                is_level_seven_collection_empty = 1;
                                                                                request.setAttribute("is_level_seven_collection_empty", new Integer(is_level_seven_collection_empty).toString());
                                                                            }else{
                                                                                is_level_seven_collection_empty = 0;
                                                                                request.setAttribute("is_level_seven_collection_empty", new Integer(is_level_seven_collection_empty).toString());
                                                                            }
                                                                            
                                                                            request.setAttribute("mptermslevelseven", mptermslevelseven);
                                                                            
                                                                            if(formDataManager.getValue("levelSeven")!=""){
                                                                                Iterator n = mptermslevelseven.iterator();
                                                                                
                                                                                while(n.hasNext()){
                                                                                    int tmp_level_seven_c = new Integer(formDataManager.getValue("levelSeven")).intValue();
                                                                                    int tmp_level_seven_a = ((PhenoOntologyDTO)n.next()).getPhenoId();
                                                                                    
                                                                                    if(tmp_level_seven_c!=tmp_level_seven_a){
                                                                                        mptermsleveleight = bulk;
                                                                                        mptermslevelnine = bulk;
                                                                                    }
                                                                                    else{
                                                                                        mptermsleveleight = modelManager.phenoCollectorLowerLevel(new Integer(formDataManager.getValue("levelSeven")).intValue());
                                                                                        
                                                                                        if (mptermsleveleight.size()==0){
                                                                                            is_level_eight_collection_empty = 1;
                                                                                            request.setAttribute("is_level_eight_collection_empty", new Integer(is_level_eight_collection_empty).toString());
                                                                                        }else{
                                                                                            is_level_eight_collection_empty = 0;
                                                                                            request.setAttribute("is_level_eight_collection_empty", new Integer(is_level_eight_collection_empty).toString());
                                                                                        }
                                                                                        
                                                                                        request.setAttribute("mptermsleveleight", mptermsleveleight);
                                                                                        
                                                                                        if(formDataManager.getValue("levelEight")!=""){
                                                                                            Iterator o = mptermsleveleight.iterator();
                                                                                            
                                                                                            while(o.hasNext()){
                                                                                                int tmp_level_eight_c = new Integer (formDataManager.getValue("levelEight")).intValue();
                                                                                                int tmp_level_eight_a = ((PhenoOntologyDTO)o.next()).getPhenoId();
                                                                                                
                                                                                                if(tmp_level_eight_c!=tmp_level_eight_a){
                                                                                                    mptermslevelnine = bulk;
                                                                                                }
                                                                                                else{
                                                                                                    mptermslevelnine = modelManager.phenoCollectorLowerLevel(new Integer(formDataManager.getValue("levelEight")).intValue());
                                                                                                    
                                                                                                    if (mptermslevelnine.size()==0){
                                                                                                        is_level_nine_collection_empty = 1;
                                                                                                        request.setAttribute("is_level_nine_collection_empty", new Integer(is_level_nine_collection_empty).toString());
                                                                                                    }else{
                                                                                                        is_level_nine_collection_empty = 0;
                                                                                                        request.setAttribute("is_level_nine_collection_empty", new Integer(is_level_nine_collection_empty).toString());
                                                                                                    }
                                                                                                    
                                                                                                    request.setAttribute("mptermslevelnine", mptermslevelnine);
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
                }
            }
            
            
            //old algorithm follows
            /*if(formDataManager.getValue("levelOne")!=""){
                request.setAttribute("mptermsleveltwo", modelManager.phenoCollectorLowerLevel(new Integer(formDataManager.getValue("levelOne")).intValue()));
            }
            
            if(formDataManager.getValue("levelTwo")!=""){
                request.setAttribute("mptermslevelthree", modelManager.phenoCollectorLowerLevel(new Integer(formDataManager.getValue("levelTwo")).intValue()));
            }
            
            if(formDataManager.getValue("levelThree")!=""){
                request.setAttribute("mptermslevelfour", modelManager.phenoCollectorLowerLevel(new Integer(formDataManager.getValue("levelThree")).intValue()));
            }
            
            if(formDataManager.getValue("levelFour")!=""){
                request.setAttribute("mptermslevelfive", modelManager.phenoCollectorLowerLevel(new Integer(formDataManager.getValue("levelFour")).intValue()));
            }
            
            if(formDataManager.getValue("levelFive")!=""){
                request.setAttribute("mptermslevelsix", modelManager.phenoCollectorLowerLevel(new Integer(formDataManager.getValue("levelFive")).intValue()));
            }
            
            if(formDataManager.getValue("levelSix")!=""){
                request.setAttribute("mptermslevelseven", modelManager.phenoCollectorLowerLevel(new Integer(formDataManager.getValue("levelSix")).intValue()));
            }*/
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }      
}
