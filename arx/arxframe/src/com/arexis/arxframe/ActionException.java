/*
 * ActionException.java
 *
 * Created on October 31, 2005, 12:40 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.arxframe;

import com.arexis.arxframe.advanced.Workflow;

/**
 *
 * @author heto
 */
public class ActionException extends ArxFrameException {
    
    private String alt;
    private Workflow workflow;
    
    /** Creates a new instance of ActionException */
    public ActionException(String msg) {
        super(msg);
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
    
    
    
    
    
}
