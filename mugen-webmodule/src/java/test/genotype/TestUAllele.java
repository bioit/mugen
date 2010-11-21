/*
 * TestUAllele.java
 *
 * Created on June 27, 2005, 2:18 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package test.genotype;

import com.arexis.mugen.genotype.uallele.UAlleleRemoteHome;
import com.arexis.mugen.genotype.uallele.UAlleleRemote;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import test.SqlHelper;


/**
 *
 * @author lami
 */
public class TestUAllele extends ServletTestCase {
    
    public TestUAllele(String theName) {
        super(theName);
    }
    
    public static Test suite() {        
        return new TestSuite(TestUAllele.class);
    }
    

    
    public void testUAllele() {
        int uaid = 9999;
        int umid = 1001;
        
        String name = "Arne";        
        String comment = "Arnes comment";
        
        SqlHelper db = null;
        
        try {
            UAlleleRemoteHome umh = lookupUAlleleBean();
            UAlleleRemote prj = umh.create(uaid, umid, name, comment, null);           
            
            db = new SqlHelper("u_alleles", "uaid", "9999");

            /*
             * Check that create() inserted correct values
             */
            assertEquals(name, db.getString("name"));
            assertEquals(comment, db.getString("comm"));
            assertEquals(umid, db.getInt("umid"));
            assertEquals(uaid, db.getInt("uaid"));

            
            /*
             * Check values in bean
             */
            assertEquals(umid, prj.getUmid());
            assertEquals(name, prj.getName());
            assertEquals(comment, prj.getComm());
            assertEquals(uaid, prj.getUaid());
            
            /* Change parameter values
             */            
           comment = "New comment";
           name = "Agne";           
           umid = 1026;
            
            /*
             * Set the properties
             */
            prj.setComm(comment);
            prj.setName(name);
            prj.setUmid(umid);
            /*
             * Check values in db
             */
            assertEquals(name, db.getString("name"));
            assertEquals(comment, db.getString("comm"));
            assertEquals(umid, db.getInt("umid"));
            
            /*
             * Check values in bean
             */
            assertEquals(umid, prj.getUmid());
            assertEquals(name, prj.getName());            
            assertEquals(comment, prj.getComm());               
                                  
            /*
             * Remove test object
             */
            prj.remove();
            
            this.assertNull(db.getString("uaid"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //e.
            
            //e.getCause().printStackTrace();
            
            fail(e.getMessage());
        } finally {
            try {
                db.delete("uaid", "9999");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private com.arexis.mugen.genotype.uallele.UAlleleRemoteHome lookupUAlleleBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/UAlleleBean");
            com.arexis.mugen.genotype.uallele.UAlleleRemoteHome rv = (com.arexis.mugen.genotype.uallele.UAlleleRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.genotype.uallele.UAlleleRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }
}
