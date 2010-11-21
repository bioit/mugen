package com.arexis.arxframe;

import com.arexis.util.Timer;
import java.io.*;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Controller extends HttpServlet {
    
    private static Logger logger = Logger.getLogger(Controller.class);
    
    private static final String CONTROLLER_TAG = "controller";
    private static final String WORKFLOWMANAGERS_TAG = "workflow-managers";
    private static final String WORKFLOWMANAGER_TAG = "workflow-manager";

    private static final String CLASS_ATTR = "class";
    private static final String ACTIONPREFIX_ATTR = "actionPrefix";
    
    private static final String WORKFLOW_MANAGER = "WorkflowManager";
    
    private String workflowManagerClassName;
    private String loginClassName;
    
    private String requery = "";
    private String _requery = "Controller";
    
    private boolean newLogin = false;
    

    public void init() throws ServletException{           
        try {
            parseXML();
            navAction = new NavigatorAction();
            requestLogAction = new RequestLogAction();
        } catch (Exception e) {
            throw new ServletException("Failed to read controller.xml");
        }
    }
    
    private void debugSession(HttpSession session){
        Enumeration names = session.getAttributeNames();
        String out = "";
        while (names.hasMoreElements())
        {
            out += names.nextElement()+"\n";
        }
        logger.debug(out);
    }
    
    private NavigatorAction navAction;
    private RequestLogAction requestLogAction;
    
    private Caller checkLogin(HttpServletRequest request, HttpServletResponse response) throws ArxFrameException{
        try
        {
            HttpSession session = request.getSession();
            Caller caller = (Caller)session.getAttribute("caller");
            if (caller == null){
                
                logger.debug("pre-login: caller==null. proceed to doLogin");
                Class c = Class.forName(loginClassName);
                ILogin login = (ILogin)c.newInstance();
                caller = login.doLogin(request, response);
                session.setAttribute("caller", caller);
                return caller;
                
            }else{
                logger.debug("pre-login: caller!=null. proceed to doLogin");
                Class c = Class.forName(loginClassName);
                ILogin login = (ILogin)c.newInstance();
                Caller newcaller = login.doLogin(request, response);
                
                if(newcaller.getUsr().compareTo(caller.getUsr())!=0){
                    logger.debug("pre-login: caller!=null. user: "+caller.getUsr()+" logs out. user: "+newcaller.getUsr()+" logs in.");
                    caller = null;
                    session.invalidate();
                    Navigator nav = new Navigator();
                    request.getSession().setAttribute("caller", newcaller);
                    request.getSession().setAttribute("navigator", nav);
                    System.gc();
                    return newcaller;
                }else{
                    newcaller = null;
                    System.gc();
                    return caller;
                }   
            }
        } catch (ArxLoginForward alf) {
            throw alf;
        } catch (Exception e) {
            logger.error("Could not login", e);
            throw new ArxFrameException("Could not login", e);
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //very very important cache issue
            // Set to expire far in the past.
            response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
            // Set standard HTTP/1.1 no-cache headers.
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            // Set standard HTTP/1.0 no-cache header.
            response.setHeader("Pragma", "no-cache");
            
            response.setHeader("Content-Type", "text/html; charset=utf-8");
            request.setCharacterEncoding("UTF-8");
            
            Timer t = new Timer();
            logger.debug(">>>>>>> START REQUEST @ "+t+" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            HttpSession session = request.getSession();
            ServletContext context = getServletContext();
            
            // Log QS [just print something]
            requestLogAction.performAction(request, context);
            
            _requery = (String) session.getAttribute("RQ");
            
            //store the URL as a session parameter for refresh reasons
            if(request.getRequestURI()==null || request.getRequestURI().equals("")){
                requery = _requery;
            } else{
                requery = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")+1);
                
                if(requery.equals("")) {
                    requery = _requery;
                } else {
                    
                    if(request.getQueryString()!=null && !request.getQueryString().equals("")){
                        requery += "?"+request.getQueryString();
                    }
                    
                    _requery = requery;
                }
            }
            logger.debug(">>>>>>> _RQ IS >>>>>>>>>"+_requery+">>>>>>>>>>>>>>>>>>>>>>");
            logger.debug(">>>>>>> RQ IS >>>>>>>>>"+requery+">>>>>>>>>>>>>>>>>>>>>>");
            session.setAttribute("RQ", requery);
            requery = "";

            //debugSession(session);

            Caller caller = checkLogin(request, response);
            
            /** Handle Navigation actions */
            navAction.performAction(request, response, context);

            /**
             * The user have a workflow manager to handle workflows in the appliction.
             */
            IWorkFlowManager workflowManager = getWorkflowManager(request,context);
            workflowManager.setCaller(caller);
           

            /**
             * Get the next page from workflow manager
             */
            String nextPage = workflowManager.getNextPage(request, context);
            
            /**
             * Forward to view
             */
            //RequestDispatcher forwarder = request.getRequestDispatcher("/"+nextPage);
            //forwarder.forward(request, response);
            RequestDispatcher forwarder = context.getRequestDispatcher("/"+nextPage);
            
            if(request.getParameter("workflow")==null || !request.getParameter("workflow").equals("ViewFile")) {
                forwarder.include(request, response);
                PrintWriter out = response.getWriter();
                out.println("<html><head>");
                out.println("<script defer language=\"JavaScript\" src=\"javascripts/framebreak.js\"></script>");
                out.println("</head></html>");
                out.close();
            } else {
                forwarder.forward(request, response);
            }

            // Stop timer and display time, End request.
            t.stop();
            logger.debug(">>>>>>> END REQUEST @ "+t+" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            
            //garbage collector
            System.gc();
        } catch (ArxLoginForward alf) {
            //response.setHeader("Cache-Control", "no-cache");
            //response.setHeader("Pragma", "no-cache");
            // Set to expire far in the past.
            response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
            // Set standard HTTP/1.1 no-cache headers.
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            // Set standard HTTP/1.0 no-cache header.
            response.setHeader("Pragma", "no-cache");
            
            RequestDispatcher forwarder = request.getRequestDispatcher(alf.getUrl());
            forwarder.forward(request, response);
        } catch (ArxFrameException afe) {
            logger.error("ArxFrameException.", afe);
            //response.setHeader("Cache-Control", "no-cache");
            //response.setHeader("Pragma", "no-cache");
            // Set to expire far in the past.
            response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
            // Set standard HTTP/1.1 no-cache headers.
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            // Set standard HTTP/1.0 no-cache header.
            response.setHeader("Pragma", "no-cache");
            
            RequestDispatcher forwarder = request.getRequestDispatcher("error/GeneralError.jsp");
            forwarder.forward(request, response);
        }
    }
    
    /**
     * Get the workflow object. The workflow object are stored in session
     * for a user and is available during the session. Each user has a 
     * workflow manager.
     *
     * @param request is the HttpServletRequest object
     * @param context is the ServletContext object
     * @throws javax.servlet.ServletException 
     * @throws java.io.IOException 
     * @return a workflow manager for the user.
     */
    private IWorkFlowManager getWorkflowManager(HttpServletRequest request, ServletContext context) throws ArxFrameException {
        IWorkFlowManager mgr = null;
        try
        {
            mgr = (IWorkFlowManager)request.getSession().getAttribute(WORKFLOW_MANAGER);
            if (mgr == null)
            {
                Class c = Class.forName(workflowManagerClassName);
                mgr = (IWorkFlowManager)c.newInstance();
                mgr.setContext(context);
                request.getSession().setAttribute(WORKFLOW_MANAGER, mgr);
            }
        }
        catch (Exception e)
        {
            logger.error("Failed to get workflow manager", e);
            throw new ArxFrameException("Failed to get workflowManager.", e);
        }
        return mgr;
    }
    
    private void parseXML() throws Exception {
        Timer t = new Timer();
        try
        {
            ServletContext context = this.getServletContext();
            InputStream is = context.getResourceAsStream("/controller.xml");
            
            //wrkFlwMgrs = new ArrayList();
        
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            
            // Get login class name
            NodeList nl = doc.getElementsByTagName("login");
            Element e = (Element)nl.item(0);
            loginClassName = e.getAttribute(CLASS_ATTR);

            NodeList nl2 = doc.getElementsByTagName(WORKFLOWMANAGER_TAG);
            Element wflmgr = (Element)nl2.item(0);
            workflowManagerClassName = wflmgr.getAttribute(CLASS_ATTR);
        }
        catch (Exception e)
        {
            logger.error("Failed to read xml data for Controller", e);
            throw new Exception("Could read configuration", e);
        }
        t.stop();
        //System.out.println("Controller#parseXML "+t);
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException 
     * @throws java.io.IOException 
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException 
     * @throws java.io.IOException 
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Returns a short description of the servlet.
     * @return 
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
