package com.arexis.mugen.phenotype.phenotype;

import com.arexis.arxframe.PageManager;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.phenotype.phenotypemanager.PhenotypeDTO;
import javax.ejb.*;
import com.arexis.mugen.phenotype.variable.VariableRemote;
import com.arexis.mugen.phenotype.variable.VariableRemoteHome;
import com.arexis.mugen.samplingunit.individual.IndividualRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.samplingunit.expobj.ExpObj;
import com.arexis.mugen.samplingunit.individual.IndividualRemoteHome;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This is the bean class for the PhenotypeBean enterprise bean.
 * Created Jun 16, 2005 10:13:00 AM
 * @todo fix date for phenotypes
 * @author lami
 */
public class PhenotypeBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.phenotype.phenotype.PhenotypeRemoteBusiness {
    private javax.ejb.EntityContext context;
    private int vid, eid, suid;
    private String value, reference, comm;
    private java.sql.Date date, ts;
    private int id;
    
    private boolean dirty;
    
    private UserRemoteHome userHome;
    private IndividualRemoteHome individualHome;
    private VariableRemoteHome variableHome;
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
        userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
        individualHome = (IndividualRemoteHome)locator.getHome(ServiceLocator.Services.INDIVIDUAL);
        variableHome = (VariableRemoteHome)locator.getHome(ServiceLocator.Services.VARIABLE);
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
            ps = conn.prepareStatement("delete from phenotypes where vid = ? and iid=?");
            ps.setInt(1, vid);
            ps.setInt(2, eid);
            
            int res = ps.executeUpdate();
            
            if (res!=1) {
                throw new EJBException("PhenotypeBean#ejbRemove: Cannot remove Phenotype");
            }
        } catch (SQLException se) {
            throw new EJBException("PhenotypeBean#ejbRemove: SQL Exception: "+se.getMessage(), se);
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
        PhenotypePk pk = (PhenotypePk)context.getPrimaryKey();
        
        vid = pk.getVid().intValue();
        eid  = pk.getIid().intValue();
        
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select suid,value,reference,comm,date_,id,ts from phenotypes where vid = ? and iid = ?");
            ps.setInt(1,vid);
            ps.setInt(2,eid);
            
            result = ps.executeQuery();
            
            if (result.next()) {
                suid = result.getInt("suid");
                value = result.getString("value");
                comm = result.getString("comm");
                reference = result.getString("reference");
                date = result.getDate("date_");
                ts = result.getDate("ts");
                id = result.getInt("id");
                dirty = false;
            } else
                throw new EJBException("PhenotypeBean#ejbLoad: Failed to load bean (empty resultset?)");
        } catch (SQLException se) {
            throw new EJBException("PhenotypeBean#ejbLoad: SQL Exception: "+se.getMessage(), se);
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
                ps = conn.prepareStatement("update phenotypes set vid = ?, iid = ?," +
                        " suid = ?, value = ?, date_ = ?, reference = ?, comm = ?, id = ?, ts = ? " +
                        "where vid = ? and iid = ?");

                ps.setInt(1, vid);
                ps.setInt(2, eid);
                ps.setInt(3, suid);
                ps.setString(4, value);
                ps.setDate(5, date);
                ps.setString(6, reference);
                ps.setString(7,  comm);
                ps.setInt(8, id);
                ps.setDate(9, ts);
                ps.setInt(10, vid);
                ps.setInt(11, eid);            


                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("PhenotypeBean#ejbStore: Error updating Phenotype [vid="+vid+", eid="+eid+"]");
            } finally {
                releaseConnection();
                dirty = false;
            }                
        }
    }
    
    // </editor-fold>
    
    /**
     * Finds the phenotype by its primary key
     * @param pk The primary key
     * @return The primary key if the phenotype was found
     * @throws FinderException If no phenotype could be found
     */
    public com.arexis.mugen.phenotype.phenotype.PhenotypePk ejbFindByPrimaryKey(com.arexis.mugen.phenotype.phenotype.PhenotypePk pk) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select vid, iid from phenotypes where vid = ? and iid=?");
            ps.setInt(1, pk.getVid().intValue());
            ps.setInt(2, pk.getIid().intValue());
            
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("PhenotypeBean#ejbFindByPrimaryKey: Cannot find Phenotype");
            }
        } catch (SQLException se) {
            throw new EJBException("PhenotypeBean#ejbFindByPrimaryKey: SQL Exception: "+se.getMessage(), se);
        } finally {
            releaseConnection();
        }
                
        return pk;
    }

    /**
     * Returns the value for this phenotype
     * @return The phenotype value
     */
    public String getValue() {        
        return value;
    }

    /**
     * Sets the value for this phenotype
     * @param value The new phenotype value
     */
    public void setValue(java.lang.String value) {
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());        
        this.value = value;
        dirty = true;
    }

    /**
     * Returns the variable for the phenotype
     * @return The variable for the phenotype
     */
    public VariableRemote getVariable()
    {
        VariableRemote v = null;
        try
        {
            v = variableHome.findByPrimaryKey(new Integer(vid));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return v;
    }
    
    /**
     * Returns the individual for the phenotype
     * @return The individual for the phenotype
     */
    public IndividualRemote getIndividual()
    {
        IndividualRemote i = null;
        try
        {
            i = individualHome.findByPrimaryKey(new Integer(eid));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * Returns the reference for the phenotype
     * @return The phenotype reference
     */
    public String getReference() {        
        return reference;
    }

    /**
     * Sets the reference for the phenotype
     * @param reference The new reference
     */
    public void setReference(java.lang.String reference) {
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());        
        this.reference = reference;
        dirty = true;
    }

    /**
     * Returns the comment for the phenotype
     * @return The comment
     */
    public String getComm() {        
        return comm;
    }

    /**
     * Sets the comment for the phenotype
     * @param comm The new comment
     */
    public void setComm(java.lang.String comm) {
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());        
        this.comm = comm;
        dirty = true;
    }

    /**
     * Returns the date for the phenotype
     * @return The date for the phenotype
     */
    public java.sql.Date getDate() {        
        return date;
    }

    /**
     * Sets the date for the phenotype
     * @param date The new date for the phenotype
     */
    public void setDate(java.sql.Date date) {
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());        
        this.date = date;
        dirty = true;
    }
    
    /**
     * Returns the sampling unit for the phenotype
     * @return The sampling unit for the phenotype
     */
    public SamplingUnitRemote getSamplingUnit()
    {
        SamplingUnitRemote s = null;
        try
        {
            s = samplingUnitHome.findByPrimaryKey(new Integer(suid));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * Sets the sampling unit id
     * @param suid The sampling unit id
     */
    public void setSuid(int suid) {
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());        
        this.suid = suid;
        dirty = true;
    }

    /**
     * Finds all phenotypes for a specific sampling unit
     * @param suid The sampling unit id
     * @throws javax.ejb.FinderException If the pheontypes could not be found or retrieved
     * @return A collection of phenotypes
     */
    public java.util.Collection ejbFindBySamplingUnit(int suid) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        Collection ph = new ArrayList();
        try {
            ps = conn.prepareStatement("select iid,vid from phenotypes where suid = ? order by iid, vid");
            ps.setInt(1, suid);
            result = ps.executeQuery();
            
            while(result.next()){
                ph.add(new PhenotypePk(result.getInt("vid"), result.getInt("iid")));
            }
        } catch (SQLException se) {
            throw new FinderException("PhenotypeBean#ejbFindBySamplingUnit: Cannot find phenotype"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return ph;
    }

    /**
     * Creates a new phenotype
     * @return The primary key of creation was succesful
     * @param eid The model/individual id
     * @param caller The caller
     * @param varRem The VariableRemote interface
     * @param value The phenotype value
     * @param reference The phenotype reference
     * @param comm The phenotype comment
     * @param date The phenotype date
     * @param suRem The SamplingUnitRemote interface
     * @throws CreateException If the phenotype could not be created
     */
    public com.arexis.mugen.phenotype.phenotype.PhenotypePk ejbCreate(int eid, VariableRemote varRem, SamplingUnitRemote suRem, String value, String reference, String comm, java.sql.Date date, MugenCaller caller) throws javax.ejb.CreateException {
        makeConnection();
        PreparedStatement ps = null;
        PhenotypePk pk = null;
        this.value = value;
        this.reference = reference;
        this.comm = comm;
        this.date = date;
        this.id = caller.getId();
        this.ts = new java.sql.Date(System.currentTimeMillis());
        
        try {
            vid = varRem.getVid();
            //iid = indRem.getIid();
            suid = suRem.getSuid();
            
            ps = conn.prepareStatement("insert into phenotypes (vid,iid,suid,value,date_,reference,id,ts,comm) values (?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, vid);
            ps.setInt(2, eid);
            ps.setInt(3, suid);
            ps.setString(4, value);
            ps.setDate(5, date);
            ps.setString(6, reference);
            ps.setInt(7, id);
            ps.setDate(8, ts);
            ps.setString(9, comm);
            ps.execute();
            
            pk = new PhenotypePk(vid, eid);
            dirty = false;
        } catch (Exception e) {
            throw new EJBException("PhenotypeBean#ejbCreate: Failed to create bean: "+e.getMessage(), e);
        } finally {
            releaseConnection();
        }
        
        return pk;
    }

    /**
     * 
     * @param eid 
     * @param caller 
     * @param varRem 
     * @param suRem 
     * @param value 
     * @param reference 
     * @param comm 
     * @param date 
     * @throws javax.ejb.CreateException 
     */
    public void ejbPostCreate(int eid, VariableRemote varRem, SamplingUnitRemote suRem, String value, String reference, String comm, java.sql.Date date, MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }

    /**
     * Writes a log entry to track changes history     
     */
    public void addHistory() {        
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into phenotypes_log (vid, iid, value, date_, reference, comm, id, ts) values (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, vid);
            ps.setInt(2, eid);
            ps.setString(3, value);
            ps.setDate(4, date);
            ps.setString(5, reference);
            ps.setString(6, comm);
            ps.setInt(7, id);
            ps.setDate(8, ts);            
            
            ps.execute();
            
        } catch (Exception e) {
            throw new EJBException("PhenotypeBean#addHistory: Error writing history for phenotype. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns a collection of history log entries for the phenotype
     * @return A collection of history log entries
     */
    public Collection getHistory() {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select * from phenotypes_log where iid = ? and vid = ? order by ts desc");
            ps.setInt(1, eid);
            ps.setInt(2, vid);
            result = ps.executeQuery();
            PhenotypeDTO dto = null;
            UserRemote ur = null;

            while (result.next()) {
                ur = userHome.findByPrimaryKey(new Integer(result.getInt("id")));
                dto = new PhenotypeDTO(result.getString("value"), result.getDate("date_"), result.getString("comm"), result.getString("reference"), ur, result.getDate("ts"));              

                arr.add(dto);
            }
        } catch (Exception se) {
            throw new EJBException("PhenotypeBean#getHistory: unable to find phenotype history. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    /**
     * Finds all phenotypes with a query restricted by the parameters in the formdata object
     * @param formdata The form data
     * @param pid The project id
     * @param pageManager The pagemanager (used for optimization purposes)
     * @throws javax.ejb.FinderException If the phenotypes could not be retrieved
     * @return The phenotypes given the query parameter filter from the UI
     */
    public java.util.Collection ejbFindByQuery(FormDataManager formdata, int pid, PageManager pageManager) throws javax.ejb.FinderException {
        String sql = "";      
        Collection arr = new ArrayList();
        String[] keys = new String[] {"suid", "name", "identity"};

        if(formdata != null) {      
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
                data = data.replace('*', '%');
                if(data != null && data.length() > 0 && !data.equalsIgnoreCase("null")) {
                    sql += term+" "+column+" LIKE '"+data+"'";
                }
            }
            sql += " ORDER BY identity";
            
            makeConnection();
            

            Statement st = null;    

            try {
                String sqlFull = "SELECT eid, vid FROM v_phenotypes_3 WHERE pid='"+pid+"' AND ";
                System.out.println(sqlFull+sql);
                st = conn.createStatement();
                    
                ResultSet result = null;                
                
                // Tuning...use LIMIT on postgres
                if(conn.getMetaData().getDatabaseProductName().equalsIgnoreCase("PostgreSQL")) {
                    result = st.executeQuery(sqlFull + sql +" LIMIT "+pageManager.getDelta()+ " OFFSET "+(pageManager.getStart()-1));
                    while (result.next())
                        arr.add(new PhenotypePk(result.getInt("vid"), result.getInt("eid")));
                }
                else {
                    result = st.executeQuery(sqlFull + sql);   
                
                    int ctr = 0;
                    int delta = pageManager.getDelta();
                    int start = pageManager.getStart();
                    int stop = pageManager.getStop();
                    int index = 0;

                    while (result.next() && ctr < delta) {
                        if (index>=start && index<=stop) {                    
                            arr.add(new PhenotypePk(result.getInt("vid"), result.getInt("eid")));
                            ctr++;
                        }
                        index++;                  
                    }
                   
              }        
            } catch (Exception se) {
                se.printStackTrace();
                throw new FinderException("Unable to find phenotypes."+se.getMessage());
            } finally {
                releaseConnection();
            }                      
        }  
        
        return arr; 
    }

    /**
     * Returns the number of phenotypes in the database given the filtering parameters in the formdata object
     * @param formdata The formdata
     * @param pid The project id
     * @return The number of phenotypes in the database that would be returned given the filter parameters in the formdata object
     */
    public int ejbHomeGetNumberOfPhenotypes(FormDataManager formdata, int pid) {
        String sql = "";      
        Collection arr = new ArrayList();
        String[] keys = new String[] {"suid", "name", "identity"};
        
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
                data = data.replace('*', '%');
                if(data != null && data.length() > 0 && !data.equalsIgnoreCase("null")) {
                    sql += term+" "+column+" LIKE '"+data+"'";
                }
            }
            
            makeConnection();
            

            Statement st = null;    

            try {
                String sqlFull = "SELECT count(eid) AS num FROM v_phenotypes_3 WHERE pid='"+pid+"' AND ";
                System.out.println(sqlFull+sql);
                st = conn.createStatement();
                    
                ResultSet result = st.executeQuery(sqlFull + sql);
                   
                if(result.next()) 
                    return result.getInt("num");
            } catch (Exception se) {
                se.printStackTrace();
                throw new EJBException("Unable to count phenotypes."+se.getMessage());
            } finally {
                releaseConnection();
            }                      
        }  
        
        return 0; 
    }

    /**
     * Returns the experimental object for the phenotype
     * @param caller The caller
     * @throws com.arexis.mugen.exceptions.ApplicationException If the experimental object could not be retrieved
     * @return The experimental object for the phenotype
     */
    public ExpObj getExperimentalObject(MugenCaller caller) throws ApplicationException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select * from expobj where eid=?");
            ps.setInt(1, eid);
            
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ApplicationException("PhenotypeBean#getExperimentalObject: Cannot find experimental object");
            }                       
            
            ExpObj experimentalObject = new ExpObj(eid, result.getInt("id"), result.getDate("ts"));
            experimentalObject.setCaller(caller);
            experimentalObject.setIdentity(result.getString("identity"));
            experimentalObject.setAlias(result.getString("alias"));
            experimentalObject.setComm(result.getString("comm"));
            experimentalObject.setSuid(result.getInt("suid"));
            experimentalObject.setStatus(result.getString("status"));
            
            ps = conn.prepareStatement("select * from individuals where iid=?");
            ps.setInt(1, eid);
            
            result = ps.executeQuery();
            int type = 0;
            if (!result.next()) {
                type = 1;
            }   
            
            experimentalObject.setType(type);
            
            return experimentalObject;
        } catch (SQLException se) {
            throw new EJBException("PhenotypeBean#getExperimentalObject: SQL Exception: "+se.getMessage(), se);
        } finally {
            releaseConnection();
        }                
    }

    public java.util.Collection ejbFindByVariable(int vid) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        Collection ph = new ArrayList();
        try {
            ps = conn.prepareStatement("select iid,vid from phenotypes where vid = ? order by iid, vid");
            ps.setInt(1, vid);
            result = ps.executeQuery();
            
            while(result.next()){
                ph.add(new PhenotypePk(result.getInt("vid"), result.getInt("iid")));
            }
        } catch (SQLException se) {
            throw new FinderException("PhenotypeBean#ejbFindByVariable: Cannot find phenotype"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return ph;
    }

    /**
     * Returns the date for when the phenotype was last changed
     * @return The date for the last modification
     */    
    public java.sql.Date getTs() {    
        return ts;
    }

    /**
     * Sets the caller for the phenotype
     * @param caller The caller
     */
    public void setCaller(MugenCaller caller) {
        this.caller = caller;
        dirty = true;
    }
    
    /**
     * Returns the user which made the last update
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
}
