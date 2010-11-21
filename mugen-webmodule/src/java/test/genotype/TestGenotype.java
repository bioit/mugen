///*
// * TestGenotype.java
// *
// * Created on June 28, 2005, 12:51 PM
// *
// * To change this template, choose Tools | Options and locate the template under
// * the Source Creation and Management node. Right-click the template and choose
// * Open. You can then make changes to the template in the Source Editor.
// */
//package test.genotype;
//
//import com.arexis.mugen.genotype.genotype.GenotypeRemoteHome;
//import com.arexis.mugen.genotype.genotype.GenotypeRemote;
//
//import com.arexis.mugen.genotype.marker.MarkerRemoteHome;
//import com.arexis.mugen.genotype.marker.MarkerRemote;
//
//import com.arexis.mugen.samplingunit.individual.IndividualRemoteHome;
//import com.arexis.mugen.samplingunit.individual.IndividualRemote;
//
//import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
//import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
//
//import com.arexis.mugen.species.species.SpeciesRemoteHome;
//import com.arexis.mugen.species.species.SpeciesRemote;
//
//import junit.framework.Test;
//import junit.framework.TestSuite;
//import org.apache.cactus.ServletTestCase;
//import test.SqlHelperTwoKeys;
//
//
///**
// *
// * @author lami
// */
//public class TestGenotype extends ServletTestCase {
//    
//    public TestGenotype(String theName) {
//        super(theName);
//    }
//    
//    public static Test suite() {        
//        return new TestSuite(TestGenotype.class);
//    }
//    
//
//    
//    public void testGenotype() {
//        String raw1 = "raw1";
//        String raw2 = "raw2";
//        String comm = "comm";
//        String reference = "ref";
//        int level = 1;
//        int aid1 = 1;
//        int aid2 = 1;
//        int suid = 1;     
//        
//        SqlHelperTwoKeys db = null;
//        
//        try {
//            db = new SqlHelperTwoKeys("genotypes", "mid", "iid", "13432", "16199");
//            
//            MarkerRemoteHome mh = lookupMarkerBean();                        
//            MarkerRemote mr = mh.findByPrimaryKey(new Integer(13432));         
//            
//            IndividualRemoteHome ih = lookupIndividualBean();                        
//            IndividualRemote ir = ih.findByPrimaryKey(new Integer(16199));          
//            
//            GenotypeRemoteHome gh = lookupGenotypeBean();
//            GenotypeRemote gr = gh.create(ir, mr, raw1, raw2, comm, reference, level, aid1, aid2, suid, null);
//            /*
//             * Check that create() inserted correct values
//             */
//            assertEquals(raw1, db.getString("raw1"));
//            assertEquals(raw2, db.getString("raw2"));
//            assertEquals(reference, db.getString("reference"));
//            assertEquals(comm, db.getString("comm"));
//            assertEquals(level, db.getInt("level_"));
//            assertEquals(aid1, db.getInt("aid1"));
//            assertEquals(aid2, db.getInt("aid2"));
//            assertEquals(suid, db.getInt("suid"));
//            
//            /*
//             * Check values in bean
//             */
//            assertEquals(raw1, gr.getRaw1());
//            assertEquals(raw2, gr.getRaw2());
//            assertEquals(reference, gr.getReference());
//            assertEquals(comm, gr.getComm());
//            assertEquals(level, gr.getLevel());
//            assertEquals(aid1, gr.getAid1());
//            assertEquals(aid2, gr.getAid2());
//            assertEquals(suid, gr.getSamplingUnit().getSuid());
//            
//            /* Change parameter values
//             */            
//            raw1 = "raw1";
//            raw2 = "raw2";
//            comm = "comm";
//            reference = "ref";
//            level = 1;
//            aid1 = 1;
//            aid2 = 1;
//            suid = 1;  
//            
//            /*
//             * Set the properties
//             */
//            gr.setRaw1(raw1);
//            gr.setRaw2(raw2);
//            gr.setAid1(aid1);
//            gr.setAid2(aid2);
//            gr.setReference(reference);
//            gr.setComm(comm);
//            gr.setLevel(level);
//            gr.setSuid(suid);
//            /*
//             * Check values in db
//             */
//            assertEquals(raw1, db.getString("raw1"));
//            assertEquals(raw2, db.getString("raw2"));
//            assertEquals(reference, db.getString("reference"));
//            assertEquals(comm, db.getString("comm"));
//            assertEquals(level, db.getInt("level_"));
//            assertEquals(aid1, db.getInt("aid1"));
//            assertEquals(aid2, db.getInt("aid2"));
//            assertEquals(suid, db.getInt("suid"));       
//            
//            /*
//             * Check values in bean
//             */
//            assertEquals(raw1, gr.getRaw1());
//            assertEquals(raw2, gr.getRaw2());
//            assertEquals(reference, gr.getReference());
//            assertEquals(comm, gr.getComm());
//            assertEquals(level, gr.getLevel());
//            assertEquals(aid1, gr.getAid1());
//            assertEquals(aid2, gr.getAid2());
//            assertEquals(suid, gr.getSamplingUnit().getSuid());         
//                                  
//            /*
//             * Remove test object
//             */
//            gr.remove();
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
//                db.delete("mid", "iid", "13432", "16199");
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
//
//    private com.arexis.mugen.samplingunit.individual.IndividualRemoteHome lookupIndividualBean() {
//        try {
//            javax.naming.Context c = new javax.naming.InitialContext();
//            Object remote = c.lookup("java:comp/env/ejb/IndividualBean");
//            com.arexis.mugen.samplingunit.individual.IndividualRemoteHome rv = (com.arexis.mugen.samplingunit.individual.IndividualRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.samplingunit.individual.IndividualRemoteHome.class);
//            return rv;
//        }
//        catch(javax.naming.NamingException ne) {
//            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
//            throw new RuntimeException(ne);
//        }
//    }
//
//    private com.arexis.mugen.genotype.genotype.GenotypeRemoteHome lookupGenotypeBean() {
//        try {
//            javax.naming.Context c = new javax.naming.InitialContext();
//            Object remote = c.lookup("java:comp/env/ejb/GenotypeBean");
//            com.arexis.mugen.genotype.genotype.GenotypeRemoteHome rv = (com.arexis.mugen.genotype.genotype.GenotypeRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.genotype.genotype.GenotypeRemoteHome.class);
//            return rv;
//        }
//        catch(javax.naming.NamingException ne) {
//            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
//            throw new RuntimeException(ne);
//        }
//    }
//
//    private com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome lookupSamplingUnitBean() {
//        try {
//            javax.naming.Context c = new javax.naming.InitialContext();
//            Object remote = c.lookup("java:comp/env/ejb/SamplingUnitBean");
//            com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome rv = (com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome.class);
//            return rv;
//        }
//        catch(javax.naming.NamingException ne) {
//            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
//            throw new RuntimeException(ne);
//        }
//    }
//
//    private com.arexis.mugen.species.species.SpeciesRemoteHome lookupSpeciesBean() {
//        try {
//            javax.naming.Context c = new javax.naming.InitialContext();
//            Object remote = c.lookup("java:comp/env/ejb/SpeciesBean");
//            com.arexis.mugen.species.species.SpeciesRemoteHome rv = (com.arexis.mugen.species.species.SpeciesRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.species.species.SpeciesRemoteHome.class);
//            return rv;
//        }
//        catch(javax.naming.NamingException ne) {
//            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
//            throw new RuntimeException(ne);
//        }
//    }
//    
//}
