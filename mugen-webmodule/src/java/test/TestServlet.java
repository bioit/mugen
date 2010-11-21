/*
 * TestServlet.java
 *
 * Created on May 18, 2005, 11:10 AM
 */

package test;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.*;
import javax.servlet.http.*;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.privilege.PrivilegeRemote;
import com.arexis.mugen.project.privilege.PrivilegeRemoteHome;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.role.RoleRemote;
import com.arexis.mugen.project.securityprinciple.SecurityPrinciplePk;
import com.arexis.mugen.project.securityprinciple.SecurityPrincipleRemote;
import com.arexis.mugen.project.securityprinciple.SecurityPrincipleRemoteHome;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.species.species.SpeciesRemote;
import com.arexis.mugen.species.species.SpeciesRemoteHome;

/**
 *
 * @author heto
 * @version
 */
public class TestServlet extends HttpServlet {
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet TestServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet TestServlet at " + request.getContextPath () + "</h1>");
        out.println("</body>");
        out.println("</html>");
        
        try
        {
            
            com.arexis.mugen.project.user.UserRemoteHome userHome;
            userHome = lookupUserBean();
            
            out.println("Number of users= "+userHome.getNumberOfUsers());
            
            /*
            String id = (String)request.getParameter("id");
            
            //UserRemote usr = home.findByPrimaryKey(Integer.valueOf(id));            
            UserRemote usr = home.findByUsr("heto");
            //UserRemote usr = home.create(new Integer(3), "heto22", "1233", "Tobias Hermansson", "E");
            //usr.setName("Kalle Anka");
            out.println("Name="+usr.getName());
            //user.create(new Integer(1), "heto", "123", "Tobias Hermansson", "E");
            
            */
            
            
            UserRemote usr = userHome.findByPrimaryKey(new Integer(1));
            
            
            
            usr.setName("kalle");
            
            
            
            
            
            com.arexis.mugen.project.project.ProjectRemoteHome prjHome = null;
            prjHome = lookupProjectBean();
            
            ProjectRemote prj = prjHome.findByPrimaryKey(new Integer(1),null);
            
            //prj.addUser(usr);
            
            //prjHome.create(1, "prj1", "", "E");
            //prjHome.create(2, "prj2", "", "E");
            //prjHome.create(3, "prj3", "", "E");
            //prjHome.create(4, "prj4", "", "E");
            
            //ProjectRemote prj = null;
            
            
            
            
            /*
            ProjectRemote prj = prjHome.findByPrimaryKey("1");
            out.println("PrjName="+prj.getName());
            */
            
            /*
            
            prj.setComm("This is a comment");
            prj.enable();
            prj.remove();
            */
            
            /*
            Collection projects = prjHome.findByAll();
            Iterator prjItr = projects.iterator();
            
            
            
            while (prjItr.hasNext())
            {
                ProjectRemote prj = (ProjectRemote)prjItr.next();
                
                out.println("Name="+prj.getName()+"\n");
            }
             */
            
            com.arexis.mugen.project.role.RoleRemoteHome roleHome = null;
            roleHome = lookupRoleBean();
            
            //RoleRemote role = roleHome.create(1, prj, "Testrole", "");
            RoleRemote role = roleHome.findByPrimaryKey(new Integer(1));
            out.println("roleName="+role.getName());
            
            
            
            com.arexis.mugen.project.projectmanager.ProjectManagerRemote prjMgr = null;
            prjMgr = lookupProjectManagerBean();
            
            //prjMgr.addRole(prj, usr, role);
            //prjMgr.removeRole(prj, usr, role);
            
            //role.remove();
            
            
            
             PrivilegeRemoteHome priHome = lookupPrivilegeBean();
             //priHome.create(2, "User", "");
             //priHome.create(3, "ReadOnly", "");
             
             
             PrivilegeRemote pri = priHome.findByPrimaryKey(new Integer(3));
             
             //role.addPrivilege(pri);
             Collection privs = role.getPrivileges();
             Iterator itr = privs.iterator();
             
             while (itr.hasNext())
             {
                 PrivilegeRemote priv = (PrivilegeRemote)itr.next();
                 out.println("PrivilegeName="+priv.getName());
             }
             
             
             
             SecurityPrincipleRemoteHome secPrinHome = lookupSecurityPrincipleBean();
             SecurityPrincipleRemote secPrin = secPrinHome.findByPrimaryKey(new SecurityPrinciplePk(1,1,1));
             //SecurityPrincipleRemote secPrin = secPrinHome.create(prj, usr, role);
             
             out.println("<pre>"+secPrin.getId()+"</pre>");
             
             Collection secPrins = secPrinHome.findByProject(prj.getPid());
             itr = secPrins.iterator();
             
             
             while (itr.hasNext())
             {
                secPrin = (SecurityPrincipleRemote)itr.next();
                out.println("<p>"+secPrin.getId()+":"+secPrin.getPid()+":"+secPrin.getRid()+"</p>");
             }
             
             SpeciesRemoteHome specHome = lookupSpeciesBean();
             //SpeciesRemote spec = specHome.create(1, "human", "");
             SpeciesRemote spec = specHome.findByPrimaryKey(new Integer(1));
             spec.setComm("hejsan!");
             
             
             SamplingUnitRemoteHome sHome = lookupSamplingUnitBean();
             SamplingUnitRemote s = sHome.create(new Integer(1), "samp1", "", null,null);
             //SamplingUnitRemote s = sHome.findByPrimaryKey(Integer.valueOf(1));
             
             out.println("<p>SamplingUnitName:</p><p>"+s.getName()+"</p>");
             
        }
        catch (Exception e)
        {
            out.println("error: "+e.getMessage());
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }
        
        out.close();
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>

   

   

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

    private com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome lookupSamplingUnitBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/SamplingUnitBean");
            com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome rv = (com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome.class);
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

   

   

   
}
