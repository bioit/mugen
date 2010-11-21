package com.arexis.mugen.species.lallele;

import javax.ejb.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.arexis.mugen.exceptions.DbException;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.servicelocator.ServiceLocator;
import com.arexis.mugen.species.lmarker.LMarkerRemote;
import com.arexis.mugen.species.lmarker.LMarkerRemoteHome;

/**
 * This is the bean class for the LAlleleBean enterprise bean.
 * Created Jun 13, 2005 1:09:16 PM
 * @author lami
 * @todo Additional findBy's()
 * @todo ejbCreate(), lookup lmid used in old agdb.DblMarker.java but not used here
 */
public class LAlleleBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.species.lallele.LAlleleRemoteBusiness {
    private javax.ejb.EntityContext context;
    private int laid, lmid;
    private String name;
    private String comm;
    
    private boolean dirty;
    
    LMarkerRemoteHome lMarkerHome;
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise beans, Web services)
    // TODO Add business methods
    // TODO Add create methods
    /**
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(javax.ejb.EntityContext aContext) {
        setContext(aContext);
        lMarkerHome = (LMarkerRemoteHome)locator.getHome(ServiceLocator.Services.LMARKER);
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
            ps = conn.prepareStatement("delete from l_alleles where laid = ?");
            ps.setInt(1,laid);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            
            throw new EJBException("LAlleleBean#ejbRemove: Internal error. Failed to delete library allele\n(" +
                    e.getMessage() + ")");
        } finally {
            releaseConnection();
        }   
    }
    
    /**
     * @see javax.ejb.EntityBean#unsetEntityContext()
     */
    public void unsetEntityContext() {
        setContext(null);
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbLoad()
     */
    public void ejbLoad() {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        Integer pk = (Integer)context.getPrimaryKey();
        try
        {
            ps = conn.prepareStatement("select name,comm,lmid from L_Alleles where laid = ?");
            ps.setInt(1, pk.intValue());
                     
            result = ps.executeQuery();
            
            if (result.next())
            {
                laid = pk.intValue();
                name = result.getString("name");
                comm = result.getString("comm");
                lmid = result.getInt("lmid");
                dirty = false;
            }
            else
                throw new EJBException("LAlleleBean#ejbLoad: Failed to load bean (resultset empty?)");
        }
        catch (SQLException se)
        {
            throw new EJBException("LAlleleBean#ejbLoad: SQL exception thrown: "+se);
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
                ps = conn.prepareStatement("update l_alleles set name = ?," +
                        " comm = ?, lmid = ? where laid = ?");

                ps.setString(1, name);            
                ps.setString(2, comm); 
                ps.setInt(3, lmid);
                ps.setInt(4, laid);

                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("LAlleleBean#ejbStore: Error updating library alelle ["+laid+"]");
            } finally {
                releaseConnection();
                dirty = false;
            }                
        }
    }
    
    // </editor-fold>
    
    /**
     * Finds the library allele by its primary key
     * @param aKey The primary key
     * @return The primary key
     * @throws FinderException If no library allele was found
     */
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer aKey) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select laid from L_Alleles where laid = ?");
            ps.setInt(1, aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("LAlleleBean#ejbFindByPrimaryKey: Cannot find lallele by primary key");
            }
        } catch (SQLException se) {
            throw new EJBException("LAlleleBean#ejbFindByPrimaryKey: SQL exception thrown", se);
        } finally {
            releaseConnection();
        }                
        
        return aKey; 
    }
    
    /**
     * Finds the library allele by library marker and name
     * @param name The library allele name
     * @param lmid The library marker id
     * @return The primary key
     * @throws FinderException If no library allele was found
     */
    public java.lang.Integer ejbFindByNameAndLMarker(String name, int lmid) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select laid from L_Alleles where lmid = ? and name = ?");
            
            ps.setInt(1, lmid);
            ps.setString(2, name);
            
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("LAlleleBean#ejbFindByNameAndLMarker: Cannot find lallele by name and lmarker");
            }
        } catch (SQLException se) {
            throw new EJBException("LAlleleBean#ejbFindByNameAndLMarker: SQL exception thrown ", se);
        } finally {
            releaseConnection();
        }                
        
        return new Integer(laid);
    }    
    
    /**
     * Create method for the LAllele bean.
     * @return the LAllele id
     * @param laid an LAllele identifier
     * @param name an LAllele name
     * @param comm an comment
     * @throws com.arexis.mugen.exceptions.DbException If the library allele could not be inserted
     * @throws javax.ejb.CreateException If no library allele could be created
     * @param lmid The library marker id
     */    
    public java.lang.Integer ejbCreate(int laid,
            java.lang.String name, java.lang.String comm, int lmid) throws DbException, CreateException{
        this.laid = laid;
        this.setName(name);
        this.setComm(comm);
        this.lmid = lmid;
        
        makeConnection();
        
        try {    
            PreparedStatement ps = null;
            ps = conn.prepareStatement("insert into L_Alleles (laid,name,comm,lmid) values (?,?,?,?)");
            ps.setInt(1, laid);
            ps.setString(2, name);
            ps.setString(3, comm);
            ps.setInt(4, lmid);
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            // Perhaps not meaningful to catch both?
            if(e instanceof DbException)
                throw new DbException("LAlleleBean#ejbCreate: Error storing data to the database"+e.getMessage());
            else
                throw new CreateException("LAlleleBean#ejbCreate: Unable to create lallele: "+e.getMessage());
        } finally {
            releaseConnection();
        }
        
        
        
        return new Integer(laid);         
    }
    
    /**
     * 
     * @param laid 
     * @param name 
     * @param comm 
     * @param lmid 
     * @throws com.arexis.mugen.exceptions.DbException 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int laid,
            java.lang.String name, java.lang.String comm, int lmid) throws DbException, CreateException {
        
    }

    /**
     * Returns the context of this EJB.
     * @return javax.ejb.EntityContext
     */
    public javax.ejb.EntityContext getContext() {
        return context;
    }

    /**
     * Sets the context of this EJB
     * @param context sets the javax.ejb.EntityContext
     */
    public void setContext(javax.ejb.EntityContext context) {
        this.context = context;
    }

    /**
     * Returns the id of the LAllele
     * @return java.lang.Integer the LAllele id
     */
    public int getLaid() {
        return laid;
    }

    /**
     * Returns the name of this LAllele
     * @return The name of the LAllele
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the LAllele
     * @param name The new name of the LAllele
     */
    public void setName(String name) {
        this.name = name;
        dirty = true;
    }

    /**
     * Returns a comment for the LAllele
     * @return The comment for the LAllele
     */
    public String getComm() {
        return comm;
    }

    /**
     * Returns the library marker for the library allele
     * @return The library marker for the library allele
     */
    public LMarkerRemote getLMarker() {
        LMarkerRemote lm = null;
        try
        {
            lm = lMarkerHome.findByPrimaryKey(new Integer(lmid));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return lm;
    }

    /**
     * Sets the library marker id
     * @param lmid The library marker id
     */
    public void setLmid(int lmid) {
        this.lmid = lmid;
        dirty = true;
    }

    /**
     * Sets the comment for the LAllele
     * @param comm The new comment
     */
    public void setComm(String comm) {
        this.comm = comm;
    }
}
