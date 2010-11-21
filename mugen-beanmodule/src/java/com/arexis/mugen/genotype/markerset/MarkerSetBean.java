package com.arexis.mugen.genotype.markerset;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.genotype.genotypemanager.MarkerSetDTO;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.servicelocator.ServiceLocator;
import javax.ejb.*;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This is the bean class for the MarkerSetBean enterprise bean.
 * Created Jun 15, 2005 6:33:47 PM
 * @author lami
 */
public class MarkerSetBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.genotype.markerset.MarkerSetRemoteBusiness {
    private javax.ejb.EntityContext context;
    private int msid, suid, id;
    private String name, comm;
    private java.sql.Date updated;
    
    private boolean dirty;
    
    private SamplingUnitRemoteHome samplingUnitHome;
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise beans, Web services)
    // TODO Add business methods
    // TODO Add create methods
    /**
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(javax.ejb.EntityContext aContext) {
        context = aContext;
        samplingUnitHome = (SamplingUnitRemoteHome)locator.getHome(ServiceLocator.Services.SAMPLINGUNIT);
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
            ps = conn.prepareStatement("delete from marker_sets where msid = ?");
            ps.setInt(1, msid);
            
            int res = ps.executeUpdate();
            
            if (res!=1) {
                throw new EJBException("MarkerSetBean#ejbRemove: Cannot remove markerset");
            }
        } catch (SQLException se) {
            throw new EJBException("MarkerSetBean#ejbRemove: SQL Exception: "+se.getMessage(), se);
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
            ps = conn.prepareStatement("select msid,suid,name,comm, id, ts from marker_sets where msid=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                msid = rs.getInt("msid");
                suid = rs.getInt("suid");
                name = rs.getString("name");
                comm = rs.getString("comm");
                id = rs.getInt("id");
                updated = rs.getDate("ts");
                dirty = false;
            } else
                throw new EJBException("MarkerSetBean#ejbLoad: Error loading markerset unit");
        } catch (Exception e) {
            throw new EJBException("MarkerSetBean#ejbLoad: Exception: "+e.getMessage(), e);
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
                ps = conn.prepareStatement("update marker_sets set suid = ?, name = ?, comm = ?, ts = ?, id = ? where msid = ?");
                ps.setInt(1, suid);
                ps.setString(2, name);           
                ps.setString(3, comm);             
                ps.setDate(4, updated);
                ps.setInt(5, id);
                ps.setInt(6, msid);

                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("Error updating markerset ["+msid+"]");
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
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer aKey) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select msid from marker_sets where msid = ?");
            ps.setInt(1, aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("MarkerSetBean#ejbFindByPrimaryKey: Cannot find Marker set");
            }
        } catch (SQLException se) {
            throw new EJBException("MarkerSetBean#ejbFindByPrimaryKey: SQL Exception: "+se.getMessage(), se);
        } finally {
            releaseConnection();
        }
                
        return aKey;
    }

    /**
     * Returns the name of the markerset
     * @return The markerset name
     */
    public String getName() {        
        return name;
    }

    /**
     * Sets the name of the markerset
     * @param name The new markerset name
     */
    public void setName(java.lang.String name) {
        this.name = name;
        this.id = caller.getId();
        this.updated = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }

    /**
     * Sets the comment for the markerset
     * @param comm The new comment for the markerset
     */
    public void setComm(java.lang.String comm) {
        this.comm = comm;
        this.id = caller.getId();
        this.updated = new java.sql.Date(System.currentTimeMillis());   
        dirty = true;
    }

    /**
     * Returns the comment for the markerset
     * @return The markerset comment
     */
    public String getComm() {        
        return comm;
    }

    /**
     * Creates a new markerset
     * @return The primary key if creation was succesful
     * @param caller The current caller object
     * @param msid The markerset id
     * @param suid The sampling unit id
     * @param name The name of the markerset
     * @param comm The comment for the markerset
     * @throws CreateException If the markerset could not be created
     */
    public java.lang.Integer ejbCreate(int msid, int suid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException {
        makeConnection();
        
        this.msid = msid;
        this.name = name;
        this.comm = comm;
        this.suid = suid;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis());
        
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into marker_sets (msid,suid,name,comm,id,ts) values (?,?,?,?,?,?)");
            ps.setInt(1, msid);
            ps.setInt(2, suid);
            ps.setString(3, name);
            ps.setString(4, comm);            
            ps.setInt(5, id);
            ps.setDate(6, updated);            
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            throw new CreateException("MarkerSetBean#ejbCreate: Unable to create markerset: "+e.getMessage());
        } finally {
            releaseConnection();
        }
                        
        return new Integer(msid);
    }
    
    /**
     * Returns the sampling unit
     * @return The sampling unit
     */
    public SamplingUnitRemote getSamplingUnit() {        
        try {
            return samplingUnitHome.findByPrimaryKey(new Integer(suid));
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Sets the sampling unit id
     * @param suid The sampling unit id
     */
    public void setSuid(int suid) {
        this.suid = suid;
        this.id = caller.getId();
        this.updated = new java.sql.Date(System.currentTimeMillis());   
        dirty = true;
    }

    /**
     * Finds a markerset by its name and sampling unit id
     * @param name The name of the markerset
     * @param suid The sampling unit id
     * @return The primary key if a markerset was found
     * @throws FinderException If no markerset was found
     */
    public java.lang.Integer ejbFindByNameSamplingUnit(java.lang.String name, int suid) throws javax.ejb.FinderException {
         makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select msid from marker_set where name = ? and suid = ?");
            ps.setString(1, name);
            ps.setInt(2,suid);
            
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("MarkerSetBean#ejbFindByNameSamplingUnit: Cannot find marker set by name and sampling unit id");
            }
        } catch (SQLException se) {
            throw new EJBException("MarkerSetBean#ejbFindByNameSamplingUnit: SQL Exception: "+se.getMessage(), se);
        } finally {
            releaseConnection();
        }                
        
        return new Integer(msid);
    }

    /**
     * Returns the date for the latest changes on the marker set
     * @return The date for the latest changes on the marker set
     */
    public java.sql.Date getTs() {
        return updated;
    }

    /**
     * Returns the id of the user which made the latest changes on the marker set
     * @return The user id
     */
    public int getId() {
        return id;
    }
    
    /**
     * Returns the user which made the last update on the markerset
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
     * Retrieves all marker sets for a specific marker set
     * @param forSuid The sampling unit id
     * @throws javax.ejb.FinderException If the marker sets could not be retrieved
     * @return The marker sets for the specified sampling unit
     */
    public java.util.Collection ejbFindMarkerSets(int forSuid) throws javax.ejb.FinderException {
        Collection markersets = new ArrayList();

        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select msid, name from marker_sets where suid = ? order by name");
            ps.setInt(1, forSuid);
            result = ps.executeQuery();
            
            while (result.next()) {
                markersets.add(new Integer(result.getInt("msid")));
            }
        } catch (SQLException se) {
            throw new EJBException("MarkerSetBean#ejbFindMarkerSets: unable to find markersets. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
      
        return markersets;
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
            ps = conn.prepareStatement("insert into marker_sets_log (msid, name, comm, id, ts) values (?, ?, ?, ?, ?)");
            ps.setInt(1, msid);
            ps.setString(2, name);
            ps.setString(3, comm);
            ps.setInt(4, id);
            ps.setDate(5, updated);            
            
            ps.execute();
            
        } catch (Exception e) {
            throw new EJBException("MarkerSetBean#addHistory: Error writing history for marker set. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns a collection of history log entries for the marker set
     * @return A collection of history log entries
     */
    public Collection getHistory() {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        
        ResultSet rs = null;
        
        try {
            ps = conn.prepareStatement("select * from marker_sets_log where msid = ? order by ts desc");
            ps.setInt(1, msid);
            rs = ps.executeQuery();
            MarkerSetDTO dto = null;
            
            UserRemote ur = null;
    
            while (rs.next()) {
                ur = userHome.findByPrimaryKey(new Integer(rs.getInt("id")));
                
                dto = new MarkerSetDTO(rs.getString("name"), rs.getString("comm"), ur.getUsr(), rs.getDate("ts"));              

                arr.add(dto);
            }
        } catch (Exception se) {
            throw new EJBException("MarkerSetBean#getHistory: unable to find marker set history. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }  

    /**
     * Finds markersets using a query based on the restrictions from parameters in the formdatamanager.
     * @param fdm The formdatamanager holding parameters from the filter UI
     * @throws javax.ejb.FinderException If the markersets could not be found
     * @return A collection of marker sets
     */
    public java.util.Collection ejbFindByQuery(FormDataManager fdm) throws javax.ejb.FinderException {
        String sql = "";      
        String sqlAppend = "";
        Collection arr = new ArrayList();
        String[] myKeys = {"v.suid", "v.name", "v.comm", "member"};
        fdm = FormDataManager.requestFormData(fdm, myKeys);        
        
        if(fdm != null && fdm.hasValues()) {
            ArrayList list = fdm.getKeys();
            String column = "";
            String data = "";
            String term = "";

            for(int i=0;i<list.size();i++) {
                if(sql.length() > 0)
                    term = " AND";
                column = (String)list.get(i);
                
                data = fdm.getValue(column);
                data = data.replace('*', '%');
                if(data != null && data.length() > 0 && !data.equalsIgnoreCase("null")) {
                    if(!data.equals("%")) {
                        if(column.equals("member")) {
                            sqlAppend += term+" msid in (select p.msid from markers m, positions p where m.mid=p.mid and name like '"+data+"')";
                        }
                        else
                            sql += term+" "+column+" LIKE '"+data+"'";
                    }
                }
            }            
            
            makeConnection();
            

            Statement st = null;    
            try {
                String sqlquery = "SELECT v.msid, v.name FROM v_marker_sets_1 v";                
                String cond = " WHERE ";
                sqlquery += cond;
                sqlquery += sql;
                sqlquery += sqlAppend;
                
                sqlquery += " ORDER BY v.name";
                
                System.out.println(sqlquery);
                st = conn.createStatement();

                ResultSet result = st.executeQuery(sqlquery);         

                while (result.next()) {
                    arr.add(new Integer(result.getInt("msid")));
                }           
            } catch (Exception se) {
                se.printStackTrace();
                throw new FinderException("Unable to find marker sets."+se.getMessage());
            } finally {
                releaseConnection();
            }                      
        }  
        
        return arr; 
    }

    /**
     * Returns the markers for the marker set (not implemented yet)
     * 
     * TODO: This method should not require a msid to be passed along!
     * @param msid The marker set id
     * @return The markers for the marker set
     */
    public Collection getMarkers(int msid) {
        throw new EJBException("Not implemented yet");
    }

    /**
     * 
     * @param caller 
     * @param msid 
     * @param suid 
     * @param name 
     * @param comm 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int msid, int suid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }

    /**
     * Returns the markerset id
     * @return The markerset id
     */
    public int getMsid() {        
        return msid;
    }
}
