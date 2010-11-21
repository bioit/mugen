package com.arexis.mugen.genotype.uposition;

import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.genotype.umarker.UMarkerRemote;
import com.arexis.mugen.genotype.umarkerset.UMarkerSetRemote;
import com.arexis.mugen.genotype.uposition.UPositionPk;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ejb.EJBException;
import javax.ejb.*;
/**
 * This is the bean class for the UPositionBean enterprise bean.
 * Created Jun 14, 2005 5:46:18 PM
 * @todo Complement id and ts in create(), currently dummy values only
 * @author lami
 */
public class UPositionBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.genotype.uposition.UPositionRemoteBusiness {
    private javax.ejb.EntityContext context;
    private int umsid;
    private int umid;
    private double value;
    
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
            ps = conn.prepareStatement("delete from u_positions where umsid = ? and umid=?");
            ps.setInt(1, getUmsid());
            ps.setInt(2, getUmid());
            
            int res = ps.executeUpdate();
            
            if (res!=1) {
                throw new EJBException("UPositionBean#ejbRemove: Cannot remove UPosition");
            }
        } catch (SQLException se) {
            throw new EJBException("UPositionBean#ejbRemove: SQL exception thrown", se);
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
        UPositionPk pk = (UPositionPk)context.getPrimaryKey();
        
        umsid = pk.getUmsid().intValue();
        umid  = pk.getUmid().intValue();
        
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select value from u_positions where umsid = ? and umid = ?");
            ps.setInt(1,umsid);
            ps.setInt(2,umid);
            
            result = ps.executeQuery();
            
            if (result.next()) {                
                value = result.getDouble("value");
            } else
                throw new EJBException("UPositionBean#ejbLoad: Exception thrown (empty resultset?)");
            
            dirty = false;
        } catch (SQLException se) {
            throw new EJBException("UPositionBean#ejbLoad: SQL exception thrown", se);
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
                ps = conn.prepareStatement("update U_positions set umsid = ?, umid = ? , value = ?, id = ?, ts = ? where umid = ? and umsid = ?");

                ps.setInt(1, umsid);
                ps.setInt(2, umid);
                ps.setDouble(3, value);
                ps.setInt(4, 1);
                ps.setDate(5, new java.sql.Date(System.currentTimeMillis()));
                ps.setInt(6, umid);
                ps.setInt(7, umsid);            

                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("UPositionBean#ejbStore: Error updating UPosition [Umsid="+umsid+", Umid="+umid+"]");
            } finally {
                releaseConnection();
                dirty = false;
            }                
        }
    }
    
    // </editor-fold>
    
    /**
     * Finds the unified position by its primary key
     * @return The primary key
     * @param upk The unified position primary key
     * @throws FinderException If no unified position could be found
     */
    public com.arexis.mugen.genotype.uposition.UPositionPk ejbFindByPrimaryKey(com.arexis.mugen.genotype.uposition.UPositionPk upk) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select umsid, umid from u_postions where umsid = ? and umid=?");
            ps.setInt(1, upk.getUmsid().intValue());
            ps.setInt(2, upk.getUmid().intValue());
            
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("UPositionBean#ejbFindByPrimaryKey: Cannot find UPosition");
            }
        } catch (SQLException se) {
            throw new EJBException("UPositionBean#ejbFindByPrimaryKey: SQL exception thrown.", se);
        } finally {
            releaseConnection();
        }
                
        return upk;
    }

    /**
     * Creates a new unified position
     * @param umr The UMarkerRemote interface
     * @param umsr The UMarkerSetRemote interface
     * @return The primary key
     * @throws CreateException If no unified position could be created
     * @param value The value for the unified position
     */
    public com.arexis.mugen.genotype.uposition.UPositionPk ejbCreate(UMarkerRemote umr, UMarkerSetRemote umsr, double value) throws javax.ejb.CreateException {
        makeConnection();
        PreparedStatement ps = null;
        UPositionPk pk = null;
        
        try {
            umsid = umsr.getUmsid();
            umid = umr.getUmid();
            this.value = value;
            
            ps = conn.prepareStatement("insert into u_positions (umsid,umid,value,id,ts) values (?,?,?,?,?)");
            ps.setInt(1, umsid);
            ps.setInt(2, umid);
            ps.setDouble(3, value);
            ps.setInt(4, 1);
            ps.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            ps.execute();
            
            pk = new UPositionPk(umsr.getUmsid(), umr.getUmid());
            dirty = false;
        } catch (Exception e) {
            throw new EJBException("UPositionBean#ejbCreate: Exception thrown: "+e.getMessage(), e);
        } finally {
            releaseConnection();
        }
        
        return pk;
    }

    /**
     * 
     * @param umr 
     * @param umsr 
     * @param value 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(UMarkerRemote umr, UMarkerSetRemote umsr, double value) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }

    /**
     * Returns the UMarkerSet id
     * @return The UMarkerSet id
     */
    public int getUmsid() {
        return umsid;
    }

    
    /**
     * Returns the UMarker id
     * @return The UMarker id
     */
    public int getUmid() {
        return umid;
    }

    /**
     * Returns the value for the unified position
     * @return The value
     */
    public double getValue() {        
        return value;
    }

    /**
     * Sets the value for the unified position
     * @param value The new value
     */
    public void setValue(double value) {
        this.value = value;
        dirty = true;
    }
}
