/*
 * TestLMarker.java
 *
 * Created on June 16, 2005, 1:03 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package test.species;

import com.arexis.mugen.species.lmarker.LMarkerRemoteHome;
import com.arexis.mugen.species.lmarker.LMarkerRemote;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import test.SqlHelper;


/**
 *
 * @author lami
 */
public class TestLMarker extends ServletTestCase {
    
    public TestLMarker(String theName) {
        super(theName);
    }
    
    public static Test suite() {        
        return new TestSuite(TestLMarker.class);
    }
    

    
    public void testLMarker() {
        int lmid = 10000;
        int cid = 1011;
        int sid = 1001;
        String name = "kdflkdflködsk";
        String comm = "comment1233";
        double position = 0.12;
        String p1 = "asd";
        String p2 = "fgdgf";
        String alias = "ghghghg";
        
        SqlHelper db = null;
        
        try {
            db = new SqlHelper("l_markers", "lmid", "10000");
            
            LMarkerRemoteHome home = lookupLMarkerBean();
            LMarkerRemote prj = home.create(lmid, name, alias, comm, p1, p2, position, sid, cid);
            /*
             * Check that create() inserted correct values
             */
            assertEquals(lmid, db.getInt("lmid"));
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(alias, db.getString("alias"));
            assertEquals(p1, db.getString("p1"));
            assertEquals(p2, db.getString("p2"));
            assertEquals(cid, db.getInt("cid"));
            assertEquals(sid, db.getInt("sid"));
            assertEquals(position, db.getDouble("position"), 0.0);
            /*
             * Check values in bean
             */
            assertEquals(lmid, prj.getLmid());
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());
            assertEquals(alias, prj.getAlias());
            assertEquals(p1, prj.getP1());
            assertEquals(p2, prj.getP2());
            assertEquals(position, prj.getPosition(), 0.0);
            assertEquals(sid, prj.getSpecies().getSid());
            assertEquals(cid, prj.getChromosome().getCid());
            
            /* Change parameter values
             */            
            cid = 1012;
            sid = 1002;
            name = "kdflködsk";
            comm = "comment12443";
            position = 0.14;
            p1 = "dsa";
            p2 = "asdgf";
            alias = "hjh333hg";            
            
            /*
             * Set the properties
             */
            prj.setName(name);
            prj.setComm(comm);
            prj.setAlias(alias);
            prj.setP1(p1);
            prj.setP2(p2);
            prj.setPosition(position);
            prj.setSid(sid);
            prj.setCid(cid);
            /*
             * Check values in db
             */
            assertEquals(lmid, db.getInt("lmid"));
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(alias, db.getString("alias"));
            assertEquals(p1, db.getString("p1"));
            assertEquals(p2, db.getString("p2"));
            assertEquals(cid, db.getInt("cid"));
            assertEquals(sid, db.getInt("sid"));
            assertEquals(position, db.getDouble("position"), 0.0);
            
            /*
             * Check values in bean
             */
            assertEquals(lmid, prj.getLmid());
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());
            assertEquals(alias, prj.getAlias());
            assertEquals(p1, prj.getP1());
            assertEquals(p2, prj.getP2());
            assertEquals(position, prj.getPosition(), 0.0);
            assertEquals(sid, prj.getSpecies().getSid());
            assertEquals(cid, prj.getChromosome().getCid());            
                                  
            /*
             * Remove test object
             */
            prj.remove();
            this.assertNull(db.getString("lmid"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //e.
            
            //e.getCause().printStackTrace();
            
            fail(e.getMessage());
        } finally {
            try {
                db.delete("lmid", "10000");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }

    private com.arexis.mugen.species.lmarker.LMarkerRemoteHome lookupLMarkerBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/LMarkerBean");
            com.arexis.mugen.species.lmarker.LMarkerRemoteHome rv = (com.arexis.mugen.species.lmarker.LMarkerRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.species.lmarker.LMarkerRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }
}

