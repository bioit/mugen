/*
 * AltState.java
 *
 * Created on November 15, 2005, 10:32 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.arxframe.advanced;

/**
 *
 * @author heto
 */
public class AltState extends State {
    
    /** Creates a new instance of AltState */
    public AltState() {
    }
    
    public String toString()
    {
        String out = "<alt name=\""+getName()+"\" viewURI=\""+getView()+"\" workflow=\""+getNewWorkflow()+"\"/>";
        return out;
    }
    
}
