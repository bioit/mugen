package com.arexis.mugen.phenotype.uvariableset;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.phenotype.phenotypemanager.UVariableSetDTO;
import javax.ejb.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.user.UserRemote;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;


/**
 * This is the bean class for the UVariableSetBean enterprise bean.
 * Created Jun 16, 2005 9:44:26 AM
 * @author lami
 */
public class UVariableSetBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.phenotype.uvariableset.UVariableSetRemoteBusiness {
    private javax.ejb.EntityContext context;
    private int uvsid, pid, sid, id;
    private String name, comm;
    private java.sql.Date updated;
    
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
            ps = conn.prepareStatement("delete from u_variable_sets where uvsid = ?");
            ps.setInt(1, getUvsid());            
            
            int res = ps.executeUpdate();
            
            if (res!=1) {
                throw new EJBException("UVariableSetBean#ejbRemove: Cannot remove u variable set");
            }
        } catch (SQLException se) {
            throw new EJBException("UVariableSetBean#ejbRemove: SQL Exception: "+se.getMessage(), se);
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
            ps = conn.prepareStatement("select name,comm,pid,sid, id, ts from u_variable_sets where uvsid = ?");
            ps.setInt(1,pk.intValue());
            
            result = ps.executeQuery();
            
            if (result.next()) {
                uvsid = pk.intValue();
                name = result.getString("name");
                comm = result.getString("comm");
                sid = result.getInt("sid");
                pid = result.getInt("pid");
                id = result.getInt("id");
                updated = result.getDate("ts");
                dirty = false;
            } else
                throw new EJBException("UVariableSetBean#ejbLoad: Failed to load bean (empty resultset?)");
        } catch (SQLException se) {
            throw new EJBException("UVariableSetBean#ejbLoad: SQL Exception: "+se.getMessage(), se);
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
                ps = conn.prepareStatement("update u_variable_sets set name = ? , comm = ?," +
                        " pid = ?, sid = ?, id = ?, ts = ? where uvsid = ?");

                ps.setString(1, name);
                ps.setString(2, comm);
                ps.setInt(3, pid);
                ps.setInt(4, sid);
                ps.setInt(5, id);
                ps.setDate(6, updated);
                ps.setInt(7, uvsid);

                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("UVariableSetBean#ejStore: Error updating uvariable set [uvsid="+uvsid+"]");
            } finally {
                releaseConnection(); 
                dirty = false;
            }                
        }
    }

    /**
     * Returns the uvariable set id
     * @return The uvariable set id
     */
    public int getUvsid() {        
        return uvsid;
    }

    /**
     * Returns the name of the uvariable set
     * @return The name of the uvariable set
     */
    public String getName() {        
        return name;
    }

    /**
     * Sets the name of the uvariable set
     * @param name The new name of the uvariable set
     */
    public void setName(java.lang.String name) {
        this.name = name;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis()); 
        dirty = true;
    }

    /**
     * Returns the comment for the uvariable set
     * @return The uvariable set comment
     */
    public String getComm() {        
        return comm;
    }

    /**
     * Sets the comment for the uvariable set
     * @param comm The new comment
     */
    public void setComm(java.lang.String comm) {
        this.comm = comm;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis());  
        dirty = true;
    }

    /**
     * Creates a new unified variable set
     * @return The primary key if the unified variable set could be created
     * @param caller The current caller object
     * @param uvsid The unified variable set id
     * @param pid The project id
     * @param sid The species id
     * @param name The unified variable set name
     * @param comm The unified variable set comment
     * @throws CreateException If the unified variable set could not be created
     */
    public java.lang.Integer ejbCreate(int uvsid, int pid, int sid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException {
        this.uvsid = uvsid;
        this.name = name;
        this.comm = comm;
        this.pid = pid;
        this.sid = sid;
        this.id = caller.getId();
        this.updated = new java.sql.Date(System.currentTimeMillis());
        
        makeConnection();
        
        try {    
            PreparedStatement ps = null;
            ps = conn.prepareStatement("insert into u_variable_sets " +
                    " (uvsid,name,comm,pid,sid,id,ts) values (?,?,?,?,?,?,?)");
            ps.setInt(1, uvsid);
            ps.setString(2, name);
            ps.setString(3, comm);
            ps.setInt(4, pid);
            ps.setInt(5, sid);
            ps.setInt(6, id);
            ps.setDate(7, updated);            
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
                throw new CreateException("UVariableSetBEan#ejbCreate: Unable to create uvariable set: "+e.getMessage());
        } finally {
            releaseConnection();
        }                
        
        return new Integer(uvsid);  
    }

    /**
     * Returns the project id
     * @return The project id
     */
    public int getPid() {        
        return pid;
    }

    /**
     * Sets the species id
     * @param sid The species id
     */
    public void setSid(int sid) {
        this.sid = sid;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis());   
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
     * Sets the project id
     * @param pid The project id
     */
    public void setPid(int pid) {
        this.pid = pid;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis());     
        dirty = true;
    }

    /**
     * Returns the date for when the unified variable set was last updated
     * @return The date for when the unified variable set was last updated
     */
    public java.sql.Date getUpdated() {
        return updated;
    }

    /**
     * Returns the id of the user which made the latest changes to the unified variable set
     * @return The user id
     */
    public int getId() {
        return id;
    }

    /**
     * Retrives all unified variable sets for the specified project and species
     * @return The unified variable set for the specified species and project
     * @param forSid The species id
     * @param forPid The project id
     * @throws javax.ejb.FinderException If the unified variables could not be retrieved
     */
    public java.util.Collection ejbFindAllUVariableSets(int forSid, int forPid) throws javax.ejb.FinderException {
        makeConnection();
        Collection uvariablesets = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select name, uvsid from u_variable_sets where sid = ? and pid = ? order by name");
            ps.setInt(1, forSid);
            ps.setInt(2, forPid);
            result = ps.executeQuery();
            while(result.next()){
                uvariablesets.add(new Integer(result.getInt("uvsid")));
            }
        } catch (SQLException se) {
            throw new EJBException("UVariableSetsBean#ejbFindAllUVariableSets: SQL Exception: "+se.getMessage(), se);
        } finally {
            releaseConnection();
        }                
        
        return uvariablesets;
    }

    /**
     * Returns all unified variables for the set
     * @return A collection of unified variables belonging to the unified variable set
     */
    public Collection getAllUVariablesInSet() {
        makeConnection();
        Collection uvariables = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select uvid from r_u_var_set where uvsid = ?");
            ps.setInt(1, uvsid);
            result = ps.executeQuery();
            while(result.next()){
                uvariables.add(new Integer(result.getInt("uvid")));
            }
        } catch (SQLException se) {
            throw new EJBException("UVariableSetsBean#getAllUVariablesInSet: SQL Exception: "+se.getMessage(), se);
        } finally {
            releaseConnection();
        }                
        
        return uvariables;
    }

    /**
     * Sets the caller
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
            ps = conn.prepareStatement("insert into u_variable_sets_log (uvsid, name, comm, id, ts) values (?, ?, ?, ?, ?)");
            ps.setInt(1, uvsid);
            ps.setString(2, name);
            ps.setString(3, comm);
            ps.setInt(4, id);
            ps.setDate(5, updated);            
            
            ps.execute();
            
        } catch (Exception e) {
            throw new EJBException("UVariableSetBean#addHistory: Error writing history for unified variable set. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns a collection of history log entries for the unified variable set
     * @return A collection of history log entries
     */
    public Collection getHistory() {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select * from u_variable_sets_log where uvsid = ? order by ts desc");
            ps.setInt(1, uvsid);

            result = ps.executeQuery();
            UVariableSetDTO dto = null;
            UserRemote ur = null;

            while (result.next()) {
                ur = userHome.findByPrimaryKey(new Integer(result.getInt("id")));
                dto = new UVariableSetDTO(result.getString("name"), result.getString("comm"), ur.getUsr(), result.getDate("ts"));

                arr.add(dto);
            }
        } catch (Exception se) {
            throw new EJBException("UVariableSetBean#getHistory: unable to find unified variable set history. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    /**
     * Finds a unified variable set using the filter parameters in the formdata object
     * @param formdata The formdata object
     * @throws javax.ejb.FinderException If the unified variable sets could not be retrieved
     * @return The unified variables set given the filter parameters
     */
    public java.util.Collection ejbFindByQuery(FormDataManager formdata) throws javax.ejb.FinderException {
        String sql = "";      
        Collection arr = new ArrayList();
        
        String[] keys = new String[] {"sid", "vsname", "vname", "pid"};                    
            
        if(formdata != null) {
            formdata = FormDataManager.requestFormData(formdata, keys);
            ArrayList list = formdata.getKeys();
            String column = "";
            String data = "";
            String term = "";
            boolean members = false;

            for(int i=0;i<list.size();i++) {
                if(sql.length() > 0)
                    term = " AND";
                column = (String)list.get(i);
                
                data = formdata.getValue(column);

                data = data.replace('*', '%');
                if(data != null && data.length() > 0 && !data.equalsIgnoreCase("null")) {
                    if(column.equalsIgnoreCase("vsname")) {
                        sql += term+" vs.name LIKE '"+data+"'";
                    }
                    else if(column.equalsIgnoreCase("vname")) {
                        sql += term+" v.name LIKE '"+data+"'";
                        members = true;
                    }
                    else if(column.equalsIgnoreCase("sid")) {
                        sql += term+" vs.sid LIKE '"+data+"'";
                    }                    
                    else if(column.equalsIgnoreCase("pid")) {
                        sql += term+" vs.pid LIKE '"+data+"'";
                    }                                        
                }
            }
            
            if(members)
                sql += term+" r.uvsid = vs.uvsid AND r.uvid = v.uvid ORDER BY vs.name";
            
            makeConnection();
            

            Statement st = null;    

            try {
                String sqlFull = "";
                if(members)
                    sqlFull = "SELECT DISTINCT vs.uvsid, vs.name FROM u_variable_sets vs, u_variables v, r_u_var_set r WHERE ";
                else
                    sqlFull = "SELECT DISTINCT vs.uvsid, vs.name FROM u_variable_sets vs WHERE ";
                System.out.println(sqlFull+sql);
                st = conn.createStatement();

                ResultSet result = st.executeQuery(sqlFull + sql);         

                while (result.next()) {
                    arr.add(new Integer(result.getInt("uvsid")));
                }           
            } catch (Exception se) {
                se.printStackTrace();
                throw new FinderException("Unable to find unified variable sets."+se.getMessage());
            } finally {
                releaseConnection();
            }                      
        }  
        
        return arr; 
    }

    /**
     * Removes a unified variable from this unified variable set
     * @param uvid The id of the unified variable to remove
     * @param caller The caller
     */
    public void removeUVariable(int uvid, com.arexis.mugen.MugenCaller caller) {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {            
            ps = conn.prepareStatement("delete from r_u_var_set where uvsid = ? AND uvid = ?");
            ps.setInt(1, uvsid);
            ps.setInt(2, uvid);
            ps.execute();
        } catch (SQLException se) {
            se.printStackTrace();
            throw new EJBException("Could not remove unified variable uvid:"+uvid+" from unified variable set:"+uvsid);
        } finally {
            releaseConnection();
        }  
    }

    /**
     * Adds a unified variable to this unified variable set
     * @param uvid The unified variable id
     * @param caller The caller
     */
    public void addUVariable(int uvid, com.arexis.mugen.MugenCaller caller) {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {            
            java.sql.Date ts = new java.sql.Date(System.currentTimeMillis());

            int id = caller.getId();
            ps = conn.prepareStatement("insert into r_u_var_set (uvsid, uvid, pid, ts, id) values (?, ?, ?, ?, ?)");
            ps.setInt(1, uvsid);
            ps.setInt(2, uvid);
            ps.setInt(3, pid);
            ps.setDate(4, ts);
            ps.setInt(5, id);
            ps.execute();
        } catch (SQLException se) {
            se.printStackTrace();
            throw new EJBException("Could not insert unified variable uvid:"+uvid+" into unified variable set:"+uvsid);
        } finally {
            releaseConnection();
        } 
    }


    /**
     * 
     * @param caller 
     * @param uvsid 
     * @param pid 
     * @param sid 
     * @param name 
     * @param comm 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int uvsid, int pid, int sid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }
    
    // </editor-fold>
    
    /**
     * Find a unified variable set by its primary key
     * @param aKey The primary key
     * @return The primary key if the unified variable set could be found
     * @throws FinderException If no unified variable set could be found
     */
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer aKey) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select uvsid from u_variable_sets where uvsid = ?");
            ps.setInt(1,aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("UVariableSetBean#ejbFindByPrimaryKey: Cannot find uvariable set");
            }
        } catch (SQLException se) {
            throw new EJBException("UVariableSetBean#ejbFindByPrimaryKey: SQL Exception: "+se.getMessage(), se);
        } finally {
            releaseConnection();
        }                
        
        return aKey;
    }
}
