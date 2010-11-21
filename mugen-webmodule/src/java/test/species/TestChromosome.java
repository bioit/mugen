///*
// * TestChromosome.java
// *
// * Created on June 16, 2005, 1:03 PM
// *
// * To change this template, choose Tools | Options and locate the template under
// * the Source Creation and Management node. Right-click the template and choose
// * Open. You can then make changes to the template in the Source Editor.
// */
//
//package test.species;
//
//import com.arexis.mugen.species.chromosome.ChromosomeRemoteHome;
//import com.arexis.mugen.species.chromosome.ChromosomeRemote;
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
//public class TestChromosome extends ServletTestCase {
//    
//    public TestChromosome(String theName) {
//        super(theName);
//    }
//    
//    public static Test suite() {        
//        return new TestSuite(TestChromosome.class);
//    }
//    
//    /**
//     * Test a chromosome bean, create a new bean. Set and get functions and
//     * verify against the database
//     */
//    public void testChromosome() {     
//        int cid = 1212;
//        int sid = 1001;
//        String name = "WW";
//        String comm = "comment1233";
//        
//        SqlHelper db = null;
//        
//        try {
//            db = new SqlHelper("chromosomes", "cid", ""+cid);
//            
//            ChromosomeRemoteHome home = lookupChromosomeBean();
//            ChromosomeRemote prj = home.create(cid, name, comm, sid);
//            /*
//             * Check that create() inserted correct values
//             */
//            assertEquals(cid, db.getInt("cid"));
//            assertEquals(name, db.getString("name"));
//            assertEquals(comm, db.getString("comm"));
//            assertEquals(sid, db.getInt("sid"));            
//            /*
//             * Check values in bean
//             */
//            assertEquals(cid, prj.getCid());
//            assertEquals(name, prj.getName());
//            assertEquals(comm, prj.getComm());            
//            assertEquals(sid, prj.getSid());            
//            
//            /* Change parameter values
//             */            
//            sid = 1002;
//            name = "DD";
//            comm = "comment12443";         
//            
//            /*
//             * Set the properties
//             */
//            prj.setName(name);
//            prj.setComm(comm);
//            prj.setSid(sid);
//            /*
//             * Check values in db
//             */
//            assertEquals(cid, db.getInt("cid"));
//            assertEquals(name, db.getString("name"));
//            assertEquals(comm, db.getString("comm"));
//            assertEquals(sid, db.getInt("sid"));            
//            
//            /*
//             * Check values in bean
//             */
//            assertEquals(cid, prj.getCid());
//            assertEquals(name, prj.getName());
//            assertEquals(comm, prj.getComm());
//            assertEquals(sid, prj.getSid());            
//                                  
//            /*
//             * Remove test object
//             */
//            prj.remove();
//            this.assertNull(db.getString("cid"));
//        } catch (Exception e) {
//            e.printStackTrace(System.err);
//            //e.
//            
//            //e.getCause().printStackTrace();
//            
//            fail(e.getMessage());
//        } finally {
//            try {
//                db.delete("cid", "1212");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        
//    }
//
//    private com.arexis.mugen.species.chromosome.ChromosomeRemoteHome lookupChromosomeBean() {
//        try {
//            javax.naming.Context c = new javax.naming.InitialContext();
//            Object remote = c.lookup("java:comp/env/ejb/ChromosomeBean");
//            com.arexis.mugen.species.chromosome.ChromosomeRemoteHome rv = (com.arexis.mugen.species.chromosome.ChromosomeRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.species.chromosome.ChromosomeRemoteHome.class);
//            return rv;
//        }
//        catch(javax.naming.NamingException ne) {
//            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
//            throw new RuntimeException(ne);
//        }
//    }
//}
//
