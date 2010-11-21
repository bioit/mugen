/*
 * TestVariableSet.java
 *
 * Created on June 29, 2005, 10:35 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package test.phenotype;

import com.arexis.mugen.phenotype.variableset.VariableSetRemote;
import com.arexis.mugen.phenotype.variableset.VariableSetRemoteHome;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import test.SqlHelper;


/**
 *
 * @author lami
 */
public class TestVariableSet extends ServletTestCase {
    
    public TestVariableSet(String theName) {
        super(theName);
    }
    
    public static Test suite() {
        
        return new TestSuite(TestVariableSet.class);
    }    
    
    /**
     * Test a lallele bean, create a new bean. Set and get functions and
     * verify against the database
     */
    public void testVariableSet() {     
        int vsid = 1;
        int suid = 1001;        
        String name = "Eagle";
        String comm = "comment1233";
        SqlHelper db = null;
       
        try {
            db = new SqlHelper("variable_sets", "vsid", ""+vsid);                      
            
            VariableSetRemoteHome home = lookupVariableSetBean();
            VariableSetRemote prj = home.create(vsid, suid, name, comm, null);
            /*
             * Check that create() inserted correct values
             */
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(vsid, db.getInt("vsid"));            
            assertEquals(suid, db.getInt("suid"));             
            /*
             * Check values in bean
             */
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());            
            assertEquals(vsid, prj.getVsid());          
            assertEquals(suid, prj.getSuid());            
            
            /* Change parameter values
             */            
            name = "Crow";
            comm = "comment12443";
            suid = 1032;            
            
            /*
             * Set the properties
             */
            prj.setName(name);
            prj.setComm(comm);
            prj.setSuid(suid);
            
            /*
             * Check values in db
             */
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(vsid, db.getInt("vsid"));                        
            assertEquals(suid, db.getInt("suid")); 
            
            /*
             * Check values in bean
             */
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());            
            assertEquals(vsid, prj.getVsid());                       
            assertEquals(suid, prj.getSuid());
                                  
            /*
             * Remove test object
             */
            prj.remove();
            this.assertNull(db.getString("vsid"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //e.
            
            //e.getCause().printStackTrace();
            
            fail(e.getMessage());
        } finally {
            try {
                db.delete("vsid", "1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }  

    private com.arexis.mugen.phenotype.variableset.VariableSetRemoteHome lookupVariableSetBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/VariableSetBean");
            com.arexis.mugen.phenotype.variableset.VariableSetRemoteHome rv = (com.arexis.mugen.phenotype.variableset.VariableSetRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.phenotype.variableset.VariableSetRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }
}
