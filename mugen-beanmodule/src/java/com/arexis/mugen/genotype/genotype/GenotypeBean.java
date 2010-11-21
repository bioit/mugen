package com.arexis.mugen.genotype.genotype;

import com.arexis.arxframe.PageManager;
import com.arexis.mugen.genotype.allele.AlleleRemote;
import com.arexis.mugen.genotype.allele.AlleleRemoteHome;
import com.arexis.mugen.genotype.genotypemanager.GenotypeDTO;
import javax.ejb.*;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.genotype.marker.MarkerRemote;
import com.arexis.mugen.genotype.marker.MarkerRemoteHome;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.ParamDataObject;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.samplingunit.individual.IndividualRemote;
import com.arexis.mugen.samplingunit.individual.IndividualRemoteHome;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This is the bean class for the GenotypeBean enterprise bean.
 * Created Jun 15, 2005 5:06:16 PM
 * @todo What about aid1 and aid2 etc.???
 * @author lami
 */
public class GenotypeBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.genotype.genotype.GenotypeRemoteBusiness {
    private javax.ejb.EntityContext context;
    private int mid, iid, level, suid, aid1, aid2, id;
    private String raw1, raw2, reference, comm;
    private java.sql.Date updated;
    private boolean dirty;
    
    private UserRemoteHome userHome;
    private IndividualRemoteHome indHome;
    private SamplingUnitRemoteHome samplingUnitHome;
    private MarkerRemoteHome markerHome;
    private AlleleRemoteHome alleleHome;
    
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
        indHome = (IndividualRemoteHome)locator.getHome(ServiceLocator.Services.INDIVIDUAL);
        samplingUnitHome = (SamplingUnitRemoteHome)locator.getHome(ServiceLocator.Services.SAMPLINGUNIT);
        markerHome = (MarkerRemoteHome)locator.getHome(ServiceLocator.Services.MARKER);
        alleleHome = (AlleleRemoteHome)locator.getHome(ServiceLocator.Services.ALLELE);
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
            ps = conn.prepareStatement("delete from genotypes where mid = ? and iid=?");
            ps.setInt(1, mid);
            ps.setInt(2, iid);
            
            int res = ps.executeUpdate();
            
            if (res!=1) {
                throw new EJBException("GenotypeBean#ejbRemove: Cannot remove genotype");
            }
        } catch (SQLException se) {
            throw new EJBException("GenotypeBean#ejbRemove: SQL Exception",se);
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
        GenotypePk pk = (GenotypePk)context.getPrimaryKey();
        
        mid = pk.getMid().intValue();
        iid  = pk.getIid().intValue();
        
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select * from genotypes where mid = ? and iid = ?");
            ps.setInt(1, mid);
            ps.setInt(2, iid);
            
            result = ps.executeQuery();
            
            if (result.next()) {
                aid1 = result.getInt("aid1");
                aid2 = result.getInt("aid2");
                suid = result.getInt("suid");
                level =result.getInt("level_");
                raw1 = result.getString("raw1");
                raw2 = result.getString("raw2");
                comm = result.getString("comm");
                id = result.getInt("id");
                updated = result.getDate("ts");
                reference = result.getString("reference");
                dirty = false;
            } else
                throw new EJBException("GenotypeBean#ejbLoad: Empty resultset?");
        } catch (SQLException se) {
            throw new EJBException("GenotypeBean#ejbLoad: SQL Exception", se);
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbStore()
     */
    public void ejbStore() {
        if (dirty) {
            makeConnection();
            try {
                PreparedStatement ps = null;
                ps = conn.prepareStatement("update genotypes set " +
                        " mid = ?, iid = ? , level_ = ?," +
                        " raw1 = ?, raw2 = ?, reference = ?, id = ?, ts = ?," +
                        " comm = ? , aid1 = ?, aid2 = ?, suid = ? where mid = ? and iid = ?");
                
                ps.setInt(1, mid);
                ps.setInt(2, iid);
                ps.setInt(3, level);
                ps.setString(4, raw1);
                ps.setString(5, raw2);
                ps.setString(6, reference);
                ps.setInt(7, id);
                ps.setDate(8, updated);
                ps.setString(9, comm);
                ps.setInt(10, aid1);
                ps.setInt(11, aid2);
                ps.setInt(12, suid);
                ps.setInt(13, mid);
                ps.setInt(14, iid);
                
                
                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("Error updating genotype [Mid="+mid+", Iid="+iid+"]");
            } finally {
                releaseConnection();
                dirty = false;
            }
        }
    }
    
    // </editor-fold>
    
    /**
     * Finds the genotype using its primary key
     * @param pk The primary key
     * @return The primary key if genotype exist
     * @throws FinderException If no genotype could be found
     */
    public com.arexis.mugen.genotype.genotype.GenotypePk ejbFindByPrimaryKey(com.arexis.mugen.genotype.genotype.GenotypePk pk) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select mid, iid from genotypes where mid = ? and iid= ?");
            ps.setInt(1, pk.getMid().intValue());
            ps.setInt(2, pk.getIid().intValue());
            
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("GenotypeBean#ejbFindByPrimaryKey: Cannot find genotype");
            }
        } catch (SQLException se) {
            throw new EJBException("GenotypeBean#ejbFindByPrimaryKey: SQL Exception",se);
        } finally {
            releaseConnection();
        }
        
        return pk;
    }
    
    /**
     * Creates a new genotype
     * @return The primary key for the genotype
     * @param caller
     * @param indRem The individual remote interface
     * @param markRem The MarkerRemote interface
     * @param raw1 The raw1 value
     * @param raw2 The raw2 value
     * @param aid1 The id for allele 1
     * @param aid2 The id for allele 2
     * @param suid The sampling unit id
     * @param level The level for the genotype
     * @param reference The reference for the genotype
     * @param comm A comment for the genotype
     * @throws CreateException If the genotype could not be created
     */
    public com.arexis.mugen.genotype.genotype.GenotypePk ejbCreate(
            IndividualRemote indRem,
            MarkerRemote markRem,
            String raw1, String raw2,
            String comm, String reference, int level,
            int aid1, int aid2, int suid, MugenCaller caller) throws javax.ejb.CreateException {
        makeConnection();
        PreparedStatement ps = null;
        GenotypePk pk = null;
        
        try {
            mid = markRem.getMid();
            iid = indRem.getIid();
            this.comm = comm;
            this.reference = reference;
            this.raw1 = raw1;
            this.raw2 = raw2;
            this.level = level;
            this.aid1 = aid1;
            this.aid2 = aid2;
            this.suid = suid;
            id = caller.getId();
            updated = new java.sql.Date(System.currentTimeMillis());
            
            ps = conn.prepareStatement("insert into genotypes (mid,iid,aid1,aid2,suid,level_," +
                    " raw1,raw2,reference,id,ts,comm) values (?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, markRem.getMid());
            ps.setInt(2, indRem.getIid());
            ps.setInt(3, aid1);
            ps.setInt(4, aid2);
            ps.setInt(5, suid);
            ps.setInt(6, level);
            ps.setString(7, raw1);
            ps.setString(8, raw2);
            ps.setString(9, reference);
            ps.setInt(10, id);
            ps.setDate(11, updated);
            ps.setString(12, comm);
            ps.execute();
            
            pk = new GenotypePk(markRem.getMid(),
                    indRem.getIid());
            
            dirty = false;
            
        } catch (Exception e) {
            throw new EJBException("GenotypeBean#ejbCreate: Error:"+e.getMessage(), e);
        } finally {
            releaseConnection();
        }
        
        return pk;
    }
    
    /**
     *
     * @param caller
     * @param indRem
     * @param markRem
     * @param raw1
     * @param raw2
     * @param comm
     * @param reference
     * @param level
     * @param aid1
     * @param aid2
     * @param suid
     * @throws javax.ejb.CreateException
     */
    public void ejbPostCreate(IndividualRemote indRem, MarkerRemote markRem, String raw1, String raw2, String comm, String reference, int level, int aid1, int aid2, int suid, MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }
    
    
    /**
     * Returns the individual associated with this genotype
     * @return The individual for this genotype
     */
    public IndividualRemote getIndividual() {
        IndividualRemote ind = null;
        try {
            ind = indHome.findByPrimaryKey(new Integer(iid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ind;
    }
    
    /**
     * Returns the raw1
     * @return The raw 1
     */
    public String getRaw1() {
        return raw1;
    }
    
    /**
     * Sets raw 1
     * @param raw1 The new raw 1
     */
    public void setRaw1(java.lang.String raw1) {
        this.raw1 = raw1;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }
    
    /**
     * Returns raw 2
     * @return The raw 2
     */
    public String getRaw2() {
        return raw2;
    }
    
    /**
     * Sets raw 2
     * @param raw2 Sets the new raw 2
     */
    public void setRaw2(java.lang.String raw2) {
        this.raw2 = raw2;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }
    
    /**
     * Returns the genotype reference
     * @return Genotype reference
     */
    public String getReference() {
        return reference;
    }
    
    /**
     * Sets the genotype reference
     * @param reference The new genotype reference
     */
    public void setReference(java.lang.String reference) {
        this.reference = reference;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }
    
    /**
     * Returns the genotype level
     * @return Genotype level
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * Sets the genotype level
     * @param level The new genotype level
     */
    public void setLevel(int level) {
        this.level = level;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }
    
    /**
     * Returns the genotype comment
     * @return The genotype comment
     */
    public String getComm() {
        return comm;
    }
    
    /**
     * Returns allele 1
     * @return Allele 1
     */
    public AlleleRemote getAid1() {
        try {
            return alleleHome.findByPrimaryKey(new Integer(aid1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Sets allele id 1
     * @param aid1 The allele id
     */
    public void setAid1(int aid1) {
        this.aid1 = aid1;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }
    
    /**
     * Returns allele 2
     * @return Allele 2
     */
    public AlleleRemote getAid2() {
        try {
            return alleleHome.findByPrimaryKey(new Integer(aid2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;        
    }
    
    /**
     * Sets allele id 2
     * @param aid2 The allele id
     */
    public void setAid2(int aid2) {
        this.aid2 = aid2;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }
    
    /**
     * Returns the sampling unit for the genotype
     * @return The sampling unit for the genotype
     */
    public SamplingUnitRemote getSamplingUnit() {
        SamplingUnitRemote su = null;
        try {
            su = samplingUnitHome.findByPrimaryKey(new Integer(suid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return su;
    }
    
    /**
     * Returns the user that made the latest changes on the genotype
     * @return The user that made the latest changes on the genotype
     */
    public UserRemote getUser() {
        UserRemote usr = null;
        try {
            usr = userHome.findByPrimaryKey(new Integer(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usr;
    }
    
    /**
     * Returns the date for when the last changes were made to the genotype
     * @return The date for the last changes
     */
    public java.sql.Date getUpdated() {
        return updated;
    }
    
    /**
     * Retrives all genotypes for the specified sampling unit
     * @param forSuid The sampling unit id
     * @throws javax.ejb.FinderException If the genotypes could not be retrieved
     * @return A collection of Genotype primary keys
     */
    public java.util.Collection ejbFindAllGenotypes(int forSuid) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        Collection genotypes = new ArrayList();
        try {
            ps = conn.prepareStatement("select * from genotypes where suid = ? order by mid, iid");
            ps.setInt(1, forSuid);
            
            result = ps.executeQuery();
            
            while(result.next()){
                genotypes.add(new GenotypePk(result.getInt("mid"), result.getInt("iid")));
            }
        } catch (SQLException se) {
            throw new EJBException("GenotypeBean#ejbFindAllGenotypes: SQL Exception",se);
        } finally {
            releaseConnection();
        }
        
        return genotypes;
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
            ps = conn.prepareStatement("insert into genotypes_log (mid, iid, aid1, aid2, level_, raw1, raw2, reference, comm, id, ts) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, mid);
            ps.setInt(2, iid);
            ps.setInt(3, aid1);
            ps.setInt(4, aid2);
            ps.setInt(5, level);
            ps.setString(6, raw1);
            ps.setString(7, raw2);
            ps.setString(8, reference);
            ps.setString(9, comm);
            ps.setInt(10, id);
            ps.setDate(11, updated);
            
            ps.execute();
            
        } catch (Exception e) {
            throw new EJBException("GenotypeBean#addHistory: Error writing history for genotype. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns a collection of history log entries for the genotype
     * @return A collection of history log entries
     */
    public Collection getHistory() {
        makeConnection();
        
        
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        
        ResultSet rs = null;
        
        try {
            ps = conn.prepareStatement("select * from genotypes_log where iid = ? and mid = ? order by ts desc");
            ps.setInt(1, iid);
            ps.setInt(2, mid);
            rs = ps.executeQuery();
            GenotypeDTO dto = null;
            UserRemote ur = null;
            
            
            while (rs.next()) {
                ur = userHome.findByPrimaryKey(new Integer(rs.getInt("id")));
                dto = new GenotypeDTO(rs.getInt("aid1"), rs.getInt("aid2"), rs.getString("raw1"), rs.getString("raw2"), rs.getInt("level_"), rs.getString("comm"), rs.getString("reference"), ur.getUsr(), rs.getDate("ts"));
                
                arr.add(dto);
            }
        } catch (Exception se) {
            throw new EJBException("GenotypeBean#getHistory: unable to find genotype history. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    /**
     * Finds all genotypes using parameters from the paramdataobject as restrictions on the query.
     * @param pdo The paramdataobject containing parameters from the data mining filter form
     * @param pageManager Used for optimization of the query
     * @throws javax.ejb.FinderException If the genotypes could not be retrieved
     * @return A collection of genotypes
     */
    public java.util.Collection ejbFindByQuery(ParamDataObject pdo, PageManager pageManager) throws javax.ejb.FinderException {
        String sql3 = "";
        Collection arr = new ArrayList();
        boolean[] useTable = new boolean[6];
        for(int i=0;i<useTable.length;i++)
            useTable[i] = false;
        
        if(pdo != null && pdo.hasValues()) {
            ArrayList list = pdo.getKeys();
            String column = "";
            String data = "";
            String term = "";
            
            for(int i=0;i<list.size();i++) {
                if(sql3.length() > 0)
                    term = " AND";
                column = (String)list.get(i);
                
                data = pdo.getValue(column);
                if(data != null && data.length() > 0 && !data.equalsIgnoreCase("null")) {
                    data = data.replace('*', '%');
                    
                    // Check if we should use the chromosome table (only if not wildcard)
                    if(column.equalsIgnoreCase("chromosome") && !data.equals("%")) {
                        sql3 += term+" c.name = '"+data+"' AND c.sid = su.sid AND su.suid = g.suid AND m.mid = g.mid AND c.cid = m.cid";
                        useTable[1] = true;
                        useTable[0] = true;
                    } else if(column.equalsIgnoreCase("marker")) {
                        sql3 += term+" m.name LIKE '"+data+"' AND m.suid = g.suid AND g.mid = m.mid";
                        useTable[0] = true;
                    } else if(column.equalsIgnoreCase("g.level_") && !data.equals("%")) {
                        if(!data.equals("*"))
                            sql3 += term+" g.level_ = '"+data+"'";
                    } else if(column.equalsIgnoreCase("allele1") && !data.equals("%")) {
                        sql3 += term+" a.aid = g.aid1 AND a.name LIKE '"+data+"'";
                        useTable[2] = true;
                    } else if(column.equalsIgnoreCase("allele2") && !data.equals("%")) {
                        sql3 += term+" a.aid = g.aid2 AND a.name LIKE '"+data+"'";
                        useTable[2] = true;
                    } else if(column.equalsIgnoreCase("identity") && !data.equals("%")) {
                        sql3 += term+" i.identity LIKE '"+data+"' AND g.iid = i.iid";
                        useTable[3] = true;
                    } else if(column.equalsIgnoreCase("datefrom") && !data.equals("%")) {
                        sql3 += term+" g.date_ >= '"+data+"'";
                    } else if(column.equalsIgnoreCase("dateto") && !data.equals("%")) {
                        sql3 += term+" g.date_ <= '"+data+"'";
                    } else if(column.equalsIgnoreCase("usr") && !data.equals("%")) {
                        sql3 += term+" u.usr LIKE '"+data+"' AND u.id = g.id";
                        useTable[4] = true;
                    } else if(column.equalsIgnoreCase("gid")) {
                        if(!data.equals("%") && !data.equals("-1")) {                            
                            useTable[5] = true;  
                            if(!useTable[0]) {
                                useTable[0] = true;                           
                                sql3 += term+" rgr.iid = g.iid AND rgr.gid = "+data+" AND g.mid = m.mid";
                            }
                            else
                                sql3 += term+" rgr.iid = g.iid AND rgr.gid = "+data;
                        }
                    } else if(!data.equals("%") && !column.equalsIgnoreCase("gsid"))
                        sql3 += term+" "+column+" LIKE '"+data+"'";
                }
            }
            
            if(useTable[3])
                sql3 += " ORDER BY i.identity";
            
            makeConnection();
            
            Statement st = null;
            
            try {
                String sql1 = "SELECT g.iid, g.mid FROM genotypes g";
                String[] tables = {"markers m", "chromosomes c, sampling_units su", "alleles a", "individuals i", "users u", "r_ind_grp rgr"};
                String sql2 = " WHERE ";
                long t1 = System.currentTimeMillis();  
                for(int i=0;i<useTable.length;i++) {
                    if(useTable[i]) {
                        sql1 += ","+tables[i];
                    }
                }
                
                st = conn.createStatement();
                ResultSet result = null;                
                
                // Tuning...use LIMIT on postgres
                if(conn.getMetaData().getDatabaseProductName().equalsIgnoreCase("PostgreSQL")) {
                    result = st.executeQuery(sql1+sql2+sql3+" LIMIT "+pageManager.getDelta()+ " OFFSET "+(pageManager.getStart()-1));
                    while (result.next())
                        arr.add(new GenotypePk(result.getInt("mid"), result.getInt("iid")));
                }
                else {
                    result = st.executeQuery(sql1+sql2+sql3);
                
                    int ctr = 0;
                    int delta = pageManager.getDelta();
                    int start = pageManager.getStart();
                    int stop = pageManager.getStop();
                    int index = 0;

                    while (result.next() && ctr < delta) {
                        if (index>=start && index<=stop) {                    
                            arr.add(new GenotypePk(result.getInt("mid"), result.getInt("iid")));
                            ctr++;
                        }
                        index++;                  
                    }
                   
              }
              
            } catch (Exception se) {
                se.printStackTrace();
                throw new FinderException("Unable to find genotypes."+se.getMessage());
            } finally {
                releaseConnection();
            }
        }
        
        return arr;
    }

    /**
     * Returns the alleles for this genotype
     * @return The alleles for the genotype
     */
    public Collection getAlleles() {
        Collection alleles = new ArrayList();
        alleles.add(getAid1());
        alleles.add(getAid2());
        return alleles;
    }

    /**
     * Returns the number of genotypes in the database given the restrictions on the query using parameters in the paramdataobject
     * @param pdo The paramdataobject holding parameters from the datamining filter form
     * @return The number of genotypes in the database given when using the datamining filter
     */
    public int ejbHomeGetNumberOfGenotypes(com.arexis.mugen.project.ParamDataObject pdo) {
        String sql3 = "";
        Collection arr = new ArrayList();
        boolean[] useTable = new boolean[6];
        for(int i=0;i<useTable.length;i++)
            useTable[i] = false;
        
        if(pdo != null && pdo.hasValues()) {
            ArrayList list = pdo.getKeys();
            String column = "";
            String data = "";
            String term = "";
            
            for(int i=0;i<list.size();i++) {
                if(sql3.length() > 0)
                    term = " AND";
                column = (String)list.get(i);
                
                data = pdo.getValue(column);
                if(data != null && data.length() > 0 && !data.equalsIgnoreCase("null")) {
                    data = data.replace('*', '%');
                    
                    // Check if we should use the chromosome table (only if not wildcard)
                    if(column.equalsIgnoreCase("chromosome") && !data.equals("%")) {
                        sql3 += term+" c.name = '"+data+"' AND c.sid = su.sid AND su.suid = g.suid AND m.mid = g.mid AND c.cid = m.cid";
                        useTable[1] = true;
                        useTable[0] = true;
                    } else if(column.equalsIgnoreCase("marker")) {
                        sql3 += term+" m.name LIKE '"+data+"' AND m.suid = g.suid AND g.mid = m.mid";
                        useTable[0] = true;
                    } else if(column.equalsIgnoreCase("g.level_") && !data.equals("%")) {
                        if(!data.equals("*"))
                            sql3 += term+" g.level_ = '"+data+"'";
                    } else if(column.equalsIgnoreCase("allele1") && !data.equals("%")) {
                        sql3 += term+" a.aid = g.aid1 AND a.name LIKE '"+data+"'";
                        useTable[2] = true;
                    } else if(column.equalsIgnoreCase("allele2") && !data.equals("%")) {
                        sql3 += term+" a.aid = g.aid2 AND a.name LIKE '"+data+"'";
                        useTable[2] = true;
                    } else if(column.equalsIgnoreCase("identity") && !data.equals("%")) {
                        sql3 += term+" i.identity LIKE '"+data+"' AND g.iid = i.iid";
                        useTable[3] = true;
                    } else if(column.equalsIgnoreCase("datefrom") && !data.equals("%")) {
                        sql3 += term+" g.date_ >= '"+data+"'";
                    } else if(column.equalsIgnoreCase("dateto") && !data.equals("%")) {
                        sql3 += term+" g.date_ <= '"+data+"'";
                    } else if(column.equalsIgnoreCase("usr") && !data.equals("%")) {
                        sql3 += term+" u.usr LIKE '"+data+"' AND u.id = g.id";
                        useTable[4] = true;
                    } else if(column.equalsIgnoreCase("gid")) {
                        if(!data.equals("%") && !data.equals("-1")) {                            
                            useTable[5] = true;  
                            if(!useTable[0]) {
                                useTable[0] = true;                           
                                sql3 += term+" rgr.iid = g.iid AND rgr.gid = "+data+" AND g.mid = m.mid";
                            }
                            else
                                sql3 += term+" rgr.iid = g.iid AND rgr.gid = "+data;
                        }
                    } else if(!data.equals("%") && !column.equalsIgnoreCase("gsid"))
                        sql3 += term+" "+column+" LIKE '"+data+"'";
                }
            }
            
            
            makeConnection();
            
            Statement st = null;
            
            try {
                String sql1 = "SELECT count(g.iid) as num FROM genotypes g";
                String[] tables = {"markers m", "chromosomes c, sampling_units su", "alleles a", "individuals i", "users u", "r_ind_grp rgr"};
                String sql2 = " WHERE ";
                
                for(int i=0;i<useTable.length;i++) {
                    if(useTable[i]) {
                        sql1 += ","+tables[i];
                    }
                }
                
                System.out.println(sql1+sql2+sql3);
                st = conn.createStatement();
                
                ResultSet result = st.executeQuery(sql1+sql2+sql3);
                
                if (result.next()) {
                    return result.getInt("num");
                }
            } catch (Exception se) {
                se.printStackTrace();
                throw new EJBException("Unable to find genotypes."+se.getMessage());
            } finally {
                releaseConnection();
            }
        }
        
        return 0;
    }
    
    /**
     * Sets the genotype comment
     * @param comm The new comment
     */
    public void setComm(java.lang.String comm) {
        this.comm = comm;
        id = caller.getId();
        updated = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }
    
    /**
     * Returns the marker for the genotype
     * @return The genotype marker
     */
    public MarkerRemote getMarker() {
        MarkerRemote m = null;
        try {
            m = markerHome.findByPrimaryKey(new Integer(mid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }
    
}
