package com.arexis.mugen.genotype.allele;

import com.arexis.mugen.genotype.genotypemanager.AlleleDTO;
import com.arexis.mugen.genotype.marker.MarkerRemote;
import com.arexis.mugen.genotype.marker.MarkerRemoteHome;
import com.arexis.mugen.MugenCaller;
import javax.ejb.*;
import java.sql.PreparedStatement;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This is the bean class for the AlleleBean enterprise bean.
 * Created Jun 15, 2005 4:39:07 PM
 * @todo In create()..what about mid?
 * @author lami
 */
public class AlleleBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.genotype.allele.AlleleRemoteBusiness {
    private javax.ejb.EntityContext context;
    private int aid, mid, id;
    private String name;
    private String comm;    
    private java.sql.Date updated;
    private boolean dirty;
    
    private UserRemoteHome userHome;
    private MarkerRemoteHome markerHome;
    
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
        markerHome = (MarkerRemoteHome)locator.getHome(ServiceLocator.Services.MARKER);
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
            ps = conn.prepareStatement("delete from alleles where aid = ?");
            ps.setInt(1, getAid());
            
            int res = ps.executeUpdate();
            
            if (res!=1) {
                throw new EJBException("AlleleBean#ejbRemove: Cannot remove Allele");
            }
        } catch (SQLException se) {
            throw new EJBException("AlleleBean#ejbRemove: SQL Exception", se);
        } finally{
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
            ps = conn.prepareStatement("select name,comm,mid, id, ts from alleles where aid = ?");
            ps.setInt(1,pk.intValue());
            
            result = ps.executeQuery();
            
            if (result.next()) {
                aid = pk.intValue();
                name = result.getString("name");
                comm = result.getString("comm");
                mid = result.getInt("mid");
                id = result.getInt("id");
                updated = result.getDate("ts");
                dirty = false;
            } else
                throw new EJBException("AlleleBean#ejbLoad: Failed to load bean");
        } catch (SQLException se) {
            throw new EJBException("AlleleBean#ejbLoad: SQL Exception",se);
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
                ps = conn.prepareStatement("update alleles set aid = ? ," +
                        " name = ?, comm = ?, mid = ?, id = ?, ts = ? where aid = ?");

                ps.setInt(1, aid);
                ps.setString(2, name);
                ps.setString(3, comm);
                ps.setInt(4, mid);
                ps.setInt(5, id); 
                ps.setDate(6, updated);
                ps.setInt(7, aid);

                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("AlleleBean#ejbStore: Error updating allele [aid="+aid+"]");
            } finally {
                releaseConnection();
                dirty = false;
            }        
        }
    }
    
    // </editor-fold>
    
    /**
     * Find the allele using its primary key
     * @param aKey The primary key
     * @return The primary key if the allele was found
     * @throws FinderException If the allele could not be found
     */
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer aKey) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select aid from alleles where aid = ?");
            ps.setInt(1,aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("AlleleBean#ejbFindByPrimaryKey: Cannot find allele");
            }
        } catch (SQLException se) {
            throw new EJBException("AlleleBean#ejbFindByPrimaryKey: SQL Exception", se);
        } finally {
            releaseConnection();
        }                
        
        return aKey;
    }

    /**
     * Creates a new allele
     * @return The primary key
     * @param caller The current caller object
     * @param aid The allele id
     * @param mid The marker id
     * @param name The allele name
     * @param comm The allele comment
     * @throws CreateException If the allele could not be created
     */
    public java.lang.Integer ejbCreate(int aid, int mid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException {
        this.aid = aid;
        this.name = name;
        this.comm = comm;
        this.mid = mid;
        this.id = caller.getId();
        this.updated = new java.sql.Date(System.currentTimeMillis());
        
        makeConnection();
        
        try {    
            PreparedStatement ps = null;
            ps = conn.prepareStatement("insert into alleles (aid,name,comm,mid,id,ts) values (?,?,?,?,?,?)");
            ps.setInt(1, aid);
            ps.setString(2, name);
            ps.setString(3, comm);
            ps.setInt(4, mid);
            ps.setInt(5, id);
            ps.setDate(6, updated);            
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
                throw new CreateException("AlleleBean#ejbCreate: Unable to create allele: "+e.getMessage());
        } finally {
            releaseConnection();
        }                
        
        return new Integer(aid);  
    }

    /**
     * 
     * @param caller 
     * @param aid 
     * @param mid 
     * @param name 
     * @param comm 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int aid, int mid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }

    /**
     * Returns the Allele id
     * @return The id of the allele
     */
    public int getAid() {        
        return aid;
    }

    /**
     * Returns the name of the allele
     * @return The name of the allele
     */
    public String getName() {        
        return name;
    }

    /**
     * Sets the name of the allele
     * @param name The new name for the allele
     */
    public void setName(java.lang.String name) {
        this.name = name;
        this.id = caller.getId();
        this.updated = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }

    /**
     * Returns the comment for the allele
     * @return The comment for the allele
     */
    public String getComm() {
        return comm;
    }

    /**
     * Returns the marker
     * @return The marker
     */
    public MarkerRemote getMarker() {        
        try {
            return markerHome.findByPrimaryKey(new Integer(mid));
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Returns the date for when the allele was last updated
     * @return The date for the last update
     */
    public java.sql.Date getUpdated() {
        return updated;
    }
    
    /**
     * Returns the user which made the last update on the allele
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
     * Sets the marker id
     * @param mid The marker id
     */
    public void setMid(int mid) {
        this.mid = mid;
        this.id = caller.getId();
        this.updated = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }

    /**
     * Finds the allele using its name and marker id
     * @param name The allele name
     * @param mid The marker id
     * @return The primary key
     * @throws FinderException If no allele was found
     */
    public java.lang.Integer ejbFindByNameMarker(java.lang.String name, int mid) throws javax.ejb.FinderException {
         makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select aid from alleles where name = ? and mid = ?");
            ps.setString(1, name);
            ps.setInt(2,mid);
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("AlleleBean#ejbFindByNameMarker: Cannot find allele by name and marker id");
            }
        } catch (SQLException se) {
            throw new EJBException("AlleleBean#ejbFindByNameMarker: SQL Exception", se);
        } finally {
            releaseConnection();
        }                
        
        return new Integer(aid);
    }

    /**
     * Returns all alleles for a specific marker
     * @param forMid The marker id
     * @throws javax.ejb.FinderException If the alleles could not be retrieved
     * @return A collection of alleles
     */
    public java.util.Collection ejbFindAllAllelesForMarker(int forMid) throws javax.ejb.FinderException {
        makeConnection();
        Collection alleles = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select aid, name from alleles where mid = ? order by name");
            ps.setInt(1, forMid);
            
            result = ps.executeQuery();
            
            while(result.next()) {
                alleles.add(new Integer(result.getInt("aid")));
            }
        } catch (SQLException se) {
            throw new EJBException("AlleleBean#ejbFindAllAllelesForMarker: SQL Exception", se);
        } finally {
            releaseConnection();
        }   
        
        return alleles;
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
     */
    public void addHistory() {        
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into alleles_log (aid, name, comm, id, ts) values (?, ?, ?, ?, ?)");
            ps.setInt(1, aid);
            ps.setString(2, name);
            ps.setString(3, comm);
            ps.setInt(4, id);
            ps.setDate(5, updated);            
            
            ps.execute();
            
        } catch (Exception e) {
            throw new EJBException("AlleleBean#addHistory: Error writing history for allele. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns a collection of history log entries for the allele
     * @return A collection of history log entries
     */
    public Collection getHistory() {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        
        ResultSet rs = null;
        
        try {
            ps = conn.prepareStatement("select * from alleles_log where aid = ? order by ts desc");
            ps.setInt(1, aid);
            rs = ps.executeQuery();
            AlleleDTO dto = null;
            UserRemote ur = null;
    
            while (rs.next()) {
                ur = userHome.findByPrimaryKey(new Integer(rs.getInt("id")));
                
                dto = new AlleleDTO(rs.getString("name"), rs.getString("comm"), ur.getUsr(), rs.getDate("ts"));              

                arr.add(dto);
            }
        } catch (Exception se) {
            throw new EJBException("AlleleBean#getHistory: unable to find allele history. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }  


    /**
     * Sets the comment for the allele
     * @param comm The comment for the allele
     */
    public void setComm(java.lang.String comm) {
        this.comm = comm;
        this.id = caller.getId();
        this.updated = new java.sql.Date(System.currentTimeMillis());       
        dirty = true;
    }
}
