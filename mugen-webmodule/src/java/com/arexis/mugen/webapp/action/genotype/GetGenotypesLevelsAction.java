/*
 * GetGenotypesLevelsAction.java
 *
 * Created on July 27, 2005, 1:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.genotype.genotypemanager.LevelDTO;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.ParamCollector;
import com.arexis.mugen.project.ParamDataObject;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import com.arexis.mugen.webapp.action.*;


/**
 * MugenAction class for handling the retrieval of genotype levels
 * @author lami
 */
public class GetGenotypesLevelsAction extends MugenAction{
    
    /** Creates a new instance of GetGenotypesLevelsAction */
    public GetGenotypesLevelsAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GetGenotypesLevelsAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ApplicationException {
        try {
            MugenCaller caller = (MugenCaller)req.getSession().getAttribute("caller");            
            Navigator nav = (Navigator)req.getSession().getAttribute("navigator");
            
            HttpSession se = req.getSession();
            
            int suid = caller.getSuid();
            
            ParamCollector pc = new ParamCollector(true);
            pc.putDefault("g.suid", ""+suid);   
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
            pc.putDefault("usr", "");
            
            ParamDataObject pdo = pc.collectParams(req, "getgenotypesaction", nav.getPageManager());            
            suid = Integer.parseInt(pdo.getValue("g.suid"));
            caller.setSuid(suid);   

            Collection levels = new ArrayList();
            
            for(int i=0;i<10;i++)
                levels.add(new LevelDTO(i));            
            
            req.setAttribute("paramdataobject", pdo);                        
            req.setAttribute("levels", levels);
            
            if(req.getParameter("collect.getgenotypesaction.display") != null) {
                genotypeManager.updateGenotypeLevels(caller, pdo, req.getParameter("ignore.level"));
            }            
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve genotype levels.");
        }
    }   
}
