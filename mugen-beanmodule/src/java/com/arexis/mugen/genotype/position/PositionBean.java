package com.arexis.mugen.genotype.position;

import com.arexis.mugen.servicelocator.ServiceLocator;
import javax.ejb.*;
import com.arexis.mugen.project.AbstractMugenBean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.arexis.mugen.genotype.marker.MarkerRemote;
import com.arexis.mugen.genotype.marker.MarkerRemoteHome;
import com.arexis.mugen.genotype.markerset.MarkerSetRemote;
import com.arexis.mugen.genotype.markerset.MarkerSetRemoteHome;

/**
 * This is the bean class for the PositionBean enterprise bean.
 * Created Jun 15, 2005 6:17:37 PM
 * @author lami
 */
public class PositionBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.genotype.position.PositionRemoteBusiness {
    private javax.ejb.EntityContext context;
    private int msid;
    private int mid;
    private double value;
    
    private boolean dirty;
    
    private MarkerSetRemoteHome markerSetHome;
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
        markerSetHome = (MarkerSetRemoteHome)locator.getHome(ServiceLocator.Services.MARKERSET);
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
            ps = conn.prepareStatement("delete from positions where msid = ? and mid=?");
            ps.setInt(1, msid);
            ps.setInt(2, mid);
            
            int res = ps.executeUpdate();
            
            if (res!=1) {
                throw new EJBException("PositionBean#ejbRemove: Cannot remove Position");
            }
        } catch (Exception se) {
            throw new EJBException("PositionBean#ejbRemove: SQL Exception: "+se.getMessage(),se);
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
        PositionPk pk = (PositionPk)context.getPrimaryKey();
        
        msid = pk.getMsid().intValue();
        mid  = pk.getMid().intValue();
        
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select value from positions where msid = ? and mid = ?");
            ps.setInt(1,msid);
            ps.setInt(2,mid);
            
            result = ps.executeQuery();
            
            if (result.next()) {                
                value = result.getDouble("value");
                dirty = false;
            } else
                throw new EJBException("PositionBean#ejbLoad: Failed to load bean");
        } catch (SQLException se) {
            throw new EJBException("PositionBean#ejbLoad: SQL Exception: "+se.getMessage(), se);
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
                ps = conn.prepareStatement("update positions set msid = ?, mid = ? , value = ?, id = ?, ts = ? where mid = ? and msid = ?");

                ps.setInt(1, msid);
                ps.setInt(2, mid);
                ps.setDouble(3,value);
                ps.setInt(4, 1);
                ps.setDate(5, new java.sql.Date(System.currentTimeMillis()));
                ps.setInt(6, mid);
                ps.setInt(7, msid);

                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("PositionBean#ejbStore: Error updating Position [msid="+msid+", mid="+mid+"]");
            } finally {
                releaseConnection();
                dirty = false;
            }                
        }
    }
    
    // </editor-fold>
    
    /**
     * Finds a position using its primary key
     * @param pk The primary key
     * @return The primary key if the position was found
     * @throws FinderException If no position could be found
     */
    public com.arexis.mugen.genotype.position.PositionPk ejbFindByPrimaryKey(com.arexis.mugen.genotype.position.PositionPk pk) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select msid, mid from postions where msid = ? and mid=?");
            ps.setInt(1, pk.getMsid().intValue());
            ps.setInt(2, pk.getMid().intValue());
            
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("PositionBean#ejbFindByPrimaryKey: Cannot find Position");
            }
        } catch (SQLException se) {
            throw new EJBException("PositionBean#ejbFindByPrimaryKey: SQL Exception: "+se.getMessage(), se);
        } finally {
            releaseConnection();
        }
                
        return pk;
    }
    
    /**
     * Returns the marker set for this position
     * @return The marker set for the position
     */
    public MarkerSetRemote getMarkerSet()
    {
        MarkerSetRemote markerSet = null;
        try
        {
            markerSet = markerSetHome.findByPrimaryKey(new Integer(msid));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return markerSet;
    }
    
    
    /**
     * Returns the marker for the position
     * @return 
     */
    public MarkerRemote getMarker()
    {
        MarkerRemote marker = null;
        try
        {
            marker = markerHome.findByPrimaryKey(new Integer(mid));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return marker;
    }

    /**
     * Creates a new position
     * @param markRem The Marker remote interface
     * @param markSetRem The MarkerSet remote interface
     * @param value The value for the position
     * @return The primary key if the position could be created
     * @throws CreateException If the position could not be created
     */
    public com.arexis.mugen.genotype.position.PositionPk ejbCreate(MarkerRemote markRem, MarkerSetRemote markSetRem, double value) throws javax.ejb.CreateException {
        makeConnection();
        PreparedStatement ps = null;
        PositionPk pk = null;
        
        try {
            msid = markSetRem.getMsid();
            mid = markRem.getMid();
            this.value = value;
            
            ps = conn.prepareStatement("insert into positions (msid,mid,value,id,ts) values (?,?,?,?,?)");
            ps.setInt(1, msid);
            ps.setInt(2, mid);
            ps.setDouble(3, value);
            ps.setInt(4, 1);
            ps.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            ps.execute();
            
            pk = new PositionPk(msid, mid);
            dirty = false;
        } catch (Exception e) {
            throw new EJBException("PositionBean#ejbCreate: Failed to create Position bean: "+e.getMessage(), e);
        } finally {
            releaseConnection();
        }
        
        return pk;
    }

    /**
     * 
     * @param markRem 
     * @param markSetRem 
     * @param value 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(MarkerRemote markRem, MarkerSetRemote markSetRem, double value) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }

    /**
     * Returns the value for the position
     * @return The position value
     */
    public double getValue() {        
        return value;
    }

    /**
     * Sets the position value
     * @param value The position value
     */
    public void setValue(double value) {
        this.value = value;
        dirty = true;
    }
}
