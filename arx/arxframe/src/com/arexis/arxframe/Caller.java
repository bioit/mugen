/*
 * Caller.java
 *
 * Created on June 29, 2005, 2:45 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.arxframe;
import java.io.Serializable;
import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 * The Caller object is a object for keeping important information about the user
 * and the user session values important for the application.
 * 
 * Important: The set methods does only set the value in this object. No database
 * state will be changed. Use the appropriate Enterprice Bean to change values.
 * @author heto
 */
public class Caller implements Serializable {
    
    protected static Logger logger = Logger.getLogger(Caller.class);
    
    /**
     * the user id, an int value, of the caller
     */
    protected int id;
    
    
    
    /**
     * The users full name. This could be displayed in the client
     */
    private String name;
    /**
     * The users login name, a unique name
     */
    private String usr;
    /**
     * the users password entered at login time
     */
    private String pwd;
    /**
     * the users email adress
     */
    private String email;
    
    /** Is this user server admin? */
    protected boolean isAdmin;
    
    protected HashMap privs;
    
    protected HashMap properties;
    
    /**
     * Creates a new instance of Caller
     */
    public Caller(){
        properties = new HashMap();
    }
    
    
    /**
     * Get the users name
     * @return A string of the users name
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * Set the users name. This method does not change the database, only session 
     * information.
     * @param name A string of the users name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the users login name
     * @return A string of the login name
     */
    public String getUsr() {
        return usr;
    }

    /**
     * Set the users login name. This does not change the database.
     * @param usr a string of the users login name
     */
    public void setUsr(String usr) {
        this.usr = usr;
    }

    /**
     * Get the users password. This should not be needed?
     * @return A string of the users password in clear text format.
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * Set the users password. This does not change the database.
     * @param pwd A string of the users password.
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * get the users email.
     * @return a string of the users email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the users email. This does not change the database
     * @param email a string of the users email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Check if a user have a privilege in the application.
     * @param privilegeName a string of the privilege name to check for
     * @return true if the user have a privilege, otherwise false.
     */
    public boolean hasPrivilege(String privilegeName){
        try
        {            
            long t1 = System.currentTimeMillis();
            
            if (privs.containsKey(privilegeName))
            {
                long t2 = System.currentTimeMillis();
                logger.debug("UserPrinciple#hasPrivilege: Time="+(t2-t1)+" ms, PrivilegeName="+privilegeName);
                return true;
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get the users id value
     * @return an int value of the users id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the users id value. This does not change the database.
     * @param id the users id value
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Updates the privilege information
     */
    public void updatePrivileges(){
        logger.warn("updatePrivileges() is empty in Caller object. Override!");
//        try
//        {
//            long t1 = System.currentTimeMillis();
//            
//            privs = new HashMap();
//            
//            ServiceLocator locator = ServiceLocator.getInstance();
//            UserRemoteHome userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
//            UserRemote user = userHome.findByPrimaryKey(new Integer(id));
//            
//            Collection arr = user.getSecurityPrinciples();
//            SecurityPrincipleRemote sp = null;
//            Iterator iPrivs = null;
//            Collection arrPrivs = null;
//            PrivilegeRemote priv = null;
//            Privilege p = null;
//            Iterator i = arr.iterator();
//            while (i.hasNext())
//            {
//                sp = (SecurityPrincipleRemote)i.next();
//                if (sp.getProject().getPid()==pid)
//                {
//                    arrPrivs = sp.getRole().getPrivileges();
//                    iPrivs = arrPrivs.iterator();
//                    while (iPrivs.hasNext())
//                    {
//                        priv = (PrivilegeRemote)iPrivs.next();
//                        p = new Privilege(priv.getPrid(), priv.getName());
//                        privs.put(p.getName(), p);
//                    }
//                }
//            }
//            long t2 = System.currentTimeMillis();
//            System.err.println("Caller#updatePrivileges: Time="+(t2-t1)+" ms");
//            
//            if (privs.containsKey("PROJECT_ADM"))
//            {
//                System.out.println("PROJECT_ADM exist!");
//            }
//            
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
    }
    
    public void setAttribute(String key, String value){
        properties.put(key, value);
    }
    
    public String getAttribute(String key){
        return (String)properties.get(key);
    }
    
    public void setAttribute(String key, int value){
        properties.put(key, new Integer(value));
    }
    
    public int getAttributeInt(String key){
        return ((Integer)properties.get(key)).intValue();
    }
    
     /** Is this caller a server admin */
    public boolean isAdmin(){
        return isAdmin;
    }
}
