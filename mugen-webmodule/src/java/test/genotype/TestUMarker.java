/*
 * TestUMarker.java
 *
 * Created on June 27, 2005, 1:59 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package test.genotype;

import com.arexis.mugen.genotype.umarker.UMarkerRemoteHome;
import com.arexis.mugen.genotype.umarker.UMarkerRemote;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import test.SqlHelper;


/**
 *
 * @author lami
 */
public class TestUMarker extends ServletTestCase {
    
    public TestUMarker(String theName) {
        super(theName);
    }
    
    public static Test suite() {        
        return new TestSuite(TestUMarker.class);
    }
    

    
    public void testUMarker() {
        int umid = 1000;
        double position = 0.0;
        String name = "Arne";
        String alias = "Arnes alias";
        String comment = "Arnes comment";
        int pid = 1021;
        int sid = 1001;
        int cid = 1035;
        
        SqlHelper db = null;
        
        try {
            UMarkerRemoteHome umh = lookupUMarkerBean();
            UMarkerRemote prj = umh.create(umid, name, alias, comment, position, pid, sid, cid, null);           
            
            db = new SqlHelper("u_markers", "umid", "1000");

            /*
             * Check that create() inserted correct values
             */
            assertEquals(name, db.getString("name"));
            assertEquals(alias, db.getString("alias"));
            assertEquals(comment, db.getString("comm"));
            assertEquals(""+position, ""+db.getDouble("position"));
            assertEquals(umid, db.getInt("umid"));
            assertEquals(pid, db.getInt("pid"));
            assertEquals(sid, db.getInt("sid"));
            assertEquals(cid, db.getInt("cid"));
            
            /*
             * Check values in bean
             */
            assertEquals(umid, prj.getUmid());
            assertEquals(name, prj.getName());
            assertEquals(""+position, ""+prj.getPosition());
            assertEquals(alias, prj.getAlias());
            assertEquals(pid, prj.getPid());
            assertEquals(sid, prj.getSid());
            assertEquals(cid, prj.getCid());
            
            /* Change parameter values
             */            
           position = 0.1;
           comment = "New comment";
           name = "Agne";
           alias = "Aliasalias";
           sid = 1003;
           cid = 1025;
           pid = 1022;
            
            /*
             * Set the properties
             */
            prj.setPosition(position);
            prj.setComm(comment);
            prj.setName(name);
            prj.setAlias(alias);
            prj.setSid(sid);
            prj.setCid(cid);
            prj.setPid(pid);
            /*
             * Check values in db
             */
            assertEquals(name, db.getString("name"));
            assertEquals(alias, db.getString("alias"));
            assertEquals(comment, db.getString("comm"));
            assertEquals(""+position, ""+db.getDouble("position"));
            assertEquals(umid, db.getInt("umid"));
            assertEquals(pid, db.getInt("pid"));
            assertEquals(sid, db.getInt("sid"));
            assertEquals(cid, db.getInt("cid"));
            
            /*
             * Check values in bean
             */
            assertEquals(umid, prj.getUmid());
            assertEquals(name, prj.getName());
            assertEquals(""+position, ""+prj.getPosition());
            assertEquals(alias, prj.getAlias());
            assertEquals(pid, prj.getPid());
            assertEquals(sid, prj.getSid());
            assertEquals(cid, prj.getCid());       
                                  
            /*
             * Remove test object
             */
            prj.remove();
            
            this.assertNull(db.getString("umid"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //e.
            
            //e.getCause().printStackTrace();
            
            fail(e.getMessage());
        } finally {
            try {
                db.delete("umid", "1000");
            } catch (Exception e) {
                e.printStackTrace();
            }
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
