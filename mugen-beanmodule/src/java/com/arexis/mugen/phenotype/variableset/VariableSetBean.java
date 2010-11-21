package com.arexis.mugen.phenotype.variableset;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.phenotype.phenotypemanager.VariableSetDTO;
import com.arexis.mugen.phenotype.variable.VariableRemoteHome;
import javax.ejb.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This is the bean class for the VariableSetBean enterprise bean.
 * Created Jun 16, 2005 8:46:01 AM
 * @author lami
 */
public class VariableSetBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.phenotype.variableset.VariableSetRemoteBusiness {
    private javax.ejb.EntityContext context;
    
    private int vsid, suid, id;
    private String name, comm;
    private java.sql.Date updated;    
    private boolean dirty;
    
    private UserRemoteHome userHome;
    private SamplingUnitRemoteHome samplingUnitHome;    
    private VariableRemoteHome variableHome;
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise beans, Web services)
    // TODO Add business methods
    // TODO Add create methods
    /**
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(javax.ejb.EntityContext aContext) {
        context = aContext;
        userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
        samplingUnitHome = (SamplingUnitRemoteHome)locator.getHome(ServiceLocator.Services.SAMPLINGUNIT);
        variableHome = (VariableRemoteHome)locator.getHome(ServiceLocator.Services.VARIABLE);
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
            ps = conn.prepareStatement("delete from variable_sets where vsid = ?");
            ps.setInt(1, getVsid());            
            
            int res = ps.executeUpdate();
            
            if (res!=1) {
                throw new EJBException("Cannot remove variable set");
            }
        } catch (SQLException se) {
            throw new EJBException(se);
        }
        finally
        {
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
            ps = conn.prepareStatement("select name,comm,suid, id, ts from variable_sets where vsid = ?");
            ps.setInt(1,pk.intValue());
            
            result = ps.executeQuery();
            
            if (result.next()) {
                vsid = pk.intValue();
                name = result.getString("name");
                comm = result.getString("comm");
                suid = result.getInt("suid");
                id = result.getInt("id");
                updated = result.getDate("ts");
                dirty = false;
            } else
                throw new EJBException();
        } catch (SQLException se) {
            throw new EJBException(se);
        }
        finally
        {
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
                ps = conn.prepareStatement("update variable_sets set name = ? , comm = ?," +
                        " suid = ?, id  = ?, ts = ? where vsid = ?");

                ps.setString(1, name);
                ps.setString(2, comm);
                ps.setInt(3, suid);
                ps.setInt(4, id);
                ps.setDate(5, updated);
                ps.setInt(6, vsid);

                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("Error updating variable set [vsid="+vsid+"]");
            }
            finally
            {
                releaseConnection();          
                dirty = false;
            }
        }
    }

    /**
     * Returns the variable set id
     * @return The variable set id
     */
    public int getVsid() {        
        return vsid;
    }

    /**
     * Returns the variable set name
     * @return The variable set name
     */
    public String getName() {        
        return name;
    }

    /**
     * Sets the variable set name
     * @param name The new variable set name
     */
    public void setName(java.lang.String name) {
        this.name = name;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }

    /**
     * Returns the comment for the variable set
     * @return The variable set comment
     */
    public String getComm() {        
        return comm;
    }

    /**
     * Sets the variable set comment
     * @param comm The new comment for the variable set
     */
    public void setComm(java.lang.String comm) {
        this.comm = comm;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis());                
        dirty = true;
    }

    /**
     * Creates a new variable set
     * @return The primary key if creation was succesful
     * @param caller The current caller object
     * @param vsid The variable set id
     * @param name The variable set name
     * @param comm The variable set comment
     * @param suid The sampling unit id
     * @throws CreateException If the variable set could not be created
     */
    public java.lang.Integer ejbCreate(int vsid, int suid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException {
        this.vsid = vsid;
        this.name = name;
        this.comm = comm;
        this.suid = suid;
        this.id = caller.getId(); 
        this.updated = new java.sql.Date(System.currentTimeMillis());
        
        makeConnection();
        
        try {    
            PreparedStatement ps = null;
            ps = conn.prepareStatement("insert into variable_sets " +
                    " (vsid,name,comm,suid,id,ts) values (?,?,?,?,?,?)");
            ps.setInt(1, vsid);
            ps.setString(2, name);
            ps.setString(3, comm);
            ps.setInt(4, suid);
            ps.setInt(5, id);
            ps.setDate(6, updated);            
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
                throw new CreateException("Unable to create variable set: "+e.getMessage());
        } 
        finally
        {
            releaseConnection();
        }
        return new Integer(vsid);
    }

    /**
     * Returns the sampling unit id
     * @return The sampling unit id
     */
    public int getSuid() {        
        return suid;
    }

    /**
     * Sets the sampling unit id
     * @param suid The sampling unit id
     */
    public void setSuid(int suid) {
        this.suid = suid;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis());                
        dirty = true;
    }

    /**
     * Find a variable set using the name and sampling unit id
     * @param name The variable set name
     * @param suid The sampling unit id
     * @return The primary key if a sampling unit was found
     * @throws FinderException If no sampling unit could be found
     */
    public java.lang.Integer ejbFindByNameSamplingUnit(java.lang.String name, java.lang.Integer suid) throws javax.ejb.FinderException {
         makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        Integer pk = null;
        try {
            ps = conn.prepareStatement("select vsid from variable_sets where name = ? and suid = ?");
            ps.setString(1, name);
            ps.setInt(2,suid.intValue());
            result = ps.executeQuery();
            if (result.next()) 
            {
                pk = new Integer(result.getInt("vsid")); 
            }
            else
            {
                throw new ObjectNotFoundException("Cannot find variable set by name and sampling unit id");
            }
        } catch (SQLException se) {
            throw new EJBException(se);
        }
        finally
        {
            releaseConnection();
        }
        return pk;
    }

    /**
     * Returns all variable sets for the specified sampling unit id
     * @param forSuid The sampling unit id
     * @throws javax.ejb.FinderException If the variable sets could not be retrieved
     * @return A collection of variable sets
     */
    public java.util.Collection ejbFindAllVariableSets(int forSuid) throws javax.ejb.FinderException {
        makeConnection();
        Collection variablesets = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select name, vsid from variable_sets where suid = ? order by name");
            ps.setInt(1, forSuid);
            result = ps.executeQuery();
            while(result.next()){
                variablesets.add(new Integer(result.getInt("vsid")));
            }
        } catch (SQLException se) {
            throw new EJBException("VariableSetsBean#ejbFindAllVariableSets: SQL Exception: "+se.getMessage(), se);
        } finally {
            releaseConnection();
        }                
        
        return variablesets;
    }

    /**
     * Returns the user id of the user which performed the latest changes on the variable set
     * @return The user id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the date for when the variable set was last updated
     * @return The date for the last update
     */
    public java.sql.Date getUpdated() {        
        return updated;
    }
    
    public Collection getVariables() throws ApplicationException
    {
        try
        {
            return variableHome.findByVariableSet(vsid);
        }
        catch (Exception e)
        {
            throw new ApplicationException("Failed to get variables",e);
        }
    }

    /**
     * Returns all variables for this Variable set bean
     * WARNING!!! This method returns a collection of variable ids as Integers!!!
     * @return A collection of variables
     */
    public Collection getAllVariablesInSet() {
        makeConnection();
        Collection variables = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select vid from r_var_set where vsid = ?");
            ps.setInt(1, vsid);
            result = ps.executeQuery();
            while(result.next()){
                variables.add(new Integer(result.getInt("vid")));
            }
        } catch (SQLException se) {
            throw new EJBException("VariableSetsBean#getAllVariablesInSet: SQL Exception: "+se.getMessage(), se);
        } finally {
            releaseConnection();
        }                
        
        return variables;
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
            ps = conn.prepareStatement("insert into variable_sets_log (vsid, name, comm, id, ts) values (?, ?, ?, ?, ?)");
            ps.setInt(1, vsid);
            ps.setString(2, name);
            ps.setString(3, comm);
            ps.setInt(4, id);
            ps.setDate(5, updated);            
            
            ps.execute();
            
        } catch (Exception e) {
            throw new EJBException("VariableSetBean#addHistory: Error writing history for variable set. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns a collection of history log entries for the variable set
     * @return A collection of history log entries
     */
    public Collection getHistory() {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select * from variable_sets_log where vsid = ? order by ts desc");
            ps.setInt(1, vsid);

            result = ps.executeQuery();
            VariableSetDTO dto = null;
            UserRemote ur = null;

            while (result.next()) {
                ur = userHome.findByPrimaryKey(new Integer(result.getInt("id")));
                dto = new VariableSetDTO(result.getString("name"), result.getString("comm"), ur.getUsr(), result.getDate("ts"));

                arr.add(dto);
            }
        } catch (Exception se) {
            throw new EJBException("VariableSetBean#getHistory: unable to find variable set history. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    /**
     * Finds variable sets using a restricted query based on the filter parameters in the formdata object
     * @param formdata The formdata object
     * @throws javax.ejb.FinderException If the variable sets could not be retrieved
     * @return The variable sets given the filter parameters in the formdata object
     */
    public java.util.Collection ejbFindByQuery(FormDataManager formdata) throws javax.ejb.FinderException {
        String sql = "";      
        Collection arr = new ArrayList();
        
        String[] keys = new String[] {"suid", "vsname", "vname"};                    
            
        if(formdata != null && formdata.hasValues()) {
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
                    else if(column.equalsIgnoreCase("suid")) {
                        sql += term+" vs.suid LIKE '"+data+"'";
                    }                    
                }
            }
            
            if(members)
                sql += " AND r.vsid = vs.vsid AND r.vid = v.vid ORDER BY vs.name";                
            
            makeConnection();
            

            Statement st = null;    

            try {
                String sqlFull = "";
                if(members)
                    sqlFull = "SELECT DISTINCT vs.vsid, vs.name FROM variable_sets vs, variables v, r_var_set r WHERE ";
                else
                    sqlFull = "SELECT DISTINCT vs.vsid, vs.name FROM variable_sets vs WHERE ";
                System.out.println(sqlFull+sql);
                st = conn.createStatement();

                ResultSet result = st.executeQuery(sqlFull + sql);         

                while (result.next()) {
                    arr.add(new Integer(result.getInt("vsid")));
                }           
            } catch (Exception se) {
                se.printStackTrace();
                throw new FinderException("Unable to find variable sets."+se.getMessage());
            } finally {
                releaseConnection();
            }                      
        }  
        
        return arr; 
    }

    /**
     * Returns the number of variables in the variable set
     * @return The number of variables in the set
     */
    public int getNumberOfVariables() {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        int num = 0;
        try {
            ps = conn.prepareStatement("select count(vid) as num from r_var_set where vsid = ?");
            ps.setInt(1, vsid);
            result = ps.executeQuery();
            if(result.next()){
                num = result.getInt("num");
            }
        } catch (SQLException se) {
            throw new EJBException("VariableSetsBean#getNumberOfVariables: SQL Exception: "+se.getMessage(), se);
        } finally {
            releaseConnection();
        }                
        
        return num;
    }

    /**
     * Returns the user that performed the latest changes on the variable set
     * @return The user that made the last changes on the variable set
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
     * Returns the sampling unit for this variable set
     * @return The sampling units for this variable set
     */
    public SamplingUnitRemote getSamplingUnit() {
        SamplingUnitRemote sur = null;
        try {
            sur = samplingUnitHome.findByPrimaryKey(new Integer(suid));
        } catch (Exception e) {
            throw new EJBException("VariableSetBean#getSamplingUnit: Failed to get sampling unit for variable set");
        }
        return sur;   
    }

    /**
     * Adds a variable to this set
     * @param vid The id of the variable to add
     * @param caller The caller
     */
    public void addVariable(int vid, MugenCaller caller) {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {            
            java.sql.Date ts = new java.sql.Date(System.currentTimeMillis());

            int id = caller.getId();
            ps = conn.prepareStatement("insert into r_var_set (vsid, vid, ts, id) values (?, ?, ?, ?)");
            ps.setInt(1, vsid);
            ps.setInt(2, vid);
            ps.setDate(3, ts);
            ps.setInt(4, id);
            ps.execute();
        } catch (SQLException se) {
            se.printStackTrace();
            throw new EJBException("Could not insert variable vid:"+vid+" into variable set:"+vsid);
        } finally {
            releaseConnection();
        }   
    }

    /**
     * Removes a variable from this set
     * @param vid The variable to remove
     * @param caller The caller
     */
    public void removeVariable(int vid, com.arexis.mugen.MugenCaller caller) {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {            
            ps = conn.prepareStatement("delete from r_var_set where vsid = ? AND vid = ?");
            ps.setInt(1, vsid);
            ps.setInt(2, vid);
            ps.execute();
        } catch (SQLException se) {
            se.printStackTrace();
            throw new EJBException("Could not remove variable vid:"+vid+" from variable set:"+vsid);
        } finally {
            releaseConnection();
        }   
    }

    public Collection ejbFindByName(java.lang.String name) throws javax.ejb.FinderException {
        makeConnection();
        Collection arr = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        Integer pk = null;
        try {
            ps = conn.prepareStatement("select vsid from variable_sets where lower(name) like lower(?)");
            ps.setString(1, name);
            
            result = ps.executeQuery();
            while (result.next()) 
            {
                arr.add(new Integer(result.getInt("vsid")));
            }
        } catch (Exception se) {
            throw new FinderException("cannot find variable set");
        }
        finally
        {
            releaseConnection();
        }
        return arr;
    }

    /**
     * 
     * @param caller 
     * @param vsid 
     * @param suid 
     * @param name 
     * @param comm 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int vsid, int suid, java.lang.String name, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }
    
    // </editor-fold>
    
    /**
     * Finds a variable set by using its primary key
     * @param aKey The primary key
     * @return The primary key if the variable set could be found
     * @throws FinderException If the variable set could not be found
     */
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer aKey) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select vsid from variable_sets where vsid = ?");
            ps.setInt(1,aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("Cannot find variable set");
            }
        } catch (SQLException se) {
            throw new EJBException(se);
        }
        finally
        {
            releaseConnection();
        }
        return aKey;
    }
}
