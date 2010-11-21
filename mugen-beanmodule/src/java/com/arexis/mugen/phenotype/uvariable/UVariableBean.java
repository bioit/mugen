package com.arexis.mugen.phenotype.uvariable;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.phenotype.phenotypemanager.UVariableDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.user.UserRemote;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.*;

/**
 * This is the bean class for the UVariableBean enterprise bean.
 * Created Jun 16, 2005 9:14:34 AM
 * @author lami
 */
public class UVariableBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.phenotype.uvariable.UVariableRemoteBusiness {
    private javax.ejb.EntityContext context;
    private int uvid, pid, sid, id;
    private String name, type, unit, comm;
    private java.sql.Date updated;
    
    private boolean dirty;
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">

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
            ps = conn.prepareStatement("delete from u_variables where uvid = ?");
            ps.setInt(1, getUvid());            
            
            int res = ps.executeUpdate();
            
            if (res!=1) {
                throw new EJBException("UVariableBean#ejbRemove: Cannot remove uvariable");
            }
        } catch (SQLException se) {
            throw new EJBException("UVariableBean#ejbRemove: SQL Exception: "+se.getMessage(), se);
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
            ps = conn.prepareStatement("select name,comm,unit,type,pid,sid, id, ts from u_variables where uvid = ?");
            ps.setInt(1,pk.intValue());
            
            result = ps.executeQuery();
            
            if (result.next()) {
                uvid = pk.intValue();
                name = result.getString("name");
                comm = result.getString("comm");
                type = result.getString("type");
                unit = result.getString("unit");
                sid = result.getInt("sid");
                pid = result.getInt("pid");
                id = result.getInt("id");
                updated = result.getDate("ts");
                dirty = false;
            } else
                throw new EJBException("UVariableBean#ejbLoad: Failed to load bean (resultset empty?)");
        } catch (SQLException se) {
            throw new EJBException("UVariableBean#ejbLoad: SQL Exception: "+se.getMessage(), se);
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
                ps = conn.prepareStatement("update u_variables set name = ? , type = ?," +
                        "unit = ?, comm = ?," +
                        " pid = ?, sid = ?, id  = ?, ts = ? where uvid = ?");

                ps.setString(1, name);
                ps.setString(2, type);
                ps.setString(3, unit);
                ps.setString(4, comm);
                ps.setInt(5, pid);
                ps.setInt(6, sid);
                ps.setInt(7, id);
                ps.setDate(8, updated);
                ps.setInt(9, uvid);

                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("UVariableBean#ejbStore: Error updating uvariable  [uvid="+uvid+"]");
            } finally {
                releaseConnection(); 
                dirty = false;
            }                
        }   
    }

    /**
     * Returns the name of the Uvariable
     * @return The name of the uvariable
     */
    public String getName() {        
        return name;
    }

    /**
     * Sets the name of the uvariable
     * @param name The new name
     */
    public void setName(java.lang.String name) {
        this.name = name;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis());         
        dirty = true;
    }

    /**
     * Returns the uvariable id
     * @return The uvariable id
     */
    public int getUvid() {        
        return uvid;
    }

    /**
     * Returns the uvariable comment
     * @return The uvariable comment
     */
    public String getComm() {        
        return comm;
    }

    /**
     * Sets the uvariable comment
     * @param comm The new uvariable comment
     */
    public void setComm(java.lang.String comm) {
        this.comm = comm;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis());  
        dirty = true;
    }

    /**
     * Returns the unit for the unified variable
     * @return The unit
     */
    public String getUnit() {        
        return unit;
    }

    /**
     * Sets the uvariable unit
     * @param unit The new uvariable unit
     */
    public void setUnit(java.lang.String unit) {
        this.unit = unit;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis());       
        dirty = true;
    }

    /**
     * Returns the type of the uvariable
     * @return The type of the uvariable
     */
    public String getType() {        
        return type;
    }

    /**
     * Sets the type of the uvariable
     * @param type The new uvariable type
     */
    public void setType(java.lang.String type) {
        this.type = type;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis());         
        dirty = true;
    }

    /**
     * Creates a new unified variable
     * 
     * java.lang.Integer uvid
     * Integer pid
     * Integer sid
     * java.lang.String name
     * java.lang.String comm
     * java.lang.String unit
     * java.lang.String type
     * @return The primary key if able to create a new unified variable
     * @param caller 
     * @param uvid The unified variable id
     * @param pid The project id
     * @param sid The species id
     * @param name The unified variable name
     * @param comm The unified variable comment
     * @param unit The unified variable unit
     * @param type The unified variable type
     * @throws CreateException If no unified variable could be created
     */
    public java.lang.Integer ejbCreate(int uvid, int pid, int sid, java.lang.String name, java.lang.String comm, java.lang.String unit, java.lang.String type, MugenCaller caller) throws javax.ejb.CreateException {
        this.uvid = uvid;
        this.unit = unit;
        this.type = type;
        this.name = name;
        this.comm = comm;
        this.pid = pid;
        this.sid = sid;
        this.id = caller.getId();
        this.updated = new java.sql.Date(System.currentTimeMillis());
        
        makeConnection();
        
        try {    
            PreparedStatement ps = null;
            ps = conn.prepareStatement("insert into u_variables " +
                    " (uvid,name,type,unit,comm,pid,sid,id,ts) values (?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, uvid);
            ps.setString(2, name);
            ps.setString(3, type);
            ps.setString(4, unit);
            ps.setString(5, comm);
            ps.setInt(6, pid);
            ps.setInt(7, sid);
            ps.setInt(8, id);
            ps.setDate(9, updated);            
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
                throw new CreateException("UVariableBean#ejbCreate: Unable to create uvariable: "+e.getMessage());
        } finally {
            releaseConnection();
        }                
        
        return new Integer(uvid); 
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
     * Find a unified variable by its name, the project id and species id
     * @param name The unified variable name
     * @param pid The project id
     * @param sid The species id
     * @return The primary key if an unified variable was found
     * @throws FinderException If no unified variable was found
     */
    public java.lang.Integer ejbFindByNameProjectSpecies(java.lang.String name, int pid, int sid) throws javax.ejb.FinderException {
         makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select uvid from u_variables where name = ? and sid = ? and pid = ?");
            ps.setString(1, name);
            ps.setInt(2, sid);
            ps.setInt(3, pid);
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("UVariableBean#ejbFindByNameProjectSpecies: Cannot find uvariable by name, project and species id ");
            }
        } catch (SQLException se) {
            throw new EJBException("UVariableBean#ejbFindByNameProjectSpecies: SQL Exception: "+se.getMessage(), se);
        } finally {
            releaseConnection();
        }                
        
        return new Integer(uvid);
    }

    /**
     * Finds all unified variables for a specific project
     * @param forPid The project id
     * @throws javax.ejb.FinderException If the unified variables could not be retrieved
     * @return A collection of unified variables
     */
    public java.util.Collection ejbFindAllUVariables(int forPid) throws javax.ejb.FinderException {
        makeConnection();
        Collection uvariables = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select name, uvid from u_variables where pid = ? order by name");
            ps.setInt(1, forPid);
            result = ps.executeQuery();
            while(result.next()){
                uvariables.add(new Integer(result.getInt("uvid")));
            }
        } catch (SQLException se) {
            throw new EJBException("UVariableBean#ejejbFindAllUVariables: SQL Exception: "+se.getMessage(), se);
        } finally {
            releaseConnection();
        }                
        
        return uvariables;
    }

    /**
     * Returns the date for when the latest changes were made on the unified variable
     * @return The date for when the latest changes were made
     */
    public java.sql.Date getUpdated() {
        return updated;
    }

    /**
     * Returns the id of the user which made the latest changes on the unified variable
     * @return The id of the user which made the latest changes on the unified variable
     */
    public int getId() {
        return id;
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
            ps = conn.prepareStatement("insert into u_variables_log (uvid, name, type, unit, comm, id, ts) values (?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, uvid);
            ps.setString(2, name);
            ps.setString(3, type);
            ps.setString(4, unit);
            ps.setString(5, comm);
            ps.setInt(6, id);
            ps.setDate(7, updated);            
            
            ps.execute();
            
        } catch (Exception e) {
            throw new EJBException("UVariableBean#addHistory: Error writing history for unified variable. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns a collection of history log entries for the unified variable
     * @return A collection of history log entries
     */
    public Collection getHistory() {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select * from u_variables_log where uvid = ? order by ts desc");
            ps.setInt(1, uvid);

            result = ps.executeQuery();
            UVariableDTO dto = null;
            UserRemote ur = null;

            while (result.next()) {
                ur = userHome.findByPrimaryKey(new Integer(result.getInt("id")));
                dto = new UVariableDTO(result.getString("name"), result.getString("type"), result.getString("unit"), result.getString("comm"), ur.getUsr(), result.getDate("ts"));

                arr.add(dto);
            }
        } catch (Exception se) {
            throw new EJBException("UVariableBean#getHistory: unable to find unified variable history. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    /**
     * Fetches all unified variables that are not member of the specified set.
     * @return The unified variables that are not a member of the specified unified variable set
     * @param forPid The project id
     * @param nonUvsid The set that the unifiedvariables should NOT belong to
     * @throws javax.ejb.FinderException If the unified variables could not be found
     */    
    public java.util.Collection ejbFindByNotInUVariableSet(int nonUvsid, int forPid) throws javax.ejb.FinderException {
       makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select v.uvid from u_variables v  where pid=? except (select v.uvid from u_variables v, r_u_var_set r where v.uvid=r.uvid and r.uvsid=?)");
            ps.setInt(2,nonUvsid);
            ps.setInt(1,forPid);

            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("uvid")));
            }
        } catch (SQLException se) {
            throw new FinderException("UVariableBean#ejbFindByNotInVariableSet: Cannot find unified variables by non set "+nonUvsid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    /**
     * Finds all unified variables using a filtered query based on the parameters in the formdata object
     * @param formdata The formdata
     * @throws javax.ejb.FinderException If the unified variables could not be retrieved
     * @return The unified variables
     */
    public java.util.Collection ejbFindByQuery(FormDataManager formdata) throws javax.ejb.FinderException {
        String sql = "";      
        Collection arr = new ArrayList();
        
        String[] keys = new String[] {"sid", "pid", "type", "name", "unit"};

        if(formdata != null && formdata.hasValues()) {      
            formdata = FormDataManager.requestFormData(formdata, keys); 
            ArrayList list = formdata.getKeys();
            String column = "";
            String data = "";
            String term = "";

            for(int i=0;i<list.size();i++) {
                if(sql.length() > 0)
                    term = " AND";
                column = (String)list.get(i);
                
                data = formdata.getValue(column);
                if(!column.equalsIgnoreCase("type"))
                    data = data.replace('*', '%');
                if(data != null && data.length() > 0 && !data.equalsIgnoreCase("null")) {
                    if(column.equalsIgnoreCase("type")) {
                        if(!data.equals("*"))
                            sql += term+" "+column+" LIKE '"+data+"'";
                    }    
                    else
                        sql += term+" "+column+" LIKE '"+data+"'";
                }
            }
            
            if(sql.length() > 0)
                sql += " ORDER BY name";
            
            makeConnection();
            

            Statement st = null;    

            try {
                String sqlFull = "SELECT uvid FROM u_variables WHERE ";
                System.out.println(sqlFull+sql);
                st = conn.createStatement();

                ResultSet result = st.executeQuery(sqlFull + sql);         

                while (result.next()) {
                    arr.add(new Integer(result.getInt("uvid")));
                }           
            } catch (Exception se) {
                se.printStackTrace();
                throw new FinderException("Unable to find unified variables."+se.getMessage());
            } finally {
                releaseConnection();
            }                      
        }  
        
        return arr; 
    }

    /**
     * 
     * @param caller 
     * @param uvid 
     * @param pid 
     * @param sid 
     * @param name 
     * @param comm 
     * @param unit 
     * @param type 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int uvid, int pid, int sid, java.lang.String name, java.lang.String comm, java.lang.String unit, java.lang.String type, MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }
    
    // </editor-fold>
    
    /**
     * Find the unified variable using the primary key
     * @param aKey The primary key
     * @return The primary key if an unified variable was found
     * @throws FinderException If no unified variable was found
     */
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer aKey) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select uvid from u_variables where uvid = ?");
            ps.setInt(1,aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("UVariableBean#ejbFindByPrimaryKey: Cannot find uvariable");
            }
        } catch (SQLException se) {
            throw new EJBException("UVariableBean#ejbFindByPrimaryKey: SQL Exception: "+se.getMessage(), se);
        } finally {
            releaseConnection();
        }                
        
        return aKey;
    }
}
