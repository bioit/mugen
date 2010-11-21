/*
 * GetGenotypesAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;

import com.arexis.arxframe.ActionException;
import com.arexis.arxframe.Navigator;
import com.arexis.arxframe.advanced.Workflow;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.genotype.genotypemanager.ChromosomeDTO;
import com.arexis.mugen.genotype.genotypemanager.LevelDTO;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.ParamCollector;
import com.arexis.mugen.project.ParamDataObject;
import com.arexis.mugen.samplingunit.samplingunitmanager.GroupDTO;
import com.arexis.mugen.samplingunit.samplingunitmanager.GroupingDTO;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;


/**
 * MugenAction class for handling the retrieval of genotypes
 * @author lami
 */
public class GetGenotypesAction extends MugenAction{
    
    /** Creates a new instance of GetGenotypesAction */
    public GetGenotypesAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetGenotypesAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ActionException {
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");
            
            HttpSession se = req.getSession();
            
            ParamDataObject pdo = (ParamDataObject)se.getAttribute("genotypes.pdo");
            
            if(pdo == null) {
                
                ParamCollector pc = new ParamCollector(true);
                pc.putDefault("g.suid", ""+caller.getSuid());   
                pc.putDefault("g.level_", "*");
                pc.putDefault("chromosome", "*");
                pc.putDefault("g.reference", "");
                pc.putDefault("identity", "");
                pc.putDefault("usr", "");
                pc.putDefault("allele1", "");
                pc.putDefault("allele2", "");
                pc.putDefault("marker", "");
                pc.putDefault("datefrom", "");
                pc.putDefault("dateto", "");
                pc.putDefault("gid", "*");
                pc.putDefault("gsid", "*");  
                
                pdo = pc.fillDefaults(se, "getgenotypesaction", nav.getPageManager());
            }                                    
            
            int suid = caller.getSuid();
            
            String tmpGsid = pdo.getValue("gsid");
            String tmpGid = pdo.getValue("gid");                          
            caller.setSuid(Integer.parseInt(pdo.getValue("g.suid")));
            
            Collection groupings = samplingUnitManager.getGroupings(caller.getSuid(), caller);
            int gid = -1;
            int gsid = -1;                
            Collection groups = new ArrayList();

            Iterator i = null;
            // If it is the first time the page is displayed...choose the first 
            // grouping in the collection            
            if(tmpGsid != null && !tmpGsid.equals("*")){
                gsid = Integer.parseInt(tmpGsid);
//                if(!groupings.contains(new GroupingDTO(gsid))) {
//                    i = groupings.iterator();
//                    if(i.hasNext()) {
//                        GroupingDTO grDto = (GroupingDTO)i.next();                
//                        gsid = grDto.getGsid();   
//
//                    }
//                }
            }
            
            
            if(gsid > 0)
                groups = samplingUnitManager.getGroups(gsid, caller, caller.getSuid()); 
            
            // If it is the first time the page is displayed...choose the first 
            // group in the collection            
            if(tmpGid == null){
                i = groups.iterator();
                if(i.hasNext()) {
                    GroupDTO grDto = (GroupDTO)i.next();                
                    gid = grDto.getGid();
                }
            }
            // else, use the requested group id
            else if(!tmpGid.equals("*")){                
                gid = Integer.parseInt(tmpGid);
//                if(!groups.contains(new GroupDTO(gid))) {
                    i = groups.iterator();
                    if(i.hasNext()) {
                        GroupDTO grDto = (GroupDTO)i.next();                
                        gid = grDto.getGid();
                    }
                    else
                        gid = -1;
//                }                
            }                                       

            nav.getPageManager().setMax(genotypeManager.getNumberOfGenotypes(pdo, caller));
            Collection genotypes = genotypeManager.getGenotypes(caller, nav.getPageManager(), pdo);
            Collection samplingunits = samplingUnitManager.getSamplingUnits(caller.getPid(), caller); 
            Collection levels = new ArrayList();
            
            levels.add(new LevelDTO("*"));
            for(int j=0;j<10;j++)
                levels.add(new LevelDTO(j));                        
            
            Collection chr = genotypeManager.getChromosomes(caller.getSid(), caller);
            
            //Wild card chromo insert
            chr.add(new ChromosomeDTO());
            
            req.setAttribute("genotypesdto", genotypes);
            req.setAttribute("samplingunits", samplingunits);
            req.setAttribute("chromosomes", chr);
            req.setAttribute("paramdataobject", pdo);
            req.setAttribute("levels", levels);
            req.setAttribute("groupdto", groups);
            req.setAttribute("groupingsdto", groupings);            
            
            Workflow wf = (Workflow)req.getAttribute("workflow");
            
            String querytype = req.getParameter("querytype");
            if(querytype == null)
                querytype = wf.getAttribute("genotype.querytype");  
            if(querytype == null)
                querytype = "simple";            
            
            req.setAttribute("querytype", querytype);
            wf.setAttribute("genotype.querytype", querytype);
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActionException("Could not retrieve genotypes.");
        }
    }   
}
