package com.arexis.mugen.species.gene;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.expmodel.ExpModelRemoteHome;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.project.ProjectRemoteHome;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.search.Keyword;
import com.arexis.mugen.servicelocator.ServiceLocator;
import com.arexis.mugen.species.chromosome.ChromosomeRemote;
import com.arexis.mugen.species.chromosome.ChromosomeRemoteHome;
import com.arexis.mugen.model.strain.allele.StrainAlleleRemote;
import com.arexis.mugen.model.strain.allele.StrainAlleleRemoteHome;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;

public class GeneBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.species.gene.GeneRemoteBusiness {
    private javax.ejb.EntityContext context;
    
    private int gaid, id, pid, cid;
    private String name, comm, mgiid, genesymbol, geneexpress, idgene, idensembl;
    private java.sql.Date ts;
    
    private boolean dirty;
    private UserRemoteHome userHome;
    private ExpModelRemoteHome modelHome;
    private ProjectRemoteHome projectHome;
    private ChromosomeRemoteHome chromosomeHome;
    private StrainAlleleRemoteHome alleleHome;
    
    //ejb methods
    // <editor-fold defaultstate="collapsed">
    
    public void setEntityContext(javax.ejb.EntityContext aContext) {
        context = aContext;
        userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
        modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
        projectHome = (ProjectRemoteHome)locator.getHome(ServiceLocator.Services.PROJECT);
        chromosomeHome = (ChromosomeRemoteHome)locator.getHome(ServiceLocator.Services.CHROMOSOME);
        alleleHome = (StrainAlleleRemoteHome)locator.getHome(ServiceLocator.Services.STRAIN_ALLELE);
    }
    
    public void ejbActivate() {}
    
    public void ejbPassivate() {}
    
    public void ejbRemove() {
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from gene where gaid=?");
            ps.setInt(1, gaid);
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("GeneBean#ejbRemove: Unable to delete Gene.\n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void unsetEntityContext() {
        context = null;
    }
    
    public void ejbLoad() {
        makeConnection();
        Integer pk = (Integer)context.getPrimaryKey();
        PreparedStatement ps = null;
        try {
            
            ps = conn.prepareStatement("select gaid, pid, name,comm, id, ts, mgiid, genesymbol, geneexpress, idgene, idensembl, cid " +
                    "from gene where gaid=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                gaid = rs.getInt("gaid");
                pid = rs.getInt("pid");
                name = rs.getString("name");
                comm = rs.getString("comm");
                id = rs.getInt("id");
                ts = rs.getDate("ts");
                mgiid = rs.getString("mgiid");
                genesymbol = rs.getString("genesymbol");
                geneexpress = rs.getString("geneexpress");
                idgene = rs.getString("idgene");
                idensembl = rs.getString("idensembl");
                cid = rs.getInt("cid");
                dirty = false;
            } else
                throw new EJBException("GeneBean#ejbLoad: Error loading Gene");
        } catch (Exception e) {
            throw new EJBException("GeneBean#ejbLoad: error loading Gene. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void ejbStore() {
        if (dirty)
        {
            makeConnection();
            PreparedStatement ps = null;
            try {
                
                ps = conn.prepareStatement("update gene set name=?,comm=?,id=?, ts=?, mgiid=?, genesymbol=?, geneexpress=?, idgene=?, idensembl=?, cid=? " +
                        "where gaid=?");

                ps.setString(1, name);
                ps.setString(2, comm);
                ps.setInt(3, id);
                ps.setDate(4, new Date(System.currentTimeMillis()));
                //ps.setInt(5, gaid);
                ps.setString(5, mgiid);
                ps.setString(6, genesymbol);
                ps.setString(7, geneexpress);
                ps.setString(8, idgene);
                ps.setString(9, idensembl);
                ps.setInt(10, cid);
                ps.setInt(11, gaid);
               
                ps.execute();
            } catch (Exception e) {
                throw new EJBException("GeneBean#ejbStore: error storing Gene. \n"+e.getMessage());
            } finally {
                releaseConnection();
                dirty = false;
            }
        }
    }
    
    // </editor-fold>
    
    //finder methods
    // <editor-fold defaultstate="collapsed">
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer key) throws javax.ejb.FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select gaid from gene where gaid = ?");
            ps.setInt(1,key.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("GeneBean#ejbFindByPrimaryKey: Cannot find Gene. No next in resultset");
            }
        } catch (SQLException se) {
            throw new FinderException("GeneBean#ejbFindByPrimaryKey: Cannot find Gene. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return key;
    }
    
    public java.util.Collection ejbFindByName(java.lang.String name) throws javax.ejb.FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        int key = 0;
        Collection arr = new ArrayList();
        try {
            
            ps = conn.prepareStatement("select gaid from gene where lower(name) like ?");
            //ps = conn.prepareStatement("select gaid from gene where lower(name) like lower(?)");
            ps.setString(1, name);
            result = ps.executeQuery();
            
            while (result.next())
            {
                arr.add(new Integer(result.getInt("gaid")));
            }
        } catch (SQLException se) {
            throw new FinderException("GeneBean#ejbFindByName: Cannot find Gene. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    public java.util.Collection ejbFindByEnsemblid() throws javax.ejb.FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        int key = 0;
        Collection arr = new ArrayList();
        try {
            
            ps = conn.prepareStatement("select gaid from gene where idensembl not like '' and idensembl not like 'not provided yet'");
            result = ps.executeQuery();
            
            while (result.next())
            {
                arr.add(new Integer(result.getInt("gaid")));
            }
        } catch (SQLException se) {
            throw new FinderException("GeneBean#ejbFindByEnsemblid: cannot find gene based on its ensembl id. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    public java.util.Collection ejbFindByKeyword(Keyword keyword, MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        int key = 0;
        int gaid = 0;
        Collection arr = new ArrayList();
        try {
            
            ps = conn.prepareStatement("select gaid from gene where lower(name) like ? or lower(comm) like ? or lower(genesymbol) like ? or lower(geneexpress) like ?");
            
            String search = "%"+keyword.getKeyword()+"%";
            
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            ps.setString(4, search);
            result = ps.executeQuery();
            
            while (result.next()) {
                gaid = result.getInt("gaid");
                if(geneSecurityControl(gaid, caller, true))
                    arr.add(new Integer(gaid));
            }
        } catch (SQLException se) {
            throw new FinderException("GeneBean#ejbFindByName: Cannot find Gene. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    public java.util.Collection ejbFindByProject(int pid, MugenCaller caller, boolean all) throws javax.ejb.FinderException {
        makeConnection();
        
        Collection arr = new ArrayList();
        int gaid = 0;
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select gaid from gene where pid = ? order by name");
            ps.setInt(1,pid);
            result = ps.executeQuery();
            
            while (result.next()) {
                gaid = result.getInt("gaid");
                if(geneSecurityControl(gaid, caller, all))
                    arr.add(new Integer(gaid));
            }
        } catch (SQLException se) {
            throw new FinderException("GeneBean#ejbFindByProject: Cannot find gene by project "+pid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    public java.util.Collection ejbFindByModel(int eid) throws javax.ejb.FinderException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select g.gaid from gene g, r_gene_model r where g.gaid=r.gaid and r.eid = ? order by g.name");
            ps.setInt(1,eid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("gaid")));
            }
        } catch (SQLException se) {
            throw new FinderException("GeneBean#ejbFindByModel: Cannot find gene by model "+eid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    public java.util.Collection ejbFindUnassignedGenes(int eid, int pid) throws javax.ejb.FinderException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select gaid from gene where gaid in ((select g.gaid from gene g where g.pid=?) except (select g.gaid from gene g, r_gene_model r where r.eid=? and g.gaid=r.gaid)) order by name");
            ps.setInt(1,pid);
            ps.setInt(2,eid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("gaid")));
            }
        } catch (SQLException se) {
            throw new FinderException("GeneBean#ejbFindByModel: Cannot find genes not assigned to model "+eid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    public java.util.Collection ejbFindUnassignedGenesForTransgenic(int eid, int strainid, int pid) throws javax.ejb.FinderException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            String sql = "select g.gaid from gene g where g.pid=? "
                        +"and g.gaid not in (select g2.gaid from gene g2, r_gene_model r2 where r2.eid=? and g2.gaid=r2.gaid) "
                        +"and g.gaid not in (select gene from strain_allele where strainid=? and gene is not NULL)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,pid);
            ps.setInt(2,eid);
            ps.setInt(3,strainid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("gaid")));
            }
        } catch (SQLException se) {
            throw new FinderException("GeneBean#ejbFindUnassignedGenesForTransgenic: Cannot find genes not assigned to transgenic model "+eid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    public java.util.Collection ejbFindStandAloneGenes(int pid) throws javax.ejb.FinderException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            String sql = "select g.gaid from gene g where g.pid=? "
                        +"and g.gaid not in (select distinct gaid from r_gene_model) and g.gaid not in (select distinct r.gene from strain_allele r where r.gene not like '')";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,pid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("gaid")));
            }
        } catch (SQLException se) {
            throw new FinderException("GeneBean#ejbFindStandAloneGenes: Cannot find genes that are not attached to a model or strain_allele. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    public java.util.Collection ejbFindByMP(String potid) throws javax.ejb.FinderException {
        makeConnection();
        
        potid = "%>"+potid;
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select gaid from gene where gaid in (select gaid from r_gene_model where eid in (select m.eid from model m, expobj e where m.eid = e.eid and m.eid in (select eid from pheno_model_r_ where mp like ?)))");
            ps.setString(1, potid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("gaid")));
            }
        } catch (SQLException se) {
            throw new FinderException("GeneBean#ejbFindByMP: Cannot find gene by mammalian phenotype ontology \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    //</editor-fold>
    
    //misc methods
    // <editor-fold defaultstate="collapsed">
    
    public int getCallerLevel (MugenCaller caller){
        int level = 0;
        
        if (caller.hasPrivilege("MODEL_ADM")) {
            level = 2;
        } else if (caller.hasPrivilege("MODEL_MUGEN")) {
            level = 1;
        }
        
        return level;
    }
    
    public boolean geneSecurityControl(int gaid, MugenCaller caller, boolean all) throws javax.ejb.FinderException {
        
        boolean check = false;
        int level = getCallerLevel(caller);//0;
        /*
        if (caller.hasPrivilege("MODEL_ADM")) {
            level = 2;
        } else if (caller.hasPrivilege("MODEL_MUGEN")) {
            level = 1;
        }*/
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            
            //control via mutant level
            ps = conn.prepareStatement("select level from model where eid in (select eid from r_gene_model where gaid = ?)");
            ps.setInt(1,gaid);
            result = ps.executeQuery();
            
            while (result.next()) {
                if(result.getInt("level")<=level)
                    check = true;
            }
            
            //conditional control via allele level
            if(all){
                ps = conn.prepareStatement("select level from model where strain in (select strainid from strain_allele where gene = ?)");
                ps.setInt(1,gaid);
                result = ps.executeQuery();
            
                while (result.next()) {
                    if(result.getInt("level")<=level)
                        check = true;
                }
            }//all
            
        } catch (SQLException se) {
            throw new FinderException("GeneBean#geneSecurityControl: Cannot gene's security level \n"+se.getMessage());
        }
        return check;
    }
    
    //</editor-fold>
    
    //setter+getter methods
    // <editor-fold defaultstate="collapsed">

    public int getGaid() {
        return gaid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        dirty = true;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());         
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
        dirty = true;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());         
    }
    
    public String getMgiid() {
        return mgiid;
    }

    public void setMgiid(String mgiid) {
        this.mgiid = mgiid;
        dirty = true;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());         
    }
    
    public String getGenesymbol() {
        return genesymbol;
    }

    public void setGenesymbol(String genesymbol) {
        this.genesymbol = genesymbol;
        dirty = true;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());         
    }
    
    public String getGeneexpress() {
        return geneexpress;
    }

    public void setGeneexpress(String geneexpress) {
        this.geneexpress = geneexpress;
        dirty = true;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());         
    }

    public String getIdgene() {
        return idgene;
    }

    public void setIdgene(String idgene) {
        this.idgene = idgene;
        dirty = true;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());         
    }
    
    public String getIdensembl() {
        return idensembl;
    }

    public void setIdensembl(String idensembl) {
        this.idensembl = idensembl;
        dirty = true;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());         
    }
    
    public void setCaller(MugenCaller caller) {
        this.caller = caller;
    }

    public Collection getModels() {
        try {
            Collection models = modelHome.findByGene(gaid, caller);
            return models;
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException("Could not get models");
        }
    }
    
    public int getModelscount() throws ApplicationException {
        makeConnection();
        try {
            
            PreparedStatement ps = conn.prepareStatement("select count(m.eid) as num from r_gene_model r, model m where r.eid = m.eid and gaid = ? and level <= ? ");
            ps.setInt(1, gaid);
            ps.setInt(2, getCallerLevel(caller));
            
            ResultSet result = ps.executeQuery();
            int num = 0;
            if(result.next())
                num = result.getInt("num");
            
            return num;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("GeneBean#getModelscount Unable to count models. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public Collection getAlleles() {
        try {
            Collection alleles = alleleHome.findByGene(gaid, caller);
            return alleles;
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException("Could not get alleles");
        }
    }
    
    public int getAllelescount() throws ApplicationException {
        makeConnection();
        try {
            
            PreparedStatement ps = conn.prepareStatement("select count(id) as num from strain_allele where gene = ? and strainid in (select strain from model where level <= ? )");
            ps.setInt(1, gaid);
            ps.setInt(2, getCallerLevel(caller));
            
            ResultSet result = ps.executeQuery();
            int num = 0;
            if(result.next())
                num = result.getInt("num");
            
            return num;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("GeneBean#getAllelescount Unable to count alleles. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }

    public Date getTs() {
        return ts;
    }
    
    public UserRemote getUser() {
        try {
            UserRemote usr = userHome.findByPrimaryKey(new Integer(id));
            return usr;
        } catch (Exception e) {
            throw new EJBException("Could not get user");
        }
    }
    
    public ProjectRemote getProject() throws ApplicationException{
        try {
            ProjectRemote prj = projectHome.findByPrimaryKey(new Integer(pid));
            return prj;
        } catch (Exception e) {
            throw new ApplicationException("Could not get project");
        }
    }
    
    public ChromosomeRemote getChromosome(){
        try {
            return chromosomeHome.findByPrimaryKey(new Integer(cid));
        } catch (Exception e) {
            throw new EJBException("Failed to get chromosome",e);
        }
    }
    
    public void setChromosome(ChromosomeRemote chromosome){
        try {
            this.cid = chromosome.getCid();
            dirty = true;
        } catch (RemoteException ex) {
            ex.printStackTrace();
            throw new EJBException("Failed to set chromosome on gene", ex);
        }
    }
    
    //</editor-fold>
    
    //create+postcreate methods
    // <editor-fold defaultstate="collapsed">

    public java.lang.Integer ejbCreate(int gaid, java.lang.String name, String comm, String mgiid, String genesymbol, String geneexpress, String idgene, String idensembl, ChromosomeRemote chr, ProjectRemote project, MugenCaller caller) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        try {
            
            this.gaid = gaid;
            this.name = name;
            this.comm = comm;
            this.mgiid = mgiid;
            this.genesymbol = genesymbol;
            this.geneexpress = geneexpress;
            this.idgene = idgene;
            this.idensembl = idensembl;
            
            
            this.id = caller.getId();
            this.ts = new Date(System.currentTimeMillis());
            
            this.caller = caller;
            
            this.pid = project.getPid();
            
            pk = new Integer(gaid);
            
            PreparedStatement ps = conn.prepareStatement("insert into gene (gaid,name,comm,pid,id,ts, mgiid, genesymbol, geneexpress, idgene, idensembl,cid) values (?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, gaid);
            ps.setString(2, name);
            ps.setString(3, comm);
            ps.setInt(4, pid);
            ps.setInt(5, id);
            ps.setDate(6, ts);
            ps.setString(7, mgiid);
            ps.setString(8, genesymbol);
            ps.setString(9, geneexpress);
            ps.setString(10, idgene);
            ps.setString(11, idensembl);
            ps.setInt(12, chr.getCid());
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("GeneBean#ejbCreate: Unable to create Gene. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    public void ejbPostCreate(int gaid, java.lang.String name, String comm, String mgiid, String genesymbol, String geneexpress, String idgene, String idensembl, ChromosomeRemote chromosome, ProjectRemote project, MugenCaller caller) throws javax.ejb.CreateException {}
    
    //</editor-fold>
    
    
    //web services methods
    //<editor-fold defaultstate="collapsed">
    
    
    //</editor-fold>

}
