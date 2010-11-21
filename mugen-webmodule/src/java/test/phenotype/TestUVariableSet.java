/*
 * TestUVariableSet.java
 *
 * Created on June 29, 2005, 10:15 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package test.phenotype;
import com.arexis.mugen.phenotype.uvariableset.UVariableSetRemote;
import com.arexis.mugen.phenotype.uvariableset.UVariableSetRemoteHome;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import test.SqlHelper;


/**
 *
 * @author lami
 */
public class TestUVariableSet extends ServletTestCase {
    
    public TestUVariableSet(String theName) {
        super(theName);
    }
    
    public static Test suite() {
        
        return new TestSuite(TestUVariableSet.class);
    }    
    
    /**
     * Test a lallele bean, create a new bean. Set and get functions and
     * verify against the database
     */
    public void testUVariableSet() {     
        int uvsid = 1;
        int sid = 1001;
        int pid = 1001;
        String name = "Eagle";
        String comm = "comment1233";
        SqlHelper db = null;
       
        try {
            db = new SqlHelper("u_variable_sets", "uvsid", ""+uvsid);                      
            
            UVariableSetRemoteHome home = lookupUVariableSetBean();
            UVariableSetRemote prj = home.create(uvsid, pid, sid, name, comm, null);
            /*
             * Check that create() inserted correct values
             */
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(uvsid, db.getInt("uvsid"));            
            assertEquals(pid, db.getInt("pid")); 
            assertEquals(sid, db.getInt("sid")); 
            /*
             * Check values in bean
             */
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());            
            assertEquals(uvsid, prj.getUvsid());          
            assertEquals(pid, prj.getSid());
            assertEquals(sid, prj.getPid());
            
            /* Change parameter values
             */            
            name = "Crow";
            comm = "comment12443";
            sid = 1002;
            pid = 1021;
            
            /*
             * Set the properties
             */
            prj.setName(name);
            prj.setComm(comm);
            prj.setSid(sid);
            prj.setPid(pid);
            /*
             * Check values in db
             */
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(uvsid, db.getInt("uvsid"));            
            assertEquals(pid, db.getInt("pid")); 
            assertEquals(sid, db.getInt("sid")); 
            
            /*
             * Check values in bean
             */
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());            
            assertEquals(uvsid, prj.getUvsid());           
            assertEquals(pid, prj.getPid());
            assertEquals(sid, prj.getSid());
                                  
            /*
             * Remove test object
             */
            prj.remove();
            this.assertNull(db.getString("uvsid"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //e.
            
            //e.getCause().printStackTrace();
            
            fail(e.getMessage());
        } finally {
            try {
                db.delete("uvsid", "1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }  

    private com.arexis.mugen.phenotype.uvariableset.UVariableSetRemoteHome lookupUVariableSetBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/UVariableSetBean");
            com.arexis.mugen.phenotype.uvariableset.UVariableSetRemoteHome rv = (com.arexis.mugen.phenotype.uvariableset.UVariableSetRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.phenotype.uvariableset.UVariableSetRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }
}
