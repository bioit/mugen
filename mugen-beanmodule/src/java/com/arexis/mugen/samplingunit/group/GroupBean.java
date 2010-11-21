package com.arexis.mugen.samplingunit.group;
import com.arexis.mugen.exceptions.PermissionDeniedException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.samplingunit.grouping.GroupingRemote;
import com.arexis.mugen.samplingunit.grouping.GroupingRemoteHome;
import com.arexis.mugen.samplingunit.individual.IndividualRemote;
import com.arexis.mugen.samplingunit.individual.IndividualRemoteHome;
import com.arexis.mugen.samplingunit.samplingunitmanager.GroupDTO;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.*;

/**
 * This is the bean class for the GroupBean enterprise bean.
 * Created Jun 8, 2005 4:35:54 PM
 * @author heto
 */
public class GroupBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.samplingunit.group.GroupRemoteBusiness {
    private javax.ejb.EntityContext context;
    
    private int gid;
    private String name;
    private String comm;
    private int id;
    private int gsid;
    private java.sql.Date date;
    private boolean dirty;
    
    private GroupingRemoteHome groupingHome;
    private UserRemoteHome userHome;
    private IndividualRemoteHome indHome;
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise beans, Web services)
    // TODO Add business methods
    // TODO Add create methods
    /**
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(javax.ejb.EntityContext aContext) {
        context = aContext;
        groupingHome = (GroupingRemoteHome)locator.getHome(ServiceLocator.Services.GROUPING);
        userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
        indHome = (IndividualRemoteHome)locator.getHome(ServiceLocator.Services.INDIVIDUAL);
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbActivate()
     */
    public void ejbActivate() {
        
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbPassivate()
     */
    public void ejbPassivate() {
        
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbRemove()
     */
    public void ejbRemove() {
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from groups where gid=?");
            ps.setInt(1, gid);
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("GroupBean#ejbRemove: Unable to delete group. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * @see javax.ejb.EntityBean#unsetEntityContext()
     */
    public void unsetEntityContext() {
        context = null;
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbLoad()
     */
    public void ejbLoad() {
        makeConnection();
        Integer pk = (Integer)context.getPrimaryKey();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("select gid,gsid,name,comm,id,ts " +
                    "from groups where gid=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                gid = rs.getInt("gid");
                name = rs.getString("name");
                comm = rs.getString("comm");
                id = rs.getInt("id");
                date = rs.getDate("ts");
                gsid = rs.getInt("gsid");
                
                dirty = false;
            } else
                throw new EJBException("GroupBean#ejbLoad: Error loading group");
        } catch (Exception e) {
            throw new EJBException("GroupBean#ejbLoad: error loading group. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbStore()
     */
    public void ejbStore() {
        if (dirty)
        {
            makeConnection();
            PreparedStatement ps = null;
            try {
                ps = conn.prepareStatement("update groups set name=?,comm=?," +
                        "id=?,ts=? where gid=?");

                ps.setString(1, name);
                ps.setString(2, comm);
                ps.setInt(3, id);
                ps.setDate(4, date);                       
                ps.setInt(5, gid);

                ps.execute();
            } catch (Exception e) {
                throw new EJBException("GroupBean#ejbStore: error storing group. \n"+e.getMessage());
            } finally {
                releaseConnection();
                dirty = false;
            }
        }
    }
    
    // </editor-fold>
    
    /**
     * See EJB 2.0 and EJB 2.1 section 12.2.5
     * @param gid 
     * @param caller 
     * @throws javax.ejb.FinderException 
     * @return 
     */
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer gid) throws javax.ejb.FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select gid from groups where gid = ?");
            ps.setInt(1,gid.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("GroupBean#ejbFindByPrimaryKey: Cannot find group. No next in resultset");
            }
        } catch (SQLException se) {
            throw new FinderException("GroupBean#ejbFindByPrimaryKey: Cannot find group. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return gid;
    }
    
    /**
     * Returns the group id
     * @return The groupd id
     */
    public int getGid() {
        return gid;
    }
    
    /**
     * Returns the grouping that this group belongs to
     * @return The grouping that this group belongs to
     */
    public GroupingRemote getGrouping()
    {
        GroupingRemote grp = null;
        try
        {
            grp = groupingHome.findByPrimaryKey(new Integer(gsid));
            grp.setCaller(caller);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return grp;
    }
    
    /**
     * Returns the name of the group
     * @return The name of the group
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of the group
     * @param name The name of the group
     */
    public void setName(String name) {
        this.name = name;
        id = caller.getId();
        date = new java.sql.Date(System.currentTimeMillis());  
        dirty = true;
    }
    
    /**
     * Returns the date for when the group was last changed
     * @return The date for the last change of the group
     */
    public java.sql.Date getDate() {
        return date;
    }
    
    /**
     * sets the date for when the group was last changed
     * @param date The date for when the group was last changed
     */
    public void setDate(java.sql.Date date) {
        this.date = date;
        dirty = true;
    }
    
    /**
     * Returns the comment for the group
     * @return The comment for the group
     */
    public String getComm() {
        return comm;
    }
    
    /**
     * Sets the comment for the group
     * @param comm The comment
     */
    public void setComm(String comm) {             
        this.comm = comm;
        id = caller.getId();
        date = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }
    
    /**
     * Returns the user that made the last changes on the group
     * @return The user that made the last changes on the group
     */
    public UserRemote getUser()
    {
        UserRemote usr = null;
        try
        {
            usr = userHome.findByPrimaryKey(new Integer(id));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return usr;
    }
    
    
    
    /**
     * Finds all groups that belongs to a certain grouping
     * @param gsid The grouping id for which the groups should belong to
     * @param caller The current caller object
     * @param suid The sampling unit id
     * @throws javax.ejb.FinderException If the groups could not be retrieved
     * @return A collection of groups
     */
    public java.util.Collection ejbFindByGrouping(int gsid, MugenCaller caller, int suid) throws javax.ejb.FinderException {
        makeConnection();
        this.caller = caller;
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {

            ps = conn.prepareStatement("select gid from groups g where gsid = ? order by gid");
            ps.setInt(1,gsid);

            /**
            if(gsid == -1) {
                ps = conn.prepareStatement("select gid from v_groups_3 g where suid = ? and pid = ? order by gid");
                ps.setInt(1,suid);
                ps.setInt(2, caller.getPid());                
            }
            else {
                ps = conn.prepareStatement("select gid from groups g where gsid = ? order by gid");
                ps.setInt(1,gsid);
                ps.setInt(2,suid);
                ps.setInt(3,caller.getPid());
            }
             */
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("gid")));
            }
        } catch (SQLException se) {
            throw new FinderException("GroupBean#ejbFindByGrouping: unable to find groups. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    /**
     * Returns the individuals in the group
     * @return A collection of individuals
     */
    public Collection getIndividuals() {
        Collection arr = null;
        try {
            arr = indHome.findByGroup(gid);
        } catch (FinderException fe) {
            throw new EJBException(fe);
        } catch (RemoteException re) {
            throw new EJBException(re);
        }
        return arr;
    }
    
    /**
     * Creates a new group
     * @return The primary key of the group
     * @param caller The caller
     * @param gid The id of the new group
     * @param name The name of the new group
     * @param grouping The grouping which the group should belong to
     * @param comm The comment for the group
     * @throws javax.ejb.CreateException If the group could not be created
     */
    public java.lang.Integer ejbCreate(int gid, java.lang.String name, GroupingRemote grouping, String comm, MugenCaller caller) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        try {
            this.gid = gid;
            this.name = name;
            this.comm = comm;
            this.gsid = grouping.getGsid();
            date = new java.sql.Date(System.currentTimeMillis());
            id = caller.getId();
            pk = new Integer(gid);
            
            PreparedStatement ps = conn.prepareStatement("insert into groups (gid,name,comm,gsid,id,ts) values (?,?,?,?,?,?)");
            ps.setInt(1, gid);
            ps.setString(2, name);
            ps.setString(3, comm);
            ps.setInt(4, grouping.getGsid());
            ps.setInt(5, id);
            ps.setDate(6, date);
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("GroupBean#ejbCreate: Unable to create group. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }
    
    /**
     * 
     * @param caller 
     * @param gid 
     * @param name 
     * @param grouping 
     * @param comm 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int gid, java.lang.String name, GroupingRemote grouping, String comm, MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }
    
    /**
     * Adds an individual to the group
     * @param individual The individual to add
     * @throws java.rmi.RemoteException If the individual could not be added
     */
    public void addIndividual(IndividualRemote individual) {
        makeConnection();
        int iid = 0;
        try {
            iid = individual.getIid();
            PreparedStatement ps = conn.prepareStatement("insert into r_ind_grp (iid,gid,id,ts) values (?,?,?,?) ");
            ps.setInt(1, individual.getIid());
            ps.setInt(2, gid);
            ps.setInt(3, caller.getId());
            ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
            
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("GroupBean#addIndividual: Unable to add individual "+iid+" to group "+gid);
        } finally {
            releaseConnection();
        }
        
    }
    
    /**
     * Removes an individual from the group
     * @param individual The individual to remove
     * @throws java.rmi.RemoteException If the individual could not be removed
     */
    public void removeIndividual(com.arexis.mugen.samplingunit.individual.IndividualRemote individual) throws RemoteException{
        makeConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("delete from r_ind_grp where iid=? and gid=?");
            ps.setInt(1, individual.getIid());
            ps.setInt(2, gid);
            
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("GroupBean#removeIndividual: Unable to remove individual "+individual.getIid()+" to group "+gid);
        } finally {
            releaseConnection();
        }
        
    }
    
    /**
     * Sets the caller object
     * @param caller The caller
     */
    public void setCaller(MugenCaller caller) {
        this.caller = caller;
        dirty = true;
    }
    
    /**
     * Writes a log entry to track changes history
     * @throws com.arexis.mugen.exceptions.PermissionDeniedException If the caller does not have GR_W privilege
     */
    public void addHistory() throws PermissionDeniedException {
        if (!caller.hasPrivilege("GRP_W"))
            throw new PermissionDeniedException("Permission denied. Unable to write history."); 
        
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into groups_log (gid, name, comm, id, ts) values (?, ?, ?, ?, ?)");
            ps.setInt(1, gid);
            ps.setString(2, name);
            ps.setString(3, comm);
            ps.setInt(4, caller.getId());
            ps.setDate(5, date);
                                                
            ps.execute();

        } catch (Exception e) {
            throw new EJBException("GroupBean#addHistory: Error writing history for group. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }

    /**
     * Returns a collection of history log entries for the group
     * @return A collection of history log entries
     */
    public Collection getHistory() {               
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select * from groups_log where gid = ? order by ts desc");
            ps.setInt(1, gid);
            result = ps.executeQuery();
            GroupDTO dto = null;
            UserRemote ur = null;
            while (result.next()) {
                ur = userHome.findByPrimaryKey(new Integer(result.getInt("id")));
                dto = new GroupDTO(result.getString("name"), result.getString("comm"), ur.getUsr(), result.getDate("ts"));
                
                arr.add(dto);
            }
        } catch (Exception se) {
            throw new EJBException("GroupBean#getHistory: unable to find group history. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    /**
     * Returns the number of objects in the group
     * @return The number of objects that belongs to the group
     */
    public int getNumberOfIndividuals() {
        makeConnection();
        
        int nr = 0;
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select count(r.iid) as num from r_ind_grp r, individuals i where i.iid=r.iid and r.gid = ?");
            ps.setInt(1,gid);
            result = ps.executeQuery();
            
            if (result.next()) {
                nr = result.getInt("num");
            }
        } catch (SQLException se) {
            throw new EJBException("GroupBean#getNumberOfIndividuals: Cannot count individuals by group "+gid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return nr;
    }

    public Integer ejbFindByNameAndGrouping(String name, int gsid, MugenCaller caller) throws javax.ejb.FinderException
    {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        this.caller = caller;
        Integer gid = null;
        try {
            ps = conn.prepareStatement("select gid from groups where gsid = ? and name=?");
            ps.setInt(1, gsid);
            ps.setString(2, name);
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("GroupBean#ejbFindByNameAndGrouping: Cannot find group");
            }
            else
            {
                gid = new Integer(result.getInt("gid"));
            }
        } catch (SQLException se) {
            se.printStackTrace();
            throw new FinderException("GroupBean#ejbFindByNameAndGrouping: Could not find group. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return gid;
    }
}
