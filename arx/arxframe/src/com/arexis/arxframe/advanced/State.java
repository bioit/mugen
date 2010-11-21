/*
 * State.java
 *
 * Created on November 14, 2005, 12:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.arxframe.advanced;

import com.arexis.arxframe.Action;
import com.arexis.arxframe.ActionException;
import com.arexis.arxframe.Caller;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 * Handle a state in an web application.
 * 
 * State:
 * PreAction -> Viev -> PostAction
 *
 * A state may point to a new workflow and therefor abandon the current 
 * workflow.
 *
 * A state may have attributes that could be stored during the state and may
 * be available in history.
 *
 * @author tobias
 */
public class State {
    private static Logger logger = Logger.getLogger(State.class);
    private String name;
    private Action preAction;
    private Action postAction;
    private String view;
    private String newWorkflow;
    private HashMap attributes;
    protected AltState[] altStates;
    
    private Caller caller;
    private Workflow workflow;
    
    
    /** 
     * Creates a new instance of State 
     */
    public State() {
        attributes = new HashMap();
    }

    /**
     * Get the name of the state
     * @return the name of the state
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the state
     * @param name a string of the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the pre action class for this state
     * This Action performs some task, usually to prepare some data, fetch data,
     * to display in the view.
     * @param preAction the action class
     */
    public void setPreAction(Action preAction) {
        this.preAction = preAction;
    }
    
    /**
     * Get the preAction class. 
     * This Action performs some task, usually to prepare some data, fetch data,
     * to display in the view.
     * @return the action class
     */
    public Action getPreAction() {
        return preAction;
    }
    
    /**
     * A convinience method to perfom the pre action. 
     * @param req is the servlet request object forwarded to the Action.
     * @param context is the servlet context object forwarded to the action.
     * @throws com.arexis.arxframe.ActionException if the action returns false 
     * or if action class throws exception.
     */
    public void performPreAction(HttpServletRequest req, ServletContext context) throws ActionException
    {
        logger.debug("PreAction in state "+this.getName());
        if (preAction!=null)
        {
            preAction.setCaller(caller);
            preAction.setWorkflow(workflow);
            boolean res = preAction.performAction(req, context);
            if (!res)
                throw new ActionException("Action \""+postAction.getName()+"\" failed");
        }
    }

    /**
     * Set the post action class for this state
     * This Action performs some task, usually to handle posted data fetched 
     * from the view.
     * @param postAction is the action class performing the task.
     */
    public void setPostAction(Action postAction) {
        this.postAction = postAction;
    }
    
    /**
     * Get the postAction class. 
     * This Action performs some task, usually to handle posted data fetched 
     * from the view.
     * @return the action class.
     */
    public Action getPostAction() {
        return postAction;
    }
    
    /**
     * A convinience method to perfom the pre action. 
     * @param req is the servlet request object forwarded to the Action.
     * @param context is the servlet context object forwarded to the action.
     * @throws com.arexis.arxframe.ActionException if the action returns false 
     * or if action class throws exception.
     */
    public void performPostAction(HttpServletRequest req, ServletContext context) throws ActionException
    {
        logger.debug("PostAction in state "+this.getName());
        if (postAction!=null)
        {
            postAction.setCaller(caller);
            postAction.setWorkflow(workflow);
            boolean res = postAction.performAction(req, context);
            if (!res)
                throw new ActionException("Action \""+postAction.getName()+"\" failed");
        }
    }

    /**
     * Return the view for this state. Usually this returns the name of a  
     * jsp file.
     * @return the name of the view.
     */
    public String getView() {
        return view;
    }

    /**
     * Set the view uri for this state. Usually this is a jsp view.
     * @param view the view uri
     */
    public void setView(String view) {
        this.view = view;
    }

    /**
     * Get the pointer to a new workflow. If this is null, the state has no 
     * pointer to a new workflow. 
     * @return the name of a new workflow.
     */
    public String getNewWorkflow() {
        if (newWorkflow!=null && newWorkflow.equals(""))
            return null;
        return newWorkflow;
    }

    /**
     * Set the pointer to a new workflow. 
     * @param newWorkflow is the name of the new workflow.
     */
    public void setNewWorkflow(String newWorkflow) {
        this.newWorkflow = newWorkflow;
    }
    
    /**
     * Set a string attribute on the state available during current workflow. 
     * This could be used to save posted data to be able to go back in states
     * and yet have posted data available. 
     * @param name is the name of the attribute.
     * @param value is the value of the attribute.
     */
    public void setAttribute(String name, String value)
    {
        attributes.put(name, value);
    }
    
    /**
     * Get a string attribute on the state available during current workflow. 
     * This could be used to save posted data to be able to go back in states
     * and yet have posted data available. 
     * @param name is the name of the attribute 
     * @return the value of the given name. Null is returned if not found.
     */
    public String getAttribute(String name)
    {
        return (String)attributes.get(name);
    }
    
    /**
     * Remove all attributes available in this state.
     */
    public void removeAllAttributes()
    {
        attributes = new HashMap();
    }
    
    /**
     * Get an alternative state if any actions in this state throws an 
     * ActionException with a message setAlt(...). The name available in the 
     * exception message and the name of the altState must match.
     * @param name the name of the alternative state to fetch
     * @return an altState object with the alternative state.
     */
    public AltState getAltState(String name)
    {
        if (altStates == null)
            return null;
        for (int i=0;i<altStates.length;i++)
        {
            if (name.equals(altStates[i].getName()))
            {
                return altStates[i];
            }
        }
        return null;
    }
    
    /**
     * Return this object to a string representation.
     * @return a string of the representation.
     */
    public String toString()
    {
        String preActionString = "";
        if (preAction==null)
            preActionString="null";
        else
            preActionString = preAction.toString();
        
        String out = "<state name=\""+name+"\" preaction=\""+preActionString+"\">\n";
        for (int i=0;i<altStates.length;i++)
        {
            out += "\t"+altStates[i].toString();
        }
        out += "</state>\n";
        return out;
    }
    
    public void setCaller(Caller caller)
    {
        this.caller = caller;
    }
    public void setWorkflow(Workflow workflow)
    {
        this.workflow = workflow;
    }
}
