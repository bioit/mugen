package com.arexis.mugen.genotype.marker;
import com.arexis.arxframe.PageManager;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.genotype.allele.AlleleRemoteHome;
import com.arexis.mugen.genotype.genotypemanager.MarkerDTO;
import com.arexis.mugen.MugenCaller;
import javax.ejb.*;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.servicelocator.ServiceLocator;
import com.arexis.mugen.species.chromosome.ChromosomeRemote;
import com.arexis.mugen.species.chromosome.ChromosomeRemoteHome;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.log4j.Logger;

/**
 * This is the bean class for the MarkerBean enterprise bean.
 * Created Jun 15, 2005 4:11:37 PM
 * @author lami
 */
public class MarkerBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.genotype.marker.MarkerRemoteBusiness {
    private static Logger logger = Logger.getLogger(MarkerBean.class);
    private javax.ejb.EntityContext context;
    private String name, alias;
    private String p1, p2;
    private double position;
    private String comm;
    private int mid, suid, cid, id;
    private java.sql.Date ts;
    
    private ChromosomeRemoteHome chromosomeHome;
    private UserRemoteHome userHome;
    private AlleleRemoteHome alleleHome;
    private SamplingUnitRemoteHome samplingUnitHome;
    
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
        chromosomeHome = (ChromosomeRemoteHome)locator.getHome(ServiceLocator.Services.CHROMOSOME);
        userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);        
        alleleHome = (AlleleRemoteHome)locator.getHome(ServiceLocator.Services.ALLELE);         
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
    public void ejbRemove() throws RemoveException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("delete from markers where mid=?");
            ps.setInt(1, getMid());
            
            int res = ps.executeUpdate();
            
            if (res!=1) {
                throw new RemoveException("Could not remove marker");
            }
        } catch (SQLException se) {
            logger.error("Failed to remove marker", se);
            throw new RemoveException("Could not remove marker");
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
            ps = conn.prepareStatement("select name,comm,alias,p1,p2,position,suid,cid,ts,id from markers where mid = ?");
            ps.setInt(1,pk.intValue());
            
            result = ps.executeQuery();
            
            if (result.next()) {
                mid = pk.intValue();
                name = result.getString("name");
                alias = result.getString("alias");
                p1 = result.getString("p1");
                p2 = result.getString("p2");
                position = result.getDouble("position");
                comm = result.getString("comm");
                suid = result.getInt("suid");
                id = result.getInt("id");
                ts = result.getDate("ts");
                cid = result.getInt("cid");
                dirty = false;
            } else
                throw new EJBException("MarkerBean#ejbLoad: Failed to load bean");
        } catch (SQLException se) {
            throw new EJBException("MarkerBean#ejbLoad: SQL Exception", se);
        } finally {
            releaseConnection();
        }                
    }

    /**
     * Creates a new marker
     * @return The primary key
     * @param caller 
     * @param mid The marker id
     * @param cid The chromosome id
     * @param suid The sampling unit id
     * @param name The marker name
     * @param comm The marker comment
     * @param position The marker position
     * @param alias The marker alias
     * @param p1 The marker p1
     * @param p2 The marker p2
     * @throws CreateException If the marker could not be created
     */
    public java.lang.Integer ejbCreate(int mid, int suid, int cid, java.lang.String name, java.lang.String comm, java.lang.String alias, double position, java.lang.String p1, java.lang.String p2, MugenCaller caller) throws javax.ejb.CreateException {
        this.name = name;
        this.alias = alias;
        this.comm = comm;
        this.p1 = p1;
        this.p2 = p2;
        this.position = position;
        this.mid = mid;
        this.suid = suid;
        this.cid = cid;
        this.id = caller.getId();
        this.ts = new java.sql.Date(System.currentTimeMillis());
        
        makeConnection();
            
        try { 
            PreparedStatement ps = null;
            ps = conn.prepareStatement("insert into markers(mid, name, alias, comm, suid, cid, p1, " +
                    "p2, position, id, ts) values (?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, getMid());
            ps.setString(2, name.toUpperCase());
            ps.setString(3, alias);
            ps.setString(4, comm);
            ps.setInt(5, suid);
            ps.setInt(6, cid);
            ps.setString(7, p1);
            ps.setString(8, p2);
            ps.setDouble(9, position);
            ps.setInt(10, id);
            ps.setDate(11, ts);            
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
                throw new CreateException("MarkerBean#ejbCreate: Unable to create Marker: "+e.getMessage());
        } finally {
            releaseConnection();
        }                   
                
        return new Integer(mid);
    }

    /**
     * 
     * @param caller 
     * @param mid 
     * @param suid 
     * @param cid 
     * @param name 
     * @param comm 
     * @param alias 
     * @param position 
     * @param p1 
     * @param p2 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int mid, int suid, int cid, java.lang.String name, java.lang.String comm, java.lang.String alias, double position, java.lang.String p1, java.lang.String p2, MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
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
                ps = conn.prepareStatement("update markers set mid = ? ," +
                        " name = ?, alias = ?, comm = ?, position = ?," +
                        " cid = ?, suid = ?, p1 = ?, p2 = ?, id = ?, ts = ? where mid = ?");

                ps.setInt(1, mid);
                ps.setString(2, name);
                ps.setString(3, alias);
                ps.setString(4, comm);
                ps.setDouble(5, position);
                ps.setInt(6, cid);
                ps.setInt(7, suid);            
                ps.setString(8, p1);
                ps.setString(9, p2);                       
                ps.setInt(10, id);
                ps.setDate(11, ts);
                ps.setInt(12, mid);

                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("MarkerBean#ejbStore: Error updating marker [mid="+mid+"]");
            } finally {
                releaseConnection();
                dirty = false;
            }                
        }
    }
 
    // </editor-fold>
    
    /**
     * Sets the marker position
     * @param position The new position
     */
    public void setPosition(double position) {
        this.position = position;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }

    /**
     * Returns the position of the marker
     * @return The position
     */
    public double getPosition() {        
        return position;
    }

    /**
     * Returns the marker id
     * @return The marker id
     */
    public int getMid() {        
        return mid;
    }
    
    /**
     * Returns p1
     * @return p1 of the marker
     */
    public String getP1() {
        return p1;
    }

    /**
     * Sets p1 for the marker
     * @param p1 The new p1
     */
    public void setP1(java.lang.String p1) {
        this.p1 = p1;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());   
        dirty = true;
    }

    /**
     * Returns p2 for the marker
     * @return The p2 for the marker
     */
    public String getP2() {
        return p2;
    }

    /**
     * Sets p2 for the marker
     * @param p2 The new p2
     */
    public void setP2(java.lang.String p2) {
        this.p2 = p2;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }      

    /**
     * Returns the name of the marker
     * @return The name
     */
    public String getName() {        
        return name;
    }

    /**
     * Sets the name of the marker
     * @param name The name of the marker
     */
    public void setName(java.lang.String name) {
        this.name = name;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());   
        dirty = true;
    }

    /**
     * Returns the alias for the marker
     * @return The alias for the marker
     */
    public String getAlias() {        
        return alias;
    }

    /**
     * Sets the alias for the marker
     * @param alias The new alias for the marker
     */
    public void setAlias(java.lang.String alias) {
        this.alias = alias;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());   
        dirty = true;
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
     * @param suid The new sampling unit id
     */
    public void setSuid(int suid) {
        this.suid = suid;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());  
        dirty = true;
    }

    /**
     * Returns the chromosome
     * @return The chromosome
     */
    public ChromosomeRemote getChromosome() {        
        try {
            return chromosomeHome.findByPrimaryKey(new Integer(cid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Sets the chromosome id
     * @param cid The chromosome id
     */
    public void setCid(int cid) {
        this.cid = cid;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());   
        dirty = true;
    }

    /**
     * Find marker through the name and sampling unit id
     * @param name The marker name
     * @param suid The sampling unit id
     * @return The primary key if the marker was found
     * @throws FinderException If no marker was found
     */
    public java.lang.Integer ejbFindByNameSamplingUnit(java.lang.String name, int suid) throws javax.ejb.FinderException {
        makeConnection();
        int tmpMid = 0;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select mid from markers where suid = ? and name = ?");
            
            ps.setInt(1,suid);
            ps.setString(2, name);
            
            result = ps.executeQuery();
            
            if (!result.next()) 
            {
                throw new ObjectNotFoundException("MarkerBean#ejbFindByNameSamplingUnit: Cannot find marker by name and samplingunit id");
            }
            else
            {
                tmpMid=result.getInt("mid");
            }
        } catch (SQLException se) {
            logger.error("Failed to find marker from name and sampling unit", se);
            throw new FinderException("Cannot find marker");
        } finally {
            releaseConnection();
        }                
        
        return new Integer(tmpMid);
    }

    /**
     * Returns all markers for the specified chromosome
     * @param forSuid The sampling unit id
     * @throws javax.ejb.FinderException If the markers could not be retrived
     * @return A collection of markers
     */
    public java.util.Collection ejbFindAllMarkers(int forSuid) throws javax.ejb.FinderException {
        Collection markers = new ArrayList();

        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select mid, name from markers where suid = ? order by name");
            ps.setInt(1, forSuid);
            result = ps.executeQuery();
            
            while (result.next()) {
                markers.add(new Integer(result.getInt("mid")));
            }
        } catch (SQLException se) {
            logger.error("Failed to find all markers", se);
            throw new FinderException("Cannot find all markers");
        } finally {
            releaseConnection();
        }
      
        return markers;
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
     * Returns the date for when the last changes were made on the marker
     * @return The date for the latest changes
     */
    public java.sql.Date getTs() {
        return ts;
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
            ps = conn.prepareStatement("insert into markers_log (mid, name, alias, comm, p1, p2, position, cid, id, ts) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, mid);
            ps.setString(2, name);
            ps.setString(3, alias);
            ps.setString(4, comm);
            ps.setString(5, p1);
            ps.setString(6, p2);
            ps.setDouble(7, position);            
            ps.setInt(8, cid);
            ps.setInt(9, id);
            ps.setDate(10, ts);            
            
            ps.execute();
            
        } catch (Exception e) {
            throw new EJBException("MarkerBean#addHistory: Error writing history for marker. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns a collection of history log entries for the marker
     * @return A collection of history log entries
     */
    public Collection getHistory() {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        
        ResultSet rs = null;
        
        try {
            ps = conn.prepareStatement("select * from markers_log where mid = ? order by ts desc");
            ps.setInt(1, mid);
            rs = ps.executeQuery();
            MarkerDTO dto = null;
            UserRemote ur = null;
            ChromosomeRemote cr = null;
    
            while (rs.next()) {
                ur = userHome.findByPrimaryKey(new Integer(rs.getInt("id")));
                cr = chromosomeHome.findByPrimaryKey(new Integer(rs.getInt("cid")));
                
                dto = new MarkerDTO(rs.getString("name"), rs.getString("alias"), rs.getString("p1"), rs.getString("p2"), rs.getDouble("position"), cr.getName(), rs.getString("comm"), ur.getUsr(), rs.getDate("ts"));              

                arr.add(dto);
            }
        } catch (Exception se) {
            throw new EJBException("MarkerBean#getHistory: unable to find marker history. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }  

    /**
     * Finds markers using a restricted query based on the incoming parameters found in the formdatamanager. The pagemanager is used for optimization..
     * @param fdm The formdatamanager holding parameters from the filter UI
     * @param pageManager The pagemanager that holds information regarding the number of objects needed to fill a page
     * @throws javax.ejb.FinderException If the markers could not be found
     * @return A collection of markers
     */
    public java.util.Collection ejbFindByQuery(FormDataManager fdm, PageManager pageManager) throws javax.ejb.FinderException {
        String sql = "";      
        Collection arr = new ArrayList();
        String[] myKeys = {"suid", "cname", "name"};
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
                
                if(data != null && data.length() > 0 && !data.equalsIgnoreCase("null")) {
                    data = data.replace('*', '%');
                    if(!data.equals("%"))
                        sql += term+" "+column+" LIKE '"+data+"'";
                }
            }
            sql += " ORDER BY name";
            
            makeConnection();
            

            Statement st = null;    

            try {
                String sqlFull = "SELECT mid FROM v_markers_3 WHERE ";
                System.out.println(sqlFull+sql);
                st = conn.createStatement();

                ResultSet result = null;
                
                // Tuning...use LIMIT on postgres
                if(conn.getMetaData().getDatabaseProductName().equalsIgnoreCase("PostgreSQL")) {
                    result = st.executeQuery(sqlFull+sql+" LIMIT "+pageManager.getDelta()+ " OFFSET "+(pageManager.getStart()-1));
                    while (result.next())
                        arr.add(new Integer(result.getInt("mid")));
                }
                else {
                    result = st.executeQuery(sqlFull+sql);
                
                    int ctr = 0;
                    int delta = pageManager.getDelta();
                    int start = pageManager.getStart();
                    int stop = pageManager.getStop();
                    int index = 0;

                    while (result.next() && ctr < delta) {
                        if (index>=start && index<= stop) {                    
                            arr.add(new Integer(result.getInt("mid")));
                            ctr++;
                        }
                        index++;                  
                    }
                   
              }          
            } catch (Exception se) {
                se.printStackTrace();
                throw new FinderException("Unable to find markers."+se.getMessage());
            } finally {
                releaseConnection();
            }                      
        }  
        
        return arr; 
    }

    /**
     * Returns the alleles for the marker
     * @return The alleles for the marker
     */
    public Collection getAlleles() {
        Collection alleles = null;
        try {
            return alleleHome.findAllAllelesForMarker(mid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException("MarkerBean#getAlleles: Failed to get alleles for marker");
        }
    }

    /**
     * Returns the number of markers that will be returned from a restricted query based on the parameters in the formdatamanager
     * @param fdm The formdatamanager with filter parameters from the UI
     * @return The number of markers the query will return
     */
    public int ejbHomeGetNumberOfMarkers(FormDataManager fdm) {
        String sql = "";      
        Collection arr = new ArrayList();
        String[] myKeys = {"suid", "cname", "name"};
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

                if(data != null && data.length() > 0 && !data.equalsIgnoreCase("null")) {
                    data = data.replace('*', '%');
                    if(!data.equals("%"))
                        sql += term+" "+column+" LIKE '"+data+"'";
                }
            }
            
            makeConnection();
            

            Statement st = null;    

            try {
                String sqlFull = "SELECT count(mid) AS num FROM v_markers_3 WHERE ";
                System.out.println(sqlFull+sql);
                st = conn.createStatement();

                ResultSet result = st.executeQuery(sqlFull+sql);
                if(result.next())
                    return result.getInt("num");
            } catch (Exception se) {
                se.printStackTrace();
                throw new EJBException("Unable to count markers."+se.getMessage());
            } finally {
                releaseConnection();
            }                      
        }  
        
        return 0; 
    }

    /**
     * Finds the markers for the given chromosome and sampling unit
     * @param forCid The chromosome id
     * @param forSuid The sampling unit id
     * @throws javax.ejb.FinderException If the markers could not be found
     * @return A collection of markers
     */
    public java.util.Collection ejbFindByChromosome(int forCid, int forSuid) throws javax.ejb.FinderException {
        Collection markers = new ArrayList();

        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;

        try {
            ps = conn.prepareStatement("select mid, name from markers where suid = ? AND cid = ? order by name");
            ps.setInt(1, forSuid);
            ps.setInt(2, forCid);
            result = ps.executeQuery();
            
            while (result.next()) {
                markers.add(new Integer(result.getInt("mid")));
            }
        } catch (SQLException se) {
            throw new EJBException("MarkerBean#ejbFindAllMarkers: unable to find markers. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
      
        return markers;
    }

    /**
     * Gets the comment for the marker
     * @return The comment for the marker
     */
    public String getComm() {        
        return comm;
    }

    /**
     * Sets the comment for the marker
     * @param comm The new comment for the marker
     */
    public void setComm(java.lang.String comm) {
        this.comm = comm;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());       
        dirty = true;
    }    
    
    
    
    /**
     * Find the marker using its primary key
     * @param aKey The primary key
     * @return The primary key if the marker could be found
     * @throws FinderException If the marker could not be found
     */
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer aKey) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select mid from markers where mid = ?");
            ps.setInt(1, aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("MarkerBean#ejbFindByPrimaryKey: Cannot find Marker");
            }
        } catch (SQLException se) {
            throw new EJBException("MarkerBean#ejbFindByPrimaryKey: SQL Exception", se);
        } finally {
            releaseConnection();
        }                   
        
        return aKey;  
    }
}
