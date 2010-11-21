package com.arexis.mugen.model.geneontology;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.model.geneticmodification.GeneticModificationRemote;
import com.arexis.mugen.model.geneticmodification.GeneticModificationRemoteHome;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
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
 * This is the bean class for the GeneOntologyBean enterprise bean.
 * Created Dec 13, 2005 4:37:25 PM
 * <CODE>
 *    CREATE TABLE GENE_ONTOLOGY
 *    (
 *          GOID            INT          NOT NULL,
 *          NAME            VARCHAR      NOT NULL,
 *          gmid            INT          NOT NULL,
 *          id              INT          NULL,
 *          ts              TIMESTAMP    NULL,
 *          PRIMARY KEY (GOID),
 *          FOREIGN KEY (gmid) REFERENCES GENETIC_MODIFICATION(gmid),
 *          FOREIGN KEY (id) REFERENCES USERS (id)
 *    );
 * </CODE>
 * @author heto
 */
public class GeneOntologyBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.model.geneontology.GeneOntologyRemoteBusiness {
    private javax.ejb.EntityContext context;
    
    private int goid;
    private String name, comm, linkid;
    private int gmid;
    private int id;
    private java.sql.Date ts;
    
    private boolean dirty;
    
    private GeneticModificationRemoteHome geneticModificationHome;
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
        geneticModificationHome = (GeneticModificationRemoteHome)locator.getHome(ServiceLocator.Services.GENETICMODIFICATION);
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
            ps = conn.prepareStatement("delete from gene_ontology where goid=?");
            ps.setInt(1, goid);
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("GeneOntologyBean#ebjRemove: Unable to remove gene ontology link. \n"+e.getMessage());
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
            ps = conn.prepareStatement("select goid,name,comm, linkid, gmid,id, ts " +
                    "from gene_ontology where goid=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                goid = rs.getInt("goid");
                name = rs.getString("name");
                comm = rs.getString("comm");
                linkid = rs.getString("linkid");
                gmid = rs.getInt("gmid");
                id = rs.getInt("id");
                ts = rs.getDate("ts");
                dirty = false;
            } else
                throw new EJBException("GeneOntologyBean#ejbLoad: Error loading gene ontology");
        } catch (Exception e) {
            throw new EJBException("GeneOntologyBean#ejbLoad: "+e.getMessage());
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
                ps = conn.prepareStatement("update gene_ontology set name=?,comm=?,linkid=?,id=?,ts=? " +
                        "where goid=?");

                int i=0;
                ps.setString(++i, name);
                ps.setString(++i, comm);
                ps.setString(++i, linkid);
                ps.setInt(++i, id);
                ps.setDate(++i, new java.sql.Date(System.currentTimeMillis()));
                ps.setInt(++i, goid);
                
                
                int rows = ps.executeUpdate();
                if (rows!=1) {
                    throw new EJBException("GeneOntologyBean#ejbLoad: Error saving gene ontology. Rows affected "+rows);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("GeneOntologyBean#ejbLoad: Error saving gene ontology. \n"+e.getMessage());
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
            ps = conn.prepareStatement("select goid from gene_ontology where goid = ?");
            ps.setInt(1,key.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("GeneOntologyBean#ejbFindByPrimaryKey: Cannot find gene ontology");
            }
        } catch (SQLException se) {
            throw new FinderException("GeneOntologyBean#ejbFindByPrimaryKey: Cannot find gene ontology "+goid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return key;
    }

    /**
     * Returns the id of the gene ontology
     * @return The gene ontology id
     */
    public int getGoid() {
        return goid;
    }

    /**
     * Returns the name of the gene ontology
     * @return The name of the gene ontology
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the gene ontology
     * @param name The name of the gene ontology
     */
    public void setName(String name) {
        this.name = name;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());         
        dirty = true;        
    }

    /**
     * Returns the comment for the gene ontology
     * @return The comment for the gene ontology
     */
    public String getComm() {
        return comm;
    }

    /**
     * Sets the comment for the gene ontology
     * @param comm The comment
     */
    public void setComm(String comm) {
        this.comm = comm;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());         
        dirty = true;        
    }

    /**
     * Returns the id for the link of this gene ontology
     * @return The link id
     */
    public String getLinkid() {
        return linkid;
    }

    /**
     * Sets the link id for the gene ontology
     * @param linkid The link id
     */
    public void setLinkid(String linkid) {
        this.linkid = linkid;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());         
        dirty = true;        
    }

    /**
     * Returns the username of the user that made the latest changes on the gene ontology
     * @return The name of the user that made the latest changes on the gene ontology
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
     * Finds gene ontologies for the genetic modification
     * @param gmid The genetic modification id
     * @throws javax.ejb.FinderException If the gene ontologies could not be retrieved
     * @return The gene ontologies for the genetic modification
     */
    public java.util.Collection ejbFindByGeneticModification(int gmid) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        
        Collection arr = new ArrayList();
        try {
            ps = conn.prepareStatement("select goid from gene_ontology where gmid = ?");
            ps.setInt(1, gmid);
            result = ps.executeQuery();
            
            while (result.next()) 
            {
                arr.add(new Integer(result.getInt("goid")));
            }
        } catch (SQLException se) {
            throw new FinderException("GeneOntologyBean#ejbFindByGeneticModification: Cannot find gene ontology goid="+goid+", gmid="+gmid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return arr;
    }

    /**
     * 
     * @param goid 
     * @param linkid 
     * @param name 
     * @param comm 
     * @param gm 
     * @param caller 
     * @throws javax.ejb.CreateException 
     * @return 
     */
    public java.lang.Integer ejbCreate(int goid, java.lang.String linkid, java.lang.String name, String comm, GeneticModificationRemote gm, MugenCaller caller) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        try {
            this.goid = goid;
            this.name = name;
            this.comm = comm;
            this.linkid = linkid;
            this.gmid = gm.getGmid();
            
            ts = new java.sql.Date(System.currentTimeMillis());
            id = caller.getId();
            
            pk = new Integer(goid);
            
            PreparedStatement ps = conn.prepareStatement("insert into gene_ontology (goid,name,comm,linkid,gmid,id,ts) values (?,?,?,?,?,?,?)");
            ps.setInt(1, goid);
            ps.setString(2, name);
            ps.setString(3, comm);
            ps.setString(4, linkid);
            ps.setInt(5, gmid);
            ps.setInt(6, id);
            ps.setDate(7, ts);
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("GeneOntologyBean#ejbCreate: Unable to create gene ontology. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    /**
     * 
     * @param goid 
     * @param linkid 
     * @param name 
     * @param comm 
     * @param gm 
     * @param caller 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int goid, java.lang.String linkid, java.lang.String name, String comm, GeneticModificationRemote gm, MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }

    /**
     * Sets the caller for the gene ontology
     * @param caller The caller
     */
    public void setCaller(MugenCaller caller) {
        this.caller = caller;
    }

    /**
     * Return the genetic modification for the gene ontology
     * @return The genetic modification
     */
    public GeneticModificationRemote getGeneticModification() {
        try {
            return geneticModificationHome.findByPrimaryKey(new Integer(gmid));
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * The date for when the gene ontology was last modified
     * @return The date for the last modification
     */
    public Date getTs() {
        return ts;
    }
}
