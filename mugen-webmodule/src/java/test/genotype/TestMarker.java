///*
// * TestMarker.java
// *
// * Created on June 27, 2005, 2:30 PM
// *
// * To change this template, choose Tools | Options and locate the template under
// * the Source Creation and Management node. Right-click the template and choose
// * Open. You can then make changes to the template in the Source Editor.
// */
//
//package test.genotype;
//
//import com.arexis.mugen.genotype.marker.MarkerRemoteHome;
//import com.arexis.mugen.genotype.marker.MarkerRemote;
//
//import junit.framework.Test;
//import junit.framework.TestSuite;
//import org.apache.cactus.ServletTestCase;
//import test.SqlHelper;
//
//
///**
// *
// * @author lami
// */
//public class TestMarker extends ServletTestCase {
//    
//    public TestMarker(String theName) {
//        super(theName);
//    }
//    
//    public static Test suite() {        
//        return new TestSuite(TestMarker.class);
//    }
//    
//
//    
//    public void testMarker() {
//        int mid = 1000;
//        double position = 0.0;
//        String name = "Arne";
//        String alias = "Arnes alias";
//        String comment = "Arnes comment";
//        
//        String p1 = "1";
//        String p2 = "2";
//        int suid = 1021;
//        int cid = 1026;
//        
//        SqlHelper db = null;
//        
//        try {
//            MarkerRemoteHome umh = lookupMarkerBean();
//            MarkerRemote prj = umh.create(mid, suid, cid, name, comment, alias, position, p1, p2, null);           
//            
//            db = new SqlHelper("markers", "mid", "1000");
//
//            /*
//             * Check that create() inserted correct values
//             */
//            assertEquals(name, db.getString("name"));
//            assertEquals(alias, db.getString("alias"));
//            assertEquals(comment, db.getString("comm"));
//            assertEquals(""+position, ""+db.getDouble("position"));
//            assertEquals(mid, db.getInt("mid"));
//            assertEquals(suid, db.getInt("suid"));
//            assertEquals(cid, db.getInt("cid"));
//            assertEquals(p1, db.getString("p1"));  
//            assertEquals(p2, db.getString("p2"));  
//            
//            /*
//             * Check values in bean
//             */
//            assertEquals(mid, prj.getMid());
//            assertEquals(name, prj.getName());
//            assertEquals(""+position, ""+prj.getPosition());
//            assertEquals(alias, prj.getAlias());
//            assertEquals(suid, prj.getSuid());
//            assertEquals(p1, prj.getP1());  
//            assertEquals(p2, prj.getP2()); 
//            assertEquals(cid, prj.getCid()); 
//            
//            /* Change parameter values
//             */            
//           position = 0.1;
//           comment = "New comment";
//           name = "Agne";
//           alias = "Aliasalias";
//           suid = 1003;
//           p1 = "11";
//           p2 = "22";
//           cid = 1027;
//            
//            /*
//             * Set the properties
//             */
//            prj.setPosition(position);
//            prj.setComm(comment);
//            prj.setName(name);
//            prj.setAlias(alias);
//            prj.setSuid(suid);
//            prj.setCid(cid);
//            prj.setP1(p1);
//            prj.setP2(p2);
//            /*
//             * Check values in db
//             */
//            assertEquals(name, db.getString("name"));
//            assertEquals(alias, db.getString("alias"));
//            assertEquals(comment, db.getString("comm"));
//            assertEquals(""+position, ""+db.getDouble("position"));
//            assertEquals(mid, db.getInt("mid"));
//            assertEquals(suid, db.getInt("suid"));
//            assertEquals(p1, db.getString("p1"));
//            assertEquals(p2, db.getString("p2"));
//            assertEquals(cid, db.getInt("cid"));
//            
//            /*
//             * Check values in bean
//             */
//            assertEquals(mid, prj.getMid());
//            assertEquals(name, prj.getName());
//            assertEquals(""+position, ""+prj.getPosition());
//            assertEquals(alias, prj.getAlias());
//            assertEquals(suid, prj.getSuid());
//            assertEquals(cid, prj.getCid());
//            assertEquals(p1, prj.getP1());
//            assertEquals(p2, prj.getP2());
//                                  
//            /*
//             * Remove test object
//             */
//            prj.remove();
//            
//            this.assertNull(db.getString("mid"));
//        } catch (Exception e) {
//            e.printStackTrace(System.err);
//            //e.
//            
//            //e.getCause().printStackTrace();
//            
//            fail(e.getMessage());
//        } finally {
//            try {
//                db.delete("mid", "1000");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    } 
//
//    private com.arexis.mugen.genotype.marker.MarkerRemoteHome lookupMarkerBean() {
//        try {
//            javax.naming.Context c = new javax.naming.InitialContext();
//            Object remote = c.lookup("java:comp/env/ejb/MarkerBean");
//            com.arexis.mugen.genotype.marker.MarkerRemoteHome rv = (com.arexis.mugen.genotype.marker.MarkerRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.genotype.marker.MarkerRemoteHome.class);
//            return rv;
//        }
//        catch(javax.naming.NamingException ne) {
//            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
//            throw new RuntimeException(ne);
//        }
//    }
//}
