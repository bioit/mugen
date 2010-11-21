package com.arexis.mugen.model.functionalsignificance;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.expmodel.ExpModelRemote;
import com.arexis.mugen.model.expmodel.ExpModelRemoteHome;
import com.arexis.mugen.model.functionalsignificancetype.FunctionalSignificanceTypeRemote;
import com.arexis.mugen.model.functionalsignificancetype.FunctionalSignificanceTypeRemoteHome;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.resource.file.FileRemote;
import com.arexis.mugen.resource.file.FileRemoteHome;
import com.arexis.mugen.search.Keyword;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.sql.Date;
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
 * This is the bean class for the FunctionalSignificanceBean enterprise bean.
 * Created Dec 14, 2005 1:34:21 PM
 * @author heto
 */
public class FunctionalSignificanceBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.model.functionalsignificance.FunctionalSignificanceRemoteBusiness {
    private javax.ejb.EntityContext context;
    
    private int fsid, fileid, id, fstid, eid;
    private String name, comm;
    private Date ts;
    
    private boolean dirty;
    
    private FileRemoteHome fileHome;
    private UserRemoteHome userHome;
    private FunctionalSignificanceTypeRemoteHome fstHome;
    private ExpModelRemoteHome modelHome;
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise beans, Web services)
    // TODO Add business methods
    // TODO Add create methods
    /**
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(javax.ejb.EntityContext aContext) {
        context = aContext;
        fileHome = (FileRemoteHome)locator.getHome(ServiceLocator.Services.FILE);
        userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);        
        fstHome = (FunctionalSignificanceTypeRemoteHome)locator.getHome(ServiceLocator.Services.FUNCTIONALSIGNIFICANCETYPE);
        modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
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
            ps = conn.prepareStatement("delete from functional_significance where fsid=?");
            ps.setInt(1, fsid);
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("FunctionalSignificanceBean#ejbRemove: Unable to delete functional significance.\n"+e.getMessage());
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
            ps = conn.prepareStatement("select fsid,eid,name,comm,fileid, id,ts, fstid " +
                    "from functional_significance where fsid=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                fsid = rs.getInt("fsid");
                eid = rs.getInt("eid");
                name = rs.getString("name");
                comm = rs.getString("comm");
                fileid = rs.getInt("fileid");
                id = rs.getInt("id");
                ts = rs.getDate("ts");
                fstid = rs.getInt("fstid");
                dirty = false;
            } else
                throw new EJBException("FunctionalSignificanceBean#ejbLoad: Error loading FunctionalSignificance");
        } catch (Exception e) {
            throw new EJBException("FunctionalSignificanceBean#ejbLoad: error loading FunctionalSignificance. \n"+e.getMessage());
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
                ps = conn.prepareStatement("update functional_significance set name=?,comm=?,id=?, ts=?, fstid=?, fileid=? " +
                        "where fsid=?");

                ps.setString(1, name);
                ps.setString(2, comm);
                ps.setInt(3, id);
                ps.setDate(4, new Date(System.currentTimeMillis()));
                ps.setInt(5, fstid);
                if (fileid != 0)
                    ps.setInt(6, fileid); 
                else
                    ps.setNull(6, java.sql.Types.INTEGER);                
                ps.setInt(7, fsid);

               

                ps.execute();
            } catch (Exception e) {
                throw new EJBException("FunctionalSignificanceBean#ejbStore: error storing FunctionalSignificance. \n"+e.getMessage());
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
            ps = conn.prepareStatement("select fsid from functional_significance where fsid = ?");
            ps.setInt(1,key.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("FunctionalSignificanceBean#ejbFindByPrimaryKey: Cannot find FunctionalSignificance. No next in resultset");
            }
        } catch (SQLException se) {
            throw new FinderException("FunctionalSignificanceBean#ejbFindByPrimaryKey: Cannot find FunctionalSignificance. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return key;
    }

    /**
     * Returns the id of the functional signficance
     * @return The id of the functional significance
     */
    public int getFsid() {
        return fsid;
    }

    /**
     * Returns the name of the functional significance
     * @return The name of the functional significance
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the functional significance
     * @param name The name of the functional significance
     */
    public void setName(String name) {
        this.name = name;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis()); 
        dirty=true;
    }

    /**
     * Returns the comment for the functional significance
     * @return The comment for the functional significance
     */
    public String getComm() {
        return comm;
    }

    /**
     * Sets the comment for the functional significance
     * @param comm The comment
     */
    public void setComm(String comm) {
        this.comm = comm;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis()); 
        dirty=true;
    }

    /**
     * Returns the date for the last modification of the functional significance
     * @return The date for when the functional significance was last changed
     */
    public Date getTs() {
        return ts;
    }
    
    /**
     * Returns the username of the user that made the latest changes on the functional significance
     * @return The username of the user that made the latest changes on the functionals significance
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
     * Returns the file attached to the functional significance
     * @return The file attached to the functional significance
     */
    public FileRemote getFile()
    {
        try
        {
            FileRemote file = null;
            if(fileid > 0)
                file = fileHome.findByPrimaryKey(new Integer(fileid));
            return file;
        }
        catch (Exception e)
        {
            throw new EJBException("Could not get file");
        }
    }
    
    /**
     * Attaches a file to the functional significance
     * @param file The file to attach
     * @throws com.arexis.mugen.exceptions.ApplicationException If the file could not be attached
     */
    public void setFile(FileRemote file) throws ApplicationException
    {
        try
        {
            fileid = file.getFileId();
            dirty = true;
        }
        catch (Exception e)
        {
            throw new ApplicationException("Could not set file");
        }
    }

    /**
     * 
     * @param fsid 
     * @param name 
     * @param comm 
     * @param model 
     * @param caller 
     * @param type 
     * @throws javax.ejb.CreateException 
     * @return 
     */
    public java.lang.Integer ejbCreate(int fsid, java.lang.String name, String comm, ExpModelRemote model, MugenCaller caller, FunctionalSignificanceTypeRemote type) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        try {
            this.fsid = fsid;
            this.name = name;
            this.comm = comm;
            this.fstid = 0;
            
            if(type != null)
                fstid = type.getFstid();
            
            this.fileid = 0;
            this.id = caller.getId();
            this.ts = new Date(System.currentTimeMillis());
            
            this.caller = caller;
            
            this.eid = model.getEid();
            
            pk = new Integer(fsid);
            
            PreparedStatement ps = conn.prepareStatement("insert into functional_significance (fsid,name,comm,eid,fstid,id,ts) values (?,?,?,?,?,?,?)");
            ps.setInt(1, fsid);
            ps.setString(2, name);
            ps.setString(3, comm);            
            ps.setInt(4, eid);
            ps.setInt(5, fstid);
            ps.setInt(6, id);
            ps.setDate(7, ts);
            
            
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("FunctionalSignificanceBean#ejbCreate: Unable to create FunctionalSignificance. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    /**
     * 
     * @param fsid 
     * @param name 
     * @param comm 
     * @param model 
     * @param caller 
     * @param type 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int fsid, java.lang.String name, String comm, ExpModelRemote model, MugenCaller caller, FunctionalSignificanceTypeRemote type) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }

    /**
     * Finds all functional significances for a specific model
     * @param eid The model id
     * @throws javax.ejb.FinderException If the models could not be found
     * @return The functional significances for the specified model
     */
    public java.util.Collection ejbFindByModel(int eid) throws javax.ejb.FinderException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select fsid, fstid from functional_significance where eid = ? order by fstid, fsid");
            ps.setInt(1,eid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("fsid")));
            }
        } catch (SQLException se) {
            throw new FinderException("FunctionalSignificanceBean#ejbFindByModel: Cannot find FunctionalSignificance by model "+eid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    /**
     * Sets the caller for the functional significance
     * @param caller The caller
     */
    public void setCaller(MugenCaller caller) {
        this.caller = caller;
    }

    /**
     * Finds all functional significances for a sampling unit
     * @param suid The sampling unit id
     * @throws javax.ejb.FinderException If the functional significances could not be retrieved
     * @return The functional significances for the sampling unit
     */
    public java.util.Collection ejbFindBySamplingUnit(int suid) throws javax.ejb.FinderException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select fsid, fstid from functional_significance fs, model m, expobj eo where eo.suid = ? and eo.eid = m.eid and m.eid = fs.eid order by fstid, fsid");
            ps.setInt(1,suid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("fsid")));
            }
        } catch (SQLException se) {
            throw new FinderException("FunctionalSignificanceBean#ejbFindBySamplingUnit: Cannot find FunctionalSignificance by sampling unit "+suid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    /**
     * Returns the model that this functional significance belongs to
     * @throws com.arexis.mugen.exceptions.ApplicationException If the model could not be retrieved
     * @return The model that the functional significance belongs to
     */
    public ExpModelRemote getModel() throws ApplicationException {
        try
        {
            if(eid > 0)
                return modelHome.findByPrimaryKey(new Integer(eid));
           return null;
        }
        catch (Exception e)
        {
            throw new ApplicationException("Failed to get model");
        }
    }

    /**
     * Finds all functional significances for a certain functional significance type
     * @param type The functional significance type
     * @throws javax.ejb.FinderException If the functional significances could not be retrieved
     * @return The functional significances for the type
     */
    public java.util.Collection ejbFindByType(int type) throws javax.ejb.FinderException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select fsid, fstid from functional_significance where fstid = ? order by fstid, fsid");
            ps.setInt(1,type);
           
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("fsid")));
            }
           
        } catch (SQLException se) {
            throw new FinderException("FunctionalSignificanceBean#ejbFindByModel: Cannot find FunctionalSignificance by model "+eid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    /**
     * Sets the type of functional significance
     * @param fst The functional significance type
     * @throws com.arexis.mugen.exceptions.ApplicationException If the type could not be set
     */
    public void setType(FunctionalSignificanceTypeRemote fst) throws ApplicationException
    {
        try
        {
            fstid = fst.getFstid();
            id = caller.getId();
            dirty = true;
        }
        catch (Exception e)
        {
            throw new ApplicationException("Failed to set type");
        }
    }
    
    /**
     * Returns the type of functional significance
     * @throws com.arexis.mugen.exceptions.ApplicationException If the type could not be retrived
     * @return The functional significance type
     */
    public FunctionalSignificanceTypeRemote getType() throws ApplicationException
    {
        try
        {
            if(fstid > 0)
                return fstHome.findByPrimaryKey(new Integer(fstid));
           return null;
        }
        catch (Exception e)
        {
            throw new ApplicationException("Failed to get type");
        }
    }

    public Collection ejbFindByKeyword(Keyword keyword, MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        int key = 0;
        Collection arr = new ArrayList();
        try {
            ps = conn.prepareStatement("select fsid from functional_significance where lower(name) like ? or lower(comm) like ?");
            
            String search = "%"+keyword.getKeyword()+"%";
            
            ps.setString(1, search);
            ps.setString(2, search);
            result = ps.executeQuery();
            
            while (result.next())
            {
                arr.add(new Integer(result.getInt("fsid")));
            }
        } catch (SQLException se) {
            throw new FinderException("GeneBean#ejbFindByName: Cannot find Gene. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
}
