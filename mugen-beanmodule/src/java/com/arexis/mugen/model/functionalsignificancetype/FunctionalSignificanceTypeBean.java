package com.arexis.mugen.model.functionalsignificancetype;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.functionalsignificance.FunctionalSignificanceRemoteHome;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.project.ProjectRemoteHome;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.search.Keyword;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.*;

/**
 * This is the bean class for the FunctionalSignificanceTypeBean enterprise bean.
 * Created Dec 14, 2005 1:20:50 PM
 * @author heto
 */
public class FunctionalSignificanceTypeBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.model.functionalsignificancetype.FunctionalSignificanceTypeRemoteBusiness {
    private javax.ejb.EntityContext context;
    
    private int fstid, id, pid;
    private String name, comm;
    private java.sql.Date ts;
    
    private ProjectRemoteHome projectHome;
    private UserRemoteHome userHome;
    private FunctionalSignificanceRemoteHome functionalSigHome;  
    private boolean dirty;
    
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise beans, Web services)
    // TODO Add business methods
    // TODO Add create methods
    /**
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(javax.ejb.EntityContext aContext) {
        context = aContext;
        projectHome = (ProjectRemoteHome)locator.getHome(ServiceLocator.Services.PROJECT);
        userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
        functionalSigHome = (FunctionalSignificanceRemoteHome)locator.getHome(ServiceLocator.Services.FUNCTIONALSIGNIFICANCE);
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
            ps = conn.prepareStatement("delete from functional_significance_type where fstid=?");
            ps.setInt(1, fstid);
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("FunctionalSignificanceTypeBean#ejbRemove: Unable to delete functional significance type. \n"+e.getMessage());
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
            ps = conn.prepareStatement("select fstid,name,comm,pid,id,ts " +
                    "from functional_significance_type where fstid=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                fstid = rs.getInt("fstid");
                name = rs.getString("name");
                comm = rs.getString("comm");
                id = rs.getInt("id");
                ts = rs.getDate("ts");
                pid = rs.getInt("pid");
                dirty = false;
            } else
                throw new EJBException("FunctionalSignificanceTypeBean#ejbLoad: Error loading FunctionalSignificanceType");
        } catch (Exception e) {
            throw new EJBException("FunctionalSignificanceTypeBean#ejbLoad: error loading FunctionalSignificanceType. \n"+e.getMessage());
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
                ps = conn.prepareStatement("update functional_significance_type set name=?,comm=?,pid=?,id=?, ts=? " +
                        "where fstid=?");

                ps.setString(1, name);
                ps.setString(2, comm);
                ps.setInt(3, pid);
                ps.setInt(4, id);
                ps.setDate(5, new Date(System.currentTimeMillis()));
                ps.setInt(6, fstid);
               

                ps.execute();
            } catch (Exception e) {
                throw new EJBException("FunctionalSignificanceTypeBean#ejbStore: error storing FunctionalSignificanceType. \n"+e.getMessage());
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
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer key) throws javax.ejb.FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select fstid from functional_significance_type where fstid = ?");
            ps.setInt(1,key.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("FunctionalSignificanceTypeBean#ejbFindByPrimaryKey: Cannot find FunctionalSignificanceType. No next in resultset");
            }
        } catch (SQLException se) {
            throw new FinderException("FunctionalSignificanceTypeBean#ejbFindByPrimaryKey: Cannot find FunctionalSignificanceType. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return key;
    }

    /**
     * Returns the functional significance type id
     * @return The functional significance type id
     */
    public int getFstid() {
        return fstid;
    }

    /**
     * Returns the name for the functional significance type
     * @return The name for the functional significance type
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for the functional significance type
     * @param name The new name for the functional significance type
     */
    public void setName(String name) {
        this.name = name;
        dirty = true;
    }

    /**
     * Returns the comment for the functional significance type
     * @return The comment for the functional significance type
     */
    public String getComm() {
        return comm;
    }

    /**
     * Sets the comment for the functional significance type
     * @param comm The new comment for the functional significance type
     */
    public void setComm(String comm) {
        this.comm = comm;
        dirty= true;
    }

    /**
     * Create a FuncitonalSignificanceType
     * @param fstid Functional Significance Type ID
     * @param name The name
     * @param pid the project id 
     * @param caller is the mugenCaller object
     * @throws javax.ejb.CreateException 
     * @return 
     */
    public java.lang.Integer ejbCreate(int fstid, java.lang.String name, int pid, MugenCaller caller) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        try {
            this.fstid = fstid;
            this.name = name;
            this.comm = "";
            
            
            this.id = caller.getId();
            this.ts = new Date(System.currentTimeMillis());
            
            this.caller = caller;
            
            
            
            pk = new Integer(fstid);
            
            PreparedStatement ps = conn.prepareStatement("insert into functional_significance_type (fstid,name,id,ts,pid) values (?,?,?,?,?)");
            ps.setInt(1, fstid);
            ps.setString(2, name);
            ps.setInt(3, id);
            ps.setDate(4, ts);
            ps.setInt(5, pid);
            
            
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("FunctionalSignificanceTypeBean#ejbCreate: Unable to create FunctionalSignificanceType. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    /**
     * 
     * @param fstid 
     * @param name 
     * @param pid 
     * @param caller 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int fstid, java.lang.String name, int pid, MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }

    /**
     * Finds all functional significance types for a specific project
     * @param pid The project id
     * @throws javax.ejb.FinderException If the functional significance types could not be retrieved
     * @return The functional significance types for the project
     */
    public java.util.Collection ejbFindByProject(int pid) throws javax.ejb.FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        Collection arr = new ArrayList();
        try {
            ps = conn.prepareStatement("select fstid from functional_significance_type where pid = ?");
            ps.setInt(1, pid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("fstid")));
            }
        } catch (SQLException se) {
            throw new FinderException("FunctionalSignificanceTypeBean#ejbFindByProject: Cannot find FunctionalSignificanceType by project. pid="+pid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    /**
     * Returns the project that the functional significance type belongs to
     * @return The project that the functional significance type belongs to
     */
    public ProjectRemote getProject() {
        try
        {
            ProjectRemote project = projectHome.findByPrimaryKey(new Integer(pid), null);
            return project;
        }
        catch (Exception e)
        {
            throw new EJBException("Could not get project");
        }
    }

    /**
     * Returns all functional significances for the functional significance type
     * @throws com.arexis.mugen.exceptions.ApplicationException If the functional significances could not be retrieved
     * @return The functional significances for the functional significance type
     */
    public Collection getFunctionalSignificances() throws ApplicationException {
        try
        {
            
            Collection funcSigs = functionalSigHome.findByType(fstid);
            return funcSigs;
        }
        catch (Exception e)
        {
            throw new EJBException("Could not get functional significances");
        }
    }

    /**
     * Returns the date for when the functional significance type was last changed
     * @return The date for when the functional significance type was last changed
     */
    public java.sql.Date getTs() {
        return ts;
    }
    
    /**
     * The username of the user which made the latest changes on the functional significance type
     * @return The username of the user which made the latest changes on the functional significance type
     */
    public UserRemote getUser() {
        try
        {
            UserRemote usr = userHome.findByPrimaryKey(new Integer(id));
            return usr;
        }
        catch (Exception e)
        {
            throw new EJBException("Could not get user");
        }
    }
    
    /**
     * Sets the caller for the functional significance
     * @param caller The caller
     */
    public void setCaller(MugenCaller caller) {
        this.caller = caller;
    }

    public Collection ejbFindByKeyword(Keyword keyword, MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        int key = 0;
        Collection arr = new ArrayList();
        try {
            ps = conn.prepareStatement("select fstid from functional_significance_type where lower(name) like ? or lower(comm) like ?");
            
            String search = "%"+keyword.getKeyword()+"%";
            
            ps.setString(1, search);
            ps.setString(2, search);
            result = ps.executeQuery();
            
            while (result.next())
            {
                arr.add(new Integer(result.getInt("fstid")));
            }
        } catch (SQLException se) {
            throw new FinderException("FunctionalSignificanceTypeBean#ejbFindByKeyword: Cannot find functional significance type. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    
}
