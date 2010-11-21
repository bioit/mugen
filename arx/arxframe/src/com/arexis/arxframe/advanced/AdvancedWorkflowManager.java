package com.arexis.arxframe.advanced;

import com.arexis.arxframe.Action;
import com.arexis.arxframe.ActionException;
import com.arexis.arxframe.ArxFrameException;
import com.arexis.arxframe.Caller;
import com.arexis.arxframe.IWorkFlowManager;
import com.arexis.arxframe.WorkflowException;
import com.arexis.arxframe.io.WebFileUpload;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AdvancedWorkflowManager implements IWorkFlowManager {
    private static Logger logger = Logger.getLogger(AdvancedWorkflowManager.class);
    
    //String page = null;
    
    /* XML Definition strings */
    private static final String WORKFLOWS_TAG = "workflows";
    private static final String WORKFLOW_TAG = "workflow";
    private static final String STATE_TAG = "state";
    private static final String NAME_ATTR = "name";
    private static final String ACTION_ATTR = "action";
    private static final String PRE_ACTION_ATTR = "preaction";
    private static final String VIEW_ATTR = "viewURI";
    private static final String NEW_WORKFLOW_ATTR = "workflow";
    
    /** Should be set in workflow.xml, this is default value */
    private String ACTION_PREFIX = "action.";    
    private String DEFAULT_WORKFLOW;
    
    /** All workflows defined in xml */
    private HashMap workflows;
    
    /** Current workflows and previous workflows */
    private Workflow currentWorkflow;
    
    /** History manager */
    private WorkflowHistory history;
    private static final int MAX_HISTORY = 5;
    
    private Caller caller;
    
    //private AltState altState;
    
    /**
     * Workflow stack
     *
     * On position 0 is the current workflow. New nestled workflows will be 
     * inserted on position 0. Then a workflow is finished. It will be 
     * removed from position 0 and the outer workflow is now the prime 
     * workflow.
     */
    private ArrayList workflowStack;
   
    
    /** Creates a new instance of AdvancedWorkflowManager */
    public AdvancedWorkflowManager() {
        //System.out.println("AdvancedWorkflowManager#AdvancedWorkflowManager: New WorkflowManager");
    }   
    
    /**
     * Called by the controller
     * @param context is the servlet context
     * @throws java.io.IOException if xml file is not readable.
     */
    public void setContext(ServletContext context) throws IOException {
        InputStream is = context.getResourceAsStream("/workflow.xml");
        try
        {
            if (context == null)
                throw new Exception("Context is null!");
            
            // Init the HashMap
            workflows = new HashMap();
            
            history = new WorkflowHistory(MAX_HISTORY);
            workflowStack = new ArrayList();
            
            // Parse XML and populate HashMap.
            parseXML(is);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new IOException("WorkflowManager#setContext: "+e.getMessage());
        }
    }
    
    public void setCaller(Caller caller) {
        if (caller==null) 
            logger.warn("AdvancedWorkflowManager#setCaller is null!!!");
        this.caller = caller;
    }
    
    /**
     * Get the action class for an action name
     * @param name the class name for the action class
     * @throws java.lang.Exception if anything goes wrong.
     * @return the action class.
     */
    private Action getAction(String name) throws ArxFrameException {
        try
        {
            if (name != null && name.length() > 0)
            {
                Class c = Class.forName(ACTION_PREFIX + name);
                return (Action)c.newInstance();
            }
            return null;
        }
        catch (Exception e)
        {
            throw new ArxFrameException("Failed to get action "+ACTION_PREFIX+name, e);
        }
    }
    
    /**
     * Not all workflows should be added to history.
     * That way back & forward actions are limited.
     *  To enrich the workflows that must be recorded in the history obj simply add it to the string array below.
     */
    private boolean historyWorkflow(String workflow) {
        boolean worthHistory = false;
        String [] validWorkflows = {"ViewModels", "ViewModel", "ViewStrains", "ViewStrain", "ViewUser", "ViewGenes", "ViewGene", "ViewGeneticBackgroundValues", "ViewAvailableGeneticBackgrounds", "ViewRepositories", "DisseminationUpdate", "SearchKeywordFast", "ViewSimpleLogs", "ViewPhenotypeOntology", "ViewStrainAllele"};
        for(int i=0; i < validWorkflows.length; i++){
            if(workflow.compareTo(validWorkflows[i])==0){
                worthHistory = true;
                return worthHistory;
            }
        }
        return worthHistory;
    }
    
    private boolean repeatedWorkflow(String workflow, int depth) {
        boolean repeatWorkflow = false;
        if(history.elements()<depth){
            return repeatWorkflow;
        } else {
            Workflow tmp = history.get(depth);
            if(workflow.compareTo(tmp.getName())==0){
                repeatWorkflow = true;
                return repeatWorkflow;
            }
        }
        return repeatWorkflow;
    }
    
    /**
     * Some workflows contain sorting buttons,
     * That lead to them again, thus requiring special treatment.
     */
    private boolean circleWorkflow(String workflow) {
        boolean circleHistory = false;
        String [] validWorkflows = {"ViewModels", "PhenoAssign", "ViewStrains", "DisseminationUpdate", "ViewSimpleLogs"};
        for(int i=0; i < validWorkflows.length; i++){
            if(workflow.compareTo(validWorkflows[i])==0){
                circleHistory = true;
                return circleHistory;
            }
        }
        return circleHistory;
    }
    
    /** 
     * Get the workflow object from the request parameter "workflow". 
     * If workflow not found, return null.
     *
     */
    private Workflow getWorkflow(HttpServletRequest req) throws WorkflowException {
        
        String newWorkflow;
        
        if (req.getParameter("workflow") != null || workflowStack.size()==0) {
            
            if (req.getParameter("workflow")==null && workflowStack.size()==0) {
                logger.debug("Setting default workflow "+DEFAULT_WORKFLOW);
                newWorkflow = DEFAULT_WORKFLOW;
            } else {
                newWorkflow = req.getParameter("workflow");
                //logger.debug("Setting default workflow "+DEFAULT_WORKFLOW);
            }
            
            logger.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<  NEW WRKFLW = "+newWorkflow+" <<<<<<<");
            
            
            workflowStack = new ArrayList();
            Workflow tmp = (Workflow)workflows.get(newWorkflow);
            if (tmp==null) {
                throw new WorkflowException("Workflow \""+newWorkflow+"\" not found");
            }
            
            // Do not add the same workflow twice in history. 
            // This helps the back buttons then using hide-windows.
            if (currentWorkflow!=null && !currentWorkflow.getName().equals(newWorkflow) && historyWorkflow(currentWorkflow.getName()) && !repeatedWorkflow(currentWorkflow.getName(), 1)) {
                history.add(currentWorkflow);
                logger.debug("**************************** ADDED = "+currentWorkflow.getName()+" TO HISTORY [NO FWD]*********************");
            }
            
            // Set the new workflow. 
            currentWorkflow = new Workflow(tmp);
            workflowStack.add(0, currentWorkflow);
            
            if (currentWorkflow==null)
                throw new WorkflowException("Workflow "+newWorkflow+" not found");            
            
            currentWorkflow.setStart();
            
        }
        
        for(int i=1; i<history.elements()+1;i++){
            logger.debug("AdvancedWorkflowManager#getWorkflow#Element "+i+" is "+history.get(i).getName());
        }
        
        return (Workflow)workflowStack.get(0);
    }
   
    public String getNextPage(HttpServletRequest req, ServletContext context) throws ArxFrameException {
        String page = null;
        try
        {
            currentWorkflow = getWorkflow(req);
            
            logger.debug("AdvancedWorkflowManager#getNextPage# getWorkflow says currentWorkflowName is="+currentWorkflow.getName());
            
            // <editor-fold defaultstate="collapsed" desc="check for back operation">
            
            if (req.getParameter("back")!=null && req.getParameter("back").equals("state")) {
                currentWorkflow.setPrevState();
            } else if (req.getParameter("back")!=null || req.getParameter("back.x")!=null) {
                Workflow tmp = history.get(1);
                if(tmp.getName().compareTo("begin")==0){
                    currentWorkflow = tmp;
                    currentWorkflow.setStart();
                    currentWorkflow.setCaller(caller);
                    req.setAttribute("workflow", currentWorkflow);
                    page = "Welcome";
                    return page;
                } else if(!repeatedWorkflow(currentWorkflow.getName(), 1)){
                    logger.debug("<<<00>>>AdvancedWorkflowManager#getNextPage# [back!=null && !repeatedWorkflow] currentWorkflowName is="+currentWorkflow.getName());
                    if(circleWorkflow(currentWorkflow.getName())){
                        logger.debug("<<<00>>>AdvancedWorkflowManager#getNextPage# [back!=null && !repeatedWorkflow] circular currentWorkflowName is="+currentWorkflow.getName());
                        history.remove(0);
                    }
                    currentWorkflow = tmp;
                    logger.debug("<<<00>>>AdvancedWorkflowManager#getNextPage# [back!=null && !repeatedWorkflow] new currentWorkflowName is="+currentWorkflow.getName());
                    currentWorkflow.setStart();
                } else {
                    logger.debug("<<<01>>>AdvancedWorkflowManager#getNextPage# [back!=null && repeatedWorkflow] currentWorkflowName is="+currentWorkflow.getName());
                    history.remove(0);
                    currentWorkflow = history.get(1);
                    logger.debug("<<<01>>>AdvancedWorkflowManager#getNextPage# [back!=null && repeatedWorkflow] new currentWorkflowName is="+currentWorkflow.getName());
                    currentWorkflow.setStart();
                    
                    if(currentWorkflow.getName().compareTo("begin")==0){
                        currentWorkflow.setCaller(caller);
                        req.setAttribute("workflow", currentWorkflow);
                        page = "Welcome";
                        return page;
                    }
                    
                    if(currentWorkflow.getName().compareTo("ViewUser")==0){
                        currentWorkflow.setCaller(caller);
                        req.setAttribute("workflow", currentWorkflow);
                        page = "ViewUser";
                        return page;
                    }
                }
            }
            // </editor-fold>
            
            if(req.getParameter("workflow")==null){
                workflowStack.remove(0);
                workflowStack.add(0,currentWorkflow);
            }
            
            currentWorkflow.setCaller(caller);
            
            /* Save the workflow in the request object */
            req.setAttribute("workflow", currentWorkflow);
            
            page = currentWorkflow.getNextPage(req, context);
            
            logger.debug("AdvancedWorkflowManager#getNextPage#Page="+page);
        }
        
        // <editor-fold defaultstate="collapsed" desc="getNextPage exceptions">
        catch (ForwardWorkflowException fwe)
        {
            logger.debug("<<<>>>AdvancedWorkflowManager#getNextPage#ForwardWorkflowException: Forwarding to workflow "+ currentWorkflow.getCurrentState().getNewWorkflow());
            
            if (currentWorkflow!=null && historyWorkflow(currentWorkflow.getName()) && !repeatedWorkflow(currentWorkflow.getName(), 1)) {
                history.add(currentWorkflow);
                logger.debug("**************************** ADDED = "+currentWorkflow.getName()+" TO HISTORY [FWD]*********************");
            }

            currentWorkflow = new Workflow((Workflow)workflows.get(
                    currentWorkflow.getCurrentState().getNewWorkflow()));
            
            logger.debug("<<<>>>AdvancedWorkflowManager#getNextPage#ForwardWorkflowException: Now current workflow is "+ currentWorkflow.getName());

            workflowStack.remove(0);
            workflowStack.add(0,currentWorkflow);
            
            //(Workflow)workflowStack.get(0)
            logger.debug("<<<>>>AdvancedWorkflowManager#getNextPage#ForwardWorkflowException: Workflow in workflow stack is "+ ((Workflow)workflowStack.get(0)).getName());
            
            /* Save the workflow in the request object */
            req.setAttribute("workflow", currentWorkflow);
            currentWorkflow.setCaller(caller);
            
            page = currentWorkflow.getNextPage(req, context);
        }
        catch (BackWorkflowException back)
        {
            logger.debug("<<<>>>AdvancedWorkflowManager#getNextPage#BackWorkflowException: Workflow before pressing back is "+ currentWorkflow.getName());
            //Workflow tmp = history.get(1);
            if(repeatedWorkflow(currentWorkflow.getName(), 1)){
                history.remove(0);
            }
            currentWorkflow = history.get(1);//tmp;
            logger.debug("<<<>>>AdvancedWorkflowManager#getNextPage#BackWorkflowException: Workflow after pressing back is "+ currentWorkflow.getName());
            
            workflowStack.remove(0);
            workflowStack.add(0,currentWorkflow);
            logger.debug("<<<>>>AdvancedWorkflowManager#getNextPage#BackWorkflowException: Workflow in workflow stack is "+ ((Workflow)workflowStack.get(0)).getName());
            
            currentWorkflow.setStart();

            currentWorkflow.setCaller(caller);
            
            /* Save the workflow in the request object */
            req.setAttribute("workflow", currentWorkflow);
            
            page = currentWorkflow.getNextPage(req, context);
        } catch (AlterWorkflowException alt) {
            AltState a = currentWorkflow.getCurrentState().getAltState(alt.getName());
            String wfName = a.getNewWorkflow();
            Workflow tmp = new Workflow((Workflow)workflows.get(wfName));
            workflowStack.add(0, tmp);
            currentWorkflow = tmp;
            
            /* Save the workflow in the request object */
            req.setAttribute("workflow", currentWorkflow);
            
            page = currentWorkflow.getNextPage(req, context);
        } catch (ActionException ae) {
            req.setAttribute("exception", ae);
            logger.error("Action exception. Show GeneralError.jsp view", ae);
            page="error/GeneralError.jsp";
            throw ae;
        }
        catch (Exception e)
        {
            logger.error("General error in workflow logic.",e);
            throw new ArxFrameException("Workflow failure",e);
        }
        //</editor-fold>
        
        for(int i=1; i<history.elements()+1;i++){
            logger.debug("AdvancedWorkflowManager#getNextPage#Element "+i+" is "+history.get(i).getName());
        }
        //logger.debug("AdvancedWorkflowManager#getNextPage#FirstInHistory="+history.get(1).getName());
        
        return page;
    }
    
    public boolean isActionInvoked(HttpServletRequest req) {
        boolean reply = false;
        int uplwrkflw = 0;
        
        String [] params = {"back", "reset", "Login", "login", "save", "create", "add", 
                            "ass", "upload", "raid", "gaid", "groupname", "participantname",
                            "mpterm", "mutationtypes", "disslevel", "byID", "byNAME", "byDATE",
                            "byMGI", "byEMMA", "mgnaction", "user"};
        
        String [] wrkflw = {"ImportIndividual", "ImportMarker", "GenModFileUpload", 
                            "CreateModelFileReference", "AddGenotypingFile", "AddHandlingFile",
                            "AddFuncSigFile", "FileUploadTest", "CreateProjectFileResource",
                            "AddFileResourceSU", "CreateModelFileResource", "CreateProjectFileResource", 
                            "AddFileResourceSU", "CreateModelFileResource", "CreateProjectFileResource",
                            "AddFileResourceSU", "PhenoTermParser", "PhenoParser"};
        
        for(int j=0; j < wrkflw.length; j++){
            logger.debug("reached weird workflow -> "+wrkflw[j]);
            if(currentWorkflow.getName().equals(wrkflw[j])){
                uplwrkflw = 1;
                logger.debug("current workflow ("+currentWorkflow.getName()+") is webupload workflow "+uplwrkflw);
                break;
            } else {
                uplwrkflw = 0;
            }
            
            logger.debug("current workflow ("+currentWorkflow.getName()+") is webupload workflow? "+uplwrkflw);
        }
        
        if(uplwrkflw != 1) {
            for(int i=0; i < params.length; i++){
                logger.debug("non webupload parameters \""+params[i]+"\"");
                if(req.getParameter("\""+params[i]+"\"")!=null){
                    reply = true;
                    return reply;
                }
            }
        } else {
            
            logger.debug("ready to process webupload workflow "+currentWorkflow.getName());
            WebFileUpload webFile = new WebFileUpload(req, 100000000);
            
            if(webFile.getFormParameter("upload")!=null){
                logger.debug("webupload workflow "+currentWorkflow.getName()+" has upload parameter");
                reply = true;
                return reply;
            }
        }
        
        return reply;
    }
    
    
    public void removeMaliciousWorkflows(String maliciousWorkflow){
        history.massRemove(maliciousWorkflow);
    }
    
    private void debugWorkflowStack() {
        if (workflowStack==null)
            return;
        
        String out = "";
        
        out += "<workflow-stack>\n";
        for (int i=0;i<workflowStack.size();i++)
        {
            out += "\t<workflow id=\""+i+"\" name=\""+((Workflow)workflowStack.get(i)).getName()+"\"/>\n";
        }
        out += "</workflow-stack>\n";
        logger.debug(out);
    }
   
    /**
     * Read workflow information and settings from xml file
     * @param is the input stream to read xml data from.
     * @throws java.lang.Exception if exceptions occure while reading xml.
     */
    private void parseXML(InputStream is) throws ArxFrameException {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);

            // Find the workflows element
            NodeList wrkflws = doc.getElementsByTagName(WORKFLOWS_TAG);
            Element e_wrkflws = (Element)wrkflws.item(0);
            
            if (e_wrkflws.getAttribute("action-prefix")!=null && 
                    !e_wrkflws.getAttribute("action-prefix").equals(""))
                ACTION_PREFIX = e_wrkflws.getAttribute("action-prefix");
            
            if (e_wrkflws.getAttribute("default-workflow")!=null &&
                    !e_wrkflws.getAttribute("default-workflow").equals(""))
                DEFAULT_WORKFLOW = e_wrkflws.getAttribute("default-workflow");
            

            // Find the workflow element
            NodeList workflows = doc.getElementsByTagName(WORKFLOW_TAG);
            //Element workflow = (Element)workflows.item(0);

            for (int j=0;j<workflows.getLength();j++)
            {
                Element workflow  = (Element)workflows.item(j);

                Workflow w = new Workflow((String)workflow.getAttribute(NAME_ATTR));
                //System.out.println("WorkflowName="+w.getName());
                
                // Find all states
                NodeList states = workflow.getChildNodes();
                //NodeList states = doc.getElementsByTagNameNS(workflow.getPrefix(), STATE_TAG);
                
                //System.out.println("NumOfStates="+states.getLength());
                
                // Create an array for states
                ArrayList arr = new ArrayList();
                
                // Read state info
                for (int i=0;i<states.getLength();i++)
                {
                    // Get element
                    Node node = states.item(i);
                    
                    //System.out.println("NodeName="+node.getNodeName());
                    
                    Element curState = null;
                    if (node.getNodeType()==node.ELEMENT_NODE)
                    {
                        curState = (Element)node;
                        State state = new State();

                        state.setName(curState.getAttribute(NAME_ATTR));
                        state.setView(curState.getAttribute(VIEW_ATTR));
                        state.setNewWorkflow(curState.getAttribute(NEW_WORKFLOW_ATTR));

                        // Convert Action names into class instances
                        state.setPostAction(getAction(curState.getAttribute(ACTION_ATTR)));
                        state.setPreAction(getAction(curState.getAttribute(PRE_ACTION_ATTR)));
                        
                        AltState[] alts = getAltStates(curState);
                        state.altStates = alts;
                       
                        arr.add(state);
                    }
                }
                
                State[] stateList = new State[arr.size()];
                for (int k=0;k<arr.size();k++)
                {
                    stateList[k] = (State)arr.get(k);
                }
                w.states = stateList;

                // Add the workflow object to the hashmap.
                this.workflows.put(w.getName(), w);
            }
        }
        catch (Exception e)
        {
            logger.error("Error parsing workflow.xml", e);
            throw new ArxFrameException("Application error, problem reading workflow data", e);
        }
    }
    
    
    /**
     * Get all alternative states (AltState) for a state in the xml file
     * @param curState is an element in the xml file (state) to get alternative states from.
     * @throws java.lang.Exception if something goes wrong.
     * @return 
     */
    private AltState[] getAltStates(Element curState) throws Exception {
        NodeList alts = curState.getChildNodes();
        ArrayList arr = new ArrayList();
        for (int i=0;i<alts.getLength();i++)
        {
            // Get element
            Node node = alts.item(i);
            Element alt = null;
            if (node.getNodeType()==node.ELEMENT_NODE)
            {
                alt = (Element)node;
                
                AltState state = new AltState();

                state.setName(alt.getAttribute(NAME_ATTR));
                state.setView(alt.getAttribute(VIEW_ATTR));
                state.setNewWorkflow(alt.getAttribute(NEW_WORKFLOW_ATTR));

                // Convert Action names into class instances
                state.setPostAction(getAction(alt.getAttribute(ACTION_ATTR)));
                state.setPreAction(getAction(alt.getAttribute(PRE_ACTION_ATTR)));
                
                arr.add(state);
            }
        }
        
        AltState[] out = new AltState[arr.size()];
        for (int i=0;i<arr.size();i++)
        {
            out[i] = (AltState)arr.get(i);
        }
        return out;
    }
}
