/*
 * TestProject.java
 *
 * Created on June 21, 2005, 9:14 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package test.project;

import com.arexis.arxframe.Caller;
import com.arexis.mugen.exceptions.LoginException;
import com.arexis.mugen.id.IdGenerator;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.id.PostgresId;
import com.arexis.mugen.project.privilege.PrivilegeRemote;
import com.arexis.mugen.project.privilege.PrivilegeRemoteHome;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.project.ProjectRemoteHome;
import com.arexis.mugen.project.projectmanager.ProjectManagerRemote;
import com.arexis.mugen.project.role.RoleRemote;
import com.arexis.mugen.project.role.RoleRemoteHome;
import com.arexis.mugen.project.securityprinciple.SecurityPrincipleRemote;
import com.arexis.mugen.project.securityprinciple.SecurityPrincipleRemoteHome;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import javax.ejb.EJBException;
import javax.naming.Context;
import org.apache.cactus.ServletTestCase;
import test.SqlHelper;


/**
 *
 * @author heto
 */
public class TestProject extends ServletTestCase {
    
    MugenCaller caller = new MugenCaller();
    
    /** Creates a new instance of TestProject */
    public TestProject() 
    {
    }
    
    public TestProject(String theName)
    {
        super(theName);
        
        caller.setName("Testuser2");
        caller.setUsr("admin");
        caller.setId(1001);
        caller.setPid(1001);
    }
    
    public void atestSecurityPrincipleFindByProject()
    {
        try
        {
            SecurityPrincipleRemoteHome sph = lookupSecurityPrincipleBean();
            
            Collection arr = sph.findByProject(1020);
            
            Iterator itr = arr.iterator();
            while (itr.hasNext())
            {
                SecurityPrincipleRemote sec = (SecurityPrincipleRemote)itr.next();
                
                System.err.print(sec.getPid()+",");
               
                
                if (sec.getPid() != 1020)
                    throw new EJBException("Pid mismatch");
            }
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    public void atestSecurityPrinciple()
    {
        
        try
        {
            ProjectRemote prj = null;
            int pid=10000;
            
            System.err.println("find User heto:");
            UserRemoteHome usrHome = lookupUserBean();
            UserRemote user = usrHome.findByPrimaryKey(new Integer(1001));
            
            System.err.println("find role 1021");
            RoleRemoteHome roleHome = lookupRoleBean();
            RoleRemote role = roleHome.findByPrimaryKey(new Integer(1021));
            
            
            
            System.err.println("Create Security principle:");
            SecurityPrincipleRemoteHome secHome = lookupSecurityPrincipleBean();
            SecurityPrincipleRemote sp = secHome.create(prj, user, role);
                
            
            System.err.println("Loop");
            Collection arr  = prj.getSecurityPrinciples();
            Iterator itr = arr.iterator();
            while (itr.hasNext())
            {
                SecurityPrincipleRemote sec = (SecurityPrincipleRemote)itr.next();
               
                System.err.print(sec.getPid()+",");
                if (sec.getPid() != pid)
                    throw new EJBException("Pid mismatch");
            }
            
            System.err.println("Remove security principle");
            sp.remove();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void testProjectManager()
    {
        try
        {
            ProjectManagerRemote pm = lookupProjectManagerBean();
            Caller up = pm.login("heto", "test1");
            
            assertEquals("heto", up.getUsr());
            
            
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }
    
    public void testGetNextId()
    {
        try
        {
            
            
            javax.sql.DataSource ds = null;
            
            Properties p = new Properties();
            p.put(Context.SECURITY_PRINCIPAL, "123");
            p.put(Context.SECURITY_CREDENTIALS, "credent");
            
            javax.naming.Context c = new javax.naming.InitialContext(p);
            ds = (javax.sql.DataSource)c.lookup("jdbc/mugen");
            Connection conn = ds.getConnection();
            
            /*
            String test = (String)c.lookup("test");
            
            
            assertEquals("com.arexis.mugen.id.PostgresId", test);
             */
            
            IdGenerator id = new PostgresId();
            
            int pid = id.getNextId(conn, "Projects_seq");
            conn.close();
            
            String name = "prjjjj";
            String comm = "lkdxj";
            
            
            
            ProjectRemoteHome pHome = lookupProjectBean();
            ProjectRemote prj = pHome.create(pid, name, comm, "E", caller);
            
            MugenCaller usr = new MugenCaller();
            usr.setName("Tobias");
            usr.setUsr("1234");
            prj.setCaller(usr);
            
            String str = prj.getCaller();
            
            
            prj.setComm("new comment");
            
            
            prj.remove();
            
            
            
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }
    
    public void testProject()
    {
        int pid = 10000;
        String name = "kdflkdflködsk";
        String comm = "comment1233";
        String status = "E";
        
        //Verifier v = null;
        SqlHelper db = null;
        
        try
        {
            db = new SqlHelper("projects", "pid", "10000");
            
            ProjectRemoteHome pHome = lookupProjectBean();
            ProjectRemote prj = pHome.create(pid, name, comm, status, caller);
            
            assertEquals("10000",db.getString("pid"));
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(status, db.getString("status"));
            
            /*
             * Check values in bean
             */
            assertEquals(pid, prj.getPid());
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());
            assertEquals(status, prj.getStatus());
            
            /*
             * Set the properties 
             */
            name = "123";
            comm = "321";
            status = "D";
            prj.setName(name);
            prj.setComm(comm);
            prj.setStatus(status);
            
            /*
             * Check values in db
             */
            assertEquals("10000",db.getString("pid"));
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            assertEquals(status, db.getString("status"));
            
            /*
             * Check values in bean
             */
            assertEquals(pid, prj.getPid());
            assertEquals(name, prj.getName());
            assertEquals(comm, prj.getComm());
            assertEquals(status, prj.getStatus());
            
            /* 
             * Change status to E and check the result
             */
            prj.setStatus("E");
            assertEquals("E", db.getString("status"));

            prj.disable();
            assertEquals("D", db.getString("status"));
            
            prj.enable();
            assertEquals("E", db.getString("status"));
            
            
            /*
             * Remove test object
             */
            prj.remove();
            this.assertNull(db.getString("pid"));
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
            //e.
            
            //e.getCause().printStackTrace();
            
            fail(e.getMessage());
        }
        finally
        {
            try
            {
                System.err.println("Delete project pid=10000");
                db.delete("pid", "10000");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
    }
    
    public void testUser()
    {
        int id = 10000;
        String usr = "xyzå";
        String pwd = "123f";
        String name = "TestUserUnitTesting";
        String status = "E";
        
        SqlHelper db = null;

        UserRemote userBean = null;
        try
        {
            db = new SqlHelper("users", "id", "10000");
            
            UserRemoteHome home = lookupUserBean();
            
            try
            {
                userBean = home.findByPrimaryKey(new Integer(id));
            }
            catch (Exception e)
            {
                userBean = home.create(id, usr, pwd, name, status);
            }
            
            assertEquals("10000", db.getString("id"));
            assertEquals(usr, db.getString("usr"));
            assertEquals(pwd, db.getString("pwd"));
            assertEquals(name, db.getString("name"));
            assertEquals(status, db.getString("status"));
            
            usr = "kdljf";
            pwd = "jkdj";
            name = "sklöjf";
            status = "D";
            
            userBean.setUsr(usr);
            userBean.setPwd(pwd);
            userBean.setName(name);
            userBean.setStatus(status);
            
            assertEquals(usr, db.getString("usr"));
            assertEquals(pwd, db.getString("pwd"));
            assertEquals(name, db.getString("name"));
            assertEquals(status, db.getString("status"));
            
           
            assertEquals("10000", String.valueOf(userBean.getId()));
            assertEquals(usr, userBean.getUsr());
            assertEquals(pwd, userBean.getPwd());
            assertEquals(name, userBean.getName());
            assertEquals(status, userBean.getStatus());
            
            
            userBean.remove();
            assertEquals("D",db.getString("status"));
              
              
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail(e.getMessage());
        }
        finally
        {
            try
            {
                // Delete the user object from db
                db.delete("id", "10000");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void testRole()
    {
        int rid = 10000;
        int pid = 1021;
        String name = "role name";
        String comm = "this is a comment";
        
        SqlHelper db = null;
    
        try
        {
            db = new SqlHelper("roles_", "rid", "10000");
                   
            ProjectRemoteHome pHome = lookupProjectBean();
            ProjectRemote p = pHome.findByPrimaryKey(new Integer(pid), caller);
            
            RoleRemoteHome home = lookupRoleBean();
            RoleRemote role = home.create(rid, p, name, comm);
            
            /* check db values */
            assertEquals("10000", db.getString("rid"));
            assertEquals("1021", db.getString("pid"));
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            
            /* Check bean values */
            assertEquals(10000, role.getRid());
            assertEquals(1021, role.getPid());
            assertEquals(name, role.getName());
            assertEquals(comm, role.getComm());
            
            
            /*
             * Set new values
            */
            name = "ljkxxfdjlkfdk";
            comm = "kljdfl";
            role.setName(name);
            role.setComm(comm);
            
            /* check db values */
            assertEquals("10000", db.getString("rid"));
            assertEquals("1021", db.getString("pid"));
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            
            /* Check bean values */
            assertEquals(10000, role.getRid());
            assertEquals(1021, role.getPid());
            assertEquals(name, role.getName());
            assertEquals(comm, role.getComm());
            
            
            role.remove();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail(e.getMessage());
        }
        finally
        {
            try
            {
                db.delete("rid", "10000");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void testRoleSetAndGetPrivileges()
    {
        int rid = 10000;
        int pid = 1021;
        String name = "role name";
        String comm = "this is a comment";
        
        try
        {
        
            RoleRemoteHome home = lookupRoleBean();
            RoleRemote role = home.findByPrimaryKey(new Integer(rid));
            
            
            PrivilegeRemoteHome privHome = lookupPrivilegeBean();
            PrivilegeRemote priv = privHome.findByPrimaryKey(new Integer(1));
            
            role.addPrivilege(priv);
            
            Collection arr = role.getPrivileges();
            Iterator itr = arr.iterator();
            
            int i=0;
            while (itr.hasNext())
            {
                PrivilegeRemote p = (PrivilegeRemote)itr.next();
                i++;
                
                if (i>1)
                    throw new EJBException("More than one privilege");
                
                if (p.getPrid() != 1)
                    throw new EJBException("Pid mismatch");
                
            }
        }
        catch (Exception e)
        {
            
        }
        
    }
    
    public void testPrivilege()
    {
        int prid = 10000;
        String name = "ldfjldj";
        String comm = "dskljlfjdsjk";
     
        SqlHelper db = null;
        
        try
        {
            db = new SqlHelper("privileges_", "prid", "10000");
            
            PrivilegeRemoteHome home = lookupPrivilegeBean();
            PrivilegeRemote priv = home.create(prid, name, comm);
            
            assertEquals(String.valueOf(prid), db.getString("prid"));
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            
            assertEquals(prid, priv.getPrid());
            assertEquals(name, priv.getName());
            assertEquals(comm, priv.getComm());
            
            /* Set new values */
            name = "däoixjf";
            comm = "ldosrlfjsw";
            priv.setName(name);
            priv.setComm(comm);
            
            assertEquals(String.valueOf(prid), db.getString("prid"));
            assertEquals(name, db.getString("name"));
            assertEquals(comm, db.getString("comm"));
            
            assertEquals(prid, priv.getPrid());
            assertEquals(name, priv.getName());
            assertEquals(comm, priv.getComm());
           
            
            priv.remove();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail(e.getMessage());
        }
        finally
        {
            try
            {
                db.delete("prid", "10000");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void testSecurityPrincipleBean()
    {
        
        try
        {
            
            /** Dependent beans */
            ProjectRemoteHome pHome = lookupProjectBean();
            ProjectRemote p = pHome.create(10000, "123", "comment", "E", caller);
            
            UserRemoteHome uHome = lookupUserBean();
            UserRemote u = uHome.create(10000, "123", "comment", "sdf", "E");
            
            RoleRemoteHome rHome = lookupRoleBean();
            RoleRemote r = rHome.create(10000, p, "123", "comm");
            
            SecurityPrincipleRemoteHome home = lookupSecurityPrincipleBean();
            SecurityPrincipleRemote sec = home.create(p, u, r);
            
            assertEquals(p.getPid(), sec.getPid());
            assertEquals(u.getId(), sec.getId());
            assertEquals(r.getRid(), sec.getRid());
            
            sec.getProject().getName();
            
            
            sec.remove();
            
            p.remove();
            u.remove();
            r.remove();
        }
        catch (Exception e)
        {
            //throw e;
            e.printStackTrace();
            fail(e.getMessage());
        }
        finally
        {
            try
            {
                SqlHelper prj = new SqlHelper("projects", "pid", "10000");
                prj.delete("pid", "10000");
                
                SqlHelper usrDb = new SqlHelper("users", "id", "10000");
                usrDb.delete("id", "10000");
                
                SqlHelper roleDb = new SqlHelper("roles_", "rid", "10000");
                roleDb.delete("rid", "10000");
                
                //db.delete("r_prj_rol", "10000");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
  private com.arexis.mugen.project.securityprinciple.SecurityPrincipleRemoteHome lookupSecurityPrincipleBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/SecurityPrincipleBean");
            com.arexis.mugen.project.securityprinciple.SecurityPrincipleRemoteHome rv = (com.arexis.mugen.project.securityprinciple.SecurityPrincipleRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.project.securityprinciple.SecurityPrincipleRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }

    private com.arexis.mugen.project.project.ProjectRemoteHome lookupProjectBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/ProjectBean");
            com.arexis.mugen.project.project.ProjectRemoteHome rv = (com.arexis.mugen.project.project.ProjectRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.project.project.ProjectRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }

    private com.arexis.mugen.project.user.UserRemoteHome lookupUserBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/UserBean");
            com.arexis.mugen.project.user.UserRemoteHome rv = (com.arexis.mugen.project.user.UserRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.project.user.UserRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }

    private com.arexis.mugen.project.role.RoleRemoteHome lookupRoleBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/RoleBean");
            com.arexis.mugen.project.role.RoleRemoteHome rv = (com.arexis.mugen.project.role.RoleRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.project.role.RoleRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }

    private com.arexis.mugen.project.privilege.PrivilegeRemoteHome lookupPrivilegeBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/PrivilegeBean");
            com.arexis.mugen.project.privilege.PrivilegeRemoteHome rv = (com.arexis.mugen.project.privilege.PrivilegeRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.project.privilege.PrivilegeRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }

    private com.arexis.mugen.project.projectmanager.ProjectManagerRemote lookupProjectManagerBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/ProjectManagerBean");
            com.arexis.mugen.project.projectmanager.ProjectManagerRemoteHome rv = (com.arexis.mugen.project.projectmanager.ProjectManagerRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.project.projectmanager.ProjectManagerRemoteHome.class);
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
