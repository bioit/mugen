package com.arexis.mugen.genotype.umarkerset;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.genotype.genotypemanager.UMarkerSetDTO;
import com.arexis.mugen.MugenCaller;
import javax.ejb.*;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This is the bean class for the UMarkerSetBean enterprise bean.
 * Created Jun 14, 2005 11:18:59 AM
 * @todo Fix id and ts...only dummy data now
 * @author lami
 */
public class UMarkerSetBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.genotype.umarkerset.UMarkerSetRemoteBusiness {
    private javax.ejb.EntityContext context;
    private String name, alias, comm;
    private int umsid, pid, sid, id;
    private java.sql.Date ts;
    
    private boolean dirty;
    
    private UserRemoteHome userHome;
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise beans, Web services)
    // TODO Add business methods
    // TODO Add create methods
    /**
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(javax.ejb.EntityContext aContext) {
        setContext(aContext);
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
            ps = conn.prepareStatement("delete from u_marker_sets where umsid = ?");
            ps.setInt(1,umsid);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            
            throw new EJBException("UMarkerSetBean#ejbRemove: Internal error. Failed to delete unified marker set\n(" +
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
        Integer pk = (Integer)context.getPrimaryKey();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("select umsid,name,comm,pid,sid,id,ts " +
                    "from u_marker_sets where umsid = ?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                umsid = rs.getInt("umsid");
                name = rs.getString("name");
                comm = rs.getString("comm");
                sid = rs.getInt("sid");
                pid = rs.getInt("pid");
                id = rs.getInt("id");
                ts = rs.getDate("ts");
                dirty = false;
            } else
                throw new EJBException("UMarkerSetBean#ejbLoad: Error loading UMarker_set");
        } catch (Exception e) {
            throw new EJBException(e);
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
                ps = conn.prepareStatement("update u_marker_sets set name = ?, comm = ? , pid = ?, sid = ?,ts = ?, id = ? where umsid = ?");

                ps.setString(1, name);
                ps.setString(2, comm);
                ps.setInt(3, pid);
                ps.setInt(4, sid);
                ps.setDate(5, ts);
                ps.setInt(6, id);
                ps.setInt(7, umsid);

                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("UMarkerSetBean#ejbStore: Error updating UMarker set ["+umsid+"]");
            } finally {
                releaseConnection();
                dirty = false;
            }                
        }
    }
    
    // </editor-fold>
    
    /**
     * Finds the unified marker set by its primary key
     * @param aKey The unified marker set primary key
     * @return The primary key of the unified marker set
     * @throws FinderException If no unified marker set could be found
     */
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer aKey) throws javax.ejb.FinderException {
        
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select umsid from U_Marker_Sets where umsid = ?");
            ps.setInt(1,aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("UMarkerSetBean#ejbFindByPrimaryKey: Cannot find Umarkerset by primary key");
            }
        } catch (SQLException se) {
            throw new EJBException("UMarkerSetBean#ejbFindByPrimaryKey: SQL Exception", se);
        } finally {
            releaseConnection();            
        }        
       
        return aKey;
    }
    
   /**
     * Finds a unified marker set by its name, project id and species id
     * @param name The project name
     * @param sid The species id
     * @param pid The project id
     * @return The unified marker id
     * @throws FinderException If no unified marker set exists
     */
    public java.lang.Integer ejbFindByNameProjectSpecies(java.lang.String name, int pid, int sid) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select umsid from u_marker_sets where pid = ? and sid = ? and name = ?");
            
            ps.setInt(1,pid);
            ps.setInt(2,sid);
            ps.setString(3, name);
            
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("UMarkerSetBean#ejbFindByNameProjectSpecies: Cannot find umarker set by name, project id and species id");
            }
        } catch (SQLException se) {
            throw new EJBException(se);
        } finally {
            releaseConnection();
        }                
        
        return new Integer(umsid);
    }    

    /**
     * Creates a new unified marker set
     * @param umsid The unified marker set id
     * @param name The unified marker set name
     * @param comm The unified marker set comment
     * @param pid The project id
     * @param sid The species id
     * @return The unified marker id
     * @throws CreateException If not able to create the unified marker set
     */
    public java.lang.Integer ejbCreate(int umsid, String name, String comm, int pid, int sid, MugenCaller caller) throws javax.ejb.CreateException {
        makeConnection();
        
        this.umsid = umsid;
        this.name = name;
        this.alias = alias;
        this.comm = comm;
        this.pid = pid;
        this.sid = sid;
        this.id = caller.getId();
        this.ts = new java.sql.Date(System.currentTimeMillis());
        
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into u_marker_sets (umsid,name,comm,pid,sid,id,ts) values (?,?,?,?,?,?,?)");
            ps.setInt(1, umsid);
            ps.setString(2, name);
            ps.setString(3, comm);
            ps.setInt(4, pid);
            ps.setInt(5, sid);
            ps.setInt(6, id);
            ps.setDate(7, ts);
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            throw new CreateException("UMarkerSetBean#ejbFindByNameProjectSpecies: Unable to create U_Marker set: "+e.getMessage());
        } finally {
            releaseConnection();
        }
                        
        return new Integer(umsid);
    }

    /**
     * 
     * @param umsid 
     * @param name 
     * @param comm 
     * @param pid 
     * @param sid 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int umsid, String name, String comm, int pid, int sid, MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }

    /**
     * Returns the name of the UMarker
     * @return The marker
     */
    public String getName() {        
        return name;
    }
    
    /**
     * Sets the name of the marker
     * @param name The new name of the marker
     */
    public void setName(java.lang.String name) {
        this.name = name;
        this.id = caller.getId();
        this.ts = new java.sql.Date(System.currentTimeMillis());       
        dirty = true;
    }       

    /**
     * Returns the comment for the marker
     * @return The comment for the marker
     */
    public String getComm() {        
        return comm;
    }
    
    /**
     * Sets the comment for the marker
     * @param comm The new comment
     */
    public void setComm(java.lang.String comm) {
        this.comm = comm;
        this.id = caller.getId();
        this.ts = new java.sql.Date(System.currentTimeMillis());    
        dirty = true;
    }    

    /**
     * Returns the alias for the marker
     * @return The alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the alias for the marker
     * @param alias The alias for the marker
     */
    public void setAlias(java.lang.String alias) {
        this.alias = alias;
        this.id = caller.getId();
        this.ts = new java.sql.Date(System.currentTimeMillis());  
        dirty = true;
    }

    /**
     * Returns the project id
     * @return The project id
     */
    public int getPid() {        
        return pid;
    }

    /**
     * Returns the species id
     * @return The species id
     */
    public int getSid() {        
        return sid;
    }

    /**
     * Set the project id
     * @param pid The project id
     */
    public void setPid(int pid) {
        this.pid = pid;
        this.id = caller.getId();
        this.ts = new java.sql.Date(System.currentTimeMillis());       
        dirty = true;
    }

    /**
     * Set the species id
     * @param sid The species id
     */
    public void setSid(int sid) {
        this.sid = sid;
        this.id = caller.getId();
        this.ts = new java.sql.Date(System.currentTimeMillis());     
        dirty = true;
    }

    /**
     * Returns the date for when the unified marker set was last modified
     * @return The date for the last modification
     */
    public java.sql.Date getTs() {
        return ts;
    }

    /**
     * Returns the id of the user which made the last changes on the unified marker set
     * @return The user id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns all unified marker sets for the specified species
     * @return The unified marker sets for the species
     * @param forPid The project id
     * @param forSid The species id
     * @throws javax.ejb.FinderException If the unified marker sets could not be retrieved
     */
    public java.util.Collection ejbFindUMarkerSets(int forSid, int forPid) throws javax.ejb.FinderException {
        Collection umarkersets = new ArrayList();

        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select umsid, name from u_marker_sets where sid = ? and pid = ?");
            ps.setInt(1, forSid);
            ps.setInt(2, forPid);
            
            result = ps.executeQuery();
            
            while (result.next()) {
                umarkersets.add(new Integer(result.getInt("umsid")));
            }
        } catch (SQLException se) {
            throw new EJBException("UMarkerSetBean#ejbFindUMarkerSets: unable to find unified marker sets. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
      
        return umarkersets;
    }

    /**
     * Sets the current caller object
     * @param caller The caller object
     */
    public void setCaller(MugenCaller caller) {
        this.caller = caller;
    }

    /**
     * Writes a log entry to track changes history     
     */
    public void addHistory() {        
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into u_marker_sets_log (umsid, name, comm, id, ts) values (?, ?, ?, ?, ?)");
            ps.setInt(1, umsid);
            ps.setString(2, name);
            ps.setString(3, comm);
            ps.setInt(4, id);
            ps.setDate(5, ts);            
            
            ps.execute();
            
        } catch (Exception e) {
            throw new EJBException("UMarkerSetBean#addHistory: Error writing history for unified marker set. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns a collection of history log entries for the unified marker set
     * @return A collection of history log entries
     */
    public Collection getHistory() {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        
        ResultSet rs = null;
        
        try {
            ps = conn.prepareStatement("select * from u_marker_sets_log where umsid = ? order by ts desc");
            ps.setInt(1, umsid);
            rs = ps.executeQuery();
            UMarkerSetDTO dto = null;
            
            UserRemote ur = null;
    
            while (rs.next()) {
                ur = userHome.findByPrimaryKey(new Integer(rs.getInt("id")));
                
                dto = new UMarkerSetDTO(rs.getString("name"), rs.getString("comm"), ur.getUsr(), rs.getDate("ts"));              

                arr.add(dto);
            }
        } catch (Exception se) {
            throw new EJBException("UMarkerSetBean#getHistory: unable to find unified marker set history. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }  

    /**
     * Finds a unified marker set using a restricted query based on the parameters in the formdatamanger
     * @param fdm The formdatamanager with the parameters from the filter UI
     * @throws javax.ejb.FinderException If the unified marker sets could not be found
     * @return A collection of unified marker sets
     */
    public java.util.Collection ejbFindByQuery(FormDataManager fdm) throws javax.ejb.FinderException {
        String sql = "";      
        String sqlAppend = "";
        Collection arr = new ArrayList();
        String[] myKeys = {"v.sid", "v.name", "v.comm", "member", "v.pid"};
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
                            sqlAppend += term+" umsid in (select p.umsid from u_markers m, u_positions p where m.umid=p.umid and name like '"+data+"')";
                        }
                        else
                            sql += term+" "+column+" LIKE '"+data+"'";
                    }
                }
            }            
            
            makeConnection();
            

            Statement st = null;    
            try {
                String sqlquery = "SELECT v.umsid, v.name FROM v_u_marker_sets_1 v";                
                String cond = " WHERE ";
                sqlquery += cond;
                sqlquery += sql;
                sqlquery += sqlAppend;
                
                sqlquery += " ORDER BY v.name";
                
                System.out.println(sqlquery);
                st = conn.createStatement();

                ResultSet result = st.executeQuery(sqlquery);         

                while (result.next()) {
                    arr.add(new Integer(result.getInt("umsid")));
                }           
            } catch (Exception se) {
                se.printStackTrace();
                throw new FinderException("Unable to find unified marker sets."+se.getMessage());
            } finally {
                releaseConnection();
            }                      
        }  
        
        return arr; 
    }

    /**
     * 
     * @param aContext 
     */
    public void setContext(javax.ejb.EntityContext aContext) {
        this.context = aContext;
    }

    /**
     * Returns the UMarker id
     * @return The UMarker id
     */
    public int getUmsid() {
        return umsid;
    }
}
