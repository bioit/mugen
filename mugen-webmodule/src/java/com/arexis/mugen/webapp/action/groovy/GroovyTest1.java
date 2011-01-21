package com.arexis.mugen.webapp.action.groovy;

import com.arexis.mugen.webapp.action.*;
import com.arexis.mugen.exceptions.ApplicationException;
import groovy.lang.Binding;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.mugen.groovy.ScriptLocator;

public class GroovyTest1 extends MugenAction {
    
    public GroovyTest1() {}
    
    public String getName() {
        return "GroovyTest1";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            Binding binding = new Binding();
            binding.setVariable("input", "disco unix dude!");
            Object message = script_locator.runScript(ScriptLocator.Scripts.HELLO, binding);
            request.setAttribute("message", message);
        }
        catch (Exception e) {
            logger.error("GroovyTest1 failed", e);
        }
        return true;
    }
}
