package com.arexis.mugen.webapp.action.project;

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

public class BeginAction extends MugenAction {
    
    private static Logger logger = Logger.getLogger(BeginAction.class);
    
    public String getName() {
        return "BeginAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            logger.debug("BeginAction Started");
            
            HttpSession session = request.getSession();
            Navigator nav = (Navigator)session.getAttribute("navigator");
            MugenCaller caller = (MugenCaller)session.getAttribute("caller");            
            
            /** Set the project */
            ProjectDTO prj = projectManager.getDefaultProject(caller);
            session.setAttribute("project.projectdto", prj);
            
            session.setAttribute("caller", caller);
            
            session.setAttribute("RQ", "Welcome");
            
            caller.setPid(prj.getPid());
            //caller.updatePrivileges();

            /** Set sampling unit */
            SamplingUnitDTO su = samplingUnitManager.getDefaultSamplingUnit(caller);
            if (su==null)
            {
                throw new SamplingUnitNotFoundException("Sampling unit is null. Default could not be found. Pid="+caller.getPid());
                //System.out.println("SamplingUnit is null");
                //System.out.println("Pid="+caller.getPid());
            }
            caller.setSuidName(su.getName());
            caller.setSid(su.getSid());
            caller.setSuid(su.getSuid());
           
            logger.debug("sid="+caller.getSid()+", suid="+caller.getSuid()+", pid="+caller.getPid());
            
            /** Set the number of rows to display */
            int rows = 60;
            //nav.getPageManager().setDelta(new Integer(rows).intValue());
            nav.getPageManager().setDelta(rows);
            
            
            logger.debug("BeginAction ended");
            
            
            return true;
        } catch (ApplicationException e) {
            logger.error("Begin action failed", e);
        } catch (Exception e) {
            logger.error("Begin action failed", e);
        }
        return true;
    }
}
