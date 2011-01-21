package org.mugen.groovy;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import org.apache.log4j.Logger;

public class ScriptLocator {
    //the logger
    protected static Logger logger = Logger.getLogger(ScriptLocator.class);
    //singleton private instance
    private static ScriptLocator me;
    //the location of the groovy scripts
    private static String[] roots = new String[] { "../groovy" };
    //the script engine
    private static GroovyScriptEngine gse;
    
    static {
        me = new ScriptLocator();
    }
    
    private ScriptLocator() {
        try {
            gse = new GroovyScriptEngine(roots);
        } catch (Exception e) {
            logger.error("Failed to load groovy scripts", e);
        }
    }
    
    static public ScriptLocator getInstance() {
        return me;
    }
    
    public class Scripts {
        final public static int HELLO  = 0;
    }
    
    final static String HELLO_SCRIPT  = "hello.groovy";
    
    static private String getScript(int script){
        switch(script) {
            case Scripts.HELLO:
                return HELLO_SCRIPT;
        }
        return null;
    }
    
    public Object runScript(int script, Binding binding) {
        Object obj = null;
        try {
            gse.run(getScript(script), binding);
            obj = binding.getVariable("output");
        }
        catch(Exception e) {
            logger.error("Failed to run groovy script " + getScript(script),  e);
        }
        return obj;
    }

}
