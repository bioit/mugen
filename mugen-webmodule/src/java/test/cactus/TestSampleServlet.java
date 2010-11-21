/*
 * TestSampleServlet.java
 *
 * Created on June 16, 2005, 9:26 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package test.cactus;

import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.project.ProjectRemoteHome;
import javax.ejb.EJBException;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
import test.Verifier;


/**
 *
 * @author heto
 */
public class TestSampleServlet extends ServletTestCase
{
    int pid = 10000;
    String name = "testproject";
    String comm = "comment";
    String status = "E";
    
    ProjectRemote prj = null;
    
    Verifier verifier;
    
    /** Creates a new instance of TestSampleServlet */
    public TestSampleServlet() {
    }
    
    public TestSampleServlet(String theName)
    {
        super(theName);
    }

    public static Test suite()
    {
        //System.setProperty("cactus.contextURL","http://127.0.0.1:8113/mugen");
        return new TestSuite(TestSampleServlet.class);
    }

    public void beginSaveToSessionOK(WebRequest webRequest)
    {
        webRequest.addParameter("testparam", "it works!");
    }
    
    /*
    public void testLookupProjectBean()
    {
        try
        {
            ProjectRemoteHome home = lookupProjectBean();
            ProjectRemote prj = home.findByPrimaryKey(new Integer(1001));
            prj.getName();
        }
        catch (Exception e)
        {
            fail(e.getMessage()); 
        }
    }
     */
    
    public void testPid()
    {
        try
        {
            if (prj.getPid() != pid)
                throw new EJBException("error pid");
            
            verifier.verifiy("pid",String.valueOf(pid),pid);
        }
        
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }
     
    
    public void testMyCase()
    {
        //fail("Blaha!");
    }

    public void testSaveToSessionOK()
    {
        SampleServlet servlet = new SampleServlet();
        servlet.saveToSession(request);
        assertEquals("it works!", session.getAttribute("testAttribute"));
        
    }

    private com.arexis.mugen.project.project.ProjectRemoteHome lookupProjectBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/ProjectBean");
            com.arexis.mugen.project.project.ProjectRemoteHome rv = (com.arexis.mugen.project.project.ProjectRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.project.project.ProjectRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }
    
}
