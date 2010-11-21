///*
// * TestAllele.java
// *
// * Created on June 27, 2005, 3:32 PM
// *
// * To change this template, choose Tools | Options and locate the template under
// * the Source Creation and Management node. Right-click the template and choose
// * Open. You can then make changes to the template in the Source Editor.
// */
//
//package test.genotype;
//import com.arexis.mugen.genotype.allele.AlleleRemote;
//import com.arexis.mugen.genotype.allele.AlleleRemoteHome;
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
//public class TestAllele extends ServletTestCase {
//    
//    public TestAllele(String theName) {
//        super(theName);
//    }
//    
//    public static Test suite() {
//        
//        return new TestSuite(TestAllele.class);
//    }    
//    
//    /**
//     * Test a lallele bean, create a new bean. Set and get functions and
//     * verify against the database
//     */
//    public void testAllele() {     
//        int aid = 1004;
//        int mid = 1;
//        String name = "Eagle";
//        String comm = "comment1233";
//        
//        SqlHelper db = null;
//        SqlHelper dbDep = null;
//        
//        try {
//            db = new SqlHelper("alleles", "aid", ""+aid);
//            
//            /*
//             * Generate dependent data
//             */
//            dbDep = new SqlHelper("markers", "mid", "1");
//            String sql1 = "insert into markers (mid, name, alias," +
//                    " comm, suid, cid, p1, p2, position,id,ts) values (1, 'name', 'alias'," +
//                    "'comm', 1001, 1026, 'p1', 'p2', 0.0, 1, '2005-06-12')";
//            dbDep.createDependentObject(sql1);
//                      
//            
//            AlleleRemoteHome home = lookupAlleleBean();
//            AlleleRemote prj = home.create(aid, mid, name, comm, null);
//            /*
//             * Check that create() inserted correct values
//             */
//            assertEquals(name, db.getString("name"));
//            assertEquals(comm, db.getString("comm"));
//            assertEquals(mid, db.getInt("mid"));            
//            /*
//             * Check values in bean
//             */
//            assertEquals(name, prj.getName());
//            assertEquals(comm, prj.getComm());            
//            assertEquals(mid, prj.getMid());            
//            
//            /* Change parameter values
//             */            
//            name = "Crow";
//            comm = "comment12443"; 
//            
//            /*
//             * Set the properties
//             */
//            prj.setName(name);
//            prj.setComm(comm);
//            prj.setMid(mid);
//            /*
//             * Check values in db
//             */
//            assertEquals(name, db.getString("name"));
//            assertEquals(comm, db.getString("comm"));  
//            assertEquals(mid, db.getInt("mid"));
//            
//            /*
//             * Check values in bean
//             */
//            assertEquals(name, prj.getName());
//            assertEquals(comm, prj.getComm()); 
//            assertEquals(mid, prj.getMid()); 
//                                  
//            /*
//             * Remove test object
//             */
//            prj.remove();
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
//                db.delete("aid", "1004");
//                dbDep.delete("mid", "1");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        
//    }    
//
//    private com.arexis.mugen.genotype.allele.AlleleRemoteHome lookupAlleleBean() {
//        try {
//            javax.naming.Context c = new javax.naming.InitialContext();
//            Object remote = c.lookup("java:comp/env/ejb/AlleleBean");
//            com.arexis.mugen.genotype.allele.AlleleRemoteHome rv = (com.arexis.mugen.genotype.allele.AlleleRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.genotype.allele.AlleleRemoteHome.class);
//            return rv;
//        }
//        catch(javax.naming.NamingException ne) {
//            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
//            throw new RuntimeException(ne);
//        }
//    }
//}
