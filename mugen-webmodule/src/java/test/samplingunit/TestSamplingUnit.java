/*
 * TestSamplingUnit.java
 *
 * Created on June 16, 2005, 1:03 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package test.samplingunit;
import com.arexis.arxframe.PageManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.samplingunit.grouping.GroupingRemote;
import com.arexis.mugen.samplingunit.individual.IndividualRemote;
import com.arexis.mugen.samplingunit.individual.IndividualRemoteHome;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.samplingunit.samplingunitmanager.IndividualDTO;
import com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitManagerRemote;
import com.arexis.mugen.species.species.SpeciesRemote;
import com.arexis.mugen.species.species.SpeciesRemoteHome;
import java.util.Collection;
import java.util.Iterator;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import test.SqlHelper;




/**
 * @author heto
 */
public class TestSamplingUnit extends ServletTestCase {
    
    
    
    /** A test sampling unit object */
    SamplingUnitRemote su;
    
    MugenCaller caller = null;
    
    public TestSamplingUnit(String theName)
    {
        super(theName);
        
        caller = new MugenCaller();
        caller.setId(1001);
        caller.setPid(1001);
    }
    
    
    public static Test suite()
    {
        return new TestSuite(TestSamplingUnit.class);
    }
    
    public void testIndividualManager()
    {
        /*int suid = 1001;
        try
        {
            IndividualManagerRemote indMgr = lookupIndividualManagerBean();
            Collection inds = indMgr.getIndividuals(suid, null);
            Iterator i = inds.iterator();
            while (i.hasNext())
            {
                IndividualDO ind = (IndividualDO)i.next();
                System.err.print(ind.getIdentity());
            }
            System.err.println("Done");
        }
        catch (Exception e)
        {
           fail(e.getMessage());
        }*/
    }
    
    public void testIndCreateRemoveCreate()
    {
        int suid = 1056;
        
        try
        {
            SamplingUnitManagerRemote indMgr = lookupSamplingUnitManagerBean();
            
            String identity = "i1";
            String alias = "";
            String sex = "U";
           
            int iid = indMgr.createNewIndividual(suid, caller, identity, alias, sex, 0, 0, "2006-11-16", "test");
            
            indMgr.removeIndividual(iid, caller);
            
            iid = indMgr.createNewIndividual(suid, caller, identity, alias, sex, 0, 0, "2006-11-16", "test");
            
            
            
        }
        catch (Exception e)
        {
           fail(e.getMessage());
        }
    }
    
    public void testIndividualManagerGetIndividualsInterval()
    {
        int suid = 1001;
        int start = 1;
        int stop = 10;
        try
        {
            SamplingUnitManagerRemote indMgr = lookupSamplingUnitManagerBean();
            
            PageManager pm = new PageManager();
            pm.setFirst();
            
            Collection inds = indMgr.getIndividuals(suid, pm, null, null);
            Iterator i = inds.iterator();
            while (i.hasNext())
            {
                IndividualDTO ind = (IndividualDTO)i.next();
                System.err.print(ind.getIdentity());
            }
            System.err.println("Done");
        }
        catch (Exception e)
        {
           fail(e.getMessage());
        }
    }
    
    public void testIndividualDO()
    {
        int suid = 1001;
        try
        {
            IndividualRemoteHome ih = lookupIndividualBean();
            
            IndividualRemote iii = ih.findByPrimaryKey(new Integer(7412));            
            IndividualRemote father = iii.getFather();
            
            assertNotNull(father);
            assertEquals(7405, father.getIid());
            
            iii = ih.findByPrimaryKey(new Integer(1312));
            father = iii.getFather();
            
            assertNull(father);
            
            //ParentDO parent = new ParentDO(father);
            
        }
        catch (Exception e)
        {
           fail(e.getMessage());
        }
    }

    public void testSamplingUnitGetGroupings()
    {
        
        
        try
        {
            SamplingUnitRemoteHome home = lookupSamplingUnitBean();
            SamplingUnitRemote s = home.findByPrimaryKey(new Integer(1001));
            
            Collection c = s.getGroupings();
            Iterator itr = c.iterator();
            
            while (itr.hasNext())
            {
                GroupingRemote grp = (GroupingRemote)itr.next();
                
                grp.getName();
            }
            
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
        finally
        {
            
        }
    }
    
    public void testSamplingUnit()
    {
        int sid = 1001;
        int suid = 10000;
        String name = "fdlkjlkj";
        String comm = "lfdljkfd";
        String status = "E";
        
        SamplingUnitRemote su = null;
        
        SqlHelper db = null;
        
        try
        {
            db = new SqlHelper("sampling_units", "suid", String.valueOf(suid));
            
            SpeciesRemoteHome srh = lookupSpeciesBean();
            SpeciesRemote species = srh.findByPrimaryKey(new Integer(sid));
            
            
            
            
            
            SamplingUnitRemoteHome home = lookupSamplingUnitBean();
            su = home.create(new Integer(suid), name, comm, species, caller);
            
            assertEquals(suid, su.getSuid());
            assertEquals(name, su.getName());
            assertEquals(comm, su.getComm());
            assertEquals(status, su.getStatus());
            assertEquals(sid, su.getSpecies().getSid());
            
            assertEquals(suid, db.getInt("suid"));
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(status, db.getString("status"));
            assertEquals(sid, db.getInt("sid"));
            
            
            
            name = "ljkdjflkkl";
            comm = "lkdjkljda4";
            status = "D";
            su.setName(name);
            su.setComm(comm);
            su.setStatus(status);
            
            assertEquals(suid, su.getSuid());
            assertEquals(name, su.getName());
            assertEquals(comm, su.getComm());
            assertEquals(status, su.getStatus());
            assertEquals(sid, su.getSpecies().getSid());
            
            assertEquals(String.valueOf(suid), db.getString("suid"));
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(status, db.getString("status"));
            assertEquals(sid, db.getInt("sid"));
            
            su.remove();
            assertNull(db.getString("suid"));
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
        finally
        {
            try
            {
                db.delete("suid", "10000");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    
    /*public void testGrouping()
    {
        int gsid = 10000;
        String name = "dlkjf";
        String comm = "jfkdjkldfj";
        
        SqlHelper db = null;
        
        try
        {
            db = new SqlHelper("groupings", "gsid", "10000");
            
            SamplingUnitRemoteHome sh = lookupSamplingUnitBean();
            SamplingUnitRemote s = sh.findByPrimaryKey(new Integer(1001), caller);
            
            GroupingRemoteHome home = lookupGroupingBean();
            GroupingRemote gr = home.create(gsid, name, s, new Caller());
            
            assertEquals(gsid, gr.getGsid());
            assertEquals(name, gr.getName());
            
            assertEquals(gsid, db.getInt("gsid"));
            assertEquals(name, db.getString("name"));
            
            gr.remove();
            assertNull(db.getString("gsid"));
            
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
        finally
        {
            try
            {
                db.delete("gsid", "10000");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }*/
    
    /*public void testGroup()
    {
        int gid = 10000;
        int gsid = 10000;
        String name = "dlkjf";
        String comm = "jfkdjkldfj";
        
        SqlHelper dbGrouping = null;
        SqlHelper dbGroup = null;
        
        try
        {
            dbGrouping = new SqlHelper("groupings", "gsid", "10000");
            dbGroup = new SqlHelper("groups", "gid", "10000");
            
            
            SamplingUnitRemoteHome sh = lookupSamplingUnitBean();
            SamplingUnitRemote s = sh.findByPrimaryKey(new Integer(1001), caller);
            
            GroupingRemoteHome gh = lookupGroupingBean();
            GroupingRemote grouping = gh.create(gsid, name, s, new Caller());
            
            GroupRemoteHome home = lookupGroupBean();
            GroupRemote gr = home.create(gid, name, grouping);
            
            assertEquals(gid, gr.getGid());
            assertEquals(name, gr.getName());
            
            assertEquals(gid, dbGroup.getInt("gid"));
            assertEquals(name, dbGroup.getString("name"));
            
            name = "doujcfmldj";
            comm = "dkjcmck";
            gr.setName(name);
            gr.setComm(comm);
            
            
            assertEquals(gid, gr.getGid());
            assertEquals(name, gr.getName());
            assertEquals(comm, gr.getComm());
            
            
            
            assertEquals(gid, dbGroup.getInt("gid"));
            assertEquals(name, dbGroup.getString("name"));
            assertEquals(comm, dbGroup.getString("comm"));
            
            
            gr.remove();
            assertNull(dbGroup.getString("gid"));
            
            grouping.remove();
            assertNull(dbGrouping.getString("gsid"));
            
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
        finally
        {
            try
            {
                dbGroup.delete("gid","10000");
                dbGrouping.delete("gsid", "10000");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void testIndividual()
    {
        int iid = 10000;
        String identity = "fj,kjdfj";
        String alias = "kldjfds";
        
        String sex = "U";
        String birthDate = "20050427";
        String status = "E";
        
        //suid
        //father
        //mother
        
        int id = 1001;
        String comm = "jdslkjlfj";
        
        SqlHelper db = null;
        
        
        try
        {
            db = new SqlHelper("individuals", "iid", "10000");
            
            
            SamplingUnitRemoteHome sh = lookupSamplingUnitBean();
            SamplingUnitRemote samplingUnit = sh.findByPrimaryKey(new Integer(1001), caller);
            
            IndividualRemoteHome indHome = lookupIndividualBean();
            IndividualRemote ind = indHome.create(iid, identity, samplingUnit);
            
            
            assertEquals(iid, ind.getIid());
            assertEquals(identity, ind.getIdentity());
            assertEquals(sex, ind.getSex());
            assertEquals(status, ind.getStatus());
            
            assertEquals(iid, db.getInt("iid"));
            assertEquals(identity, db.getString("identity"));
            assertEquals(sex,db.getString("sex"));
            assertEquals(status, db.getString("status"));
            
            identity = "jkdj";
            comm = "jdkjdk";
            sex = "F";
            status = "D";
            ind.setComm(comm);
            ind.setIdentity(identity);
            ind.setAlias(alias);
            ind.setSex(sex);
            ind.setStatus(status);
            
            assertEquals(iid, ind.getIid());
            assertEquals(identity, ind.getIdentity());
            assertEquals(sex, ind.getSex());
            assertEquals(status, ind.getStatus());
            assertEquals(comm, ind.getComm());
            assertEquals(alias, ind.getAlias());
            
            assertEquals(iid, db.getInt("iid"));
            assertEquals(identity, db.getString("identity"));
            assertEquals(sex,db.getString("sex"));
            assertEquals(status, db.getString("status"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(alias, db.getString("alias"));
            
            
            
            
            
            
            
            
            ind.remove();
            assertNull(db.getString("iid"));
             
        }
        catch (Exception e)
        {
            try
            {
                db.delete("iid", "10000");
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
            fail(e.getMessage());
        }
    }
    
    public void testRelations()
    {
        int gsid = 10000;
        String name = "dlkjf";
        String comm = "jfkdjkldfj";
        
        int gid =10000;
        
        SqlHelper db = null;
        SqlHelper dbInd = null;
        
        try
        {
            db = new SqlHelper("groupings", "gsid", "10000");
            dbInd = new SqlHelper("individuals", "iid", "10000");
            
            
            SamplingUnitRemoteHome sh = lookupSamplingUnitBean();
            SamplingUnitRemote s = sh.findByPrimaryKey(new Integer(1001), caller);
            
            GroupingRemoteHome home = lookupGroupingBean();
            GroupingRemote gr = home.create(gsid, name, s, new Caller());
            
            GroupRemoteHome groupHome = lookupGroupBean();
            GroupRemote g = groupHome.create(gid, name, gr);
            
            IndividualRemoteHome indHome = lookupIndividualBean();
            IndividualRemote ind1 = indHome.create(10000, "ind1", s);
            IndividualRemote ind2 = indHome.create(10001, "ind2", s);
            
            g.addIndividual(ind1);
            g.addIndividual(ind2);
            
            
            Collection groupings = s.getGroupings();
            Iterator iGroupings = groupings.iterator();
            while (iGroupings.hasNext())
            {
                GroupingRemote grouping = (GroupingRemote)iGroupings.next();
                Collection groups = grouping.getGroups();
                Iterator iGroups = groups.iterator();
                while (iGroups.hasNext())
                {
                    GroupRemote group = (GroupRemote)iGroups.next();
                    Collection individuals = group.getIndividuals();
                    Iterator iIndividuals = individuals.iterator();
                    while (iIndividuals.hasNext())
                    {
                        IndividualRemote individual = (IndividualRemote)iIndividuals.next();
                        individual.getId();
                        
                        System.out.println("Ind="+individual.getIid());
                        
                        //assertEquals(s.getSuid(), )
                    }
                   
                }
            }
            
            ind1.remove();
            ind2.remove();
            
            g.remove();
            
            gr.remove();
            assertNull(db.getString("gsid"));
            
            
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
        finally
        {
            
            try
            {
                
                dbInd.delete("iid", "10000");
                dbInd.delete("iid", "10001");
                
                db.delete("gsid", "10000");
                
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }*/
    
    

    private com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome lookupSamplingUnitBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/SamplingUnitBean");
            SamplingUnitRemoteHome rv = (SamplingUnitRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, SamplingUnitRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }

    private com.arexis.mugen.species.species.SpeciesRemoteHome lookupSpeciesBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/SpeciesBean");
            com.arexis.mugen.species.species.SpeciesRemoteHome rv = (com.arexis.mugen.species.species.SpeciesRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.species.species.SpeciesRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
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

    private com.arexis.mugen.samplingunit.grouping.GroupingRemoteHome lookupGroupingBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/GroupingBean");
            com.arexis.mugen.samplingunit.grouping.GroupingRemoteHome rv = (com.arexis.mugen.samplingunit.grouping.GroupingRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.samplingunit.grouping.GroupingRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }

    private com.arexis.mugen.samplingunit.group.GroupRemoteHome lookupGroupBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/GroupBean");
            com.arexis.mugen.samplingunit.group.GroupRemoteHome rv = (com.arexis.mugen.samplingunit.group.GroupRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.samplingunit.group.GroupRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
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
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }

    private com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitManagerRemote lookupSamplingUnitManagerBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/SamplingUnitManagerBean");
            com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitManagerRemoteHome rv = (com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitManagerRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitManagerRemoteHome.class);
            return rv.create();
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
        catch(javax.ejb.CreateException ce) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ce);
            throw new RuntimeException(ce);
        }
        catch(java.rmi.RemoteException re) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,re);
            throw new RuntimeException(re);
        }
    }

}
