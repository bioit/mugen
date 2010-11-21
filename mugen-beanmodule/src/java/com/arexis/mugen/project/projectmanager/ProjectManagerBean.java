package com.arexis.mugen.project.projectmanager;

import com.arexis.arxframe.io.FileDataObject;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.exceptions.LoginException;
import com.arexis.mugen.exceptions.PermissionDeniedException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.adminmanager.AdminManagerRemote;
import com.arexis.mugen.model.reference.ReferenceRemoteHome;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.privilege.PrivilegeRemote;
import com.arexis.mugen.project.privilege.PrivilegeRemoteHome;
import java.util.Collection;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.project.ProjectRemoteHome;
import com.arexis.mugen.project.role.RoleRemote;
import com.arexis.mugen.project.role.RoleRemoteHome;
import com.arexis.mugen.project.securityprinciple.SecurityPrincipleRemote;
import com.arexis.mugen.project.securityprinciple.SecurityPrincipleRemoteHome;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.resource.file.FileRemote;
import com.arexis.mugen.resource.link.LinkRemote;
import com.arexis.mugen.resource.resourcemanager.FileDTO;
import com.arexis.mugen.resource.resourcemanager.LinkDTO;
import com.arexis.mugen.resource.resourcemanager.ResourceManagerRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.servicelocator.ServiceLocator;
import com.arexis.mugen.simplelog.SimpleLogRemoteHome;
import com.arexis.mugen.species.species.SpeciesRemote;
import com.arexis.mugen.species.species.SpeciesRemoteHome;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javax.ejb.CreateException;
import javax.ejb.FinderException;




/**
 * This is the bean class for the ProjectManagerBean enterprise bean.
 * Created May 23, 2005 9:08:58 AM
 * 
 * This Session Bean handles logic for handling project, users, roles and 
 * privileges.
 * @author heto
 */
public class ProjectManagerBean extends AbstractMugenBean implements javax.ejb.SessionBean, com.arexis.mugen.project.projectmanager.ProjectManagerRemoteBusiness {
    private javax.ejb.SessionContext context;
    
    private ProjectRemoteHome projectHome;
    private PrivilegeRemoteHome privHome;
    private RoleRemoteHome roleHome;
    private UserRemoteHome userHome;
    private SecurityPrincipleRemoteHome securityHome;
    private SpeciesRemoteHome speciesHome;
    private ReferenceRemoteHome referenceHome;
    private SamplingUnitRemoteHome samplingUnitHome;
    private ResourceManagerRemote resourceManager;
    private AdminManagerRemote adminManager;
    private SimpleLogRemoteHome logHome;
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise bean, Web services)
    // TODO Add business methods or web service operations
    /**
     * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
     */
    public void setSessionContext(javax.ejb.SessionContext aContext) {
        context = aContext;
        projectHome = (ProjectRemoteHome)locator.getHome(ServiceLocator.Services.PROJECT);
        privHome = (PrivilegeRemoteHome)locator.getHome(ServiceLocator.Services.PRIVILEGE);
        roleHome = (RoleRemoteHome)locator.getHome(ServiceLocator.Services.ROLE);
        userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
        securityHome = (SecurityPrincipleRemoteHome)locator.getHome(ServiceLocator.Services.SECURITYPRINCIPLE);
        resourceManager = (ResourceManagerRemote)locator.getManager(ServiceLocator.Services.RESOURCEMANAGER);
        speciesHome = (SpeciesRemoteHome)locator.getHome(ServiceLocator.Services.SPECIES);
        adminManager = (AdminManagerRemote)locator.getManager(ServiceLocator.Services.ADMINMANAGER);
        referenceHome = (ReferenceRemoteHome)locator.getHome(ServiceLocator.Services.REFERENCE);
        samplingUnitHome = (SamplingUnitRemoteHome)locator.getHome(ServiceLocator.Services.SAMPLINGUNIT);
        
        logHome = (SimpleLogRemoteHome)locator.getHome(ServiceLocator.Services.SIMPLELOG);
    }
    
    /**
     * @see javax.ejb.SessionBean#ejbActivate()
     */
    public void ejbActivate() {
        
    }
    
    /**
     * @see javax.ejb.SessionBean#ejbPassivate()
     */
    public void ejbPassivate() {
        
    }
    
    /**
     * @see javax.ejb.SessionBean#ejbRemove()
     */
    public void ejbRemove() {
        
    }
    // </editor-fold>
    
    /**
     * See section 7.10.3 of the EJB 2.0 specification
     * See section 7.11.3 of the EJB 2.1 specification
     */
    public void ejbCreate() {
        // TODO implement ejbCreate if necessary, acquire resources
        // This method has access to the JNDI context so resource aquisition
        // spanning all methods can be performed here such as home interfaces
        // and data sources.
    }
    
    
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
    

    /**
     * Login a user to the application if the user name and password matches an 
     * entry in the database.
     *
     * @param usr is the username of the user.
     * @param pwd the password of the user.
     * @throws com.arexis.mugen.exceptions.LoginException If anything goes wrong, this exception is thrown and the user should not login.
     * @return a caller object that the user have during a valid session.
     */
    public MugenCaller login(java.lang.String usr, java.lang.String pwd) throws LoginException {
        MugenCaller up = new MugenCaller();
        try {
            // if correct usr and password
            
            
            UserRemote user = userHome.findByUserAndPwd(usr, pwd);
            if(user!=null){
                logger.debug("user object is not null. name is " + user.getName());
//                up = new MugenCaller();
                up.setId(user.getId());
                up.setName(user.getName());
                up.setUsr(user.getUsr());
                up.updatePrivileges();
                //up.setSid(1001);
            }
            
            
        }
        catch (FinderException e) {
//            ZOUB FIX - Swallow exceptions
//            e.printStackTrace();
//            throw new LoginException("User "+usr+" could not log in. Username or password was wrong", e);
            logger.error("Did not manage to find user " + usr + " with password " + pwd + " \n " + getStackTrace(e));
        }
        catch (Exception e) {
//            e.printStackTrace();
//            throw new LoginException("Unknown error: "+e.getMessage(), e);
            logger.error("Unknow error for user " + usr + " with password " + pwd + " \n " + getStackTrace(e));
        }
        return up;
    }

    public void log(String txt, String mgnaction, String mgnuser, String remoteadd, String remotehost)
    {
        try
        {
            logHome.create(txt, mgnaction, mgnuser, remoteadd, remotehost);
        } 
        catch (RemoteException ex)
        {
            ex.printStackTrace();
        } 
        catch (CreateException ex)
        {
            ex.printStackTrace();
        }
    }
    

   

    /**
     * Check if a user is a member of a role.
     *
     * @param user the userRemote object
     * @param prj the project object
     * @param roleName the role name to check for.
     * @return boolean (true/false) true if user is a member in the role, false otherwise.
     */
//    public boolean hasUserRole(UserRemote user, ProjectRemote prj , java.lang.String roleName) throws ApplicationException {
//        try
//        {
//            Collection arr = user.getSecurityPrinciples();
//            Iterator i = arr.iterator();
//            while (i.hasNext())
//            {
//                SecurityPrincipleRemote s = (SecurityPrincipleRemote)i.next();
//                if (s.getRole().getName().equals(roleName) && s.getProject().equals(prj))
//                {
//                    return true;
//                }
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            throws new ApplicationException("Failed to g")
//        }
//        return false;
//    }

    /**
     * Check if the user have a privilege. If the privilege exist return true else false.
     *
     * @param user the UserRemote object
     * @param project the ProjectRemote object
     * @param privilegeName the name of the privilege to test for
     * @return a boolean (true/false) true if user has the privilege otherwise false
     */
//    public boolean hasUserPrivilege(com.arexis.mugen.project.user.UserRemote user, com.arexis.mugen.project.project.ProjectRemote project, java.lang.String privilegeName) {
//        try
//        {
//            Collection arr = user.getSecurityPrinciples();
//            Iterator i = arr.iterator();
//            while (i.hasNext())
//            {
//                SecurityPrincipleRemote s = (SecurityPrincipleRemote)i.next();
//                Collection privs = s.getRole().getPrivileges();
//                Iterator iPrivs = privs.iterator();
//                while (iPrivs.hasNext())
//                {
//                    PrivilegeRemote priv = (PrivilegeRemote)iPrivs.next();
//                    if (priv.getName().equals(privilegeName) && s.getProject().equals(project))
//                        return true;
//                }
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return false;
//    }
    
    /**
     * Get all projects
     *
     * @param caller the caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException throws error messages to display for the user
     * @return collection of ProjectDTO
     */
    public Collection getProjects(com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        Collection projs = new ArrayList();
        try
        {
            Collection projects = projectHome.findByAll(caller);
            
            
            Iterator i = projects.iterator();
            while (i.hasNext())
            {
                ProjectRemote prj = (ProjectRemote)i.next();
                projs.add(new ProjectDTO(prj));
            }
        }
        catch (Exception e)
        {
            throw new ApplicationException("Unable to get projects for the user "+caller.getUsr(), e);
        }
        
        return projs;
    }
    
    /**
     * Get information about one project
     * 
     * @return a ProjectDTO for a project
     * @param pid the project id for the project to get
     * @param caller the caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException throws error messages to display for the user
     */
    public ProjectDTO getProject(int pid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        /*
//        if (!caller.isAdmin())
//            throw new PermissionDeniedException("User needs to be server admin");
        */
        try
        {
            ProjectRemote prj = projectHome.findByPrimaryKey(new Integer(pid));
            prj.setCaller(caller);
            int pid2 = prj.getPid();
            ProjectDTO p = new ProjectDTO(prj);
            return p;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Unable to get project ["+pid+"]", e);
        }
    }
    
    /**
     * Get information about one project
     * 
     * @return a ProjectDTO for a project
     * @param pid the project id for the project to get
     * @param caller the caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException throws error messages to display for the user
     */
    public ProjectDTO getDefaultProject(com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try
        {
            UserRemote user = userHome.findByPrimaryKey(new Integer(caller.getId()));
            Collection sec = user.getSecurityPrinciples();
            Iterator i = sec.iterator();
            SecurityPrincipleRemote sp = (SecurityPrincipleRemote)i.next();
            ProjectRemote project = sp.getProject();
            project.setCaller(caller);
            ProjectDTO p = new ProjectDTO(project);
            return p;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Unable to get default project", e);
        }
    }
    
    /**
     * Get all enabled projects that a user is a member of
     *
     * @param caller the caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException throws error messages to display for the user
     * @return collection of ProjectDTO
     */
    public Collection getProjectsByUser(com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        Collection projs = new ArrayList();
        try
        {
            UserRemote user = userHome.findByPrimaryKey(new Integer(caller.getId()));
            
            Collection sps = user.getSecurityPrinciples();
            Iterator i = sps.iterator();
            while (i.hasNext())
            {
                SecurityPrincipleRemote sp = (SecurityPrincipleRemote)i.next();
                ProjectRemote prj = sp.getProject();
                if (prj.getStatus().equals("E"))
                    projs.add(new ProjectDTO(prj.getPid(), prj.getName()));
            }
        }
        catch (Exception e)
        {
            throw new ApplicationException("Unable to get projects for the user "+caller.getUsr(), e);
        }
        return projs;
    }

    /**
     * Get a collection of RoleDTO objects for a user. This is the roles the user
     * is a member of.
     * @param caller the caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the roles could not be retrieved
     * @return Collection of RoleDTO
     */
    public Collection getRolesByUser(MugenCaller caller) throws ApplicationException
    {
        Collection roles = new ArrayList();
        try
        {
            UserRemote usr = userHome.findByPrimaryKey(new Integer(caller.getId()));
            
            Collection arr = usr.getSecurityPrinciples();
            Iterator i = arr.iterator();
            while (i.hasNext())
            {
                SecurityPrincipleRemote sec = (SecurityPrincipleRemote)i.next();
                roles.add(new RoleDTO(sec.getRole()));
            }
        }
        catch (Exception e)
        {
            throw new ApplicationException("Unable to get roles for the user  "+caller.getUsr(), e);
        }
        return roles;
    }
    
    /**
     * 
     * Get a collection of RoleDTO for all roles in a project
     * 
     * Privilege PROJECT_ADM is required
     * @param pid the project id
     * @param caller the caller of the method.
     * @throws com.arexis.mugen.exceptions.ApplicationException If the roles could not be retrieved
     * @return a collection of RoleDTO
     */
    public Collection getRolesByProject(int pid, MugenCaller caller) throws ApplicationException
    {
        if (!caller.hasPrivilege("PROJECT_ADM") && !caller.isAdmin())
                throw new PermissionDeniedException("User is not allowed to get roles");
        Collection roles = new ArrayList();
        try
        {
            ProjectRemote prj = projectHome.findByPrimaryKey(new Integer(pid), caller);
            Collection arr = prj.getRoles();
            Iterator i = arr.iterator();
            while (i.hasNext())
            {
                RoleRemote r = (RoleRemote)i.next();
                roles.add(new RoleDTO(r));
            }
        }
        catch (Exception e)
        {
            throw new ApplicationException("Unable to get roles for the user  "+caller.getUsr(), e);
        }
        return roles;
    }
    
    
    /**
     * Get a role for a user in a project
     * @return A role
     * @param id is the user id
     * @param pid is the project id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the roles could not be retrieved
     */
    public RoleRemote getRoleByUserAndProject(int id, int pid, MugenCaller caller) throws ApplicationException
    {
        try
        {
            UserRemote usr = userHome.findByPrimaryKey(new Integer(id));
            
            Collection arr = usr.getSecurityPrinciples();
            Iterator i = arr.iterator();
            while (i.hasNext())
            {
                SecurityPrincipleRemote sec = (SecurityPrincipleRemote)i.next();
                if (sec.getProject().getPid()==pid)
                    return sec.getRole();
            }
        }
        catch (Exception e)
        {
            throw new ApplicationException("Unable to get roles for the user  "+caller.getUsr(), e);
        }
        return null;
    }
    
    /**
     * 
     * Get a collection of UserDTO for all users in a project
     * 
     * Privilege PROJECT_ADM is required
     * @param pid the project id
     * @param caller the caller of the method.
     * @throws com.arexis.mugen.exceptions.ApplicationException If the users could not be retrieved
     * @return a collection of RoleDTO
     */
    public Collection getUsersByProject(int pid, MugenCaller caller) throws ApplicationException
    {
        //if (!caller.hasPrivilege("PROJECT_ADM"))
        //        throw new PermissionDeniedException("User is not allowed to get roles");
        
        Collection users = new ArrayList();
        try
        {
            ProjectRemote prj = projectHome.findByPrimaryKey(new Integer(pid), caller);
            Collection arr = prj.getSecurityPrinciples();
            Iterator i = arr.iterator();
            while (i.hasNext())
            {
                SecurityPrincipleRemote sp = (SecurityPrincipleRemote)i.next();
                UserRemote user = sp.getUser();
                users.add(new UserDTO(user));
            }
            
            Collections.sort((ArrayList)users);
        }
        catch (Exception e)
        {
            throw new ApplicationException("Unable to get users for project.", e);
        }
        return users;
    }
    
    /**
     * Comparator for ProjectUserDTO
     * Sort on UserID
     */
    public class UserIdComparator implements Comparator
    {
        public int compare(Object r1, Object r2)
        {
            return ((ProjectUserDTO)r1).getId() - ((ProjectUserDTO)r2).getId();
        }
    }
    
    /**
     * Comparator for ProjectUserDTO
     * Sort on role name
     */
    public class UserRoleComparator implements Comparator
    {
        public int compare(Object r1, Object r2)
        {
            return ((ProjectUserDTO)r1).getRoleName().compareTo(((ProjectUserDTO)r2).getRoleName());
        }
    }
    
    public Collection getNonProjectUsers(int pid, MugenCaller caller) throws ApplicationException
    {
        try
        {
            
            ProjectRemote prj = projectHome.findByPrimaryKey(new Integer(pid));
            Collection secArr = prj.getSecurityPrinciples();
            Iterator i = secArr.iterator();
            
            Collection users = userHome.findAll(caller);
            while (i.hasNext())
            {
                SecurityPrincipleRemote sec = (SecurityPrincipleRemote)i.next();
                users.remove(sec.getUser());
            }
            return users;
        }
        catch (Exception e)
        {
            throw new ApplicationException("Failed to get non project users",e);
        }
    }
    
    /**
     * Get a collection of ProjectUserDTO
     * @param pid The project id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the information could not be retrieved
     * @return A collection of ProjectUser DTO's
     */
    public Collection getProjectUsers(int pid, MugenCaller caller) throws ApplicationException
    {
        //if (!caller.hasPrivilege("PROJECT_ADM"))
        //        throw new PermissionDeniedException("User is not allowed to get roles");
        Collection users = new ArrayList();
        try
        {
            ProjectRemote prj = projectHome.findByPrimaryKey(new Integer(pid), caller);
            Collection arr = prj.getSecurityPrinciples();
            Iterator i = arr.iterator();
            while (i.hasNext())
            {
                SecurityPrincipleRemote sp = (SecurityPrincipleRemote)i.next();
                UserRemote user = sp.getUser();
                
                // Get the role for that user
                RoleRemote role = getRoleByUserAndProject(user.getId(), pid, caller);
                
                ProjectUserDTO prjUsr = new ProjectUserDTO(user);
                prjUsr.setRid(role.getRid());
                prjUsr.setRoleName(role.getName());
                
                users.add(prjUsr);
            }
            Collections.sort((ArrayList)users);
        } 
        catch (Exception e)
        {
            throw new ApplicationException("Unable to get users for the user project.", e);
        }
        return users;
    }
    
    public Collection getRestrictedProjectUsers(int pid, MugenCaller caller) throws ApplicationException{
        Collection users = new ArrayList();
        try
        {
            ProjectRemote prj = projectHome.findByPrimaryKey(new Integer(pid), caller);
            Collection arr = prj.getSecurityPrinciples();
            Iterator i = arr.iterator();
            while (i.hasNext())
            {
                SecurityPrincipleRemote sp = (SecurityPrincipleRemote)i.next();
                UserRemote user = sp.getUser();
                
                // Get the role for that user
                RoleRemote role = getRoleByUserAndProject(user.getId(), pid, caller);
                
                ProjectUserDTO prjUsr = new ProjectUserDTO(user);
                prjUsr.setRid(role.getRid());
                prjUsr.setRoleName(role.getName());
                
                if(!prjUsr.getGroupName().equals("MUGEN") && !prjUsr.getGroupName().equals("") && !prjUsr.getName().equals("Public")){
                    users.add(prjUsr);
                }
            }
            Collections.sort((ArrayList)users);
        } 
        catch (Exception e)
        {
            throw new ApplicationException("Unable to get users for the user project.", e);
        }
        return users;
    }
    
    /**
     * Get all privileges a role does not have. 
     * 
     * Gets all privileges and removes all privleges for a role. The result is
     * returned.
     * @param rid the role id
     * @param caller the caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the privileges could not be retrieved
     * @return a collection of PrivilegeDTO
     */
    public Collection getOtherPrivileges(int rid, MugenCaller caller) throws ApplicationException
    {
        if (!caller.hasPrivilege("PROJECT_ADM") && !caller.isAdmin())
            throw new PermissionDeniedException("User is not allowed to get privileges");
        Collection arr = new ArrayList();
        try
        {
            RoleRemote role = roleHome.findByPrimaryKey(new Integer(rid));
            
            // Get all privileges for the role
            Collection rolePrivs = role.getPrivileges();
            
            // Get all privileges
            Collection allPrivs = privHome.findAll();
            
            Collection tmp = allPrivs;
            tmp.removeAll(rolePrivs);
            
            Iterator i = tmp.iterator();
            while (i.hasNext())
            {
                PrivilegeRemote priv = (PrivilegeRemote)i.next();
                
                PrivilegeDTO p = new PrivilegeDTO(priv);
                if (rolePrivs.contains(priv))
                    p.setSelected(true);
                
                arr.add(p);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Unable to get privileges", e);
        }
        return arr;
    }

    /**
     * Get the privileges for a role.
     * @param rid role id
     * @param caller the caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the privileges could not be retrieved
     * @return a collection of PrivilegeDTO
     */
    public Collection getPrivileges(int rid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        long t1 = System.currentTimeMillis();
        Collection arr = new ArrayList();
        
        if (!caller.hasPrivilege("PROJECT_ADM") && !caller.isAdmin())
            throw new PermissionDeniedException("User is not allowed to get privileges");
        
        try
        {
            RoleRemote role = roleHome.findByPrimaryKey(new Integer(rid));
            
            // Get all privileges for the role
            Collection rolePrivs = role.getPrivileges();
            
            Iterator i = rolePrivs.iterator();
            while (i.hasNext())
            {
                PrivilegeRemote priv = (PrivilegeRemote)i.next();
                
                PrivilegeDTO p = new PrivilegeDTO(priv);
                if (rolePrivs.contains(priv))
                    p.setSelected(true);
                
                arr.add(p);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Unable to get privileges", e);
        }
        System.out.println("getPrivileges Time="+(System.currentTimeMillis()-t1));
        return arr;
    }

    /**
     * Add privileges to a role. This method takes an array of Strings with
     * prid (privilege ids) to add to a role. 
     * 
     * This method loops the array and adds each privilege to the role.
     * 
     * If add fails for one entry, the method continues without notice. This
     * implies that one of the privileges can be existing to a role without 
     * disrupting this method.
     * 
     * If other errors occur (except the add failure) an ApplicationException is
     * thrown.
     * @param privIds The privilege id's
     * @param rid The role id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the privileges could not be added
     */
    public void addPrivilegesToRole(java.lang.String[] privIds, int rid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        if (!caller.hasPrivilege("PROJECT_ADM") && !caller.isAdmin())
            throw new PermissionDeniedException("User is not allowed to add privileges");
        try
        {
            RoleRemote role = roleHome.findByPrimaryKey(new Integer(rid));
            
            PrivilegeRemote priv = null;
            
            for (int i=0;i<privIds.length;i++)
            {
                priv = privHome.findByPrimaryKey(new Integer(privIds[i]));
                
                try
                {
                    role.addPrivilege(priv);
                }
                catch (RemoteException e)
                {
                    // Ignore this.
                }
            }
        }
        catch (Exception e)
        {
            throw new ApplicationException("Failed to add privileges to role", e);
        }
    }

    /**
     * Removes privileges from a role. This method takes an array of Strings with
     * prid (privilege ids) to remove them from a role.
     * 
     * This method loops the array and removes each privilege from the role.
     * 
     * If remove fails for one entry, the method continues without notice. This
     * implies that one of the privileges can be missing to a role without 
     * disrupting this method.
     * 
     * If other errors occur (except the remove failure) an ApplicationException is
     * thrown.
     * @param privIds The privilege id's
     * @param rid The role id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the privileges could not be removed
     */
    public void removePrivilegesFromRole(java.lang.String[] privIds, int rid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        if (!caller.hasPrivilege("PROJECT_ADM") && !caller.isAdmin())
            throw new PermissionDeniedException("User is not allowed to remove privileges");
        try
        {
            RoleRemote role = roleHome.findByPrimaryKey(new Integer(rid));
            
            PrivilegeRemote priv = null;
            
            for (int i=0;i<privIds.length;i++)
            {
                priv = privHome.findByPrimaryKey(new Integer(privIds[i]));
                try
                {
                    role.removePrivilege(priv);
                }
                catch (RemoteException e)
                {
                    e.printStackTrace();
                    // Ignore this.
                }
            }
        }
        catch (Exception e)
        {
            throw new ApplicationException("Failed to remove privileges to role", e);
        }
    }
    
    /**
     * Writes the statistics page using the writeStatisticsPage in
     * ServletUtil.
     * 
     * Privilege PROJECT_STA is required
     * @param pid The project id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the statistics could not be retrieved
     * @return A data transfer object containing statistics data
     */
    public ProjectStatisticsDTO getStatistics(int pid, MugenCaller caller)
            throws ApplicationException
    {
        ProjectStatisticsDTO stats = new ProjectStatisticsDTO();
        
        if (!caller.hasPrivilege("PROJECT_STA"))
            throw new PermissionDeniedException("User is not allowed");
        
        Statement stmt = null;
        ResultSet rset = null;
        try {
            makeConnection();
            
            // Get general project data
            stmt = conn.createStatement();
            rset = stmt.executeQuery("SELECT NAME, COMM, STATUS FROM V_PROJECTS_1 " +
                    "WHERE PID=" + pid);
            
            // Check that project found, otherwise bail out
            if (!rset.next())
                throw new ApplicationException("Project not found");
            
            stats.setPid(pid);
            stats.setName(rset.getString("name"));
            stats.setComm(rset.getString("comm"));
            
            // Query for all users of the project and add result to vector. If
            // no users found, add a blank string.
            rset = stmt.executeQuery("SELECT COUNT (*) FROM " +
                    "V_USERS_2 WHERE PID=" + pid);
            if (rset.next()) {
                stats.setUsers(rset.getInt(1));
            }
            
            // Query for species in project
            rset = stmt.executeQuery("SELECT COUNT (*) FROM V_PROJECTS_2 WHERE " +
                    "PID=" + pid);
            if (rset.next()) {
                stats.setSpecies(rset.getInt(1));
            }
            
            // Query for sampling units
            rset = stmt.executeQuery("SELECT COUNT (*) FROM V_SAMPLING_UNITS_2 " +
                    "WHERE PID=" + pid);
            if (rset.next()) {
                stats.setSamplingunits(rset.getInt(1));
            }
            
            // Query for individuals
            rset = stmt.executeQuery("SELECT sum(INDS) "
                    + "FROM V_SAMPLING_UNITS_3 WHERE PID="
                    + pid );
            
            if (rset.next()) {
                stats.setIndividuals(rset.getInt(1));
            }
            
            // Query for samples
            rset = stmt.executeQuery("SELECT count(*) "
                    + "FROM V_SAMPLES_2 s, V_SAMPLING_UNITS_2 su"+
                    " WHERE s.SUID=su.SUID AND su.PID=" + pid + " ");
            if (rset.next()) {
                stats.setSamples(rset.getInt(1));
            }
            
            // Query for variables
            rset = stmt.executeQuery("SELECT count(*) "
                    + "FROM V_VARIABLES_3 WHERE PID="
                    + pid + " ");
            
            if (rset.next()) {
                stats.setVariables(rset.getInt(1));
            }

            // Query for phenotypes
            rset = stmt.executeQuery("SELECT COUNT(*) "
                    + "FROM V_VARIABLES_1 V, " +
                    " PHENOTYPES P WHERE V.SUID IN " +
                    "(SELECT SUID FROM R_PRJ_SU WHERE PID=" +
                    pid + ") AND V.VID = P.VID");
          
            if (rset.next()) {
                stats.setPhenotypes(rset.getInt(1));
            }
            
            // Query for markers
            rset = stmt.executeQuery("SELECT count(*) "
                    + "FROM V_MARKERS_1 m, V_SAMPLING_UNITS_2 s "+
                    "WHERE m.SUID=s.SUID AND s.PID=" +
                    pid + " ");
            if (rset.next()) {
                stats.setMarkers(rset.getInt(1));
            }            
            
            // Query for genotypes
            rset = stmt.executeQuery("SELECT COUNT(*) " +
                    "FROM V_MARKERS_1 M, " +
                    "GENOTYPES G WHERE M.SUID IN " +
                    "(SELECT SUID FROM R_PRJ_SU WHERE PID="  +
                    pid + ") AND M.MID = G.MID");
            if (rset.next()) {
                stats.setGenotypes(rset.getInt(1));
            }
        } catch (Exception e) {
            //e.printStackTrace();
            throw new ApplicationException("Unable to get project statistics.", e);
        } finally {
            releaseConnection();
            try {
                if (rset != null) {
                    rset.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ignored) {
            }
        }
        
        return stats;
    }
    
    /**
     * 
     * Create a new role for a project 
     * 
     * Privilege PROJECT_ADM is required.
     * @return The role id
     * @param comm The comment for the role
     * @param name The name of the role
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the role could not be created
     */
    public int createRole(String name, String comm, int pid, MugenCaller caller) throws ApplicationException
    {
        if (!caller.hasPrivilege("PROJECT_ADM") && !caller.isAdmin())
            throw new PermissionDeniedException("No privilege");
        int rid = 0;
        try
        {
            makeConnection();
            rid = getIIdGenerator().getNextId(conn, "roles_seq");
            
            ProjectRemote prj = projectHome.findByPrimaryKey(new Integer(pid), caller);
            
            //System.out.println("Pid="+prj.getPid());
            
            RoleRemote role = roleHome.create(rid, prj, name, comm);
            
            //System.out.println("rid="+role.getRid());
            
            UserRemote user = userHome.findByPrimaryKey(new Integer(caller.getId()));
            
            //System.out.println("id="+user.getId());
        }
        catch (Exception e)
        {
            //e.printStackTrace();
            throw new ApplicationException("Could not create a new role: "+e.getMessage(), e);
        }
        finally
        {
            releaseConnection();
        }
        return rid;
    }
    
    /**
     * The user can change his own password.
     *
     * All passwords constraints is implemented here:
     *  Password p1 and p2 must match
     *  Password must be more than 3 characters.
     *  The old password must match database.
     *
     * @param old the password that should be changed.
     * @param p1 the first new password field
     * @param p2 the second new password field
     * @param caller is the user performing the password change
     * @throws com.arexis.mugen.exceptions.ApplicationException if anything goes wrong 
     */
    public java.lang.String setPassword(String old, String p1, String p2, MugenCaller caller) throws ApplicationException
    {
        try
        {
            UserRemote user = userHome.findByPrimaryKey(new Integer(caller.getId()));
            
            /** Do all password checks before changing */
            
            if (!p1.equals(p2)){
                return "Passwords do not match";
            }
                //throw new ApplicationException("Passwords does not match");
            
            if (p1.length()<4){
                return "Passwords must exceed 3 characters";
            }
                //throw new ApplicationException("Passwords must exceed 3 characters");
            
            if (!user.getPwd().equals(old)){
                return "Old password was wrong";
            }
                //throw new ApplicationException("Old password was wrong");
            
            /*
            if (!user.getPwd().equals(caller.getPwd()))
                throw new ApplicationException("Old password was wrong");
             */
            
            /** All password checks passed. Change password */
            user.setPwd(p1);
            
            return "Password changed successfully";
        }
        /*catch (ApplicationException e)
        {
            throw e;
        }*/
        catch (Exception e)
        {
            throw new ApplicationException("Failed to set password", e);
        }   
    }
    
    /**
     * Remove a role from database.
     *
     * Privilege PROJECT_ADM is required
     *
     * @param rid is the role id to be removed
     * @param caller is the user performing the operation
     * @throws com.arexis.mugen.exceptions.ApplicationException if anything dont work.
     */
    public void removeRole(int rid, MugenCaller caller) throws ApplicationException
    {
        if (!caller.hasPrivilege("PROJECT_ADM") && !caller.isAdmin())
            throw new PermissionDeniedException("Operation in not allowed.");
        try
        {
            RoleRemote role  = roleHome.findByPrimaryKey(new Integer(rid));
            role.remove();
        }
        catch (Exception e)
        {
            throw new ApplicationException("Unable to remove role.", e);
        }
    }

    /**
     * Updates a role
     * @param rid The role id
     * @param name The name of the role
     * @param comm The comment for the role
     * @param caller The caller
     * @throws com.arexis.mugen.exceptions.ApplicationException If the role could not be updated
     */
    public void updateRole(int rid, java.lang.String name, java.lang.String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("PROJECT_ADM", caller);

        try
        {
            RoleRemote role  = roleHome.findByPrimaryKey(new Integer(rid));
            role.setComm(comm);
            role.setName(name);
        }
        catch (Exception e)
        {
            throw new ApplicationException("Unable to update role.", e);
        }
    }

    /**
     * Retrieves a user
     * @param id The id of the user to retrieve
     * @param caller The caller
     * @throws com.arexis.mugen.exceptions.ApplicationException If the user could not be retrieved
     * @return The user with the specified id
     */
    public UserDTO getUser(int id, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        //if (!caller.hasPrivilege("PROJECT_ADM"))
        //    throw new PermissionDeniedException("User is not allowed to view user details");

        try {
            UserRemote user = userHome.findByPrimaryKey(new Integer(id));
            
            return new ProjectUserDTO(user);
            
        } catch (Exception e) {
            throw new ApplicationException("Unable to get user", e);
        }
    }

    /**
     * Updates a user
     * @param id The user id
     * @param role The role of the user
     * @param name The name of the user
     * @param email The email of the user
     * @param userLink The link to the users website
     * @param groupName The name of the research group
     * @param groupAddress The address for the research group
     * @param groupPhone The phonenumber for the research group
     * @param groupLink The link to the research group webpage
     * @param caller The caller
     * @param usr The username
     * @param pwd The password
     * @throws com.arexis.mugen.exceptions.ApplicationException If the user could not be updated
     */
    public void updateUser(int id, int role, java.lang.String name, java.lang.String email, 
            java.lang.String userLink, java.lang.String groupName, java.lang.String groupAddress,
            java.lang.String groupPhone, java.lang.String groupLink, 
            com.arexis.mugen.MugenCaller caller, String usr, String pwd) throws ApplicationException {
        validate("PROJECT_ADM", caller);
        
        try {
            UserRemote user  = userHome.findByPrimaryKey(new Integer(id));
            user.setName(name);
            user.setEmail(email);
            user.setGroupName(groupName);
            user.setGroupAddress(groupAddress);
            user.setGroupPhone(groupPhone);
            
            if(user.getUserLink() != null) {
                LinkRemote link = user.getUserLink();
                link.setCaller(caller);
                link.setName(userLink);
                link.setUrl(userLink);
            }
            else {
                int linkid = resourceManager.createLink(userLink, "User link for user id="+id, userLink, caller).getLinkId();
                user.setUserLink(linkid);
            }
            
            if(user.getGroupLink() != null) {
                LinkRemote link = user.getGroupLink();
                link.setCaller(caller);
                link.setName(groupLink);
                link.setUrl(groupLink);
            }
            else {
                int linkid = resourceManager.createLink(groupLink, "User link for research group id="+id, groupLink, caller).getLinkId();
                user.setGroupLink(linkid);
            }            
            
            
            
            SecurityPrincipleRemote sec = securityHome.findByUserProject(id, caller.getPid());
            if(role != sec.getRid()) 
            {
                sec.remove();
                assignUserToProject(id, role, caller.getPid(), caller);
            }
        } catch (Exception e) {
            throw new ApplicationException("Unable to update user.", e);
        }
    }
    
    public void assignUserToProject(int id, int role, int pid, MugenCaller caller) throws ApplicationException
    {
        if (pid!=caller.getPid() && !caller.isAdmin())
            throw new PermissionDeniedException("The user is not allowed to assign user to this project. Admin privileges is needed.");
        
        validate("PROJECT_ADM", caller);
        
        try
        {
            ProjectRemote projectRem = projectHome.findByPrimaryKey(new Integer(pid), caller);
            UserRemote userRem = userHome.findByPrimaryKey(new Integer(id));
            RoleRemote roleRem = roleHome.findByPrimaryKey(new Integer(role));

            securityHome.create(projectRem, userRem, roleRem);        
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to assign user to project [id="+id+", pid="+pid+", rid="+role+"]", e);
        }
    }
    
    public void unAssignUserFromProject(int id, int pid, MugenCaller caller) throws ApplicationException
    {
        if (pid!=caller.getPid() && !caller.isAdmin())
            throw new PermissionDeniedException("The user is not allowed to unassign user to this project. Admin privileges is needed.");
        
        validate("PROJECT_ADM", caller);
        
        try
        {
            SecurityPrincipleRemote sec = securityHome.findByUserProject(id, pid);
            if(sec != null) 
            {
                sec.remove();
            }
        }
        catch (Exception e)
        {
            throw new ApplicationException("Failed to remove user assignment to a project",e);
        }
    }
    
    public void assignSpeciesToProject(int sid, int pid, MugenCaller caller) throws ApplicationException
    {
        if (pid!=caller.getPid() && !caller.isAdmin())
            throw new PermissionDeniedException("The user is not allowed to assign user to this project. Admin privileges is needed.");
        
        validate("PROJECT_ADM", caller);
        
        try
        {
            ProjectRemote project = projectHome.findByPrimaryKey(new Integer(pid), caller);
            SpeciesRemote species = speciesHome.findByPrimaryKey(new Integer(sid));
            project.addSpecies(species);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to assign species to project [sid="+sid+", pid="+pid+"]", e);
        }
    }
        

    /**
     * Creates a new user
     * @param role The role in the project
     * @param name The name
     * @param email The email
     * @param userLink The link to the users webpage
     * @param groupName The name of the researchgroup
     * @param groupAddress The address for the research group
     * @param groupPhone The phonenumber for the research group
     * @param groupLink The link to the groups webbpage
     * @param usr The username
     * @param pwd The password
     * @param caller The caller
     * @throws com.arexis.mugen.exceptions.ApplicationException If the user could not be created
     */
    public int createUser(java.lang.String name, java.lang.String email, java.lang.String userLink, java.lang.String groupName, java.lang.String groupAddress, java.lang.String groupPhone, java.lang.String groupLink, String usr, String pwd, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("PROJECT_ADM", caller);
        
        try {
            makeConnection();
            int id = getIIdGenerator().getNextId(conn, "users_seq");
            UserRemote user  = userHome.create(id, usr, pwd, name, "E");
            
            user.setEmail(email);
            user.setGroupName(groupName);
            user.setGroupAddress(groupAddress);
            user.setGroupPhone(groupPhone);
            
            
            if(user.getUserLink() != null) {
                LinkRemote link = user.getUserLink();
                link.setUrl(userLink);
            }
            else if(userLink != null && userLink.length() > 0){
                int linkid = resourceManager.createLink(userLink, "User link for user id="+id, userLink, caller).getLinkId();
                user.setUserLink(linkid);
            }
            
            if(user.getGroupLink() != null) {
                LinkRemote link = user.getGroupLink();
                link.setUrl(groupLink);
            }
            else if(groupLink != null && groupLink.length() > 0){
                int linkid = resourceManager.createLink(groupLink, "User link for research group id="+id, groupLink, caller).getLinkId();
                user.setGroupLink(linkid);
            }    
            
            return id;
            
            //ProjectRemote projectRem = projectHome.findByPrimaryKey(new Integer(caller.getPid()), caller);
            //UserRemote userRem = userHome.findByPrimaryKey(new Integer(id));
            //RoleRemote roleRem = roleHome.findByPrimaryKey(new Integer(role));

            //securityHome.create(projectRem, userRem, roleRem);        
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Unable to create user.", e);
        }
    }

    /**
     * Removes a user
     * @param id The id of the user to remove
     * @param caller The caller
     * @throws com.arexis.mugen.exceptions.ApplicationException If the user could not be removed
     */
    public void removeUser(int id, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("PROJECT_ADM", caller);
        
        try {
            if(id != caller.getId()) {
                UserRemote user  = userHome.findByPrimaryKey(new Integer(id));            
                user.remove();
            }
            else
                throw new ApplicationException("Cannot remove the same account as logged in with.");
        } catch (Exception e) {
            throw new ApplicationException("Unable to update user.", e);
        }
    }

    public Collection getFiles(int pid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                                            
            Collection files = projectHome.findByPrimaryKey(new Integer(pid)).getFiles();
            Collection dtos = new ArrayList();
            Iterator i = files.iterator();
            while(i.hasNext()) {   
                dtos.add(new FileDTO((FileRemote)i.next()));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get files for project.");
        }
    }
    
    public Collection getLinks(int pid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                                            
            Collection links = projectHome.findByPrimaryKey(new Integer(pid)).getLinks();
            Collection dtos = new ArrayList();
            Iterator i = links.iterator();
            while(i.hasNext()) {   
                dtos.add(new LinkDTO((LinkRemote)i.next()));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get links for project.");
        }
    } 

    public Collection getCategoriesAndResources(int pid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        try {                                            
            Collection categories = projectHome.findByPrimaryKey(new Integer(pid)).getResourceCategories();
                        
            return resourceManager.getResources(categories, caller);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get resources for project.");
        }
    }   

    /** 
     * Add Resource to a project
     * 
     */
    public void addResource(java.lang.String type, int category, int project, java.lang.String name, java.lang.String comm, FileDataObject fileData, com.arexis.mugen.MugenCaller caller, String url) throws ApplicationException {
        validate("RESOURCE_W", caller);
        System.out.println("ProjectManager#addResource start");
        try {
            
            if(type.equalsIgnoreCase("file")) {
                // Store the file
                int fileId = resourceManager.saveFile(fileData.getFileName(), comm, caller, fileData).getFileId();
                // Register the file as a resource
                resourceManager.createResource(project, name, comm, fileId, 0, category, caller);
            }
            else if(type.equalsIgnoreCase("weblink")) {
                // Store the link
                int linkid = resourceManager.createLink(name, comm, url, caller).getLinkId();
                // Register the link as a resource
                resourceManager.createResource(project, name, comm, 0, linkid, category, caller);                
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not add resource to project \n"+e.getMessage());            
        }
        System.out.println("ProjectManager#addResource end");
    }

    public void unAssignSpeciesFromProject(int pid, int sid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        if (pid!=caller.getPid() && !caller.isAdmin())
            throw new PermissionDeniedException("The user is not allowed to unassign species from this project. Admin privileges is needed.");
        
        validate("PROJECT_ADM", caller);
        
        try
        {
            ProjectRemote project = projectHome.findByPrimaryKey(new Integer(pid), caller);
            SpeciesRemote species = speciesHome.findByPrimaryKey(new Integer(sid));
            project.removeSpecies(species);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to unassign species from project [sid="+sid+", pid="+pid+"]", e);
        }
    }

    public void unAssignSamplingUnitFromProject(int pid, int suid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        if (pid!=caller.getPid() && !caller.isAdmin())
            throw new PermissionDeniedException("The user is not allowed to unassign sampling units from this project. Admin privileges is needed.");
        
        validate("PROJECT_ADM", caller);
        
        try
        {
            ProjectRemote project = projectHome.findByPrimaryKey(new Integer(pid), caller);
            SamplingUnitRemote su = samplingUnitHome.findByPrimaryKey(new Integer(suid));
            project.removeSamplingUnit(su);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApplicationException("Failed to unassign sampling units from project [suid="+suid+", pid="+pid+"]", e);
        }
    }
    
    
}
