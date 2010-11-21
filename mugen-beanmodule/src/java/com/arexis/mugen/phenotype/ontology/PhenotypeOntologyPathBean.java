package com.arexis.mugen.phenotype.ontology;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.pathType;
import java.util.Iterator;
import javax.ejb.*;
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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;

import com.arexis.mugen.ontologies.xp.XpDTO;

public class PhenotypeOntologyPathBean extends AbstractMugenBean implements EntityBean, PhenotypeOntologyPathRemoteBusiness {
    private EntityContext context;
    
    private int ppid;
    private String path, pathtrans;
    
    private boolean dirty;
    private UserRemoteHome userHome;
    private ExpModelRemoteHome modelHome;
    private ProjectRemoteHome projectHome;
    private PhenotypeOntologyRemoteHome phenoHome;
    private PhenotypeXrefRemoteHome xrefHome;
    private PhenotypeAltIdRemoteHome altidHome;
    private PhenotypeSynonymRemoteHome synonymHome;
    
    //ejb methods
    // <editor-fold defaultstate="collapsed">
    
    public void setEntityContext(EntityContext aContext) {
        context = aContext;
        userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
        modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
        projectHome = (ProjectRemoteHome)locator.getHome(ServiceLocator.Services.PROJECT);
        phenoHome = (PhenotypeOntologyRemoteHome)locator.getHome(ServiceLocator.Services.PHENOTYPE_ONTOLOGY);
        xrefHome = (PhenotypeXrefRemoteHome)locator.getHome(ServiceLocator.Services.PHENOTYPE_ONTOLOGY_XREF);
        altidHome = (PhenotypeAltIdRemoteHome)locator.getHome(ServiceLocator.Services.PHENOTYPE_ONTOLOGY_ALT_ID);
        synonymHome = (PhenotypeSynonymRemoteHome)locator.getHome(ServiceLocator.Services.PHENOTYPE_ONTOLOGY_SYNONYM);
    }
    
    public void ejbActivate() {}
    
    public void ejbPassivate() {}
    
    public void ejbRemove() {
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from pheno_paths where ppid=?");
            ps.setInt(1, ppid);
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("PhenotypeOntologyPathBean#ejbRemove: Unable to delete Phenotype Ontology Path.\n"+e.getMessage());
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
            ps = conn.prepareStatement("select ppid, path from pheno_paths where ppid=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ppid = rs.getInt("ppid");
                path = rs.getString("path");
                dirty = false;
            } else
                throw new EJBException("PhenotypeOntologyPathBean#ejbLoad: Error loading Phenotype Ontology Path");
        } catch (Exception e) {
            throw new EJBException("PhenotypeOntologyPathBean#ejbLoad: Error loading Phenotype Ontology Path.\n"+e.getMessage());
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
                ps = conn.prepareStatement("update pheno_paths set path=? where ppid=?");

                ps.setString(1, path);
                ps.setInt(2, ppid);
                ps.execute();
            } catch (Exception e) {
                throw new EJBException("PhenotypeOntologyPathBean#ejbStore: error storing Phenotype Ontology Path. \n"+e.getMessage());
            } finally {
                releaseConnection();
                dirty = false;
            }
        }
    }
    
    // </editor-fold>
    
    //finder methods
    // <editor-fold defaultstate="collapsed">
    
    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select ppid from pheno_paths where ppid = ?");
            ps.setInt(1,key.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("PhenotypeOntologyPathBean#ejbFindByPrimaryKey: Cannot find Phenotype Ontology Path. No next in resultset");
            }
        } catch (SQLException se) {
            throw new FinderException("PhenotypeOntologyPathBean#ejbFindByPrimaryKey: Cannot find Phenotype Ontology Path. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return key;
    }
    
    public java.util.Collection ejbFindByModel(int eid) throws javax.ejb.FinderException {
        makeConnection();
        Collection paths = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select p.ppid from pheno_paths p, pheno_model_r_ r where r.mp = p.path and eid =  ? ");
            ps.setInt(1, eid);
            result = ps.executeQuery();
            
            while(result.next()) {
                paths.add(new Integer(result.getInt("ppid")));
            }
        } catch (SQLException se) {
            throw new FinderException("PhenotypeOntologyPathBean#ejbFindByModel: Cannot find Phenotype Ontology Paths by Model.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return paths;
    }
    
    public java.util.Collection ejbFindByRoot(String root) throws javax.ejb.FinderException {
        makeConnection();
        root = "1>"+root+">%";
        Collection paths = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select ppid from pheno_paths where path like ? ");
            ps.setString(1, root);
            result = ps.executeQuery();
            
            while(result.next()) {
                paths.add(new Integer(result.getInt("ppid")));
            }
        } catch (SQLException se) {
            throw new FinderException("PhenotypeOntologyPathBean#ejbFindByRoot: Cannot find Phenotype Ontology Paths by Root.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return paths;
    }
    
    public java.util.Collection ejbFindByEndNode(String endnode) throws javax.ejb.FinderException {
        makeConnection();
        endnode = "%>"+endnode;
        Collection paths = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select ppid from pheno_paths where path like ? ");
            ps.setString(1, endnode);
            result = ps.executeQuery();
            
            while(result.next()) {
                paths.add(new Integer(result.getInt("ppid")));
            }
        } catch (SQLException se) {
            throw new FinderException("PhenotypeOntologyPathBean#ejbFindByRoot: Cannot find Phenotype Ontology Paths by Root.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return paths;
    }
    
    public java.util.Collection ejbFindByAllele(int allid) throws javax.ejb.FinderException {
        makeConnection();
        Collection paths = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select ppid from pheno_paths where path in (select mp from pheno_model_r_ where eid in (select eid from model where strain in (select strainid from strain_allele where id = ?)))");
            ps.setInt(1, allid);
            result = ps.executeQuery();
            
            while(result.next()) {
                paths.add(new Integer(result.getInt("ppid")));
            }
        } catch (SQLException se) {
            throw new FinderException("PhenotypeOntologyPathBean#ejbFindByAllele: Cannot find Phenotype Ontology Paths by allele.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return paths;
    }
    
    public java.util.Collection ejbFindByGene(int gaid) throws javax.ejb.FinderException {
        makeConnection();
        Collection paths = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select ppid from pheno_paths where path in (select mp from pheno_model_r_ where eid in (select eid from r_gene_model where gaid = ? ))");
            ps.setInt(1, gaid);
            result = ps.executeQuery();
            
            while(result.next()) {
                paths.add(new Integer(result.getInt("ppid")));
            }
        } catch (SQLException se) {
            throw new FinderException("PhenotypeOntologyPathBean#ejbFindByAllele: Cannot find Phenotype Ontology Paths by allele.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return paths;
    }
    
    //</editor-fold>
    
    //create+postcreate methods
    // <editor-fold defaultstate="collapsed">
    public Integer ejbCreate(int ppid, String path) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        try {
            this.ppid = ppid;
            this.path = path;
            
            pk = new Integer(ppid);
            
            PreparedStatement ps = conn.prepareStatement("insert into pheno_paths (ppid, path) values (?,?)");
            ps.setInt(1, ppid);
            ps.setString(2, path);
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("PhenotypeOntologyPathBean#ejbCreate: Unable to create Phenotype Ontology Path. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    public void ejbPostCreate(int ppid, String path) throws javax.ejb.CreateException {}
    //</editor-fold>
    
    //setter+getter methods
    // <editor-fold defaultstate="collapsed">
    public int getPpid(){
        return ppid;
    }

    public String getPath(){
        return path;
    }

    public void setPath(String path){
        this.path = path;
        dirty = true;
    }
    
    public String getPathtrans(){
        pathtrans = "";
        String [] tmp = path.split(">");
        
        try
        {
            for(int i=0; i < tmp.length; i++){
                PhenotypeOntologyRemote pheno = phenoHome.findByPrimaryKey(new Integer(tmp[i]));
                if(i==0)
                    pathtrans += pheno.getName();
                else
                    pathtrans += ">"+pheno.getName();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return pathtrans;
    }
    
    public String getPathtreenode(){
        String treenode = "";
        String [] tmp = path.split(">");
        
        try
        {
            for(int i=1; i < tmp.length-1; i++){
                if(i==tmp.length-2){
                    treenode += "+&nbsp;&nbsp;";
                } else {
                    treenode += "|&nbsp;&nbsp;";
                }
            }
            
            PhenotypeOntologyRemote pheno = phenoHome.findByPrimaryKey(new Integer(tmp[tmp.length-1]));
            treenode += pheno.getName();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return treenode;
    }
    
    public String getPathtreenodePLD(){
        String pld = "";
        String [] tmp = path.split(">");
        XpDTO xp = null;
        
        try
        {
            PhenotypeOntologyRemote pheno = phenoHome.findByPrimaryKey(new Integer(tmp[tmp.length-1]));
            Collection xps = pheno.getPLD();
            Iterator i = xps.iterator();
            while(i.hasNext()) {
                xp = (XpDTO)i.next();
                pld += " "+xp.getRelation()+" <a class=\"menu\" title=\""+xp.getSch()+":"+xp.getSchid()+"\">"+xp.getName()+"</a> ";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return pld;
    }
    
    public String getPathtreenodeFullPLD(){
        String pld = "";
        String [] tmp = path.split(">");
        XpDTO xp = null;
        
        try
        {
            PhenotypeOntologyRemote pheno = phenoHome.findByPrimaryKey(new Integer(tmp[tmp.length-1]));
            Collection xps = pheno.getPLD();
            Iterator i = xps.iterator();
            while(i.hasNext()) {
                xp = (XpDTO)i.next();
                pld += " "+xp.getRelation()+" ["+xp.getSch()+":"+xp.getSchid()+"] <b>"+xp.getName()+"</b> ";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return pld;
    }
    //</editor-fold>
}
