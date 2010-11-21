/*
 * TestUMarkersSet.java
 *
 * Created on June 27, 2005, 8:13 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */


package test.genotype;

import com.arexis.mugen.genotype.umarkerset.UMarkerSetRemoteHome;
import com.arexis.mugen.genotype.umarkerset.UMarkerSetRemote;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import test.SqlHelper;


/**
 *
 * @author lami
 */
public class TestUMarkerSet extends ServletTestCase {
    
    public TestUMarkerSet(String theName) {
        super(theName);
    }
    
    public static Test suite() {        
        return new TestSuite(TestUMarkerSet.class);
    }
    

    
    public void testUMarkerSet() {
        int umsid = 10000;
        int pid = 1024;
        int sid = 1001;
        String name = "kdflkdflködsk";
        String comm = "comment1233";
        
        SqlHelper db = null;
        
        try {
            db = new SqlHelper("u_marker_sets", "umsid", "10000");
            
            UMarkerSetRemoteHome home = lookupUMarkerSetBean();
            UMarkerSetRemote prj = home.create(umsid, name, comm, pid, sid, null);
            /*
             * Check that create() inserted correct values
             */
            assertEquals(umsid, db.getInt("umsid"));
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(pid, db.getInt("pid"));
            assertEquals(sid, db.getInt("sid"));
            
            /*
             * Check values in bean
             */
            assertEquals(umsid, prj.getUmsid());
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());
            assertEquals(sid, prj.getSid());
            assertEquals(pid, prj.getPid());
            
            /* Change parameter values
             */            
            pid = 1021;
            sid = 1002;
            name = "kdflködsk";
            comm = "comment12443";
            
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
            assertEquals(umsid, db.getInt("umsid"));
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(pid, db.getInt("pid"));
            assertEquals(sid, db.getInt("sid"));            
            
            /*
             * Check values in bean
             */
            assertEquals(umsid, prj.getUmsid());
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());
            assertEquals(sid, prj.getSid());
            assertEquals(pid, prj.getPid());            
                                  
            /*
             * Remove test object
             */
            prj.remove();
            this.assertNull(db.getString("umsid"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //e.
            
            //e.getCause().printStackTrace();
            
            fail(e.getMessage());
        } finally {
            try {
                db.delete("umsid", "10000");
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        
    }
