package com.arexis.mugen.samplingunit.grouping;

import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.samplingunit.group.GroupRemoteHome;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.exceptions.PermissionDeniedException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.samplingunit.samplingunitmanager.GroupingDTO;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;

/**
 * This is the bean class for the GroupingBean enterprise bean.
 * Created Jun 8, 2005 9:45:54 AM
 * @author heto
 */
public class GroupingBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.samplingunit.grouping.GroupingRemoteBusiness {
    private javax.ejb.EntityContext context;
    
    private int gsid;
    private String name;
    private String comm;
    private int id;
    private int suid;
    private java.sql.Date ts;
    private boolean dirty;
    
    private UserRemoteHome userHome;
    private GroupRemoteHome grpHome;
    private SamplingUnitRemoteHome samplingUnitHome;
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise beans, Web services)
    // TODO Add business methods
    // TODO Add create methods
    /**
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(javax.ejb.EntityContext aContext) {
        context = aContext;
        userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
        grpHome = (GroupRemoteHome)locator.getHome(ServiceLocator.Services.GROUP);
        samplingUnitHome = (SamplingUnitRemoteHome)locator.getHome(ServiceLocator.Services.SAMPLINGUNIT);
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
        Integer pk = (Integer)context.getPrimaryKey();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete " +
                    "from groupings where gsid=?");
            ps.setInt(1, pk.intValue());
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("GroupingBean#ejbRemove: Could not remove grouping. \n"+e.getMessage());
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
            ps = conn.prepareStatement("select gsid,name,comm,id,ts,suid " +
                    "from groupings where gsid=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                gsid = rs.getInt("gsid");
                name = rs.getString("name");
                comm = rs.getString("comm");
                id = rs.getInt("id");
                ts = rs.getDate("ts");
                suid = rs.getInt("suid");
                
                dirty = false;
            } else
                throw new EJBException("GroupingBean#ejbLoad: Error loading groupings");
        } catch (Exception e) {
            throw new EJBException("GroupingBean#ejbLoad: could not load grouping. \n"+e.getMessage());
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
                ps = conn.prepareStatement("update groupings set name=?,comm=?,id=?,ts=? " +
                        "where gsid=?");

                ps.setString(1, name);
                ps.setString(2, comm);
                ps.setInt(3, id);
                ps.setDate(4, ts);            
                ps.setInt(5, gsid);

                int rows = ps.executeUpdate();

                if (rows!=1) {
                    throw new EJBException("GroupingBean#ejbStore: Error saving groupings. Rows affected "+rows);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("GroupingBean#ejbStore: could not store grouping. \n"+e.getMessage());
            } finally {
                releaseConnection();
                dirty = false;
            }
        }
    }
    
    // </editor-fold>
    
    /**
     * See EJB 2.0 and EJB 2.1 section 12.2.5
     */
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer gsid) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        this.caller = caller;
        try {
            ps = conn.prepareStatement("select gsid from groupings where gsid = ?");
            ps.setInt(1,gsid.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("GroupingBean#ejbFindByPrimaryKey: Cannot find grouping");
            }
        } catch (SQLException se) {
            throw new FinderException("GroupingBean#ejbFindByPrimaryKey: Could not find grouping. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return gsid;
    }
    
    /**
     * Finds a the groupings by the sampling unit it belongs to
     * @param suid The sampling unit id
     * @param caller The current caller object
     * @throws javax.ejb.FinderException If the sampling unit could not be retrieved
     * @return A the sampling unit that the grouping belongs to
     */
    public java.util.Collection ejbFindBySamplingUnit(int suid, MugenCaller caller)
    throws javax.ejb.FinderException {
        makeConnection();
        
        this.caller = caller;
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select gsid, name from groupings where suid = ? order by name");
            ps.setInt(1,suid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("gsid")));
            }
        } catch (SQLException se) {
            throw new EJBException("GroupingBean#ejbFindBySamplingUnit: "+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    /**
     * Returns the grouping id
     * @return The id of the grouping
     */
    public int getGsid() {
        return gsid;
    }
    
    /**
     * Returns the name of the grouping
     * @return The name of the grouping
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of the grouping
     * @param name The name
     */
    public void setName(String name){      
        this.name = name;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());          
        dirty = true;
    }
    
    /**
     * Returns the comment of the grouping
     * @return The comment for the grouping
     */
    public String getComm() {
        return comm;
    }
    
    /**
     * Sets the comment for the grouping
     * @param comm The comment
     */
    public void setComm(String comm){
        this.comm = comm;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());          
        dirty = true;
    }
    
    /**
     * Returns the user which made the last update
     * @return The user
     */
    public UserRemote getUser() {
        try {
            return userHome.findByPrimaryKey(new Integer(id));
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Returns the date for when the grouping was last changed
     * @return The date for when the grouping was last changed
     */
    public java.sql.Date getTs() {
        return ts;
    }
    
    /**
     * Returns the groups that belongs to the grouping
     * @param caller The current caller object
     * @return A collection of groups
     */
    public Collection getGroups(MugenCaller caller) {
        Collection arr = null;
        try {
            arr = grpHome.findByGrouping(gsid, caller, caller.getSuid());
        } catch (Exception e) {
            throw new EJBException("GroupingBean#getGroups: Unable to get groups. \n"+e.getMessage());
        }
        return arr;
    }
    
    /**
     * Returns the sampling unit id for the group
     * @return The sampling unit id
     */
    public int getSuid() {
        return suid;
    }
    
    /**
     * sets the sampling unit id
     * @param suid The sampling unit id
     */
    public void setSuid(int suid) {
        this.suid = suid;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());      
        dirty = true;
    }
    
    /**
     * Returns the sampling unit for the grouping
     * @return The sampling unit
     */
    public SamplingUnitRemote getSamplingUnit() {
        SamplingUnitRemote s = null;
        try {
            s = samplingUnitHome.findByPrimaryKey(new Integer(suid));
        } catch (Exception e) {
            throw new EJBException("GroupingBean#getSamplingUnit: Unable to get sampling unit. \n"+e.getMessage());
        }
        return s;
    }
    
    /**
     * Creates a new grouping
     * @param gsid The id of the new grouping
     * @param name The name of the grouping
     * @param comm The comment for the new grouping
     * @param samplingUnit The sampling unit for the grouping
     * @param caller The current caller object
     * @throws javax.ejb.CreateException If the grouping could not be created
     * @return The key for the new grouping
     */
    public java.lang.Integer ejbCreate(int gsid, java.lang.String name, java.lang.String comm, com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote samplingUnit, com.arexis.mugen.MugenCaller caller) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        this.caller = caller;
        try {
            this.gsid = gsid;;
            this.name = name;
            this.comm = comm;
            this.suid = samplingUnit.getSuid();
            this.ts = new java.sql.Date(System.currentTimeMillis());
            this.id = caller.getId();
            pk = new Integer(gsid);
            
            
            PreparedStatement ps = conn.prepareStatement("insert into groupings (gsid,name,comm,suid,id,ts) values (?,?,?,?,?,?)");
            ps.setInt(1, gsid);
            ps.setString(2, this.name);
            ps.setString(3, this.comm);
            ps.setInt(4, samplingUnit.getSuid());
            ps.setInt(5, id);
            ps.setDate(6, ts);
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            throw new CreateException("GroupingBean#ejbCreate: Unable to create grouping. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }
    
    /**
     * 
     * @param gsid 
     * @param name 
     * @param comm 
     * @param samplingUnit 
     * @param caller 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int gsid, java.lang.String name, java.lang.String comm, com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote samplingUnit, com.arexis.mugen.MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }

    /**
     * Sets the caller
     * @param caller The caller
     */
    public void setCaller(com.arexis.mugen.MugenCaller caller) {
        this.caller = caller;
        dirty = true;
    }
    
    /**
     * Writes a log entry to track changes history
     * @throws com.arexis.mugen.exceptions.PermissionDeniedException If the caller does not have GRP_W privilege
     */
    public void addHistory() throws PermissionDeniedException {
        if (!caller.hasPrivilege("GRP_W"))
            throw new PermissionDeniedException("Permission denied. Unable to write history."); 
        
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into groupings_log (gsid, name, comm, id, ts) values (?, ?, ?, ?, ?)");
            ps.setInt(1, gsid);
            ps.setString(2, name);
            ps.setString(3, comm);
            ps.setInt(4, caller.getId());
            ps.setDate(5, ts);
                                                
            ps.execute();

        } catch (Exception e) {
            throw new EJBException("GroupingBean#addHistory: Error writing history for grouping. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }

    /**
     * Returns a collection of history log entries for the grouping
     * @return A collection of history log entries
     */
    public Collection getHistory() {               
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select * from groupings_log where gsid = ? order by ts desc");
            ps.setInt(1, gsid);
            result = ps.executeQuery();
            GroupingDTO dto = null;

            while (result.next()) {
                dto = new GroupingDTO(result.getInt("gsid"), result.getString("name"), result.getString("comm"), "", result.getDate("ts"), caller, 0);
                
                arr.add(dto);
            }
        } catch (Exception se) {
            throw new EJBException("GroupingBean#getHistory: unable to find groupings history. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }    
    
    /**
     * Returns the number of groups that belongs to a certain grouping
     * @return The number of groups in a certain grouping
     * @throws com.arexis.mugen.exceptions.ApplicationException If the number of groups could not be retrieved
     */
    public int getNumberOfGroups() throws ApplicationException {
        makeConnection();
        
        int num = 0;
        int groupingsId = this.gsid;
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select count(gid) as num from groups where gsid = ?");
            ps.setInt(1,groupingsId);
            result = ps.executeQuery();
            
            if (result.next()) {
                num = result.getInt("num");
            }
        } catch (SQLException se) {
            throw new ApplicationException("GroupBean#ejbHomeGetNumberOfGroups: Cannot count the groups in grouping "+groupingsId+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return num;
    }

    public Integer ejbFindByNameAndSamplingUnit(String name, int suid, MugenCaller caller) throws javax.ejb.FinderException
    {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        this.caller = caller;
        Integer gsid = null;
        try {
            ps = conn.prepareStatement("select gsid from groupings where suid = ? and name=?");
            ps.setInt(1,suid);
            ps.setString(2, name);
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("GroupingBean#ejbFindByNameAndSamplingUnit: Cannot find grouping");
            }
            else
            {
                gsid = new Integer(result.getInt("gsid"));
            }
        } catch (SQLException se) {
            throw new FinderException("GroupingBean#ejbFindByNameAndSamplingUnit: Could not find grouping. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return gsid;
    }
    
}
