/*
 * TestAllele.java
 *
 * Created on June 16, 2005, 1:03 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package test.species;
import com.arexis.mugen.species.lallele.LAlleleRemote;
import com.arexis.mugen.species.lallele.LAlleleRemoteHome;
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
public class TestLAllele extends ServletTestCase {
    
    public TestLAllele(String theName) {
        super(theName);
    }
    
    public static Test suite() {
        
        return new TestSuite(TestLAllele.class);
    }    
    
    /**
     * Test a lallele bean, create a new bean. Set and get functions and
     * verify against the database
     */
    public void testLAllele() {     
        int laid = 1004;
        int lmid = 1;
        String name = "Eagle";
        String comm = "comment1233";
        
        SqlHelper db = null;
        SqlHelper dbDep = null;
        
        try {
            db = new SqlHelper("l_alleles", "laid", ""+laid);
            
            /*
             * Generate dependent data
             */
            dbDep = new SqlHelper("l_markers", "lmid", ""+1);
            String sql1 = "insert into l_markers (lmid, name, alias," +
                    " comm, sid, cid, p1, p2) values (1, 'name', 'alias'," +
                    "'comm', 1001, 12, 'p1', 'p2')";
            dbDep.createDependentObject(sql1);
            
            String sql2 = "insert into l_markers (lmid, name, alias," +
                    " comm, sid, cid, p1, p2) values (2, 'name', 'alias'," +
                    "'comm', 1001, 12, 'p1', 'p2')";
            dbDep.createDependentObject(sql2);            
            
            LAlleleRemoteHome home = lookupLAlleleBean();
            LAlleleRemote prj = home.create(laid, name, comm, lmid);
            /*
             * Check that create() inserted correct values
             */
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(lmid, db.getInt("lmid"));            
            /*
             * Check values in bean
             */
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());            
            assertEquals(lmid, prj.getLMarker().getLmid());    
            
            
            /* Change parameter values
             */            
            name = "Crow";
            comm = "comment12443"; 
            lmid = 2;
            
            /*
             * Set the properties
             */
            prj.setName(name);
            prj.setComm(comm);
            prj.setLmid(lmid);
            /*
             * Check values in db
             */
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));  
            assertEquals(lmid, db.getInt("lmid"));
            
            /*
             * Check values in bean
             */
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm()); 
            assertEquals(lmid, prj.getLMarker().getLmid()); 
                                  
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
                db.delete("laid", "1004");
                dbDep.delete("lmid", "1");
                dbDep.delete("lmid", "2");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }    

    private com.arexis.mugen.species.lallele.LAlleleRemoteHome lookupLAlleleBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/LAlleleBean");
            com.arexis.mugen.species.lallele.LAlleleRemoteHome rv = (com.arexis.mugen.species.lallele.LAlleleRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.species.lallele.LAlleleRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }
}
