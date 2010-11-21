package com.arexis.mugen.phenotype.ontology;

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

import com.arexis.mugen.model.modelmanager.PhenoAssignDTO;
import com.arexis.mugen.ontologies.xp.XpDTO;

public class PhenotypeOntologyBean extends AbstractMugenBean implements EntityBean, PhenotypeOntologyRemoteBusiness {
    private EntityContext context;
    
    private int id, is_obsolete;
    private String name, def, def_ref, comm;
    
    private boolean dirty;
    private UserRemoteHome userHome;
    private ExpModelRemoteHome modelHome;
    private ProjectRemoteHome projectHome;
    private PhenotypeXrefRemoteHome xrefHome;
    private PhenotypeAltIdRemoteHome altidHome;
    private PhenotypeSynonymRemoteHome synonymHome;
    
    //ejb infrastructure methods
    // <editor-fold defaultstate="collapsed">
    public void setEntityContext(EntityContext aContext) {
        context = aContext;
        userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
        modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
        projectHome = (ProjectRemoteHome)locator.getHome(ServiceLocator.Services.PROJECT);
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
            ps = conn.prepareStatement("delete from pheno_ontology where id=?");
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("PhenotypeOntologyBean#ejbRemove: Unable to delete Phenotype Ontology.\n"+e.getMessage());
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
            ps = conn.prepareStatement("select id, name, def, def_ref, is_obsolete, comm " +
                    "from pheno_ontology where id=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
                name = rs.getString("name");
                def = rs.getString("def");
                def_ref = rs.getString("def_ref");
                is_obsolete = rs.getInt("is_obsolete");
                comm = rs.getString("comm");
                dirty = false;
            } else
                throw new EJBException("PhenotypeOntologyBean#ejbLoad: Error loading Phenotype Ontology");
        } catch (Exception e) {
            throw new EJBException("PhenotypeOntologyBean#ejbLoad: error loading Phenotype Ontology. \n"+e.getMessage());
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
                ps = conn.prepareStatement("update pheno_ontology set name=? , def=?, def_ref=?, is_obsolete=?, comm=?" +
                        "where id=?");

                ps.setString(1, name);
                ps.setString(2, def);
                ps.setString(3, def_ref);
                ps.setInt(4, is_obsolete);
                ps.setString(5, comm);
                ps.setInt(6, id);
                ps.execute();
            } catch (Exception e) {
                throw new EJBException("PhenotypeOntologyBean#ejbStore: error storing Phenotype Ontology. \n"+e.getMessage());
            } finally {
                releaseConnection();
                dirty = false;
            }
        }
    }
    
    // </editor-fold>
    
    //finder methods
    //<editor-fold defaultstate="collapsed">
    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select id from pheno_ontology where id = ?");
            ps.setInt(1,key.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("PhenotypeOntologyBean#ejbFindByPrimaryKey: Cannot find Phenotype Ontology. No next in resultset");
            }
        } catch (SQLException se) {
            throw new FinderException("PhenotypeOntologyBean#ejbFindByPrimaryKey: Cannot find Phenotype Ontology. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return key;
    }
    
    public java.util.Collection ejbFindByKeyword(Keyword keyword, MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        Collection arr = new ArrayList();        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select id from pheno_ontology where lower(name) like ? "+
                    "or lower(def) like ? or lower(def_ref) like ? or lower(comm) like ? "+
                    "or id in (select pheno_id from pheno_synonym_r where pheno_synonym in (select id from pheno_synonym where lower(synonym) like ?)) "+
                    "or id in (select pheno_id from pheno_xref_r where xref_id in (select id from pheno_xref where lower(xref) like ?))");
            
            String search = "%"+keyword.getKeyword()+"%";
            
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            ps.setString(4, search);
            ps.setString(5, search);
            ps.setString(6, search);
            
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("id")));
            }
            
        } catch (SQLException se) {
            throw new FinderException("PhenotypeOntologyBean#ejbFindByKeyword: Cannot find mp term by keyword\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    public Collection ejbFindByLevelOne() throws FinderException{
        makeConnection();
        Collection mps = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select id from pheno_ontology where id in (select id_b from pheno_is_a where id_a=1) order by name");
            result = ps.executeQuery();
            
            while(result.next()) {
                mps.add(new Integer(result.getInt("id")));
            }
            
        } catch (SQLException se) {
            throw new FinderException("PhenotypeOntologyBean#ejbFindByLevelOne: Cannot find level one mp terms\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return mps;
    }
    
    public Collection ejbFindByLowerLevel(int root_pheno_id) throws FinderException{
        makeConnection();
        Collection mps = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select id from pheno_ontology where id in (select id_b from pheno_is_a where id_a=?) order by name");
            ps.setInt(1,root_pheno_id);
            result = ps.executeQuery();
            
            while(result.next()) {
                mps.add(new Integer(result.getInt("id")));
            }
            
        } catch (SQLException se) {
            throw new FinderException("PhenotypeOntologyBean#ejbFindByLowerLevel: Cannot find mp terms by lower level\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return mps;
    }
    
    public Collection ejbFindByIsA(int pheno_id) throws FinderException{
        makeConnection();
        Collection mps = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select id from pheno_ontology where id in (select id_a from pheno_is_a where id_b=?) order by name");
            ps.setInt(1,pheno_id);
            result = ps.executeQuery();
            
            while(result.next()) {
                mps.add(new Integer(result.getInt("id")));
            }
            
        } catch (SQLException se) {
            throw new FinderException("PhenotypeOntologyBean#ejbFindByIsA: Cannot find is a(s) \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return mps;
    }
    
    //</editor-fold>
    
    //create+postcreate methods
    //<editor-fold>
    public Integer ejbCreate(int id, String name, String def, String def_ref, int is_obsolete) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        try {
            this.id = id;
            this.name = name;
            this.def = def;
            this.def_ref = def_ref;
            this.is_obsolete = is_obsolete;
            
            pk = new Integer(id);
            
            PreparedStatement ps = conn.prepareStatement("insert into pheno_ontology (id, name, def, def_ref, is_obsolete) values (?,?,?,?,?)");
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, def);
            ps.setString(4, def_ref);
            ps.setInt(5, is_obsolete);
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("PhenotypeOntologyBean#ejbCreate: Unable to create Phenotype Ontology. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    public void ejbPostCreate(int id, String name, String def, String def_ref, int is_obsolete) throws javax.ejb.CreateException {}
    
    //</editor-fold>
    
    //setter+getter methods
    //<editor-fold>
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
        dirty = true;
    }
    
    public String getDef(){
        return def;
    }
    
    public void setRef(String def){
        this.def = def;
        dirty = true;
    }
    
    public String getDefRef(){
        return def_ref;
    }
    
    public void setDefRef(String def_ref){
        this.def_ref = def_ref;
        dirty = true;
    }
    
    public int getIsObsolete(){
        return is_obsolete;
    }
    
    public void setIsObsolete(int is_obsolete){
        this.is_obsolete = is_obsolete;
        dirty = true;
    }
    
    public String getComm(){
        return comm;
    }
    
    public void setComm(String comm){
        this.comm = comm;
        dirty = true;
    }
    //</editor-fold>
    
    //other methods
    //<editor-fold>
    
    public void addXref(PhenotypeXrefRemote xref) throws ApplicationException{
        try {
            makeConnection();
            PreparedStatement ps = conn.prepareStatement("insert into pheno_xref_r (pheno_id, xref_id) values (?,?)");
            ps.setInt(1, id);
            ps.setInt(2, xref.getId());
            
            ps.execute();
        } catch (Exception e) {
            throw new ApplicationException("PhenotypeOntologyBean#addXref: Failed to add xref to this ontology term",e);
        } finally {
            releaseConnection();
        }
    }
    
    public void addAltId(PhenotypeAltIdRemote altid) throws ApplicationException{
        try {
            makeConnection();
            PreparedStatement ps = conn.prepareStatement("insert into pheno_alt_id_r (pheno_id, pheno_alt_id) values (?,?)");
            ps.setInt(1, id);
            ps.setInt(2, altid.getId());
            
            ps.execute();
        } catch (Exception e) {
            throw new ApplicationException("PhenotypeOntologyBean#addAltId: Failed to add alt_id to this ontology term",e);
        } finally {
            releaseConnection();
        }
    }
    
    public void addSynonym(PhenotypeSynonymRemote synonym, String attribute) throws ApplicationException{
        try {
            makeConnection();
            PreparedStatement ps = conn.prepareStatement("insert into pheno_synonym_r (pheno_id, pheno_synonym, attribute) values (?,?,?)");
            ps.setInt(1, id);
            ps.setInt(2, synonym.getId());
            ps.setString(3, attribute);
            
            ps.execute();
        } catch (Exception e) {
            throw new ApplicationException("PhenotypeOntologyBean#addSynonym: Failed to add synonym to this ontology term",e);
        } finally {
            releaseConnection();
        }
    }
    
    public void addIsA(int is_a) throws ApplicationException{
        try {
            makeConnection();
            PreparedStatement ps = conn.prepareStatement("insert into pheno_is_a (id_a, id_b) values (?,?)");
            ps.setInt(1, is_a);
            ps.setInt(2, id);
            
            ps.execute();
        } catch (Exception e) {
            throw new ApplicationException("PhenotypeOntologyBean#addIsA: Failed to add is_a relationship to this ontology term",e);
        } finally {
            releaseConnection();
        }
    }
    
    //</editor-fold>
    
    //pato logical definition (PLD) methods
    //<editor-fold defaultstate="collapsed">
    public Collection getPLD() throws ApplicationException {
        makeConnection();
        try {
            int xpid, apporder, schid;
            String sch, relation, name;
            
            String query;
            
            Collection xps = new ArrayList();
            XpDTO xp = null;
            
            PreparedStatement ps = conn.prepareStatement("select r.xpid, r.apporder, t.sch, t.schid, t.relation from xp.xp_mp_r r, xp.xp_term t where r.xpid = t.xpid and r.mpid= ? order by r.apporder");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            
            while(result.next()) {
                xpid = result.getInt("xpid");
                apporder = result.getInt("apporder");
                sch = result.getString("sch");
                schid = result.getInt("schid");
                relation = result.getString("relation");
                
                xp = new XpDTO(xpid, apporder, sch, schid, relation);
                
                if(sch.toLowerCase().equals("mp")){
                    query = "select name from pheno_ontology where id = "+schid;
                }else{
                    query = "select name from "+sch.toLowerCase()+"."+sch.toLowerCase()+"_term where id = "+schid;
                }
                
                ps = conn.prepareStatement(query);
                ResultSet res = ps.executeQuery();
                
                if(res.next())
                    xp.setName(res.getString("name"));
                
                xps.add(xp);
            }
                //num = result.getInt("num");
            
            return xps;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("PhenotypeOntologyBean#getPLD Unable to fetch PATO Logical Definition (PLD).\n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public String getStringFullPLD(){
        String pld = "";
        XpDTO xp = null;
        
        try
        {
            Collection xps = getPLD();
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
