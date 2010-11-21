package com.arexis.mugen.species.lmarker;

import com.arexis.mugen.exceptions.DbException;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.servicelocator.ServiceLocator;
import com.arexis.mugen.species.chromosome.ChromosomeRemote;
import com.arexis.mugen.species.chromosome.ChromosomeRemoteHome;
import com.arexis.mugen.species.species.SpeciesRemote;
import com.arexis.mugen.species.species.SpeciesRemoteHome;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ejb.*;

/**
 * This is the bean class for the LMarkerBean enterprise bean.
 * Created Jun 13, 2005 1:08:40 PM
 * @author lami
 * @todo Should position be of type double or string? double in db and string in old code
 * @todo Additional findsBy's()? DblMarker.java contains a lot of 
 *       database queries...how will these be handled?
 */
public class LMarkerBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.species.lmarker.LMarkerRemoteBusiness {
    private javax.ejb.EntityContext context;
    private String name, alias;
    private String p1, p2;
    private double position;
    private String comm;
    private int lmid, sid, cid;
    
    private boolean dirty;
    
    private SpeciesRemoteHome speciesHome;
    private ChromosomeRemoteHome chromosomeHome;
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise beans, Web services)
    // TODO Add business methods
    // TODO Add create methods
    /**
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(javax.ejb.EntityContext aContext) {
        context = aContext;
        speciesHome = (SpeciesRemoteHome)locator.getHome(ServiceLocator.Services.SPECIES);
        chromosomeHome = (ChromosomeRemoteHome)locator.getHome(ServiceLocator.Services.CHROMOSOME);
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
            ps = conn.prepareStatement("delete from l_markers where lmid = ?");
            ps.setInt(1,lmid);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            
            throw new EJBException("LMarkerBean#ejbRemove: Internal error. Failed to delete library marker\n(" +
                    e.getMessage() + ")");
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
            ps = conn.prepareStatement("select name,comm,alias,position,p1,p2,sid,cid from L_Markers where lmid = ?");
            ps.setInt(1,pk.intValue());
            
            result = ps.executeQuery();
            
            if (result.next()) {
                lmid = pk.intValue();
                name = result.getString("name");
                alias = result.getString("alias");
                p1 = result.getString("p1");
                p2 = result.getString("p2");
                position = result.getDouble("position");
                comm = result.getString("comm");
                sid = result.getInt("sid");
                cid = result.getInt("cid");
                dirty = false;
            } else
                throw new EJBException("LMarkerBean#ejbLoad: Failed to load (empty resultset?)");
        } catch (SQLException se) {
            throw new EJBException("LMarkerBean#ejbLoad: SQL exception thrown...", se);
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
                ps = conn.prepareStatement("update l_markers set name = ?," +
                        " comm = ?, sid = ?, cid = ?, alias = ?, position = ?," +
                        " p1 = ?, p2 = ? where lmid = ?");

                ps.setString(1, name);            
                ps.setString(2, comm); 
                ps.setInt(3, sid);
                ps.setInt(4, cid);
                ps.setString(5, alias); 
                ps.setDouble(6, position); 
                ps.setString(7, p1); 
                ps.setString(8, p2);             
                ps.setInt(9, lmid);

                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("LMarkerBean#ejbStore: Error updating library marker ["+lmid+"]");
            } finally {
                releaseConnection();            
                dirty = false;
            }
        }
    }
    
    // </editor-fold>
    
    /**
     * See EJB 2.0 and EJB 2.1 section 12.2.5
     * @return The id for the library marker
     * @param key The key for the library marker
     * @throws FinderException If no marker is found
     * @throws RemoteException If there is a bean error
     */
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer key) throws javax.ejb.FinderException, java.rmi.RemoteException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try
        {
            ps = conn.prepareStatement("select lmid from L_Markers where lmid = ?");
            ps.setInt(1, key.intValue());
            result = ps.executeQuery();
            
            if (!result.next())
            {
                throw new ObjectNotFoundException("LMarkerBean#ejbFindByPrimaryKey: Cannot find LMarker by lmarker id");
            }
        }
        catch (SQLException se)
        {
            throw new EJBException("LMarkerBean#ejbFindByPrimaryKey: SQL exception thrown...", se);
        } finally {
            releaseConnection();
        }
        
        return key;   
    }
    
    /**
     * Finds a library marker by name and species id
     * @param name The library marker name
     * @param sid The species id
     * @return The library marker id if one is found
     * @throws FinderException If no match is found
     */
    public java.lang.Integer ejbFindByNameAndSpecies(java.lang.String name, java.lang.Integer sid) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select lmid from L_Markers where sid = ? and name = ?");
            ps.setString(2, name);
            ps.setInt(5, sid.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("LMarkerBean#ejbFindByNameAndSpecies: Cannot find LMarker by name and species");
            }
        } catch (SQLException se) {
            throw new EJBException("LMarkerBean#ejbFindByNameAndSpecies: SQL exception thrown", se);
        } finally {
            releaseConnection();
        }
        
        return new Integer(lmid);
    }
    
   /**
     * Creates a LMarker
     * @param name The name for the library marker
     * @param alias The alias for the library marker
     * @param comm The comment for the library marker
     * @param p1 The p1 for the library marker
     * @param p2 The p2 for the library marker
     * @param position The position for the library marker
     * @param sid The species id for the library marker
     * @param cid The chromosome id for the library marker
     * @throws CreateException If failed to create the library marker
     * @throws DbException If failed to insert to marker in the database
     * @return The id for the library marker
     * @throws RemoteException If there is a bean error
     * @param lmid The LMarker id
     */
    public java.lang.Integer ejbCreate(int lmid, java.lang.String name,
            java.lang.String alias, java.lang.String comm, java.lang.String p1,
            java.lang.String p2, double position,
            int sid, int cid) 
            throws javax.ejb.CreateException, java.rmi.RemoteException, DbException{
        
        this.setName(name);
        this.setAlias(alias);
        this.setComm(comm);
        this.setP1(p1);
        this.setP2(p2);
        this.setPosition(position);
        this.lmid = lmid;
        this.sid = sid;
        this.cid = cid;
        
        makeConnection();
            
        try { 
            PreparedStatement ps = null;
            ps = conn.prepareStatement("insert into L_Markers(lmid, name, alias, comm, sid, cid, p1, " +
                    "p2, position) values (?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, lmid);
            ps.setString(2, name.toUpperCase());
            ps.setString(3, alias);
            ps.setString(4, comm);
            ps.setInt(5, sid);
            ps.setInt(6, cid);
            ps.setString(7, p1);
            ps.setString(8, p2);
            ps.setDouble(9, position);
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            // Perhaps not meaningful to catch both?
            if(e instanceof DbException)
                throw new DbException("LMarkerBean#ejbCreate: Error storing data to the database"+e.getMessage());
            else
                throw new CreateException("LMarkerBean#ejbCreate: Unable to create LMarker: "+e.getMessage());
        } finally {
            releaseConnection();
        }        
  
        return new Integer(getLmid());
    } 
    
    /**
     * 
     * @param lmid 
     * @param name 
     * @param alias 
     * @param comm 
     * @param p1 
     * @param p2 
     * @param position 
     * @param sid 
     * @param cid 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int lmid, String name, String alias,
            String comm, String p1, String p2, double position,
            int sid, int cid)
            throws javax.ejb.CreateException {
    }
    
    /** 
     * Get the name of the LMarker
     * @return the name
     */
    public String getName(){
        return name;
    }
    
    /**
     * 
     * Set the name of the LMarker
     * @param name new name
     */
    public void setName(String name){
        this.name = name;
        dirty = true;
    }
    
    /**
     * Get the alias of the LMarker
     *@return the alias
     */
    public String getAlias(){
        return alias;
    }
    
    /**
     * Set the alias of the LMarker
     * @param alias the new alias
     */
    public void setAlias(String alias){
        this.alias = alias;
        dirty = true;
    }

    /**
     * Get p1
     * @return returns p1
     */
    public String getP1() {
        return p1;
    }

    /**
     * Sets p1 for the library marker
     * @param p1 the new p1
     */
    public void setP1(String p1) {
        this.p1 = p1;
        dirty = true;
    }

    /**
     * Sets p2 for the library marker
     * @return returns p2
     */
    public String getP2() {
        return p2;
    }

    /**
     * Sets p2 for the library marker
     * @param p2 The new p2
     */
    public void setP2(String p2) {
        this.p2 = p2;
        dirty = true;
    }

    /**
     * Returns the position for the library marker
     * @return The position for the library marker
     */
    public double getPosition() {
        return position;
    }

    /**
     * Sets the position for the library marker
     * @param position Sets the new position
     */
    public void setPosition(double position) {
        this.position = position;
        dirty = true;
    }
    
    /**
     * Get the species for this LMarker
     * @return A collection of species that belongs to the sampling unit
     */
    public SpeciesRemote getSpecies() {        
        SpeciesRemote species = null;
        try {
            species = speciesHome.findByPrimaryKey(new Integer(sid));
        } 
        catch (Exception e) 
        {
            throw new EJBException(e.getMessage());
        }
        return species;
    }
    
    /**
     * Get the species for this LMarker
     * @return A collection of species that belongs to the sampling unit
     */
    public ChromosomeRemote getChromosome() {        
        ChromosomeRemote c = null;
        try {
            c = chromosomeHome.findByPrimaryKey(new Integer(cid));
        } 
        catch (Exception e) 
        {
            throw new EJBException(e.getMessage());
        }
        return c;
    }

    /**
     * Sets the species id for the library marker
     * @param sid The species id
     */
    public void setSid(int sid) {
        this.sid = sid;
        dirty = true;
    }

    /**
     * Sets the chromosome id for the library marker
     * @param cid The chromosome id
     */
    public void setCid(int cid) {
        this.cid = cid;
        dirty = true;
    }

    /**
     * Returns the comment for the library marker
     * @return The comment for the library marker
     */
    public String getComm() {
        return comm;
    }

    /**
     * Sets the comment for the library marker
     * @param comm The comment for the library marker
     */
    public void setComm(String comm) {
        this.comm = comm;
        dirty = true;
    }

    /**
     * Returns the id for the library marker
     * @return The library marker identifier
     */
    public int getLmid() {
        return lmid;
    }
}
