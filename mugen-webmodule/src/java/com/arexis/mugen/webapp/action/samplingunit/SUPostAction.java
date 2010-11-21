/*
 * SUPostAction.java
 *
 * Created on November 18, 2005, 2:38 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.samplingunit;

import com.arexis.arxframe.Navigator;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.project.ParamCollector;
import com.arexis.mugen.project.ParamDataObject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.arexis.mugen.webapp.action.*;


/**
 *
 * @author heto
 */
public class SUPostAction extends MugenAction {
    
    /** Creates a new instance of SUPostAction */
    public SUPostAction() {
    }
    
    public String getName() {
        return "SUPostAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            String tmpSid = request.getParameter("sid");
            
            MugenCaller caller = (MugenCaller)request.getSession().getAttribute("caller");
            Navigator nav = (Navigator)request.getSession().getAttribute("navigator");
            
            int sid = 0;
            if(tmpSid != null) {
                sid = Integer.parseInt(tmpSid);
                caller.setSid(sid);
            }
            
            ParamCollector pc = new ParamCollector(true);
            pc.putDefault("status", "E");
            pc.putDefault("sid", ""+sid);
            
            ParamDataObject pdo = pc.collectParams(request, "getsufullaction", nav.getPageManager());
            request.getSession().setAttribute("samplingunits.pdo", pdo);
            
            System.out.println("Post\n"+pdo);
            
            return true;
       } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
