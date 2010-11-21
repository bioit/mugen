/*
 * TestSpecies.java
 *
 * Created on June 16, 2005, 1:03 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package test.species;
import com.arexis.mugen.species.species.SpeciesRemote;
import com.arexis.mugen.species.species.SpeciesRemoteHome;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import test.SqlHelper;
import test.Verifier;


/**
 *
 * @author heto
 */
public class TestSpecies extends ServletTestCase {
    
    public TestSpecies(String theName) {
        super(theName);
    }
    
    public static Test suite() {
        
        return new TestSuite(TestSpecies.class);
    }
    
    /**
     * Test a species bean, create a new bean. Set and get functions and
     * verify against the database
     */
    public void testSpecies() {     
        int sid = 1004;
        String name = "Eagle";
        String comm = "comment1233";
        
        SqlHelper db = null;
        
        try {
            db = new SqlHelper("species", "sid", ""+sid);
            
            SpeciesRemoteHome home = lookupSpeciesBean();
            SpeciesRemote prj = home.create(sid, name, comm);
            
            /**
             * Check that the finder method finds this new object
             */
            SpeciesRemote s2 = home.findByPrimaryKey(new Integer(1004));
            
            /*
             * Check that create() inserted correct values
             */
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(sid, db.getInt("sid"));            
            /*
             * Check values in bean
             */
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());            
            assertEquals(sid, prj.getSid());            
            
            /* Change parameter values
             */            
            name = "Crow";
            comm = "comment12443";         
            
            /*
             * Set the properties
             */
            prj.setName(name);
            prj.setComm(comm);
            /*
             * Check values in db
             */
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));          
            
            /*
             * Check values in bean
             */
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());           
                                  
            /*
             * Remove test object
             */
            prj.remove();
            this.assertNull(db.getString("cid"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //e.
            
            //e.getCause().printStackTrace();
            
            fail(e.getMessage());
        } finally {
            try {
                db.delete("cid", "1004");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }    
    
    private com.arexis.mugen.species.species.SpeciesRemoteHome lookupSpeciesBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/SpeciesBean");
            SpeciesRemoteHome rv = (SpeciesRemoteHome)
            javax.rmi.PortableRemoteObject.narrow(remote, SpeciesRemoteHome.class);
            return rv;
        } catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }
    
}
