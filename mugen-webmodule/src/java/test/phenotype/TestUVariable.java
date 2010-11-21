/*
 * TestUVariable.java
 *
 * Created on June 29, 2005, 10:04 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package test.phenotype;

import com.arexis.mugen.phenotype.uvariable.UVariableRemote;
import com.arexis.mugen.phenotype.uvariable.UVariableRemoteHome;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import test.SqlHelper;


/**
 *
 * @author lami
 */
public class TestUVariable extends ServletTestCase {
    
    public TestUVariable(String theName) {
        super(theName);
    }
    
    public static Test suite() {
        
        return new TestSuite(TestUVariable.class);
    }    
    
    /**
     * Test a lallele bean, create a new bean. Set and get functions and
     * verify against the database
     */
    public void testUVariable() {     
        int uvid = 1;
        int sid = 1001;
        int pid = 1001;
        String name = "Eagle";
        String comm = "comment1233";
        String type = "E";
        String unit = "A";
        SqlHelper db = null;
       
        try {
            db = new SqlHelper("u_variables", "uvid", ""+uvid);                      
            
            UVariableRemoteHome home = lookupUVariableBean();
            UVariableRemote prj = home.create(uvid, pid, sid, name, comm, unit, type, null);
            /*
             * Check that create() inserted correct values
             */
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(type, db.getString("type"));
            assertEquals(unit, db.getString("unit"));
            assertEquals(uvid, db.getInt("uvid"));            
            assertEquals(pid, db.getInt("pid")); 
            assertEquals(sid, db.getInt("sid")); 
            /*
             * Check values in bean
             */
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());            
            assertEquals(uvid, prj.getUvid());
            assertEquals(type, prj.getType());
            assertEquals(unit, prj.getUnit());            
            assertEquals(pid, prj.getSid());
            assertEquals(sid, prj.getPid());
            
            /* Change parameter values
             */            
            name = "Crow";
            comm = "comment12443";
            sid = 1002;
            pid = 1021;
            unit = "B";
            type = "N";
            
            /*
             * Set the properties
             */
            prj.setName(name);
            prj.setComm(comm);
            prj.setSid(sid);
            prj.setPid(pid);
            prj.setUnit(unit);
            prj.setType(type);
            /*
             * Check values in db
             */
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(type, db.getString("type"));
            assertEquals(unit, db.getString("unit"));
            assertEquals(uvid, db.getInt("uvid"));            
            assertEquals(pid, db.getInt("pid")); 
            assertEquals(sid, db.getInt("sid")); 
            
            /*
             * Check values in bean
             */
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());            
            assertEquals(uvid, prj.getUvid());
            assertEquals(type, prj.getType());
            assertEquals(unit, prj.getUnit());            
            assertEquals(pid, prj.getPid());
            assertEquals(sid, prj.getSid());
                                  
            /*
             * Remove test object
             */
            prj.remove();
            this.assertNull(db.getString("uvid"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //e.
            
            //e.getCause().printStackTrace();
            
            fail(e.getMessage());
        } finally {
            try {
                db.delete("uvid", "1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }  

    private com.arexis.mugen.phenotype.uvariable.UVariableRemoteHome lookupUVariableBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/UVariableBean");
            com.arexis.mugen.phenotype.uvariable.UVariableRemoteHome rv = (com.arexis.mugen.phenotype.uvariable.UVariableRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.phenotype.uvariable.UVariableRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }
}
