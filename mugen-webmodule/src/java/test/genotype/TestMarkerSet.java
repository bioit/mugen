/*
 * TestMarkerSet.java
 *
 * Created on June 28, 2005, 11:11 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package test.genotype;

import com.arexis.mugen.genotype.markerset.MarkerSetRemote;
import com.arexis.mugen.genotype.markerset.MarkerSetRemoteHome;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import test.SqlHelper;


/**
 *
 * @author lami
 */
public class TestMarkerSet extends ServletTestCase {
    
    public TestMarkerSet(String theName) {
        super(theName);
    }
    
    public static Test suite() {
        
        return new TestSuite(TestMarkerSet.class);
    }    
    
    /**
     * Test a lallele bean, create a new bean. Set and get functions and
     * verify against the database
     */
    public void testMarkerSet() {
        int msid = 1000;        
        int suid = 1032;
        String name = "kdflkdflködsk";
        String comm = "comment1233";
        
        SqlHelper db = null;
        
        try {
            db = new SqlHelper("marker_sets", "msid", "1000");
            
            MarkerSetRemoteHome home = lookupMarkerSetBean();
            MarkerSetRemote prj = home.create(msid, suid, name, comm, null);
            /*
             * Check that create() inserted correct values
             */
            assertEquals(msid, db.getInt("msid"));
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));            
            assertEquals(suid, db.getInt("suid"));
            
            /*
             * Check values in bean
             */
            assertEquals(msid, prj.getMsid());
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());
            assertEquals(suid, prj.getSamplingUnit().getSuid());
            
            /* Change parameter values
             */            
            
            suid = 1030;
            name = "kdflködsk";
            comm = "comment12443";
            
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
            assertEquals(suid, db.getInt("suid"));            
            
            /*
             * Check values in bean
             */            
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());
            assertEquals(suid, prj.getSamplingUnit().getSuid());
                                  
            /*
             * Remove test object
             */
            prj.remove();
            this.assertNull(db.getString("msid"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //e.
            
            //e.getCause().printStackTrace();
            
            fail(e.getMessage());
        } finally {
            try {
                db.delete("msid", "1000");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private com.arexis.mugen.genotype.markerset.MarkerSetRemoteHome lookupMarkerSetBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/MarkerSetBean");
            com.arexis.mugen.genotype.markerset.MarkerSetRemoteHome rv = (com.arexis.mugen.genotype.markerset.MarkerSetRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.genotype.markerset.MarkerSetRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }
}
