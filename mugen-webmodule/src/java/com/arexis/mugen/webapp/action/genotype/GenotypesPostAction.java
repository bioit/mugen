/*
 * GenotypesPostAction.java
 *
 * Created on November 23, 2005, 8:19 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;

import com.arexis.arxframe.ActionException;
import com.arexis.arxframe.Navigator;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.ParamCollector;
import com.arexis.mugen.project.ParamDataObject;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lami
 */
public class GenotypesPostAction extends MugenAction {
    
    /** Creates a new instance of GenotypesPostAction */
    public GenotypesPostAction() {
        super();
    }
    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GenotypesPostAction";
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
                  
            String tmpSuid = req.getParameter("suid");              
            if(tmpSuid != null)
                caller.setSuid(Integer.parseInt(tmpSuid));                        
            
            ParamCollector pc = new ParamCollector(true);
            pc.putDefault("g.suid", ""+caller.getSuid());   
            pc.putDefault("g.level_", "*");
            pc.putDefault("chromosome", "*");
            pc.putDefault("g.reference", "");
            pc.putDefault("identity", "");
            pc.putDefault("allele1", "");
            pc.putDefault("allele2", "");
            pc.putDefault("marker", "");
            pc.putDefault("datefrom", "");
            pc.putDefault("dateto", "");
            pc.putDefault("usr", "");
            pc.putDefault("gid", "*");
            pc.putDefault("gsid", "*");  
          
            ParamDataObject pdo = pc.collectParams(req, "getgenotypesaction", nav.getPageManager());                 
            
            se.setAttribute("genotypes.pdo", pdo);            

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActionException("Could not retrieve genotypes.");
        }
    }  
}
