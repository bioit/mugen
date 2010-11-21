/*
 * TestUPosition.java
 *
 * Created on June 27, 2005, 12:59 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package test.genotype;

import com.arexis.mugen.genotype.uposition.UPositionRemoteHome;
import com.arexis.mugen.genotype.uposition.UPositionRemote;

import com.arexis.mugen.genotype.umarker.UMarkerRemoteHome;
import com.arexis.mugen.genotype.umarker.UMarkerRemote;

import com.arexis.mugen.genotype.umarkerset.UMarkerSetRemoteHome;
import com.arexis.mugen.genotype.umarkerset.UMarkerSetRemote;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import test.SqlHelperTwoKeys;


/**
 *
 * @author lami
 */
public class TestUPosition extends ServletTestCase {
    
    public TestUPosition(String theName) {
        super(theName);
    }
    
    public static Test suite() {        
        return new TestSuite(TestUPosition.class);
    }
    

    
    public void testUPosition() {
        int umsid = 1000;
        int umid = 2222;
        double value = 0.0;
        
        SqlHelperTwoKeys db = null;
        
        try {
            UMarkerRemoteHome umh = lookupUMarkerBean();
            UMarkerRemote mprj = umh.create(2222, "Arne", "Arnes alias", 
                    "Arne comment", value, 1021, 1001, 1035, null);
            
            UMarkerSetRemoteHome umsh = lookupUMarkerSetBean();
            UMarkerSetRemote msprj = umsh.create(1000, "Namnet", "Comment", 1021, 1001, null);            
            
            db = new SqlHelperTwoKeys("u_positions", "umsid", "umid", "1000", "2222");
            
            UPositionRemoteHome home = lookupUPositionBean();
            UPositionRemote prj = home.create(mprj, msprj, value);
            /*
             * Check that create() inserted correct values
             */
            assertEquals(umsid, db.getInt("umsid"));
            assertEquals(umid, db.getInt("umid"));
            
            /*
             * Check values in bean
             */
            assertEquals(umsid, prj.getUmsid());
            assertEquals(umid, prj.getUmid());
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
            
            this.assertNull(db.getString("umsid"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //e.
            
            //e.getCause().printStackTrace();
            
            fail(e.getMessage());
        } finally {
            try {
                db.delete("umsid", "umid", "1000", "2222");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private com.arexis.mugen.genotype.uposition.UPositionRemoteHome lookupUPositionBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/UPositionBean");
            com.arexis.mugen.genotype.uposition.UPositionRemoteHome rv = (com.arexis.mugen.genotype.uposition.UPositionRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.genotype.uposition.UPositionRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }

    private com.arexis.mugen.genotype.umarkerset.UMarkerSetRemoteHome lookupUMarkerSetBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/UMarkerSetBean");
            com.arexis.mugen.genotype.umarkerset.UMarkerSetRemoteHome rv = (com.arexis.mugen.genotype.umarkerset.UMarkerSetRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.genotype.umarkerset.UMarkerSetRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }

    private com.arexis.mugen.genotype.umarker.UMarkerRemoteHome lookupUMarkerBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/UMarkerBean");
            com.arexis.mugen.genotype.umarker.UMarkerRemoteHome rv = (com.arexis.mugen.genotype.umarker.UMarkerRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.genotype.umarker.UMarkerRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }
 }
