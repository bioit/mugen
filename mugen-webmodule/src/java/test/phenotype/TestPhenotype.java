/*
 * TestPhenotype.java
 *
 * Created on June 27, 2005, 3:40 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package test.phenotype;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.samplingunit.individual.IndividualRemote;
import com.arexis.mugen.samplingunit.individual.IndividualRemoteHome;
import com.arexis.mugen.phenotype.variable.VariableRemote;
import com.arexis.mugen.phenotype.variable.VariableRemoteHome;
import com.arexis.mugen.phenotype.phenotype.PhenotypeRemote;
import com.arexis.mugen.phenotype.phenotype.PhenotypeRemoteHome;


import org.apache.cactus.ServletTestCase;
import test.SqlHelperTwoKeys;

/**
 *
 * @author heto
 */
public class TestPhenotype extends ServletTestCase {
    
    /** Creates a new instance of TestPhenotype */
    public TestPhenotype(String name) {
        super(name);
    }
    
    public void testPhenotypeSet() {
        int suid = 1032;
        int iid = 11894;
        int vid = 1435;
        String value = "x";
        String reference = "xx";
        String comm = "y";
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        SqlHelperTwoKeys db = null;
        
        MugenCaller caller = new MugenCaller();
        caller.setId(1001);
        caller.setPid(1001);
        
        try {
            
            SamplingUnitRemoteHome sh = lookupSamplingUnitBean();
            SamplingUnitRemote sr = sh.findByPrimaryKey(new Integer(suid));
            
            VariableRemoteHome vsh = lookupVariableBean();
            VariableRemote vr = vsh.findByPrimaryKey(new Integer(vid));
            
            IndividualRemoteHome ih = lookupIndividualBean();
            IndividualRemote ir = ih.findByPrimaryKey(new Integer(iid));
            
            PhenotypeRemoteHome ph = lookupPhenotypeBean();
            PhenotypeRemote pr = ph.create(ir.getIid(), vr, sr, value, reference, comm, date, caller);
            db = new SqlHelperTwoKeys("phenotypes", "vid", "iid", ""+vid, ""+iid);
           /*
            * Check that create() inserted correct values
            */
            assertEquals(comm, db.getString("comm"));
            assertEquals(value, db.getString("value"));
            assertEquals(reference, db.getString("reference"));
            assertEquals(vid, db.getInt("vid"));
            assertEquals(suid, db.getInt("suid"));
            assertEquals(iid, db.getInt("iid"));
            //assertEquals(date, db.getDate("date_"));
            /*
             * Check values in bean
             */
            assertEquals(value, pr.getValue());
            assertEquals(comm, pr.getComm());
            assertEquals(vid, pr.getVariable().getVid());
            assertEquals(reference, pr.getReference());
            assertEquals(suid, pr.getSamplingUnit().getSuid());
            assertEquals(iid, pr.getIndividual().getIid());
            //assertEquals(date, pr.getDate());
            
            /* Change parameter values
             */
            suid = 1001;
            value = "y";
            reference = "yy";
            comm = "x";

            
            /*
             * Set the properties
             */            
            pr.setComm(comm);
            pr.setSuid(suid);
            pr.setValue(value);
            pr.setReference(reference);
            /*
             * Check values in db
             */
            assertEquals(comm, db.getString("comm"));
            assertEquals(value, db.getString("value"));
            assertEquals(reference, db.getString("reference"));
            assertEquals(vid, db.getInt("vid"));
            assertEquals(suid, db.getInt("suid"));
            assertEquals(iid, db.getInt("iid"));
            //assertEquals(date, db.getDate("date_"));
            
            /*
             * Check values in bean
             */
            assertEquals(value, pr.getValue());
            assertEquals(comm, pr.getComm());
            assertEquals(vid, pr.getVariable().getVid());
            assertEquals(reference, pr.getReference());
            assertEquals(suid, pr.getSamplingUnit().getSuid());
            assertEquals(iid, pr.getIndividual().getIid());
            //assertEquals(date, pr.getDate());
            
            /*
             * Remove test object
             */
            pr.remove();
            this.assertNull(db.getString("vid"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //e.
            
            //e.getCause().printStackTrace();
            
            fail(e.getMessage());
        } finally {
            try {
                db.delete("vid", "1", "iid", "123");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
    private com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome lookupSamplingUnitBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/SamplingUnitBean");
            com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome rv = (com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome.class);
            return rv;
        } catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }
    

    
    private com.arexis.mugen.samplingunit.individual.IndividualRemoteHome lookupIndividualBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/IndividualBean");
            com.arexis.mugen.samplingunit.individual.IndividualRemoteHome rv = (com.arexis.mugen.samplingunit.individual.IndividualRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.samplingunit.individual.IndividualRemoteHome.class);
            return rv;
        } catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }

    private com.arexis.mugen.phenotype.phenotype.PhenotypeRemoteHome lookupPhenotypeBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/PhenotypeBean");
            com.arexis.mugen.phenotype.phenotype.PhenotypeRemoteHome rv = (com.arexis.mugen.phenotype.phenotype.PhenotypeRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.phenotype.phenotype.PhenotypeRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }

    private com.arexis.mugen.phenotype.variable.VariableRemoteHome lookupVariableBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/VariableBean");
            com.arexis.mugen.phenotype.variable.VariableRemoteHome rv = (com.arexis.mugen.phenotype.variable.VariableRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.phenotype.variable.VariableRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }
    
}
