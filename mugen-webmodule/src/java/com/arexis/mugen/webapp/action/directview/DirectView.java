package com.arexis.mugen.webapp.action.directview;

import com.arexis.arxframe.ArxFrameException;
import com.arexis.arxframe.ArxLoginForward;
import com.arexis.arxframe.advanced.AdvancedWorkflowManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.model.expmodel.ExpModelRemote;
import com.arexis.mugen.model.expmodel.ExpModelRemoteHome;
import com.arexis.mugen.model.functionalsignificance.FunctionalSignificanceRemote;
import com.arexis.mugen.model.functionalsignificance.FunctionalSignificanceRemoteHome;
import com.arexis.mugen.model.functionalsignificancetype.FunctionalSignificanceTypeRemote;
import com.arexis.mugen.model.functionalsignificancetype.FunctionalSignificanceTypeRemoteHome;
import com.arexis.mugen.species.gene.GeneRemote;
import com.arexis.mugen.species.gene.GeneRemoteHome;
import com.arexis.mugen.model.modelmanager.ModelManagerRemote;
import com.arexis.mugen.model.reference.ReferenceRemote;
import com.arexis.mugen.model.reference.ReferenceRemoteHome;
import com.arexis.mugen.model.researchapplication.ResearchApplicationRemote;
import com.arexis.mugen.model.researchapplication.ResearchApplicationRemoteHome;
import com.arexis.mugen.model.strain.state.StrainStateRemote;
import com.arexis.mugen.model.strain.state.StrainStateRemoteHome;
import com.arexis.mugen.model.strain.strain.StrainRemote;
import com.arexis.mugen.model.strain.strain.StrainRemoteHome;
import com.arexis.mugen.model.strain.type.StrainTypeRemote;
import com.arexis.mugen.model.strain.type.StrainTypeRemoteHome;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.project.ProjectRemoteHome;
import com.arexis.mugen.resource.file.FileRemote;
import com.arexis.mugen.resource.file.FileRemoteHome;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.servicelocator.ServiceLocator;
import com.arexis.mugen.species.species.SpeciesRemote;
import com.arexis.mugen.species.species.SpeciesRemoteHome;
import java.io.*;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.CreateException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import com.arexis.util.Timer;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import test.SqlHelper;

public class DirectView extends HttpServlet {
    
    
    ServiceLocator locator;
    
    SamplingUnitRemoteHome suHome;
    ExpModelRemoteHome modelHome;
    ResearchApplicationRemoteHome raHome;
    FileRemoteHome fileHome;
    ModelManagerRemote modelManager;
    GeneRemoteHome gaHome; 
    ReferenceRemoteHome referenceHome;
    FunctionalSignificanceTypeRemoteHome fstHome;
    SpeciesRemoteHome speciesHome;
    
    MugenCaller caller;
    
    private static Logger logger = Logger.getLogger(DirectView.class);
    
    public void lookup(){
        locator = ServiceLocator.getInstance();
        modelManager = (ModelManagerRemote)locator.getManager(ServiceLocator.Services.MODELMANAGER);
        modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
        suHome = (SamplingUnitRemoteHome)locator.getHome(ServiceLocator.Services.SAMPLINGUNIT);
        gaHome = (GeneRemoteHome)locator.getHome(ServiceLocator.Services.GENE);
        fileHome = (FileRemoteHome)locator.getHome(ServiceLocator.Services.FILE);
        referenceHome = (ReferenceRemoteHome)locator.getHome(ServiceLocator.Services.REFERENCE);
        fstHome = (FunctionalSignificanceTypeRemoteHome)locator.getHome(ServiceLocator.Services.FUNCTIONALSIGNIFICANCETYPE);
        speciesHome = (SpeciesRemoteHome)locator.getHome(ServiceLocator.Services.SPECIES);
    }
    
    public void init() throws ServletException{
        lookup();
        super.init();
    }
    
    public MugenCaller getCaller(){
        if (caller==null) {
            caller = new MugenCaller();
            caller.setId(1029);
            caller.setPid(99);
            caller.setName("Public");
            caller.setUsr("public");
            caller.setPwd("notknown");
            caller.setSuid(1003);
            caller.updatePrivileges();
            caller.setSid(99);
        }
        return caller;
    }
    
    public void logException(Exception e, PrintWriter out){
        out.println("<pre>");
        e.printStackTrace(out);
        out.println("</pre>");
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        AdvancedWorkflowManager wfm = new AdvancedWorkflowManager();
        try
        {
            /* Set headers */
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Content-Type", "text/html; charset=utf-8");
            request.setCharacterEncoding("UTF-8");
            
            //System.out.println("--- START REQUEST -------------------------------");
            Timer t = new Timer();
            logger.debug(">>>>>>> START DIRECT VIEW REQUEST @ "+t+" >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            
            wfm.setCaller(getCaller());
            
            HttpSession session = request.getSession();
            
            String workflowController = request.getParameter("workflow");
            
        if((workflowController.compareTo("ViewModelDirect")==0) || (workflowController.compareTo("ViewGeneDirect")==0) || (workflowController.compareTo("ViewStrainAlleleDirect")==0) || (workflowController.compareTo("ViewModelPrinter")==0)){

            session.setAttribute("caller", getCaller());
            
            ServletContext ctx = getServletContext();
            wfm.setContext(ctx);
            
            String nextPage = wfm.getNextPage(request, ctx);
            System.out.println("NextPage="+nextPage);
               
            /**
             * Forward to view
             */
            RequestDispatcher forwarder = request.getRequestDispatcher("/"+nextPage);
            forwarder.forward(request, response);

            // Stop timer and display time, End request.
            t.stop();
            //System.out.println("DirectView#ProcessRequest(...): "+t);
            //System.out.println("--- END REQUEST -------------------------------");
            logger.debug(">>>>>>> END DIRECT VIEW REQUEST @ "+t+" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }else{throw new ArxFrameException("Invalid Workflow!");}
            
            
        }
        catch (ArxLoginForward alf)
        {
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            RequestDispatcher forwarder = request.getRequestDispatcher(alf.getUrl());
            forwarder.forward(request, response);
        }
        catch (ArxFrameException afe)
        {
                
            request.setAttribute("exception", afe);
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            RequestDispatcher forwarder = request.getRequestDispatcher("error/NoAdmission.html");
            forwarder.forward(request, response);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    public String getServletInfo() {
        return "the DirectView servlet is used to display mutant models information directly. only certain workflows can be performed.";
    }
    
}
