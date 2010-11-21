
package com.arexis.mugen.project.projectmanager;

import com.arexis.arxframe.io.FileDataObject;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.exceptions.LoginException;
import com.arexis.mugen.MugenCaller;
import java.util.Collection;



/**
 * This is the business interface for ProjectManager enterprise bean.
 */
public interface ProjectManagerRemoteBusiness {

    MugenCaller login(java.lang.String usr, java.lang.String pwd) throws java.rmi.RemoteException, LoginException;

    //boolean hasUserRole(UserRemote user, ProjectRemote prj, java.lang.String roleName) throws java.rmi.RemoteException;

    //boolean hasUserPrivilege(UserRemote user, ProjectRemote project, java.lang.String privilegeName) throws java.rmi.RemoteException;

    Collection getProjects(MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    Collection getRolesByUser(MugenCaller caller) throws java.rmi.RemoteException, ApplicationException;

    Collection getPrivileges(int rid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    /**
     * Get all privileges a role does not have
     */
    Collection getOtherPrivileges(int rid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void addPrivilegesToRole(java.lang.String[] privIds, int rid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removePrivilegesFromRole(java.lang.String[] privIds, int rid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    /**
     * Writes the statistics page using the writeStatisticsPage in
     * ServletUtil.
     * 
     * @param request The request from client.
     * @param response The response to client.
     * @exception ServletException If error when handling request.
     * @exception IOException If I/O error when handling request.
     */
    ProjectStatisticsDTO getStatistics(int pid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    int createRole(java.lang.String name, String comm, int pid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    Collection getRolesByProject(int pid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    java.lang.String setPassword(java.lang.String old, java.lang.String p1, java.lang.String p2, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void removeRole(int rid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    /**
     * Get all projects that a user is a member of
     * 
     * @param caller the caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException throws error messages to display for the user
     * @return collection of ProjectDTO
     */
    Collection getProjectsByUser(com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    /**
     * 
     * Get a collection of RoleDTO for all roles in a project
     * 
     * Privilege PROJECT_ADM is required
     * 
     * @param pid the project id
     * @param caller the caller of the method.
     * @throws com.arexis.mugen.exceptions.ApplicationException 
     * @return a collection of RoleDTO
     */
    Collection getUsersByProject(int pid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    /**
     * Get a collection of ProjectUserDTO
     * @param pid 
     * @param caller 
     * @throws com.arexis.mugen.exceptions.ApplicationException 
     * @return 
     */
    Collection getProjectUsers(int pid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void updateRole(int rid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    UserDTO getUser(int id, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void updateUser(int id, int role, java.lang.String name, java.lang.String email, java.lang.String userLink, java.lang.String groupName, java.lang.String groupAddress, java.lang.String groupPhone, java.lang.String groupLink, MugenCaller caller, String usr, String pwd) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    int createUser(java.lang.String name, java.lang.String email, java.lang.String userLink, java.lang.String groupName, java.lang.String groupAddress, java.lang.String groupPhone, java.lang.String groupLink, String usr, String pwd, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void removeUser(int id, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    /**
     * Get information about one project
     * 
     * @return a ProjectDTO for a project
     * @param pid the project id for the project to get
     * @param caller the caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException throws error messages to display for the user
     */
    ProjectDTO getProject(int pid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void assignUserToProject(int id, int role, int pid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void unAssignUserFromProject(int id, int pid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    void assignSpeciesToProject(int sid, int pid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    /**
     * Get information about one project
     * 
     * @return a ProjectDTO for a project
     * @param pid the project id for the project to get
     * @param caller the caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException throws error messages to display for the user
     */
    ProjectDTO getDefaultProject(com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    Collection getNonProjectUsers(int pid, com.arexis.mugen.MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;

    Collection getFiles(int pid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    
    Collection getLinks(int pid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    Collection getCategoriesAndResources(int pid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void addResource(java.lang.String type, int category, int project, java.lang.String name, java.lang.String comm, FileDataObject fileData, MugenCaller caller, String url) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void unAssignSpeciesFromProject(int pid, int sid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void unAssignSamplingUnitFromProject(int pid, int suid, MugenCaller caller) throws com.arexis.mugen.exceptions.ApplicationException, java.rmi.RemoteException;

    void log(String txt, String mgnaction, String mgnuser, String remoteadd, String remotehost) throws java.rmi.RemoteException;

    java.util.Collection getRestrictedProjectUsers(int pid, MugenCaller caller) throws ApplicationException, java.rmi.RemoteException;
    
}
