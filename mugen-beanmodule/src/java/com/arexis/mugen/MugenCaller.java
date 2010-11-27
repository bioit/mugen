package com.arexis.mugen;

import com.arexis.arxframe.Caller;
import com.arexis.mugen.project.privilege.PrivilegeRemote;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.securityprinciple.SecurityPrincipleRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;


/**
 * The MugenCaller object is a object for keeping important information about the user
 * and the user session values important for the application.
 * 
 * Important: The set methods does only set the value in this object. No database
 * state will be changed. Use the appropriate Enterprice Bean to change values.
 * @author heto
 */
public class MugenCaller extends Caller implements Serializable {
    
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MugenCaller.class);
    
    private int sid;

    /**
     * The current project id
     */
    private int pid;
    
    /**
     * The current sampling unit id
     */
    private int suid;
    
    /**
     * The current sampling unit name
     */
    private String suidName;
    
    
    /** This hashmap stores privileges for each project
     * this hashmap is organized on project id (pid) as an Integer object
     */
    private HashMap projectsHashMap;
    
    /**
     * Creates a new instance of MugenCaller
     */
    public MugenCaller() {}
    
    /**
     * Creates a new mugen caller from a UserRemote object. 
     * This sets properties and updates privileges for that user.
     * @param user is the UserRemote object
     */
    public MugenCaller(UserRemote user)
    {
        try
        {
            this.id = user.getId();
            setUsr(user.getUsr());
            setName(user.getName());
            updatePrivileges();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Failed to initialize Caller.",e);
        }
    }
    
    /**
     * Get the sampling unit id
     * @return An int id of the current sampling unit
     */    
    public int getSuid(){
        return suid;       
    }

   
    
    
    /**
     * Sets the sampling unit id
     * @param suid The sampling unit id
     */    
    public void setSuid(int suid){
        this.suid = suid;  
        this.setAttribute("suid", suid);
    }
    
    /**
     * Sets the sampling unit name
     * @param suidName The name of the sampling unit
     */    
    public void setSuidName(String suidName){
        this.suidName = suidName;
        this.setAttribute("suidName", suidName);
    }    
    
    /**
     * Gets the sampling unit name
     * @return A string with sampling unit name
     */        
    public String getSuidName() {
        return suidName;
    }

 
    /**
     * Check if a user have a privilege in the application.
     * @param privilegeName a string of the privilege name to check for
     * @return true if the user have a privilege, otherwise false.
     */
    public boolean hasPrivilege(String privilegeName)
    {
        return hasPrivilege(privilegeName, pid);
    }

    /**
     * Check if a user have a privilege in the application.
     * @param privilegeName a string of the privilege name to check for
     * @return true if the user have a privilege, otherwise false.
     */
    public boolean hasPrivilege(String privilegeName, int pid)
    {
        try
        {            
            long t1 = System.currentTimeMillis();
            
            if (getPrivHashMap(pid)!=null && getPrivHashMap(pid).containsKey(privilegeName))
            {
                long t2 = System.currentTimeMillis();
                //System.out.println("UserPrinciple#hasPrivilege: Time="+(t2-t1)+" ms, PrivilegeName="+privilegeName+", ProjectId="+pid);
                return true;
            }
            else
            {
                //System.out.println("hasPrivilege(priv,pid)# No privilege for pid="+pid);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Check if a user have a privilege in the application for a Sampling Unit
     * @param privilegeName a string of the privilege name to check for
     * @return true if the user have a privilege, otherwise false.
     */
    public boolean hasPrivilegeSU(String privilegeName, int suid){
        long t1 = System.currentTimeMillis();
        try
        {   
            //ProjectManagerRemote projectManager = (ProjectManagerRemote)ServiceLocator.getInstance().getManager(ServiceLocator.Services.PROJECTMANAGER);
            SamplingUnitRemoteHome suHome = (SamplingUnitRemoteHome)ServiceLocator.getInstance().getHome(ServiceLocator.Services.SAMPLINGUNIT);
            SamplingUnitRemote su = suHome.findByPrimaryKey(new Integer(suid));
            
            Collection projects = su.getProjects();
            Iterator i = projects.iterator();
            ProjectRemote prj = null;
            while (i.hasNext())
            {
                prj = (ProjectRemote)i.next();
                //System.out.println("MugenCaller#hasPrivilegeSU, priv="+privilegeName+", suid="+suid+", pid="+prj.getPid());
                if (getPrivHashMap(prj.getPid())!=null && getPrivHashMap(prj.getPid()).containsKey(privilegeName))
                {
                    long t2 = System.currentTimeMillis();
                    //System.out.println("UserPrinciple#hasPrivilege: Time="+(t2-t1)+" ms, PrivilegeName="+privilegeName+", ProjectId="+pid);
                    return true;
                }                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
 
    
    /**
     * Get the species id value
     * @return an int value of the species id
     */    
    public int getSid() {
        return sid;
    }
    
    /**
     * Sets the species id
     * @param sid The species id
     */
    public void setSid(int sid) {
        this.sid = sid;
        this.setAttribute("sid",sid);
    }
    
    /**
     * get the users current selected project id. This is stored here to be transported
     * in the application.
     * @return the project id
     */
    public int getPid() {
        return pid;
    }

    /**
     * Set the project id. This sets the selected project.
     * @param pid The project id
     */
    public void setPid(int pid) {
        this.pid = pid;
        this.setAttribute("pid", pid);
    }
    
    /**
     * Updates the privilege information
     */
    public void updatePrivileges()
    {
        try
        {
            long t1 = System.currentTimeMillis();
            
            projectsHashMap = new HashMap();
            
            ServiceLocator locator = ServiceLocator.getInstance();
            UserRemoteHome userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
            UserRemote user = userHome.findByPrimaryKey(new Integer(id), this);
            
            isAdmin = user.isAdmin();
            
            Collection securityPrinciples = user.getSecurityPrinciples();
            SecurityPrincipleRemote sp = null;
            Iterator iPrivs = null;
            Collection arrPrivs = null;
            HashMap tempPrivsHash = null;
            Privilege p = null;
            Iterator i = securityPrinciples.iterator();
            while (i.hasNext())
            {
                tempPrivsHash = new HashMap();
                
                sp = (SecurityPrincipleRemote)i.next();
                
                // Project object
                Project prj = new Project(sp.getProject());
                
                // Get privileges for project
                arrPrivs = sp.getRole().getPrivileges();
                iPrivs = arrPrivs.iterator();
                while (iPrivs.hasNext())
                {
                    p = new Privilege((PrivilegeRemote)iPrivs.next());
                    tempPrivsHash.put(p.getName(), p);
                }
                
                // Set Privileges to project object
                prj.setPrivs(tempPrivsHash);
                
                // Set project object to hashmap
                projectsHashMap.put(new Integer(prj.getPid()), prj);
                        
            }
            long t2 = System.currentTimeMillis();
            logger.debug("Caller#updatePrivileges: Time="+(t2-t1)+" ms");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public HashMap getPrivHashMap(int pid){
        Project prj = (Project)projectsHashMap.get(new Integer(pid));
        if (prj!=null)
            return prj.getPrivs();
        else
            return null;
    }
}
