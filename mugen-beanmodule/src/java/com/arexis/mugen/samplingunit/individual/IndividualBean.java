package com.arexis.mugen.samplingunit.individual;
import com.arexis.arxframe.PageManager;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.exceptions.PermissionDeniedException;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.samplingunit.expobj.ExpObj;
import com.arexis.mugen.samplingunit.samplingunitmanager.IndividualDTO;
import com.arexis.mugen.samplingunit.samplingunitmanager.ParentDTO;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import java.rmi.RemoteException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;


/**
 * This is the bean class for the IndividualBean enterprise bean.
 * Created Jun 8, 2005 4:53:08 PM
 * @author heto
 */
public class IndividualBean extends ExpObj implements javax.ejb.EntityBean, com.arexis.mugen.samplingunit.individual.IndividualRemoteBusiness {
    private javax.ejb.EntityContext context;
    
    // Father?
    // Mother?
    private int fatherId;
    private int motherId;
    
    private String sex;
    private java.sql.Date birthDate;
    
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
        try {
            ps = conn.prepareStatement("delete from individuals where iid=?");
            ps.setInt(1, getEid());
            ps.execute();
            super.remove();
        } catch (Exception e) {
            throw new EJBException("IndividualBean#ebjRemove: Unable to remove Individual. \n"+e.getMessage());
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
            
            /** Load common things */
            super.load(pk.intValue());
            
            
            ps = conn.prepareStatement("select iid,father,mother,sex,birth_date " +
                    "from individuals where iid=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                eid = rs.getInt("iid");
                
                
                
                fatherId = rs.getInt("father");
                motherId = rs.getInt("mother");
                
                sex = rs.getString("sex");
                birthDate = rs.getDate("birth_date");
                
                dirty = false;
                
                
            } else
                throw new EJBException("IndividualBean#ejbLoad: Error loading individuals");
        } catch (Exception e) {
            throw new EJBException("IndividualBean#ejbLoad: "+e.getMessage());
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
            
            PreparedStatement ps = null;
            try {
                super.store();
                
                ps = conn.prepareStatement("update individuals set father=?,mother=?,sex=?,birth_date=? " +
                        "where iid=?");

                int i=0;
                ps.setInt(++i, fatherId);
                ps.setInt(++i, motherId);
                ps.setString(++i, sex);
                ps.setDate(++i, birthDate);
                ps.setInt(++i, eid);
                int rows = ps.executeUpdate();
                if (rows!=1) {
                    throw new EJBException("IndividualBean#ejbStore: Error saving individual. Rows affected "+rows);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("IndividualBean#ejbStore: Error saving individual. \n"+e.getMessage());
            } finally {
                releaseConnection();
                dirty = false;
            }
        }
    }
    
    // </editor-fold>
    
    /**
     * See EJB 2.0 and EJB 2.1 section 12.2.5
     * @param iid 
     * @throws javax.ejb.FinderException 
     * @return 
     */
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer eid) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select iid from individuals where iid = ?");
            ps.setInt(1,eid.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("IndividualBean#ejbFindByPrimaryKey: Cannot find individual");
            }
        } catch (SQLException se) {
            throw new FinderException("IndividualBean#ejbFindByPrimaryKey: Cannot find individual "+eid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return eid;
    }
    
    
    /**
     * Returns the sex of the individual
     * @return The sex of the individual
     */
    public String getSex() {
        return sex;
    }
    
    /**
     * Sets the sex of the individual
     * @param sex The sex of the individual
     * @throws com.arexis.mugen.exceptions.ApplicationException If the value of the sex was not recognized (M,F and U are allowed)
     */
    public void setSex(String sex) throws ApplicationException {
        if (sex.length()>1)
            throw new ApplicationException("Invalid sex: Expected value M/F/U. Value was "+sex);
        this.sex = sex;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }
    
    /**
     * Returns the birthdate of the individual
     * @return The birthdate
     */
    public java.sql.Date getBirthDate() {
        return birthDate;
    }
    
    /**
     * Sets the birthdate of the individual
     * @param birthDate The birthdate
     */
    public void setBirthDate(java.sql.Date birthDate) {
        this.birthDate = birthDate;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }
    
    
    /**
     * Finds the individuals that belongs to a certain group and returns a collection of these individuals
     * @param gid The id of the group
     * @throws javax.ejb.FinderException If the individuals could not be found
     * @return A collection of individuals that belong to the group passed along as argument
     */
    public java.util.Collection ejbFindByGroup(int gid) throws javax.ejb.FinderException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select r.iid from r_ind_grp r, v_individuals_1 i where i.iid=r.iid and r.gid = ? order by i.identity");
            ps.setInt(1,gid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("iid")));
            }
        } catch (SQLException se) {
            throw new FinderException("IndividualBean#ejbFindByGroup: Cannot find individuals by group "+gid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    /**
     * Finds the individuals that belong to a certain sampling unit
     * @param suid The sampling unit
     * @throws javax.ejb.FinderException If the individuals could not be found
     * @return A collection with the individuals that belong to the sampling unit
     */
    public java.util.Collection ejbFindBySamplingUnit(int suid) throws javax.ejb.FinderException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select iid from v_individuals_1 where suid = ? order by identity");
            ps.setInt(1,suid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("iid")));
            }
        } catch (SQLException se) {
            throw new FinderException("IndividualBean#ejbFindBySamplingUnit: Cannot find individuals by sampling unit "+suid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    
    
   
    
    /**
     * Sets the id of father to the individual
     * @param fatherId The fathers id
     */
    public void setFatherId(int fatherId) {
        this.fatherId = fatherId;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }
    
    /**
     * Sets the id of the mother to the individual
     * @param motherId The id of the mother to the individual
     */
    public void setMotherId(int motherId) {
        this.motherId = motherId;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }
    
    /**
     * Creates a new individual
     * @return The individual
     * @param eid The experimental object id
     * @param caller The caller
     * @param identity The identity of the individual
     * @param samplingUnit The sampling unit of the individual
     * @throws javax.ejb.CreateException If the individual could not be created
     */
    public java.lang.Integer ejbCreate(int eid, String identity, SamplingUnitRemote samplingUnit, MugenCaller caller) throws javax.ejb.CreateException {
        makeConnection();
        PreparedStatement ps = null;
        Integer pk = null;
        try {
            
            this.eid = eid;
            this.identity = identity;
            
            //this.identity = exp.getIdentity();
            this.suid = samplingUnit.getSuid();
            this.status = "E";
            this.sex = "U";
            this.fatherId = 0;
            this.motherId = 0;
                    
            
            create(eid, identity, samplingUnit, caller);
            
            ps = conn.prepareStatement("insert into individuals (iid,sex) values (?,?) ");
            
            
            ps.setInt(1, getEid());
            //ps.setString(2, identity);
            ps.setString(2, "U");
            //ps.setString(4, "E");
            //ps.setInt(5, samplingUnit.getSuid());
            //ps.setInt(6, caller.getId());
            //ps.setDate(7, new java.sql.Date(System.currentTimeMillis()));
            
            int rows = ps.executeUpdate();
            
            dirty = false;
            
            pk = new Integer(getEid());
        }
        catch (DuplicateKeyException dke)
        {
            throw dke;
        }
        catch (Exception e) {
            throw new CreateException("IndividualBean#ejbCreate: Error creating individual. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }
    
    /**
     *
     * @param iid
     * @param identity
     * @param samplingUnit
     * @throws javax.ejb.CreateException
     */
    public void ejbPostCreate(int iid, java.lang.String identity, SamplingUnitRemote samplingUnit, MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }
    
    /**
     * Returns the father to the individual
     * @return The father to the individual
     */
    public IndividualRemote getFather() {
        IndividualRemote father = null;
        try {
            if (fatherId!=0)
                father = ((IndividualRemoteHome)context.getEJBHome()).findByPrimaryKey(new Integer(fatherId));
        } catch (Exception e) {
            throw new EJBException("IndividualBean#getFather: Unable to get father. \n"+e.getMessage());
        }
        return father;
    }
    
    /**
     * Returns the mother of the individual
     * @return The mother of the individual
     */
    public IndividualRemote getMother() {
        IndividualRemote mother = null;
        try {
            if (motherId!=0)
                mother = ((IndividualRemoteHome)context.getEJBHome()).findByPrimaryKey(new Integer(motherId));
        } catch (Exception e) {
            throw new EJBException("IndividualBean#getMother: Unable to get mother. \n"+e.getMessage());
        }
        return mother;
    }
    

    /**
     * Checks if the individual is OK, i.e. mother is not younger than it etc.
     * @return If the individual is OK
     */
    public int checkIndividual() {
        int status = 0;        
        try {
            IndividualRemote father = getFather();
            IndividualRemote mother = getMother();

            if (father != null && !father.getSex().equals("M")) // Male
                status = status + 1;
            if (father != null && !father.getStatus().equals("E"))
                status = status + 16;
            if (father!=null && father.getBirthDate() != null
                    && getBirthDate() != null
                    && (father.getBirthDate().after(getBirthDate())
                    || father.getBirthDate().equals(getBirthDate())))
                status = status + 2;

            if (mother != null && !mother.getSex().equals("F")) // Female
                status = status + 4;
            if (mother != null && !mother.getStatus().equals("E"))
                status = status + 32;
            if (mother != null && mother.getBirthDate() != null
                    && getBirthDate() != null
                    && (mother.getBirthDate().after(getBirthDate())
                    || mother.getBirthDate().equals(getBirthDate())))
                status = status + 8;    
        } catch(Exception e) {
            throw new EJBException("IndividualBean#checkIndividual: Error:"+e.getMessage());
        }
        
        return status;
    }    
    
    /**
     * Find all males in a sampling unit
     * @param suid The sampling unit to search in
     * @throws javax.ejb.FinderException If the males could not be found
     * @return A collection of males
     */
    public java.util.Collection ejbFindMale(int suid) throws javax.ejb.FinderException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select iid from v_individuals_1 where sex='M' and suid = ? order by identity");
            ps.setInt(1,suid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("iid")));
            }
        } catch (SQLException se) {
            throw new FinderException("IndividualBean#ejbFindMale: Cannot find male individuals by sampling unit "+suid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    /**
     * Finds the females that belong to a certain sampling unit
     * @param suid The sampling unit to search in
     * @throws javax.ejb.FinderException If the females could not be found
     * @return A collection of females
     */
    public java.util.Collection ejbFindFemale(int suid) throws javax.ejb.FinderException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select iid from v_individuals_1 where sex='F' and suid = ? order by identity");
            ps.setInt(1,suid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("iid")));
            }
        } catch (SQLException se) {
            throw new FinderException("IndividualBean#ejbFindMale: Cannot find female individuals by sampling unit "+suid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
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
     * @throws com.arexis.mugen.exceptions.PermissionDeniedException If the caller does not have IND_W privilege
     */
    public void addHistory() throws PermissionDeniedException {
        if (!caller.hasPrivilege("IND_W"))
            throw new PermissionDeniedException("Permission denied. Unable to write history.");
        
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into individuals_log (iid, identity, alias, father, mother, sex, birth_date, status, comm, id, ts, suid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, getEid());
            ps.setString(2, getIdentity());
            ps.setString(3, getAlias());
            ps.setInt(4, fatherId);
            ps.setInt(5, motherId);
            ps.setString(6, sex);
            ps.setDate(7, birthDate);
            ps.setString(8, getStatus());
            ps.setString(9, getComm());
            ps.setInt(10, id);
            ps.setDate(11, getTs());
            ps.setInt(12, getSuid());
            
            ps.execute();
            
        } catch (Exception e) {
            throw new EJBException("IndividualBean#addHistory: Error writing history for individual. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns a collection of history log entries for the individual
     * @return A collection of history log entries
     */
    public Collection getHistory() {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select * from individuals_log where iid = ? order by ts desc");
            ps.setInt(1, getEid());
            result = ps.executeQuery();
            IndividualDTO dto = null;
            UserRemote ur = null;

            while (result.next()) {
                ur = userHome.findByPrimaryKey(new Integer(result.getInt("id")));
                dto = new IndividualDTO(result.getInt("iid"), result.getString("identity"), result.getString("alias"), result.getString("sex"), result.getDate("birth_date").toString(), new ParentDTO(getFather()), new ParentDTO(getMother()), ur.getUsr(), result.getDate("ts").toString(), result.getString("status"), result.getString("comm"));

                arr.add(dto);
            }
        } catch (Exception se) {
            throw new EJBException("IndividualBean#getHistory: unable to find individuals history. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    /**
     * Fetches all individuals not included in the specified group
     * @return The individuals not included in the specified group
     * @param inSuid The sampling unit id
     * @param nonGid The id of the group that should be excluded
     * @throws javax.ejb.FinderException If the individuals could not be found
     */
    public java.util.Collection ejbFindByNonGroup(int nonGid, int inSuid) throws javax.ejb.FinderException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select i.iid from v_individuals_1 i where suid=? except (select i.iid from individuals i, r_ind_grp r where i.iid=r.iid and r.gid=?)");
            ps.setInt(2,nonGid);
            ps.setInt(1,inSuid);

            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("iid")));
            }
        } catch (SQLException se) {
            throw new FinderException("IndividualBean#ejbFindByNonGroup: Cannot find individuals by non group "+nonGid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    /**
     * Fetches a collection of individuals selected based on the 'filter' options used by the user.
     * @return A collection of individuals
     * @param fdm The formdata
     * @param pageManager Used for optimization purposes
     * @throws com.arexis.mugen.exceptions.ApplicationException If the individuals could not be retrieved
     * @throws javax.ejb.FinderException If the individuals could not be retrieved
     */
    public java.util.Collection ejbFindByQuery(FormDataManager fdm, PageManager pageManager) throws javax.ejb.FinderException, ApplicationException {
        String sql = "";      
        Collection arr = new ArrayList();
        
        if(fdm != null && fdm.hasValues()) {
            String[] keys = new String[] { "sex", "bdatefrom", "bdateto", "gid", "gsid", "suid", "identity", "father", "mother", "alias" };
            fdm = FormDataManager.requestFormData(fdm, keys);
            
            ArrayList list = fdm.getKeys();
            String column = "";
            String data = "";
            String term = "";
            String addTable = "";

            for(int i=0;i<list.size();i++) {
                if(sql.length() > 0)
                    term = " AND";
                column = (String)list.get(i);
                
                data = fdm.getValue(column);
                if(data != null && data.length() > 0 && !data.equalsIgnoreCase("null")) {
                    if(!column.equalsIgnoreCase("sex"))
                        data = data.replace('*', '%');
                    if(column.equalsIgnoreCase("bdatefrom")) {
                        validate("Birthdate (from)", data, new SimpleDateFormat("yyyy-mm-dd"));
                        sql += term+" birth_date >= '"+data+"'";
                    }
                    else if(column.equalsIgnoreCase("bdateto")) {
                        validate("Birthdate (to)", data, new SimpleDateFormat("yyyy-mm-dd"));
                        sql += term+" birth_date <= '"+data+"'";
                    }          
                    else if(column.equalsIgnoreCase("sex")) {
                        if(!data.equals("*"))
                            sql += term+" "+column+" LIKE '"+data+"'";
                    }                
                    else if(column.equalsIgnoreCase("gid")) {
                        if(!data.equals("%") && !data.equals("-1")) {
                            sql += term+" g.gid = "+data+" AND g.iid = v.iid";                        
                            addTable = ", r_ind_grp g";
                        }
                    }           
                    else if(!column.equalsIgnoreCase("gsid"))
                        sql += term+" "+column+" LIKE '"+data+"'";
                }
            }
            
            sql += " ORDER BY identity";
            
            makeConnection();
            

            Statement st = null;    

            try {
                String sqlFull = "SELECT v.iid, v.identity FROM v_individuals_3 v"+addTable+" WHERE ";
                System.out.println(sqlFull+sql);
                st = conn.createStatement();


                st = conn.createStatement();
                ResultSet result = null;                
                
                if(pageManager != null) {
                    // Tuning...use LIMIT on postgres
                    if(conn.getMetaData().getDatabaseProductName().equalsIgnoreCase("PostgreSQL")) {
                        result = st.executeQuery(sqlFull+sql+" LIMIT "+pageManager.getDelta()+ " OFFSET "+(pageManager.getStart()-1));
                        while (result.next())
                            arr.add(new Integer(result.getInt("iid")));
                    }
                    else {
                        result = st.executeQuery(sqlFull+sql);

                        int ctr = 0;
                        int delta = pageManager.getDelta();
                        int start = pageManager.getStart();
                        int stop = pageManager.getStop();
                        int index = 0;

                        while (result.next() && ctr < delta) {
                            if (index>=start && index<=stop) {                    
                                arr.add(new Integer(result.getInt("iid")));
                                ctr++;
                            }
                            index++;                  
                        }

                  }   
                }
                else {
                    result = st.executeQuery(sqlFull+sql);
                    while (result.next()) {       
                        arr.add(new Integer(result.getInt("iid")));          
                    }                    
                }
            } catch (Exception se) {
                se.printStackTrace();
                throw new FinderException("IndividualBean#ejbFindByQuery: Unable to find individuals."+se.getMessage());
            } finally {
                releaseConnection();
            }                      
        }  
        
        return arr;  
    }
    
  

    /**
     * Retunrns the parents of the individual
     * @return The parents of the individual
     */
    public Collection getParents() {
        Collection parents = new ArrayList();
        IndividualRemote father = getFather();
        if(father != null)
            parents.add(father);
        
        IndividualRemote mother = getMother();
        if(mother != null)
            parents.add(mother);        
        return parents;
    }

    /**
     * Returns the children of the individual
     * @throws javax.ejb.FinderException If the children could not be retrieved
     * @throws java.rmi.RemoteException If the children could not be retrieved
     * @return The children of the individual
     */
    public Collection getChildren() throws FinderException, RemoteException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;

        try {
                ps = conn.prepareStatement("select i.iid from v_individuals_1 i where suid = ? and (father = ? or mother = ?)");
                ps.setInt(1, getSuid());
                ps.setInt(2, getEid());           
                ps.setInt(3, getEid());

            result = ps.executeQuery();
            IndividualRemoteHome irh = (IndividualRemoteHome)context.getEJBHome();
            int indId = 0;
            while (result.next()) {
                indId = result.getInt("iid");
                if(indId > 0)
                    arr.add(irh.findByPrimaryKey(new Integer(indId)));
            }
        } catch (SQLException se) {
            throw new EJBException("IndividualBean#getChildren: Cannot find children"+se.getMessage());
        } finally {
            releaseConnection();
        }

        return arr;
    }

    

    /**
     * Returns the number of individuals in the database given the filter
     * @param fdm The formdata with the filtering parameters
     * @throws com.arexis.mugen.exceptions.ApplicationException If the number of individuals could not be retrieved
     * @return The number of individuals in the database given the filter
     */
    public int ejbHomeGetNumberOfIndividuals(FormDataManager fdm) throws ApplicationException {
        String sql = "";      
        Collection arr = new ArrayList();
        String[] keys = new String[] { "sex", "bdatefrom", "bdateto", "gid", "gsid", "suid", "identity", "father", "mother", "alias" };
        fdm = FormDataManager.requestFormData(fdm, keys);
        
        if(fdm != null && fdm.hasValues()) {
            ArrayList list = fdm.getKeys();
            String column = "";
            String data = "";
            String term = "";
            String addTable = "";

            for(int i=0;i<list.size();i++) {
                if(sql.length() > 0)
                    term = " AND";
                column = (String)list.get(i);
                
                data = fdm.getValue(column);
                if(data != null && data.length() > 0 && !data.equalsIgnoreCase("null")) {
                    if(!column.equalsIgnoreCase("sex"))
                        data = data.replace('*', '%');
                    if(column.equalsIgnoreCase("bdatefrom")) {                        
                        validate("Birthdate (from)", data, new SimpleDateFormat("yyyy-mm-dd"));
                        sql += term+" birth_date >= '"+data+"'";
                    }
                    else if(column.equalsIgnoreCase("bdateto")) {
                        validate("Birthdate (to)", data, new SimpleDateFormat("yyyy-mm-dd"));
                        sql += term+" birth_date <= '"+data+"'";
                    }          
                    else if(column.equalsIgnoreCase("sex")) {
                        if(!data.equals("*"))
                            sql += term+" "+column+" LIKE '"+data+"'";
                    }                
                    else if(column.equalsIgnoreCase("gid")) {
                        if(!data.equals("%") && !data.equals("-1")) {
                            sql += term+" g.gid = "+data+" AND g.iid = v.iid";                        
                            addTable = ", r_ind_grp g";
                        }
                    }
                    else if(!column.equalsIgnoreCase("gsid"))
                        sql += term+" "+column+" LIKE '"+data+"'";
                }
            }
            
            makeConnection();
            

            Statement st = null;    

            try {
                String sqlFull = "SELECT count(v.iid) AS num FROM v_individuals_3 v"+addTable+" WHERE ";
                System.out.println(sqlFull+sql);
                st = conn.createStatement();


                st = conn.createStatement();
                ResultSet result = null;                
                

                result = st.executeQuery(sqlFull+sql);
                if(result.next())
                    return result.getInt("num");
                   
            } catch (Exception se) {
                se.printStackTrace();
                throw new EJBException("Unable to find individuals."+se.getMessage());
            } finally {
                releaseConnection();
            }
        }
        return 0;
    }
    
    /**
     * Returns the id of the individual
     * @return The id of the individual
     */
    public int getIid()
    {
        return eid;
    }

    /**
     * Finds all individuals given the filter using the parameters in the formdata
     * @param fdm The formdata to filter on
     * @throws javax.ejb.FinderException If the individuals could not be retrieved
     * @return The individuals in the database given the filter
     */
    public java.util.Collection ejbFindByQuery(com.arexis.form.FormDataManager fdm) throws javax.ejb.FinderException {
        String sql = "";      
        Collection arr = new ArrayList();
        
        String[] keys = new String[] {"suid", "sex", "identity", "father", "mother", "alias" };
        
        if(fdm != null && fdm.hasValues()) {           
            fdm = FormDataManager.requestFormData(fdm, keys);
            
            ArrayList list = fdm.getKeys();
            String column = "";
            String data = "";
            String term = "";
            String addTable = "";

            for(int i=0;i<list.size();i++) {
                if(sql.length() > 0)
                    term = " AND";
                column = (String)list.get(i);
                
                data = fdm.getValue(column);
                if(data != null && data.length() > 0 && !data.equalsIgnoreCase("null")) {
                    if(!column.equalsIgnoreCase("sex"))
                        data = data.replace('*', '%');
         
                    if(column.equalsIgnoreCase("sex")) {
                        if(!data.equals("*"))
                            sql += term+" "+column+" LIKE '"+data+"'";
                    }
                    else if (column.equalsIgnoreCase("fidentity"))
                    {
                        sql += term+" "+column+" LIKE '"+data+"'";
                    }
                    else
                        sql += term+" "+column+" LIKE '"+data+"'";
                }
            }
            
            sql += " ORDER BY identity";
            
            makeConnection();
            

            Statement st = null;    

            try {
                String sqlFull = "SELECT v.iid, v.identity FROM v_individuals_3 v"+addTable+" WHERE ";
                System.out.println(sqlFull+sql);
                st = conn.createStatement();


                st = conn.createStatement();
                ResultSet result = null;                
                
                result = st.executeQuery(sqlFull+sql);
                while (result.next()) {       
                    arr.add(new Integer(result.getInt("iid")));          
                }                    
                
            } catch (Exception se) {
                se.printStackTrace();
                throw new FinderException("IndividualBean#ejbFindByQuery: Unable to find individuals."+se.getMessage());
            } finally {
                releaseConnection();
            }                      
        }  
        
        return arr;  
    }

    public Integer ejbFindByIdentityAndSamplingUnit(String identity, int suid, MugenCaller caller) throws javax.ejb.FinderException
    {
        makeConnection();
        Integer eid = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select iid from individuals i, expobj e where i.iid=e.eid and identity = ? and suid = ?");
            ps.setString(1, identity);
            ps.setInt(2, suid);
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("IndividualBean#ejbFindByIdentityAndSamplingUnit: Cannot find individual");
            }
            else
            {
                eid = new Integer(result.getInt("iid"));
            }
        } catch (SQLException se) {
            throw new FinderException("IndividualBean#ejbFindByIdentityAndSamplingUnit: Cannot find individual "+eid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return eid;
    }
}
