/*
 * ForwardWorkflowException.java
 *
 * Created on February 1, 2006, 8:23 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.arxframe.advanced;

import com.arexis.arxframe.WorkflowException;

/**
 *
 * @author heto
 */
public class BackWorkflowException extends WorkflowException {
    
    /**
     * Creates a new instance of ForwardWorkflowException
     * @param msg is a message
     * @param wfName is the new workflow name that should be currentWorkflow
     */
    public BackWorkflowException(String msg) {
        super(msg);
    }
    
}
