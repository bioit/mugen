package com.arexis.mugen.webapp.action.questionnaire;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.exceptions.SamplingUnitNotFoundException;
import com.arexis.mugen.project.projectmanager.ProjectDTO;
import com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitDTO;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class AskAction extends MugenAction {
    
    private static Logger logger = Logger.getLogger(AskAction.class);
    
    public String getName() {
        return "AskAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            logger.debug("AskAction Started");
            
            HttpSession session = request.getSession();
            Navigator nav = (Navigator)session.getAttribute("navigator");
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");            
            
            ProjectDTO prj = projectManager.getDefaultProject(caller);
            session.setAttribute("project.projectdto", prj);
            
            session.setAttribute("caller", caller);
            
            caller.setPid(prj.getPid());
            //caller.updatePrivileges();

            SamplingUnitDTO su = samplingUnitManager.getDefaultSamplingUnit(caller);
            
            if (su==null) {
                throw new SamplingUnitNotFoundException("Sampling unit is null. Default could not be found. Pid="+caller.getPid());
            }
            
            caller.setSuidName(su.getName());
            caller.setSid(su.getSid());
            caller.setSuid(su.getSuid());
           
            logger.debug("sid="+caller.getSid()+", suid="+caller.getSuid()+", pid="+caller.getPid());
            
            /** Set the number of rows to display */
            int rows = 20;
            //nav.getPageManager().setDelta(new Integer(rows).intValue());
            nav.getPageManager().setDelta(rows);
            
            //logger.debug("AskAction ended");
            
            request.setAttribute("yesnos", modelManager.getYesNos());
            
            return true;
        } catch (ApplicationException e) {
            logger.error("AskAction failed", e);
        } catch (Exception e) {
            logger.error("AskAction failed", e);
        }
        return true;
    }
}
