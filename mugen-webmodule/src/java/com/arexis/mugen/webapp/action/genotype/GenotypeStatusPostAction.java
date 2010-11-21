/*
 * GenotypeStatusPostAction.java
 *
 * Created on November 23, 2005, 3:00 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action.genotype;

import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author lami
 */
public class GenotypeStatusPostAction extends MugenAction {
    
    /** Creates a new instance of GenotypeStatusPostAction */
    public GenotypeStatusPostAction() {
    }

    
    /**
     * Returns the name of this action
     * @return The name of this action
     */
    public String getName() {
        return "GenotypeStatusPostAction";
    }
    
    /**
     * Performs the action
     * @param req The http request object
     * @param context The servlet context
     * @return True if the action could be performed
     */
    public boolean performAction(HttpServletRequest req, ServletContext context) {        
        try {


            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }       
}
