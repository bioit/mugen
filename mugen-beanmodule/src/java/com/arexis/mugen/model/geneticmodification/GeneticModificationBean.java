package com.arexis.mugen.model.geneticmodification;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.expmodel.ExpModelRemote;
import com.arexis.mugen.model.expmodel.ExpModelRemoteHome;
import com.arexis.mugen.model.geneontology.GeneOntologyRemoteHome;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.resource.file.FileRemote;
import com.arexis.mugen.resource.file.FileRemoteHome;
import com.arexis.mugen.search.Keyword;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;

/**
 * This is the bean class for the GeneticModificationBean enterprise bean.
 * Created Dec 13, 2005 12:42:54 PM
 * 
 * <code>
 *     CREATE TABLE GENETIC_MODIFICATION 
 *     (
 *          gmid           INT            NOT NULL,
 *          name           VARCHAR        NOT NULL,
 *          comm           VARCHAR        NULL,
 *          id             int            NULL,
 *          ts             timestamp      NULL,
 *          eid            int            NOT NULL,
 *          PRIMARY KEY (gmid),
 *          FOREIGN KEY (EID) REFERENCES MODEL (EID),
 *          FOREIGN KEY (id) REFERENCES USERS (id)
 *     );
 * 
 *     CREATE TABLE R_GENMOD_FILE
 *     (
 *          gmid     int   not null,
 *          fileid   int   not null,
 *          PRIMARY KEY (GMID, FILEID),
 *          FOREIGN KEY (fileid) REFERENCES FILE (fileid),
 *          FOREIGN KEY (gmid) REFERENCES GENETIC_MODIFICATION (gmid)
 *     );
 * </code>
 * @author heto
 */
public class GeneticModificationBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.model.geneticmodification.GeneticModificationRemoteBusiness {
    private javax.ejb.EntityContext context;
    
    private int gmid, id;
    private String name, comm;
    private java.sql.Date ts;
    private int eid;
    
    private boolean dirty;
    
    private ExpModelRemoteHome expModelHome;
    private FileRemoteHome fileHome;
    private GeneOntologyRemoteHome geneOntologyHome;
    private UserRemoteHome userHome;
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise beans, Web services)
    // TODO Add business methods
    // TODO Add create methods
    /**
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(javax.ejb.EntityContext aContext) {
        context = aContext;
        expModelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
        fileHome = (FileRemoteHome)locator.getHome(ServiceLocator.Services.FILE);
        geneOntologyHome = (GeneOntologyRemoteHome)locator.getHome(ServiceLocator.Services.GENEONTOLOGY);
        userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
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
            ps = conn.prepareStatement("delete from genetic_modification where gmid=?");
            ps.setInt(1, gmid);
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("GeneticModificationBean#ebjRemove: Unable to remove genetic modification. \n"+e.getMessage());
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
            ps = conn.prepareStatement("select gmid,name, comm, id, ts, eid " +
                    "from genetic_modification where gmid=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                gmid = rs.getInt("gmid");
                name = rs.getString("name");
                comm = rs.getString("comm");
                id = rs.getInt("id");
                ts = rs.getDate("ts");
                eid = rs.getInt("eid");
                dirty = false;
            } else
                throw new EJBException("GeneticModificationBean#ejbLoad: Error loading genetic modifications");
        } catch (Exception e) {
            throw new EJBException("GeneticModificationBean#ejbLoad: "+e.getMessage());
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
                ps = conn.prepareStatement("update genetic_modification set name=?,comm=?,id=?,ts=? " +
                        "where gmid=?");

                int i=0;
                ps.setString(++i, name);
                ps.setString(++i, comm);
                ps.setInt(++i, id);
                ps.setDate(++i, new java.sql.Date(System.currentTimeMillis()));
                ps.setInt(++i, gmid);
                
                
                int rows = ps.executeUpdate();
                if (rows!=1) {
                    throw new EJBException("GeneticModificationBean#ejbLoad: Error saving genectic modification. Rows affected "+rows);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("GeneticModificationBean#ejbLoad: Error saving genectic modification. \n"+e.getMessage());
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
            ps = conn.prepareStatement("select gmid from genetic_modification where gmid = ?");
            ps.setInt(1,key.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("GeneticModificationBean#ejbFindByPrimaryKey: Cannot find genetic modification");
            }
        } catch (SQLException se) {
            throw new FinderException("GeneticModificationBean#ejbFindByPrimaryKey: Cannot find genetic modification "+gmid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return key;
    }

    /**
     * Returns the genetic modification id
     * @return The genetic modification id
     */
    public int getGmid() {
        return gmid;
    }

    /**
     * Returns the name of the genetic modification
     * @return The name of the genetic modification
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the genetic modification
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());         
        dirty = true;
    }

    /**
     * Returns the comment for the genetic modification
     * @return The comment
     */
    public String getComm() {
        return comm;
    }

    /**
     * Sets the comment for the genetic modification
     * @param comm The comment
     */
    public void setComm(String comm) {
        this.comm = comm;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());         
        dirty = true;
    }

    /**
     * Returns all genetic modifications for a model
     * @param eid The model id
     * @throws javax.ejb.FinderException If the genetic modifications could not be retrieved
     * @return The genetic modifications for the model
     */
    public java.util.Collection ejbFindByModel(int eid) throws javax.ejb.FinderException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select gmid from genetic_modification where eid = ? order by gmid");
            ps.setInt(1,eid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("gmid")));
            }
        } catch (SQLException se) {
            throw new FinderException("GeneticModificationBean#ejbFindByModel: Cannot find genetic modifications by model "+eid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    /**
     * Returns the date for when the latest changes were made on the genetic modification
     * @return The date for when the latest changes were made on the genetic modification
     */
    public Date getTs() {
        return ts;
    }
    
    /**
     * Sets the caller
     * @param caller The caller
     */
    public void setCaller(MugenCaller caller) {
        this.caller = caller;
        dirty = true;
    }    
    
    /**
     * Get all files connected to this Genetic Modification.
     * @throws com.arexis.mugen.exceptions.ApplicationException if errors occurs during finding files.
     * @return a collection of FileRemote
     */
    public Collection getFiles() throws ApplicationException
    {
        try
        {
            Collection arr = fileHome.findByGeneticModification(gmid);
            return arr;
        }
        catch (Exception e)
        {
            throw new ApplicationException("Failed to get files for the Genetic modification.");
        }
    }
    
    /**
     * Adds a file to the genetic modification
     * @param file The file to add
     */
    public void addFile(FileRemote file)
    {
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into R_GENMOD_FILE (fileid, gmid) values (?,?)");

            int i=0;
            ps.setInt(++i, file.getFileId());            
            ps.setInt(++i, gmid);
            int rows = ps.executeUpdate();
            if (rows!=1) {
                throw new EJBException("GeneticModificationBean#addFile: Error adding file to genectic modification.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException("GeneticModificationBean#addFile: Error adding file to genectic modification. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }

    /**
     * Returns the username of the user that made the latest changes on the genetic modification
     * @return The username of the user that made the latest modifications on the genetic modification
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
     * 
     * @param gmid 
     * @param name 
     * @param comm 
     * @param model 
     * @param caller 
     * @throws javax.ejb.CreateException 
     * @return 
     */
    public java.lang.Integer ejbCreate(int gmid, java.lang.String name, String comm, ExpModelRemote model, MugenCaller caller) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        try {
            
            this.caller = caller;
            
            this.gmid = gmid;
            this.eid = model.getEid();
            
            this.comm = comm;
            this.name = name;
            this.id = caller.getId();
            ts = new java.sql.Date(System.currentTimeMillis());
            
            
            pk = new Integer(gmid);
            
            PreparedStatement ps = conn.prepareStatement("insert into genetic_modification (gmid, name, comm, id, ts, eid) values (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, gmid);
            ps.setString(2, name);
            ps.setString(3, comm);
            ps.setInt(4, id);
            ps.setDate(5, ts);
            ps.setInt(6, eid);
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("GeneticModificationBean#ejbCreate: Unable to create GeneticModification. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    /**
     * 
     * @param gmid 
     * @param name 
     * @param comm 
     * @param model 
     * @param caller 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int gmid, java.lang.String name, String comm, ExpModelRemote model, MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }
    
    /**
     * Removes a file attached to the genetic modification
     * @param file The file to remove
     */
    public void removeFile(FileRemote file)
    {
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from R_GENMOD_FILE where fileid = ?  and gmid = ? ");

            int i=0;
            ps.setInt(++i, file.getFileId());            
            ps.setInt(++i, gmid);
            int rows = ps.executeUpdate();
            if (rows!=1) {
                throw new EJBException("GeneticModificationBean#removeFile: Error removing file to genectic modification.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException("GeneticModificationBean#removeFile: Error removing file to genectic modification. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns all gene ontologies for the genetic modification
     * @throws com.arexis.mugen.exceptions.ApplicationException If the ontologies could not be retrieved
     * @return The gene ontologies for the genetic modification
     */
    public Collection getGeneOntologies() throws ApplicationException
    {
        try
        {
            Collection arr = geneOntologyHome.findByGeneticModification(gmid);
            return arr;
        }
        catch (Exception e)
        {
            throw new ApplicationException("Failed to get Gene ontology for the Genetic modification.");
        }
    }

    public Collection ejbFindByKeyword(Keyword keyword, MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        int key = 0;
        Collection arr = new ArrayList();
        try {
            ps = conn.prepareStatement("select gmid from genetic_modification where lower(name) like ? or lower(comm) like ?");
            
            String search = "%"+keyword.getKeyword()+"%";
            
            ps.setString(1, search);
            ps.setString(2, search);
            result = ps.executeQuery();
            
            while (result.next())
            {
                arr.add(new Integer(result.getInt("gmid")));
            }
        } catch (SQLException se) {
            throw new FinderException("GeneticModificationBean#ejbFindByKeyword: Cannot find genetic modifications by keyword. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
}
