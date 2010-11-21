package com.arexis.arxframe;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NavigatorAction extends Action {
    
    /** Creates a new instance of NavigatorAction */
    public NavigatorAction() {
    }
          
    public String getName()
    {
        return "NavigatorAction";
    }
    
    public boolean performAction(HttpServletRequest req, ServletContext context) throws ActionException
    {
        return false;
    }
    
    /**
     * Performs the action
     * @param req The http request
     * @param context The servlet context
     * @throws com.arexis.mugen.exceptions.ApplicationException If the action could not be performed
     * @return True, if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, HttpServletResponse res, ServletContext context) {
        try {
            HttpSession session = req.getSession();
        
            /** The navigator handles list behavior. Display a number of 
             * entries in a table. Not all at once.
             */
            Navigator nav = (Navigator)session.getAttribute("navigator");
            if (nav == null) {
                nav = new Navigator();
            }
            nav.setNavigator(req);
            //nav.debug();

//            if (nav.goBack())
//            {
//                res.sendRedirect("Controller?workflow="+nav.getPrevWorkflow());
//                System.out.println("NavigatorAction#performAction(...) Go back="+nav.getPrevWorkflow());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
