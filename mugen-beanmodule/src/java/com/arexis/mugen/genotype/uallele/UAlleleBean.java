package com.arexis.mugen.genotype.uallele;

import com.arexis.mugen.genotype.genotypemanager.UAlleleDTO;
import com.arexis.mugen.genotype.umarker.UMarkerRemote;
import com.arexis.mugen.genotype.umarker.UMarkerRemoteHome;
import com.arexis.mugen.MugenCaller;
import javax.ejb.*;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This is the bean class for the UAlleleBean enterprise bean.
 * Created Jun 15, 2005 3:35:55 PM
 * @todo id and ts from relational table. Dummy data in create()
 * @author lami
 */
public class UAlleleBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.genotype.uallele.UAlleleRemoteBusiness {
    private javax.ejb.EntityContext context;
    
    private int uaid, umid, id;
    private String name, comm;
    private java.sql.Date ts;
    
    private boolean dirty;
    
    private UMarkerRemoteHome uMarkerHome;
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise beans, Web services)
    // TODO Add business methods
    // TODO Add create methods
    /**
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(javax.ejb.EntityContext aContext) {
        context = aContext;
        uMarkerHome = (UMarkerRemoteHome)locator.getHome(ServiceLocator.Services.UMARKER);
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
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("delete from u_alleles where uaid = ?");
            ps.setInt(1, getUaid());
            
            int res = ps.executeUpdate();
            
            if (res!=1) {
                throw new EJBException("UAlleleBean#ejbRemove: annot remove UAllele");
            }
        } catch (SQLException se) {
            throw new EJBException("UAlleleBean#ejbRemove: SQL exception", se);
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
        
        PreparedStatement ps = null;
        ResultSet result = null;
        Integer pk = (Integer)context.getPrimaryKey();
        try {
            ps = conn.prepareStatement("select name,comm,umid, id, ts from u_alleles where uaid = ?");
            ps.setInt(1,pk.intValue());
            
            result = ps.executeQuery();
            
            if (result.next()) {
                uaid = pk.intValue();
                name = result.getString("name");
                comm = result.getString("comm");
                umid = result.getInt("umid");
                ts = result.getDate("ts");
                id = result.getInt("id");
                dirty = false;
            } else
                throw new EJBException("UAlleleBean#ejbLoad: Empty resultset?");
        } catch (SQLException se) {
            throw new EJBException("UAlleleBean#ejbLoad: SQL Exception", se);
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
            try {
                PreparedStatement ps = null;
                ps = conn.prepareStatement("update u_alleles set uaid = ? ," +
                        " name = ?, comm = ?, umid = ?, id = ?, ts = ? where uaid = ?");

                ps.setInt(1, uaid);
                ps.setString(2, name);
                ps.setString(3, comm);
                ps.setInt(4, umid);
                ps.setInt(5, id);
                ps.setDate(6, ts);
                ps.setInt(7, uaid);

                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("UAlleleBean#ejbStore: Error updating Uallele [Uaid="+uaid+"]");
            } finally {
                releaseConnection();
                dirty = false;
            }                
        }
    }
    
    // </editor-fold>

    /**
     * Returns the UAllele id
     * @return The UAllele id
     */
    public int getUaid() {
        return uaid;
    }

    /**
     * Sets the name of the UAllele
     * @param name The new name
     */
    public void setName(java.lang.String name) {
        this.name = name;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());     
        dirty = true;
    }

    /**
     * Returns the name of the UAllele
     * @return The name of the UAllele
     */
    public String getName() {        
        return name;
    }

    /**
     * Sets the comment for the UAllele
     * @param comm The new comment
     */
    public void setComm(java.lang.String comm) {
        this.comm = comm;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());     
        dirty = true;
    }

    /**
     * Returns the comment for the UAllele
     * @return The comment for the UAllele
     */
    public String getComm() {        
        return comm;
    }

    /**
     * Creates a new unified allele
     * @return The primary key for this unified allele
     * @param caller The current caller object
     * @param uaid The id for this unified allele
     * @param name The name
     * @param comm The comment
     * @param umid The unified marker id
     * @throws CreateException If the unified allele could not be created
     */
    public java.lang.Integer ejbCreate(int uaid, int umid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException {
        this.name = name;
        this.name = comm;
        this.uaid = uaid; 
        this.umid = umid;
        this.id = caller.getId();
        this.ts = new java.sql.Date(System.currentTimeMillis());
        
        makeConnection();
            
        try { 
            PreparedStatement ps = null;
            ps = conn.prepareStatement("insert into u_alleles(uaid, name, comm, umid, id, " +
                    "ts) values (?,?,?,?,?,?)");
            ps.setInt(1, uaid);
            ps.setString(2, name.toUpperCase());
            ps.setString(3, comm);
            ps.setInt(4, umid);
            ps.setInt(5, id);
            ps.setDate(6, ts);
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
                throw new CreateException("UAlleleBean#ejbCreate: Unable to create UAllele: "+e.getMessage());
        } finally {
            releaseConnection();
        }               
                
        return new Integer(uaid);
    }

    /**
     * Returns the unified marker id
     * @return The unified marker id
     */
    public int getUmid() {        
        return umid;
    }
    
    /**
     * Returns the unified marker for the unified allele
     * @return The unified marker for the unified allele
     */
    public UMarkerRemote getUMarker()
    {
        UMarkerRemote u = null;
        try
        {
            u = uMarkerHome.findByPrimaryKey(new Integer(umid));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return u;
    }

    /**
     * Sets the unified marker id
     * @param umid The unified marker id
     */
    public void setUmid(int umid) {
        this.umid = umid;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());   
        dirty = true;
    }

    /**
     * Finds an unified allele by its name and unified marker id
     * @param name The unified allele name
     * @param umid The unified marker id
     * @return The primary key if an unified allele could be found
     * @throws FinderException If no unified allele could be found
     */
    public java.lang.Integer ejbFindByNameMarker(java.lang.String name, int umid) throws javax.ejb.FinderException {
         makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select uaid from u_alleles where name = ? and umid = ?");
            ps.setString(1, name);
            ps.setInt(2,umid);
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("UAlleleBean#ejbFindByNameMarker: Cannot find uallele by name and umarker id");
            }
        } catch (SQLException se) {
            throw new EJBException("UAlleleBean#ejbFindByNameMarker: SQL Exception", se);
        } finally {
            releaseConnection();
        }                
        
        return new Integer(uaid);
    }

    /**
     * Retrieves all unified alleles for the specified unified marker
     * @param forUmid The unified marker id
     * @throws javax.ejb.FinderException If the alleles could not be retrieved
     * @return The unified alleles for the unified marker
     */
    public java.util.Collection ejbFindAllUAllelesForUMarker(int forUmid) throws javax.ejb.FinderException {
        makeConnection();
        Collection ualleles = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select uaid, name from u_alleles where umid = ? order by name");
            ps.setInt(1, forUmid);
            
            result = ps.executeQuery();
            
            while(result.next()) {
                ualleles.add(new Integer(result.getInt("uaid")));
            }
        } catch (SQLException se) {
            throw new EJBException("UAlleleBean#ejbFindAllUAllelesForUMarker: SQL Exception", se);
        } finally {
            releaseConnection();
        }   
        
        return ualleles;
    }

    /**
     * Returns the date for the last changes on the unified allele
     * @return The date for the last changes
     */
    public java.sql.Date getTs() {
        return ts;
    }
    
    /**
     * Returns the user which made the last update on the marker
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
     * Sets the current caller object
     * @param caller The caller
     */
    public void setCaller(MugenCaller caller) {
        this.caller = caller;
        dirty = true;
    }

    /**
     * Writes a log entry to track changes history     
     */
    public void addHistory() {        
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into u_alleles_log (uaid, name, comm, id, ts) values (?, ?, ?, ?, ?)");
            ps.setInt(1, uaid);
            ps.setString(2, name);
            ps.setString(3, comm);
            ps.setInt(4, id);
            ps.setDate(5, ts);            
            
            ps.execute();
            
        } catch (Exception e) {
            throw new EJBException("UAlleleBean#addHistory: Error writing history for unified allele. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns a collection of history log entries for the unified allele
     * @return A collection of history log entries
     */
    public Collection getHistory() {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        
        ResultSet rs = null;
        
        try {
            ps = conn.prepareStatement("select * from u_alleles_log where uaid = ? order by ts desc");
            ps.setInt(1, uaid);
            rs = ps.executeQuery();
            UAlleleDTO dto = null;
            UserRemote ur = null;
    
            while (rs.next()) {
                ur = userHome.findByPrimaryKey(new Integer(rs.getInt("id")));
                
                dto = new UAlleleDTO(rs.getString("name"), rs.getString("comm"), ur.getUsr(), rs.getDate("ts"));              

                arr.add(dto);
            }
        } catch (Exception se) {
            throw new EJBException("UAlleleBean#getHistory: unable to find unified allele history. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }  

    /**
     * 
     * @param caller 
     * @param uaid 
     * @param umid 
     * @param name 
     * @param comm 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int uaid, int umid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }
    
    
    
    /**
     * Finds an unified allele by its primary key
     * @param aKey The primary key
     * @return The primary key if the unified allele is found
     * @throws FinderException If no unified allele matching the key could be found
     */
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer aKey) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select uaid from u_alleles where uaid = ?");
            ps.setInt(1, aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("UAlleleBean#ejFindByPrimaryKey: Cannot find UAllele");
            }
        } catch (SQLException se) {
            throw new EJBException("UAlleleBean#ejFindByPrimaryKey: SQL exception", se);
        } finally {
            releaseConnection();
        }
                
        return aKey;
    }
}
