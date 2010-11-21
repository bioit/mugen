/*
 * TestAction1.java
 *
 * Created on February 1, 2006, 9:58 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package test.model;

import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.webapp.action.MugenAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author heto
 */
public class TestAction1 extends MugenAction {
    
    public String getName() {
        return "TestAction1";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            System.out.println(getName());
            
            return true;
       
        } catch (Exception e) {
            throw new ApplicationException("Failed to perform action", e);
        }
    }
}
