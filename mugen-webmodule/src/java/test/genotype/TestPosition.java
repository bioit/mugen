/*
 * TestPosition.java
 *
 * Created on June 28, 2005, 11:24 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package test.genotype;

import com.arexis.mugen.genotype.position.PositionRemoteHome;
import com.arexis.mugen.genotype.position.PositionRemote;

import com.arexis.mugen.genotype.marker.MarkerRemoteHome;
import com.arexis.mugen.genotype.marker.MarkerRemote;

import com.arexis.mugen.genotype.markerset.MarkerSetRemoteHome;
import com.arexis.mugen.genotype.markerset.MarkerSetRemote;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import test.SqlHelperTwoKeys;


/**
 *
 * @author lami
 */
public class TestPosition extends ServletTestCase {
    
    public TestPosition(String theName) {
        super(theName);
    }
    
    public static Test suite() {        
        return new TestSuite(TestPosition.class);
    }
    

    
    public void testPosition() {
        int msid = 1000;
        int mid = 2222;
        double value = 0.0;
        
        SqlHelperTwoKeys db = null;
        
        try {
            MarkerRemoteHome umh = lookupMarkerBean();
                        
            MarkerRemote mprj = umh.create(2222, 1056, 1026, "Arne", "Comment", 
                    "Arnes alias", value, "p1", "p2", null);
            
            MarkerSetRemoteHome umsh = lookupMarkerSetBean();
            MarkerSetRemote msprj = umsh.create(1000, 1034, "Namnet", "Comment", null);                        
            db = new SqlHelperTwoKeys("positions", "msid", "mid", "1000", "2222");
            
            PositionRemoteHome home = lookupPositionBean();
            PositionRemote prj = home.create(mprj, msprj, value);
            /*
             * Check that create() inserted correct values
             */
            assertEquals(msid, db.getInt("msid"));
            assertEquals(mid, db.getInt("mid"));
            
            /*
             * Check values in bean
             */
            assertEquals(msid, prj.getMarkerSet().getMsid());
            assertEquals(mid, prj.getMarker().getMid());
            assertEquals(""+value, ""+prj.getValue());
            
            /* Change parameter values
             */            
           value = 0.1;
            
            /*
             * Set the properties
             */
            prj.setValue(value);
            /*
             * Check values in db
             */
            assertEquals(""+value, ""+db.getDouble("value"));          
            
            /*
             * Check values in bean
             */
            assertEquals(""+value, ""+prj.getValue());          
                                  
            /*
             * Remove test object
             */
            prj.remove();
            msprj.remove();
            mprj.remove();
            
            this.assertNull(db.getString("msid"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //e.
            
            //e.getCause().printStackTrace();
            
            fail(e.getMessage());
        } finally {
            try {
                db.delete("msid", "mid", "1000", "2222");
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

    private com.arexis.mugen.genotype.marker.MarkerRemoteHome lookupMarkerBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/MarkerBean");
            com.arexis.mugen.genotype.marker.MarkerRemoteHome rv = (com.arexis.mugen.genotype.marker.MarkerRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.genotype.marker.MarkerRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }    

    private com.arexis.mugen.genotype.position.PositionRemoteHome lookupPositionBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/PositionBean");
            com.arexis.mugen.genotype.position.PositionRemoteHome rv = (com.arexis.mugen.genotype.position.PositionRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.genotype.position.PositionRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }
}
