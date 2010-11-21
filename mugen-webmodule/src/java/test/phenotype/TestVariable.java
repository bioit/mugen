/*
 * TestVariable.java
 *
 * Created on June 28, 2005, 5:56 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package test.phenotype;

import com.arexis.mugen.phenotype.variable.VariableRemote;
import com.arexis.mugen.phenotype.variable.VariableRemoteHome;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import test.SqlHelper;


/**
 *
 * @author lami
 */
public class TestVariable extends ServletTestCase {
    
    public TestVariable(String theName) {
        super(theName);
    }
    
    public static Test suite() {
        
        return new TestSuite(TestVariable.class);
    }    
    
    /**
     * Test a lallele bean, create a new bean. Set and get functions and
     * verify against the database
     */
    public void testVariable() {     
        int vid = 1;
        int suid = 1032;
        String name = "Eagle";
        String comm = "comment1233";
        String type = "E";
        String unit = "A";
        SqlHelper db = null;
        
        try {
            db = new SqlHelper("variables", "vid", ""+vid);                      
            
            VariableRemoteHome home = lookupVariableBean();
            VariableRemote prj = home.create(vid, suid, name, type, unit, comm, null);
            /*
             * Check that create() inserted correct values
             */
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(type, db.getString("type"));
            assertEquals(unit, db.getString("unit"));
            assertEquals(vid, db.getInt("vid"));            
            assertEquals(suid, db.getInt("suid")); 
            /*
             * Check values in bean
             */
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());            
            assertEquals(vid, prj.getVid());
            assertEquals(type, prj.getType());
            assertEquals(unit, prj.getUnit());
            assertEquals(suid, prj.getSamplingUnit().getSuid());
            
            /* Change parameter values
             */            
            name = "Crow";
            comm = "comment12443";
            suid = 1056;
            unit = "B";
            type = "N";
            
            /*
             * Set the properties
             */
            prj.setName(name);
            prj.setComm(comm);
            prj.setSuid(suid);
            prj.setUnit(unit);
            prj.setType(type);
            /*
             * Check values in db
             */
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(type, db.getString("type"));
            assertEquals(unit, db.getString("unit"));
            assertEquals(vid, db.getInt("vid"));            
            assertEquals(suid, db.getInt("suid")); 
            
            /*
             * Check values in bean
             */
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());            
            assertEquals(vid, prj.getVid());
            assertEquals(type, prj.getType());
            assertEquals(unit, prj.getUnit());
            assertEquals(suid, prj.getSamplingUnit().getSuid());
                                  
            /*
             * Remove test object
             */
            prj.remove();
            this.assertNull(db.getString("vid"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //e.
            
            //e.getCause().printStackTrace();
            
            fail(e.getMessage());
        } finally {
            try {
                db.delete("vid", "1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }  

    private com.arexis.mugen.phenotype.variable.VariableRemoteHome lookupVariableBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/VariableBean");
            com.arexis.mugen.phenotype.variable.VariableRemoteHome rv = (com.arexis.mugen.phenotype.variable.VariableRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.phenotype.variable.VariableRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }
}
