package test;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

public class GroovyTest {
    
    public static void main(java.lang.String[] argList) {
        try {
            //        System.out.println("hey");
                    String[] roots = new String[] { "test/test" };
                    GroovyScriptEngine gse = new GroovyScriptEngine(roots);
                    Binding binding = new Binding();
                    binding.setVariable("input", "666");
                    gse.run("hello.groovy", binding);
                    System.out.println(binding.getVariable("output"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
