package com.arexis.mugen.genotype.umarker;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.genotype.genotypemanager.UMarkerDTO;
import com.arexis.mugen.MugenCaller;
import javax.ejb.*;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.servicelocator.ServiceLocator;
import com.arexis.mugen.species.chromosome.ChromosomeRemote;
import com.arexis.mugen.species.chromosome.ChromosomeRemoteHome;
import com.arexis.mugen.species.species.SpeciesRemote;
import com.arexis.mugen.species.species.SpeciesRemoteHome;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This is the bean class for the UMarkerBean enterprise bean.
 * Created Jun 15, 2005 1:40:41 PM
 * @todo What about cid, sid, pid??? Ignored for now at least...
 * @todo How should position be typed? Here, Double is used...in LMarker, string is used...need consistency
 * @author lami
 */
public class UMarkerBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.genotype.umarker.UMarkerRemoteBusiness {
    private javax.ejb.EntityContext context;
    private int umid, pid, sid, cid, id;
    private String name, alias, comm;
    private double position;
    private java.sql.Date ts;
    
    private boolean dirty;
    
    private ChromosomeRemoteHome chromosomeHome;
    private SpeciesRemoteHome speciesHome;
    
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
        speciesHome = (SpeciesRemoteHome)locator.getHome(ServiceLocator.Services.SPECIES);
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
            ps = conn.prepareStatement("delete from u_markers where umid=?");
            ps.setInt(1, getUmid());
            
            int res = ps.executeUpdate();
            
            if (res!=1) {
                throw new EJBException("UMarkerBean#ejbRemove: Cannot remove UMarker");
            }
        } catch (SQLException se) {
            throw new EJBException("UMarkerBean#ejbRemove: Cannot remove UMarker", se);
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
            ps = conn.prepareStatement("select * from u_markers where umid = ?");
            ps.setInt(1,pk.intValue());
            
            result = ps.executeQuery();

            if (result.next()) {
                umid = pk.intValue();
                name = result.getString("name");
                alias = result.getString("alias");
                pid = result.getInt("pid");
                sid = result.getInt("sid");
                cid = result.getInt("cid");
                position = result.getDouble("position");
                comm = result.getString("comm");
                id = result.getInt("id");
                ts = result.getDate("ts");
                dirty = false;
            } else {
                throw new EJBException("UMarkerBean#ejbLoad: Exception caught (empty resultset?)");
            }
        } catch (SQLException se) {            
            throw new EJBException("UMarkerBean#ejbLoad: SQL execption", se);
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
                ps = conn.prepareStatement("update U_markers set umid = ? ," +
                        " name = ?, alias = ?, comm = ?, position = ?, pid = ?," +
                        " sid = ?, cid = ?, id = ?, ts = ? where umid = ?");

                ps.setInt(1, umid);
                ps.setString(2, name);
                ps.setString(3, alias);
                ps.setString(4, comm);
                ps.setDouble(5, position);
                ps.setInt(6, pid);
                ps.setInt(7, sid);
                ps.setInt(8, cid);
                ps.setInt(9, id);
                ps.setDate(10, ts);
                ps.setInt(11, umid);

                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("UMarkerBean#ejbStore: Error updating Umarker [Umid="+umid+"]");
            } finally {
                releaseConnection();
                dirty = false;
            }                
        }
    }
    
    // </editor-fold>
    
    /**
     * Finds the unified marker through the primary key
     * @param aKey The primary key
     * @return The primary key if the unified marker could be found
     * @throws FinderException If no unified marker could be found
     */
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer aKey) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select umid from u_markers where umid = ?");
            ps.setInt(1, aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("UMarkerBean#ejbFindByPrimaryKey: Cannot find UMarker");
            }
        } catch (SQLException se) {
            throw new EJBException(se);
        } finally {
            releaseConnection();
        }        
        
        return aKey;
    }

    /**
     * Returns the name of the UMarker
     * @return The name
     */
    public String getName() {        
        return name;
    }

    /**
     * Sets the name of the UMarker
     * @param name The new name
     */
    public void setName(java.lang.String name) {
        this.name = name;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());  
        dirty = true;
    }

    /**
     * Returns the position of the UMarker
     * @return The position
     */
    public double getPosition() {        
        return position;
    }

    /**
     * Sets the position of the UMarker
     * @param position The new position
     */
    public void setPosition(double position) {
        this.position = position;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());   
        dirty = true;
    }

    /**
     * Returns the comment for the UMarker
     * @return The comment for the UMarker
     */
    public String getComm() {        
        return comm;
    }

    /**
     * Sets the comment for the UMarker
     * @param comm The new comment
     */
    public void setComm(java.lang.String comm) {
        this.comm = comm;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());   
        dirty = true;
    }

    /**
     * Returns the alias for the UMarker
     * @return The alias for the UMarker
     */
    public String getAlias() {        
        return alias;
    }

    /**
     * Sets the alias for the UMarker
     * @param alias The new alias for the UMarker
     */
    public void setAlias(java.lang.String alias) {
        this.alias = alias;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());   
        dirty = true;
    }

    /**
     * Creates a new unified marker
     * @param umid The unified marker id
     * @param name The name of the unified marker
     * @param comm The comment for the unified marker
     * @param alias The alias for the unified marker
     * @param position The position for the unified marker
     * @param pid The project id for the unified marker
     * @param sid The species id for the unified marker
     * @param cid The chromosome id for the unified marker
     * @throws CreateException If no marker could be created
     * @return The primary key for the unified marker
     */
    public java.lang.Integer ejbCreate(int umid, java.lang.String name, 
            java.lang.String alias, java.lang.String comm, double position, 
            int pid, int sid, int cid, MugenCaller caller) throws javax.ejb.CreateException {
        this.name = name;
        this.alias = alias;
        this.comm = comm;
        this.position = position;
        this.umid = umid;
        this.pid = pid;
        this.sid = sid;
        this.cid = cid;
        this.id = caller.getId();
        
        makeConnection();
            
        try { 
            PreparedStatement ps = null;
            ps = conn.prepareStatement("insert into u_markers(umid, name," +
                    " alias, comm, pid, sid, cid, id,ts) values " +
                    "(?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, umid);
            ps.setString(2, name.toUpperCase());
            ps.setString(3, alias);
            ps.setString(4, comm);
            ps.setInt(5, pid);
            ps.setInt(6, sid);
            ps.setInt(7, cid);
            ps.setInt(8, id);
            ps.setDate(9, new java.sql.Date(System.currentTimeMillis()));
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
                throw new CreateException("UMarkerBean#ejbCreate: Unable to create UMarker: "+e.getMessage());
        } finally {
            releaseConnection();
        }                       
                
        return new Integer(umid);
    }

    /**
     * Returns the project id
     * @return The project id
     */
    public int getPid() {        
        return pid;
    }

    /**
     * Sets the project id
     * @param pid The project id
     */
    public void setPid(int pid) {
        this.pid = pid;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());   
        dirty = true;
    }

    /**
     * Returns the chromosome id
     * @return The chromosome id
     */
    public int getCid() {        
        return cid;
    }
    
    /**
     * Returns the chromosome for the unified marker
     * @return The chromsome for the unified marker
     */
    public ChromosomeRemote getChromosome()
    {
        ChromosomeRemote c = null;
        try
        {
            c = chromosomeHome.findByPrimaryKey(new Integer(cid));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return c;
    }

    /**
     * Sets the chromosome id
     * @param cid The new chromosome id
     */
    public void setCid(int cid) {
        this.cid = cid;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());     
        dirty = true;
    }

    /**
     * Returns the species id
     * @return The species id
     */
    public int getSid() {        
        return sid;
    }
    
    /**
     * Returns the species for the unified marker
     * @return The species for the unified marker
     */
    public SpeciesRemote getSpecies()
    {
        SpeciesRemote s = null;
        try
        {
            s = speciesHome.findByPrimaryKey(new Integer(sid));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * Sets the species id
     * @param sid The new species id
     */
    public void setSid(int sid) {
        this.sid = sid;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());     
        dirty = true;
    }

    /**
     * Finds an unified marker by its name, project id and species id
     * @param name The marker name
     * @param pid The project id
     * @param sid The species id
     * @return The primary key if succesful
     * @throws FinderException If no marker could be found
     */
    public java.lang.Integer ejbFindByNameProjectSpecies(java.lang.String name, int pid, int sid) throws javax.ejb.FinderException {
         makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select umid from u_markers where pid = ? and sid = ? and name = ?");
            
            ps.setInt(1,pid);
            ps.setInt(2,sid);
            ps.setString(3, name);
            
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("UMarkerBean#ejbFindByNameProjectSpecies: Cannot find umarker by name, project id and species id");
            }
        } catch (SQLException se) {
            throw new EJBException("UMarkerBean#ejbFindByNameProjectSpecies: SQL exception", se);
        } finally {
            releaseConnection();
        }        
        
        
        return new Integer(umid);
    }

    /**
     * Returns all unified markers for the specified project and chromosome
     * @param forCid The chromosome id
     * @param forPid The project id
     * @throws javax.ejb.FinderException If the unified markers could be retrieved
     * @return A collection of unified markers
     */
    public java.util.Collection ejbFindUMarkers(int forCid, int forPid) throws javax.ejb.FinderException {
        Collection umarkers = new ArrayList();

        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select umid, name from u_markers where cid = ? and pid = ? order by name");
            ps.setInt(1, forCid);
            ps.setInt(2, forPid);
            result = ps.executeQuery();
            
            while (result.next()) {
                umarkers.add(new Integer(result.getInt("umid")));
            }
        } catch (SQLException se) {
            throw new EJBException("UMarkerBean#ejbFindUMarkers: unable to find unified markers. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
      
        return umarkers;
    }
    
    /**
     * Returns all unified markers for the specified project
     * @param forPid The project id
     * @throws javax.ejb.FinderException If the unified markers could be retrieved
     * @return A collection of unified markers
     */    
    public java.util.Collection ejbFindUMarkers(int forPid) throws javax.ejb.FinderException {
        Collection umarkers = new ArrayList();

        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select umid, name from u_markers where pid = ? order by name");
            ps.setInt(1, forPid);
            result = ps.executeQuery();
            
            while (result.next()) {
                umarkers.add(new Integer(result.getInt("umid")));
            }
        } catch (SQLException se) {
            throw new EJBException("UMarkerBean#ejbFindUMarkers: unable to find unified markers. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
      
        return umarkers;
    }    

    /**
     * Returns the date for the latest update
     * @return The date
     */
    public java.sql.Date getTs() {
        return ts;
    }

    /**
     * Returns the id of the user which made the last update
     * @return The user id
     */
    public int getId() {
        return id;
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
            ps = conn.prepareStatement("insert into u_markers_log (umid, name, alias, comm, position, cid, id, ts) values (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, umid);
            ps.setString(2, name);
            ps.setString(3, alias);
            ps.setString(4, comm);
            ps.setDouble(5, position);            
            ps.setInt(6, cid);
            ps.setInt(7, id);
            ps.setDate(8, ts);            
            
            ps.execute();
            
        } catch (Exception e) {
            throw new EJBException("UMarkerBean#addHistory: Error writing history for unified marker. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns a collection of history log entries for the unified marker
     * @return A collection of history log entries
     */
    public Collection getHistory() {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        
        ResultSet rs = null;
        
        try {
            ps = conn.prepareStatement("select * from u_markers_log where umid = ? order by ts desc");
            ps.setInt(1, umid);
            rs = ps.executeQuery();
            UMarkerDTO dto = null;
            UserRemote ur = null;
            ChromosomeRemote cr = null;
    
            while (rs.next()) {
                ur = userHome.findByPrimaryKey(new Integer(rs.getInt("id")));
                cr = chromosomeHome.findByPrimaryKey(new Integer(rs.getInt("cid")));
                
                dto = new UMarkerDTO(rs.getString("name"), rs.getString("alias"), rs.getDouble("position"), cr.getName(), rs.getString("comm"), ur.getUsr(), rs.getDate("ts"));              

                arr.add(dto);
            }
        } catch (Exception se) {
            throw new EJBException("UMarkerBean#getHistory: unable to find unified marker history. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }  


    /**
     * Finds the unified markers using a restricted query based on the parameters in the formdatamanager
     * @param fdm The formdatamanager with the parameters from the filter UI
     * @throws javax.ejb.FinderException If the umarkers could not be found
     * @return A collection of unified markers
     */
    public java.util.Collection ejbFindByQuery(FormDataManager fdm) throws javax.ejb.FinderException {
        String sql = "";      
        Collection arr = new ArrayList();
        String[] myKeys = {"sid", "cname", "pid"};
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
                    if(!data.equals("%"))
                        sql += term+" "+column+" LIKE '"+data+"'";
                }
            }
            sql += " ORDER BY name";
            
            makeConnection();
            

            Statement st = null;    

            try {
                String sqlFull = "SELECT umid FROM v_u_markers_3 WHERE ";
                System.out.println(sqlFull+sql);
                st = conn.createStatement();

                ResultSet result = st.executeQuery(sqlFull + sql);         

                while (result.next()) {
                    arr.add(new Integer(result.getInt("umid")));
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
     * 
     * @param umid 
     * @param name 
     * @param alias 
     * @param comm 
     * @param position 
     * @param pid 
     * @param sid 
     * @param cid 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int umid, java.lang.String name, java.lang.String alias, java.lang.String comm, double position, int pid, int sid, int cid, MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }

    /**
     * Returns the UMarker id for the UMarker
     * @return The UMarker id
     */
    public int getUmid() {
        return umid;
    }
}
