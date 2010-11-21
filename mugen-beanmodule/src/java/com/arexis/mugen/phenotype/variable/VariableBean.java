package com.arexis.mugen.phenotype.variable;

import com.arexis.arxframe.PageManager;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.phenotype.phenotype.PhenotypeRemoteHome;
import com.arexis.mugen.phenotype.phenotypemanager.VariableDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.*;

/**
 * This is the bean class for the VariableBean enterprise bean.
 * Created Jun 16, 2005 8:57:27 AM
 * @author lami
 */
public class VariableBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.phenotype.variable.VariableRemoteBusiness {
    private javax.ejb.EntityContext context;
    private int vid, suid, id;
    private java.sql.Date ts;
    private String name, type, unit, comm;
    
    private boolean dirty;
    
    private SamplingUnitRemoteHome samplingUnitHome;
    private PhenotypeRemoteHome phenotypeHome;
    
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
        phenotypeHome = (PhenotypeRemoteHome)locator.getHome(ServiceLocator.Services.PHENOTYPE);
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
            ps = conn.prepareStatement("delete from variables where vid = ?");
            ps.setInt(1, getVid());            
            
            int res = ps.executeUpdate();
            
            if (res!=1) {
                throw new EJBException("VariableBean#ejbRemove: Cannot remove variable");
            }
        } catch (SQLException se) {
            throw new EJBException("VariableBean#ejbRemove: SQL Exception: "+se.getMessage(), se);
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
            ps = conn.prepareStatement("select name,type,unit,comm,suid, id, ts from variables where vid = ?");
            ps.setInt(1,pk.intValue());
            
            result = ps.executeQuery();
            
            if (result.next()) {
                vid = pk.intValue();
                name = result.getString("name");
                comm = result.getString("comm");
                type = result.getString("type");
                unit = result.getString("unit");
                suid = result.getInt("suid");
                id = result.getInt("id");
                ts = result.getDate("ts");
                dirty = false;
            } else
                throw new EJBException("VariableBean#ejbLoad: Failed to load bean (empty resultset?)");
        } catch (SQLException se) {
            throw new EJBException("VariableBean#ejbLoad: SQL Exception: "+se.getMessage(), se);
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
                ps = conn.prepareStatement("update variables set name = ? , type = ?," +
                        "unit = ?, comm = ?," +
                        " suid = ?, id = ?, ts = ? where vid = ?");

                ps.setString(1, name);
                ps.setString(2, type);
                ps.setString(3, unit);
                ps.setString(4, comm);
                ps.setInt(5, suid);
                ps.setInt(6, id);
                ps.setDate(7, ts);
                ps.setInt(8, vid);

                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("VariableBean#ejbStore: Error updating variable  [vid="+vid+"]");
            } finally {
                releaseConnection();         
                dirty = false;
            }                
        }
    }
    
    // </editor-fold>

    /**
     * Returns the variable type
     * @return The variable type
     */
    public String getType() {        
        return type;
    }

    /**
     * Sets the variable type
     * @param type THe variable type
     */
    public void setType(java.lang.String type) {
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());            
        this.type = type;
        dirty = true;
    }

    /**
     * Returns the variable unit
     * @return The variable unit
     */
    public String getUnit() {        
        return unit;
    }

    /**
     * Sets the variable unit
     * @param unit The new variable unit
     */
    public void setUnit(java.lang.String unit) {
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());            
        this.unit = unit;
        dirty = true;
    }

    /**
     * Returns the variable name
     * @return The variable name
     */
    public String getName() {        
        return name;
    }

    /**
     * Sets the variable name
     * @param name The new variable name
     */
    public void setName(java.lang.String name) {
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());            
        this.name = name;
        dirty = true;
    }

    /**
     * Returns the variable comment
     * @return The variable comment
     */
    public String getComm() {       
        return comm;
    }

    /**
     * Sets the variable comment
     * @param comm The new variable comment
     */
    public void setComm(java.lang.String comm) {
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());            
        this.comm = comm;
        dirty = true;
    }

    /**
     * Returns the variable id
     * @return The variable id
     */
    public int getVid() {        
        return vid;
    }

    /**
     * Creates a new variable
     * @return The primary key if creation was succesful
     * @param caller The current caller object
     * @param vid The varaiable id
     * @param suid The sample unit id
     * @param name The variable name
     * @param type The variable type
     * @param unit The variable unit
     * @param comm The variable comment
     * @throws CreateException If the variable could not be created
     */
    public java.lang.Integer ejbCreate(int vid, int suid, java.lang.String name, java.lang.String type, java.lang.String unit, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException {
        this.vid = vid;
        this.unit = unit;
        this.type = type;
        this.name = name;
        this.comm = comm;
        this.suid = suid;
        this.id = caller.getId();
        this.ts = new java.sql.Date(System.currentTimeMillis());
        
        makeConnection();
        
        try {    
            PreparedStatement ps = null;
            ps = conn.prepareStatement("insert into variables " +
                    " (vid,name,type,unit,comm,suid,id,ts) values (?,?,?,?,?,?,?,?)");
            ps.setInt(1, vid);
            ps.setString(2, name);
            ps.setString(3, type);
            ps.setString(4, unit);
            ps.setString(5, comm);
            ps.setInt(6, suid);
            ps.setInt(7, id);
            ps.setDate(8, ts);            
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
                throw new CreateException("VariableBean#ejbCreate: Unable to create variable: "+e.getMessage());
        } finally {
            releaseConnection();
        }                
        
        return new Integer(vid);  
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
     * Sets the sample unit id
     * @param suid The sample unit id
     */
    public void setSuid(int suid) {
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());            
        this.suid = suid;
        dirty = true;
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
     * Returns the date for when the last changes were made to the variable
     * @return The date
     */
    public java.sql.Date getTs() {
        return ts;
    }

    /**
     * Returns all variables for the specified sampling unit
     * @return A collection of variables
     * @param forSuid The sampling unit that the variables should belong to
     * @throws javax.ejb.FinderException If the variables could not be found
     */
    public java.util.Collection ejbFindAllVariables(int forSuid) throws javax.ejb.FinderException {
        makeConnection();
        Collection variables = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select name, vid from variables where suid = ? order by name");
            ps.setInt(1, forSuid);
            result = ps.executeQuery();
            while(result.next()){
                variables.add(new Integer(result.getInt("vid")));
            }
        } catch (SQLException se) {
            throw new EJBException("VariableBean#ejejbFindAllVariables: SQL Exception: "+se.getMessage(), se);
        } finally {
            releaseConnection();
        }                
        
        return variables;
    }

    /**
     * Sets the caller object
     * @param caller The caller object
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
            ps = conn.prepareStatement("insert into variables_log (vid, name, type, unit, comm, id, ts) values (?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, vid);
            ps.setString(2, name);
            ps.setString(3, type);
            ps.setString(4, unit);
            ps.setString(5, comm);
            ps.setInt(6, id);
            ps.setDate(7, ts);            
            
            ps.execute();
            
        } catch (Exception e) {
            throw new EJBException("VariableBean#addHistory: Error writing history for variable. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns a collection of history log entries for the variable
     * @return A collection of history log entries
     */
    public Collection getHistory() {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select * from variables_log where vid = ? order by ts desc");
            ps.setInt(1, vid);

            result = ps.executeQuery();
            VariableDTO dto = null;
            UserRemote ur = null;

            while (result.next()) {
                ur = userHome.findByPrimaryKey(new Integer(result.getInt("id")));
                dto = new VariableDTO(result.getString("name"), result.getString("type"), result.getString("unit"), result.getString("comm"), ur.getUsr(), result.getDate("ts"));

                arr.add(dto);
            }
        } catch (Exception se) {
            throw new EJBException("VariableBean#getHistory: unable to find variable history. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    /**
     * Fetches all variables that are not member of the specified set.
     * @param nonVsid The set that the variables should NOT belong to
     * @param forSuid The sampling unit the variables should be in
     * @throws javax.ejb.FinderException If the variables could not be found
     * @return The variables that are not a member of the specified variable set
     */
    public java.util.Collection ejbFindByNotInVariableSet(int nonVsid, int forSuid) throws javax.ejb.FinderException {
       makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select v.vid from variables v where suid = ? except (select v.vid from variables v, r_var_set r where v.vid=r.vid and r.vsid=?)");
            ps.setInt(2,nonVsid);
            ps.setInt(1,forSuid);

            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("vid")));
            }
        } catch (SQLException se) {
            throw new FinderException("VariableBean#ejbFindByNotInVariableSet: Cannot find variables by non set "+nonVsid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    /**
     * Finds all variables given the filter parameters in the formdata object
     * @param formdata The formdata parameters
     * @param pageManager The pagemanager used for optimzation purposes
     * @throws javax.ejb.FinderException If the variables could not be retrieved
     * @return The variables in the database given the filter parameters
     */
    public java.util.Collection ejbFindByQuery(FormDataManager formdata, PageManager pageManager) throws javax.ejb.FinderException {
        String sql = "";      
        Collection arr = new ArrayList();
        
        String[] keys = new String[] {"v.suid", "vs.vsid", "v.type", "v.name", "v.unit"};                    
            
        if(formdata != null && formdata.hasValues()) {
            formdata = FormDataManager.requestFormData(formdata, keys);
            ArrayList list = formdata.getKeys();
            String column = "";
            String data = "";
            String term = "";
            String addRelation = "";

            for(int i=0;i<list.size();i++) {
                if(sql.length() > 0)
                    term = " AND";
                column = (String)list.get(i);
                
                data = formdata.getValue(column);
                if(data != null && data.length() > 0 && !data.equalsIgnoreCase("null")) {
                    if(!column.equalsIgnoreCase("v.type") && !column.equalsIgnoreCase("vs.vsid"))
                        data = data.replace('*', '%');
                
                    if(column.equalsIgnoreCase("v.type")) {
                        if(!data.equals("*"))
                            sql += term+" "+column+" LIKE '"+data+"'";
                    }    
                    else if(column.equalsIgnoreCase("vs.vsid")) {
                        if(!data.equals("*")) {
                            addRelation = ", r_var_set vs";
                            sql += term+" vs.vid = v.vid AND vs.vsid = "+data;                        
                        }
                    }
                    else
                        sql += term+" "+column+" LIKE '"+data+"'";
                }
            }
            sql += " ORDER BY v.name";
            
            makeConnection();
            

            Statement st = null;    

            try {
                String sqlFull = "SELECT v.vid, v.name FROM variables v"+addRelation+" WHERE ";
                System.out.println(sqlFull+sql);
                st = conn.createStatement();
                ResultSet result = null;
                
                // Tuning...use LIMIT on postgres
                if(conn.getMetaData().getDatabaseProductName().equalsIgnoreCase("PostgreSQL")) {
                    result = st.executeQuery(sqlFull+sql+" LIMIT "+pageManager.getDelta()+ " OFFSET "+(pageManager.getStart()-1));
                    while (result.next())
                        arr.add(new Integer(result.getInt("vid")));
                }
                else {
                    result = st.executeQuery(sqlFull+sql);
                
                    int ctr = 0;
                    int delta = pageManager.getDelta();
                    int start = pageManager.getStart();
                    if(start == 1)
                        start = 0;
                    int stop = pageManager.getStop();
                    int index = 0;

                    while (result.next() && ctr < delta) {
                        if (index>=start && index<=stop) {                    
                            arr.add(new Integer(result.getInt("vid")));
                            ctr++;
                        }
                        index++;                  
                    }
                   
              }         
            } catch (Exception se) {
                se.printStackTrace();
                throw new FinderException("Unable to find variables."+se.getMessage());
            } finally {
                releaseConnection();
            }                      
        }  

        return arr; 
    }

    /**
     * Returns the number of variables in the database given the filter parameters
     * @param formdata The formdata to filter on
     * @return The number of variables in the database given the filter parameters
     */
    public int ejbHomeGetNumberOfVariables(FormDataManager formdata) {
        String sql = "";      
        Collection arr = new ArrayList();
        
        String[] keys = new String[] {"v.suid", "vs.vsid", "v.type", "v.name", "v.unit"};                    
            
        if(formdata != null && formdata.hasValues()) {
            formdata = FormDataManager.requestFormData(formdata, keys);
        
            ArrayList list = formdata.getKeys();
            String column = "";
            String data = "";
            String term = "";
            String addRelation = "";

            for(int i=0;i<list.size();i++) {
                if(sql.length() > 0)
                    term = " AND";
                column = (String)list.get(i);
                
                data = formdata.getValue(column);
                if(data != null && data.length() > 0 && !data.equalsIgnoreCase("null")) {
                    if(!column.equalsIgnoreCase("v.type") && !column.equalsIgnoreCase("vs.vsid"))
                        data = data.replace('*', '%');
                
                    if(column.equalsIgnoreCase("v.type")) {
                        if(!data.equals("*"))
                            sql += term+" "+column+" LIKE '"+data+"'";
                    }    
                    else if(column.equalsIgnoreCase("vs.vsid")) {
                        if(!data.equals("*")) {
                            addRelation = ", r_var_set vs";
                            sql += term+" vs.vid = v.vid AND vs.vsid = "+data;                        
                        }
                    }
                    else
                        sql += term+" "+column+" LIKE '"+data+"'";
                }
            }
            
            makeConnection();
            

            Statement st = null;    

            try {
                String sqlFull = "SELECT count(v.vid) AS num FROM variables v"+addRelation+" WHERE ";
                System.out.println(sqlFull+sql);
                st = conn.createStatement();
                ResultSet result = st.executeQuery(sqlFull+sql);    
                if(result.next()) 
                    return result.getInt("num");
            } catch (Exception se) {
                se.printStackTrace();
                throw new EJBException("Unable to count variables."+se.getMessage());
            } finally {
                releaseConnection();
            }                      
        }  
        
        return 0; 
    }

    /**
     * 
     * @param caller 
     * @param vid 
     * @param suid 
     * @param name 
     * @param type 
     * @param unit 
     * @param comm 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int vid, int suid, java.lang.String name, java.lang.String type, java.lang.String unit, java.lang.String comm, MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }

    public java.util.Collection ejbFindByVariableSet(int vsid) throws javax.ejb.FinderException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select vid from r_var_set where vsid = ?");
            ps.setInt(1, vsid);
            result = ps.executeQuery();            
            while (result.next()) {
                arr.add(new Integer(result.getInt("vid")));
            }
        } catch (SQLException se) {
            throw new FinderException("VariableBean#ejbFindByVariableSet: Cannot find variables by set "+vsid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    
    
    /**
     * Find the variable using a primary key
     * @param aKey The primary key
     * @return The primary key if a variable was found
     * @throws FinderException If no variable was found
     */
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer aKey) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select vid from variables where vid = ?");
            ps.setInt(1,aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("VariableBean#ejbFindByPrimaryKey: Cannot find variable");
            }
        } catch (SQLException se) {
            throw new EJBException("VariableBean#ejbFindByPrimaryKey: SQL Exception: "+se.getMessage(), se);
        } finally {
            releaseConnection();
        }                
        
        return aKey;
    }
    
    public Collection getPhenotypes() throws ApplicationException
    {
        try
        {
            Collection phenotypes = phenotypeHome.findByVariable(vid);
            return phenotypes;
        }
        catch (Exception e)
        {
            throw new ApplicationException("Failed to get phenotypes");
        }
    }
}
